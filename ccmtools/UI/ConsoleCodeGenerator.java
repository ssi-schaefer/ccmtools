/* CCM Tools : User Interface Library
 * Leif Johnson <leif@ambient.2y.net>
 * Copyright (C) 2002, 2003 Salomon Automation
 *
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

package ccmtools.UI;

import ccmtools.Constants;
import ccmtools.Metamodel.BaseIDL.MContainer;
import ccmtools.IDL3Parser.ParserManager;
import ccmtools.CodeGenerator.CodeGenerator;
import ccmtools.CodeGenerator.Driver;
import ccmtools.CodeGenerator.GraphTraverser;
import ccmtools.CodeGenerator.CCMMOFGraphTraverserImpl;
import ccmtools.CodeGenerator.Template;
import ccmtools.CodeGenerator.TemplateHandler;
import ccmtools.CppGenerator.CppLocalGeneratorImpl;
import ccmtools.CppGenerator.CppMirrorGeneratorImpl;
import ccmtools.CppGenerator.CppRemoteGeneratorImpl;
import ccmtools.CppGenerator.CppRemoteTestGeneratorImpl;
import ccmtools.CppGenerator.CppPythonGeneratorImpl;
import ccmtools.IDLGenerator.IDL2GeneratorImpl;
import ccmtools.IDLGenerator.IDL3GeneratorImpl;
import ccmtools.IDLGenerator.IDL3MirrorGeneratorImpl;

import java.io.*;
import java.util.*;

public class ConsoleCodeGenerator
{
    private static final int GENERATE_ENVIRONMENT_FILES = 0x0001;
    private static final int GENERATE_APPLICATION_FILES = 0x0002;

    private static final String version = Constants.VERSION;

    private static final String usage =
"Usage: ccmtools-generate LANGUAGE [OPTIONS]... FILES...\n" +
"Options:\n" +
"  -a, --application             Generate skeletons for business logic *\n" +
"  -DFOO[=BAR]                   Define FOO (as BAR) for environment files\n" +
"  -e, --environment             Generate environment files *\n" +
"  -h, --help                    Display this help\n" +
"  -I DIR                        Add DIR to the preprocessor include path\n" +
"  -o DIR, --output=DIR          Base output in DIR (default to .)\n" +
"  -V, --version                 Display current ccmtools version\n" +
"      --generator-mask=<flags>  Mask for generator debug output\n" +
"      --parser-mask=<flags>     Mask for parser debug output\n" +
"Languages available:\n" +
"    LANGUAGES\n" +
"Generates code in the given output language after parsing FILES.\n" +
"Options marked with a star (*) are generally used once per project.\n";

    private static final String[] local_language_types =
    {
        "c++local", "c++mirror",
        "c++remote", "c++remote-test",
	"c++python",
        "idl3", "idl3mirror", "idl2"
    };

    private static List language_types = null;

    private static List languages = new ArrayList();

    private static long gen_mask = 0x00000040;
    private static long par_mask = 0x00000000;

    private static String include_path = "";

    private static Map  defines = new Hashtable();
    private static List filenames = new ArrayList();

    private static File output_directory = new File(System.getProperty("user.dir"));
    private static File base_output_directory = new File(output_directory, "");

    private static int generate_flags = 0;


    /**************************************************************************/
    /* USAGE / VERSION INFO */

    private static void printUsage(String err)
    {
        if (err.length() > 0)
            System.err.println("Error: " + err);

        StringBuffer langs = new StringBuffer();
        for (int i = 0; i < language_types.size(); i++)
            langs.append((String) language_types.get(i) + " ");

        System.out.print(usage.replaceAll("LANGUAGES", langs.toString()));

        if (err.length() > 0) System.exit(1);
        else                  System.exit(0);
    }

    private static void printVersion()
    {
        System.out.println("ccmtools version " + version);
        System.out.println("Copyright (C) 2002, 2003 Salomon Automation");
        System.out.println("The CCM Tools library is distributed under the");
        System.out.println("terms of the GNU Lesser General Public License.");
        System.exit(0);
    }

    /**************************************************************************/
    /* GENERATION */

    /**
     * Generate "environment files" for the target language. Environment files
     * are those files that generally only need to be generated once for an
     * entire project ; they might be global includes (as in C or C++) or static
     * utility modules (Java or Python).
     *
     * @param handler an TemplateHandler object that we will use to request the
     *        names and retrieve template contents for environment files.
     */
    private static void generateEnvironment(TemplateHandler handler,
                                            Environment env)
    {
        Map env_files = handler.getEnvironmentFiles();

        if (env_files == null) return;

        for (Iterator f = env_files.keySet().iterator(); f.hasNext(); ) {
            File location = (File) f.next();
            File file = new File(output_directory, location.toString());
            File parent = file.getParentFile();
            String template_name = (String) env_files.get(location);

            Template template =
                handler.getTemplateManager().getRawTemplate(template_name);

            String template_str =
                template.substituteVariables(env.getParameters());

            if (! parent.isDirectory()) parent.mkdirs();

            if (gen_mask > 0)
                System.out.println("generating environment file "+file);

            try {
                FileWriter writer = new FileWriter(file);
                writer.write(template_str, 0, template_str.length());
                writer.close();
            } catch (IOException e) {
                System.err.println("Error generating environment file " +
                                   file + ". Skipping.\n(Error: " + e + ")");
            }
        }
    }

    /**************************************************************************/
    /* SETUP FUNCTIONS */

    /**
     * Try to create a driver for the code generator. This will handle output
     * messages and could possibly react to input from the user as well.
     *
     * @return a newly created driver, or exit if there was an error.
     */
    private static Driver setupDriver()
    {
        Driver driver = null;

        try {
            driver = new ConsoleDriverImpl(gen_mask);
        } catch (FileNotFoundException e) {
            printUsage("constructing the driver object\n"+e);
        }

        if (driver == null)
            printUsage("failed to create a driver object");

        return driver;
    }

    /**
     * Set up the node handler (i.e. code generator) object based on the output
     * language provided. Use the given driver to control the handler.
     *
     * @param driver the user interface driver object to assign to this handler.
     * @param lang the language to generate.
     * @return the newly created node handler (i.e. code generator), or exit if
     *         there was an error.
     */
    private static TemplateHandler setupHandler(Driver driver, String lang)
    {
        TemplateHandler handler = null;

        try {
            if (lang.equalsIgnoreCase("C++Local"))
                handler = new CppLocalGeneratorImpl(driver, output_directory);
            else if (lang.equalsIgnoreCase("C++Mirror"))
                handler = new CppMirrorGeneratorImpl(driver, output_directory);
	    else if (lang.equalsIgnoreCase("C++Remote"))
		handler = new CppRemoteGeneratorImpl(driver, output_directory);
	    else if (lang.equalsIgnoreCase("C++Remote-Test"))
		handler = new CppRemoteTestGeneratorImpl(driver, output_directory);
	    else if (lang.equalsIgnoreCase("C++Python"))
                handler = new CppPythonGeneratorImpl(driver, output_directory);
            else if (lang.equalsIgnoreCase("IDL3"))
                handler = new IDL3GeneratorImpl(driver, output_directory);
            else if (lang.equalsIgnoreCase("IDL3Mirror"))
                handler = new IDL3MirrorGeneratorImpl(driver, output_directory);
            else if (lang.equalsIgnoreCase("IDL2"))
                handler = new IDL2GeneratorImpl(driver, output_directory);
        } catch (IOException e) {
            printUsage("while constructing a generator for "+lang+"\n"+e);
        }

        if (handler == null)
            printUsage("failed to create a language generator for "+lang);

        if ((generate_flags & GENERATE_APPLICATION_FILES) != 0)
            handler.setFlag(((CodeGenerator) handler).FLAG_APPLICATION_FILES);

        if ((generate_flags & GENERATE_ENVIRONMENT_FILES) != 0)
            handler.setFlag(((CodeGenerator) handler).FLAG_ENVIRONMENT_FILES);

        return handler;
    }

    /**************************************************************************/
    /* MAIN FUNCTION */

    /**
     * Parse and generate code for each input IDL3 file. For each input file, we
     * need to (0) run the C preprocessor on the file to assemble includes and
     * do ifdef parsing and such, then (1) parse the file, then (2) generate
     * output code. Exits with nonzero status if errors are encountered during
     * parsing or generation.
     */
    public static void main(String args[])
    {
        parseArgs(args);

        GraphTraverser traverser = new CCMMOFGraphTraverserImpl();
        if (traverser == null) printUsage("failed to create a graph traverser");

        ParserManager manager = new ParserManager(par_mask);
        if (manager == null) printUsage("failed to create a parser manager");

        Driver driver = setupDriver();
        ArrayList handlers = new ArrayList();

        for (Iterator l = languages.iterator(); l.hasNext(); ) {
            TemplateHandler handler = setupHandler(driver, (String) l.next());
            handlers.add(handler);
            traverser.addHandler(handler);
        }

        Runtime rt = Runtime.getRuntime();

        MContainer kopf = null;

        for (Iterator f = filenames.iterator(); f.hasNext(); ) {
            File source = new File((String) f.next());
            File idlfile = new File(System.getProperty("user.dir"),
                                     "_CCM_" + source.getName());

            // step (0). run the C preprocessor on the input file.
            try {
		// Run the GNU preprocessor cpp in a separate process.
                Process preproc = Runtime.getRuntime().exec("cpp -o " +
                                          idlfile + " " + include_path +
                                          " " + source);

		BufferedReader stdInput = new BufferedReader(new
		    InputStreamReader(preproc.getInputStream()));
		BufferedReader stdError = new BufferedReader(new
		    InputStreamReader(preproc.getErrorStream()));

		// Read the output and any errors from the command
                String s;
		while ((s = stdInput.readLine()) != null) System.out.println(s);
		while ((s = stdError.readLine()) != null) System.out.println(s);

		// Wait for the process to complete and evaluate the return
                // value of the attempted command
                preproc.waitFor();
                if (preproc.exitValue() != 0) throw new RuntimeException();
            } catch (Exception e) {
		System.err.println("Error preprocessing " + source +
                                   ": Please verify your include paths.");
                System.exit(10);
            }

	    // step (1). parse the resulting preprocessed file.

            manager.reset();
            manager.setOriginalFile(source.toString());

            try {
                kopf = manager.parseFile(idlfile.toString());
                if (kopf == null)
                    throw new RuntimeException(
                        "Parser returned a null container");
            } catch (Exception e) {
                System.err.println("Error parsing "+source+":\n"+e);
                System.exit(20);
            }

            String kopf_name = source.getName().split("\\.")[0];
            kopf_name = kopf_name.replaceAll("[^\\w]", "_");
            kopf.setIdentifier(kopf_name);

            // step (2). traverse the resulting metamodel graph.

            try {
                traverser.traverseGraph(kopf);
            } catch (Exception e) {
                System.err.println(
                    "Error generating code from "+source+":\n"+e);
                System.exit(30);
            }

            // delete the preprocessed temporary file if everything worked.

            idlfile.deleteOnExit();
        }

        Environment env = new ConsoleEnvironmentImpl(defines);

        for (Iterator h = handlers.iterator(); h.hasNext(); ) {
            TemplateHandler handler = (TemplateHandler) h.next();
            generateEnvironment(handler, env);
            handler.finalize(env.getParameters(), filenames);
        }

        System.exit(0);
    }

    /**************************************************************************/
    /* ARGUMENT PARSING */

    /**
     * Parse the command line arguments to the console code generator front end.
     * We might want to replace this in the future with a more flexible
     * interface (like getopt, if it exists in java), but for now the number of
     * options is fairly manageable.
     *
     * A side effect of this function (actually the point of the function) is
     * that it changes internal class instance values, if switches for them are
     * provided on the command line. If errors are encountered then warning
     * messages might be printed out, and if at the end of processing no valid
     * language has been found, the program will exit.
     *
     * @param args the arguments passed on the command line.
     */
    private static void parseArgs(String args[])
    {
        language_types = new ArrayList();
        for (int i = 0; i < local_language_types.length; i++)
            language_types.add(local_language_types[i]);

        List argv = new ArrayList();
        for (int i = 0; i < args.length; i++) argv.add(args[i]);

        if (argv.contains("-h") || argv.contains("--help"))
            printUsage("");

        else if (argv.contains("-V") || argv.contains("--version"))
            printVersion();

        for (Iterator a = argv.iterator(); a.hasNext(); ) {
            String arg = (String) a.next();
            if (arg.equals("")) continue;
            else if (arg.startsWith("--generator-mask="))
                setGeneratorMask(arg.split("=")[1]);
            else if (arg.startsWith("--parser-mask="))
                setParserMask(arg.split("=")[1]);
            else if (arg.startsWith("--output="))
                setOutputDirectory(arg.split("=")[1]);
            else if (arg.startsWith("--env"))
                generate_flags |= GENERATE_ENVIRONMENT_FILES;
            else if (arg.startsWith("--app"))
                generate_flags |= GENERATE_APPLICATION_FILES;
            else if (arg.charAt(0) == '-')
                do {
                    if (arg.charAt(0) == 'e')
                        generate_flags |= GENERATE_ENVIRONMENT_FILES;
                    else if (arg.charAt(0) == 'a')
                        generate_flags |= GENERATE_APPLICATION_FILES;
                    else if (arg.charAt(0) == 'D') {
                        addDefine(arg.substring(1));
                        break;
                    } else if (arg.charAt(0) == 'o') {
                        if (a.hasNext())
                            setOutputDirectory((String) a.next());
                        else
                            printUsage("unspecified output directory");
                        break;
                    } else if (arg.charAt(0) == 'I') {
                        File path = new File(arg.substring(1));
                        if (path.isDirectory()) include_path += " -I"+path;
                        break;
                    }
                    arg = arg.substring(1);
                } while (arg.length() > 0);
            else if (language_types.contains(arg.toLowerCase()) &&
                     ! languages.contains(arg))
                languages.add(arg);
            else
                filenames.add(arg);
        }

        if (languages.size() == 0)
            printUsage("no valid output language specified");

        if (include_path.trim().equals(""))
            include_path = " -I"+System.getProperty("user.dir");
    }

    private static void setGeneratorMask(String val)
    {
        try {
            if (val.startsWith("0x"))
                gen_mask = Long.parseLong(val.substring(2), 16);
            else if (val.startsWith("0"))
                gen_mask = Long.parseLong(val.substring(1), 8);
            else
                gen_mask = Long.parseLong(val, 10);
        } catch (NumberFormatException e) {
            System.err.println("Could not convert " + val +
                               " to a generator mask. Ignoring.");
        }
    }

    private static void setParserMask(String val)
    {
        try {
            if (val.startsWith("0x"))
                par_mask = Long.parseLong(val.substring(2), 16);
            else if (val.startsWith("0"))
                par_mask = Long.parseLong(val.substring(1), 8);
            else
                par_mask = Long.parseLong(val, 10);
        } catch (NumberFormatException e) {
            System.err.println("Could not convert " + val +
                               " to a parser mask. Ignoring.");
        }
    }

    private static void setOutputDirectory(String val)
    {
        File test_directory = new File(val);
        if (test_directory.isAbsolute())
            output_directory = test_directory;
        else
            output_directory = new File(base_output_directory, val);
    }

    private static void addDefine(String def)
    {
        String key = def;
        String value = "";
        if (def.indexOf('=') > -1) {
            String[] parts = def.split("=");
            key = parts[0];
            value = parts[1];
        }

        // trap the CCMTOOLS_HOME environment variable here.

        if (key.equals("CCMTOOLS_HOME")) {
            Properties props = System.getProperties();
            props.setProperty(key, value.substring(1, value.length() - 1));
            System.setProperties(props);
        } else {
            defines.put(key, value);
        }
    }
}

