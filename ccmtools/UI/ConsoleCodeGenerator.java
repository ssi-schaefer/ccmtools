/* CCM Tools : User Interface Library
 * Leif Johnson <leif@ambient.2y.net>
 * copyright (c) 2002, 2003 Salomon Automation
 *
 * $Id$
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
import ccmtools.CodeGenerator.GraphTraverserImpl;
import ccmtools.CodeGenerator.Template;
import ccmtools.CodeGenerator.TemplateHandler;
import ccmtools.CppGenerator.CppLocalGeneratorImpl;
import ccmtools.CppGenerator.CppMirrorGeneratorImpl;
import ccmtools.CppGenerator.CppPythonGeneratorImpl;
import ccmtools.IDLGenerator.IDL2GeneratorImpl;
import ccmtools.IDLGenerator.IDL3GeneratorImpl;
import ccmtools.IDLGenerator.IDL3MirrorGeneratorImpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class ConsoleCodeGenerator
{
    private final static int GENERATE_ENVIRONMENT_FILES = 0x0001;
    private final static int GENERATE_APPLICATION_FILES = 0x0002;
    private final static int GENERATE_USER_TYPES_FILES  = 0x0004;

    private final static String version = Constants.VERSION;

    private final static String usage =
"Usage: ccmtools-generate LANGUAGE [OPTIONS]... FILES...\n" +
"Options:\n" +
"  -a, --application             Generate skeletons for business logic *\n" +
"  -DFOO[=BAR]                   Define FOO (as BAR) for environment files\n" +
"  -e, --environment             Generate environment files *\n" +
"  -h, --help                    Display this help\n" +
"  -I DIR                        Add DIR to the include IDL search path\n" +
"  -o DIR, --output=DIR          Base output in DIR (default to .)\n" +
"  -V, --version                 Display current ccmtools version\n" +
"  -u, --no-user-types           Disable generating user types files\n" +
"      --generator-mask=<flags>  Mask for generator debug output\n" +
"      --parser-mask=<flags>     Mask for parser debug output\n" +
"Languages available (specify one):\n" +
"    LANGUAGES\n" +
"Generates code in the given output language after parsing FILES.\n" +
"Options marked with a star (*) are generally used once per project.\n";

    private final static String[] local_language_types =
    {
        "c++local", "c++mirror", "c++python", "idl3", "idl3mirror", "idl2"
    };

    private static List language_types = null;

    private static String lang = null;

    private static long gen_mask = 0x00000040;
    private static long par_mask = 0x00000000;

    private static Map  defines = new Hashtable();
    private static List include_path = new ArrayList();
    private static List filenames = new ArrayList();

    private static File output_directory = new File(System.getProperty("user.dir"));
    private static File base_output_directory = new File(output_directory, "");

    private static int generate_flags = GENERATE_USER_TYPES_FILES;

    /**************************************************************************/

    /**
     * Print out usage information for the console code generator front end, and
     * exit. This should normally be accessed by using the '--help' switch from
     * the command line, in which case the function exits with a success (0)
     * exit code. If an error string is provided, print out the type of error
     * and provide the usage information for help, but exit with a failure (1)
     * error code.
     *
     * @param err a string to print out indicating the type of error
     *        encountered.
     */
    private static void printUsage(String err)
    {
        if (err.length() > 0)
            System.err.println("Error: " + err);

        StringBuffer languages = new StringBuffer();
        for (int i = 0; i < language_types.size(); i++)
            languages.append((String) language_types.get(i) + " ");

        System.out.print(usage.replaceAll("LANGUAGES", languages.toString()));

        if (err.length() > 0) System.exit(1);
        else                  System.exit(0);
    }

    /**
     * Print out the version information for the console code generator front
     * end, and exit.
     */
    private static void printVersion()
    {
        System.out.println("ccmtools version " + version);
        System.exit(0);
    }

    /**************************************************************************/

    /**
     * Parse and generate code for the given input IDL3 file. For each input
     * file, we need to (1) parse the file, then (2) generate output code. Exits
     * if errors are encountered during parsing or generation.
     *
     * @param filename the string filename of the file we want to read.
     */
    private static void parseAndGenerate(ParserManager manager,
                                         GraphTraverser traverser,
                                         String filename)
    {
        MContainer kopf = null;

        // step (1).

        manager.createParser(filename);
        try {
            kopf = manager.parseFile();
        } catch (Exception e) {
            System.err.println("Error parsing "+filename+":\n"+e);
            System.exit(1);
        }

        // (in between)

        if (kopf == null) return;

        File top_file = new File(filename);
        String top_name = top_file.getName().toString();
        // (cut off the ".blah" from the end of the filename)
        kopf.setIdentifier(top_name.split("\\.")[0]);
        kopf.setDefinedInOriginalFile(true);

        // step (2).

        try {
            traverser.traverseGraph(kopf);
        } catch (Exception e) {
            System.err.println("Error generating code from "+filename+":\n"+e);
            System.exit(1);
        }
    }

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
     * @return the newly created node handler (i.e. code generator), or exit if
     *         there was an error.
     */
    private static TemplateHandler setupHandler(Driver driver)
    {
        TemplateHandler handler = null;

        try {
            if (lang.equalsIgnoreCase("C++Local"))
                handler = new CppLocalGeneratorImpl(driver, output_directory);
            else if (lang.equalsIgnoreCase("C++Mirror"))
                handler = new CppMirrorGeneratorImpl(driver, output_directory);
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

        if ((generate_flags & GENERATE_USER_TYPES_FILES) != 0)
            handler.setFlag(((CodeGenerator) handler).FLAG_USER_TYPES_FILES);

        return handler;
    }

    /**
     * Build a traverser object that will traverse the parse trees. Set the
     * node handler (code generator) to receive and handle traverser node
     * events.
     *
     * @param handler the node handler object to receive and deal with graph
     *        traversal events.
     * @return the newly created traverser, or exit if there was an error.
     */
    private static GraphTraverser setupTraverser(TemplateHandler handler)
    {
        GraphTraverser traverser = new GraphTraverserImpl();

        if (traverser == null)
            printUsage("failed to create a graph traverser");

        traverser.setHandler(handler);

        return traverser;
    }

    /**
     * Create a parser manager to handle the input files.
     *
     * @return the newly created parser manager, or exit if there was an error.
     */
    private static ParserManager setupParserManager()
    {
        ParserManager manager = new ParserManager(par_mask, include_path);

        if (manager == null)
            printUsage("failed to create a parser manager");

        return manager;
    }

    /**************************************************************************/

    public static void main(String args[])
    {
        parseArgs(args);

        TemplateHandler handler = setupHandler(setupDriver());

        for (Iterator f = filenames.iterator(); f.hasNext(); )
            parseAndGenerate(setupParserManager(),
                             setupTraverser(handler),
                             (String) f.next());

        Environment env = new ConsoleEnvironmentImpl(defines);

        generateEnvironment(handler, env);
        handler.finalize(env.getParameters(), filenames);

        System.exit(0);
    }

    /**************************************************************************/

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
        for (int i = 0; i < args.length; i++)
            argv.add(args[i]);

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
            else if (arg.startsWith("--no-u"))
                generate_flags &= (0xffff ^ GENERATE_USER_TYPES_FILES);
            else if (arg.charAt(0) == '-')
                do {
                    if (arg.charAt(0) == 'e')
                        generate_flags |= GENERATE_ENVIRONMENT_FILES;
                    else if (arg.charAt(0) == 'a')
                        generate_flags |= GENERATE_APPLICATION_FILES;
                    else if (arg.charAt(0) == 'u')
                        generate_flags &= (0xffff ^ GENERATE_USER_TYPES_FILES);
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
                        if (path.isDirectory()) include_path.add(path);
                        break;
                    }
                    arg = arg.substring(1);
                } while (arg.length() > 0);
            else if ((lang == null) && language_types.contains(arg.toLowerCase()))
                lang = new String(arg);
            else
                filenames.add(arg.toString());
        }

        if (lang == null)
            printUsage("no valid output language specified");
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

