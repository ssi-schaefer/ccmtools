/***
 * CCM Tools : User Interface Library Leif Johnson <leif@ambient.2y.net> Egon
 * Teiniker <egon.teiniker@salomon.at> Copyright (C) 2002, 2003 Salomon
 * Automation
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
 ***/

package ccmtools.UI;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import ccmtools.Constants;
import ccmtools.CodeGenerator.CCMMOFGraphTraverserImpl;
import ccmtools.CodeGenerator.CodeGenerator;
import ccmtools.CodeGenerator.Driver;
import ccmtools.CodeGenerator.GraphTraverser;
import ccmtools.CodeGenerator.TemplateHandler;
import ccmtools.CppGenerator.CppLocalDbcGeneratorImpl;
import ccmtools.CppGenerator.CppLocalGeneratorImpl;
import ccmtools.CppGenerator.CppLocalTestGeneratorImpl;
import ccmtools.CppGenerator.CppRemoteGeneratorImpl;
import ccmtools.CppGenerator.CppRemoteTestGeneratorImpl;
import ccmtools.IDL3Parser.ParserManager;
import ccmtools.IDLGenerator.IDL2GeneratorImpl;
import ccmtools.IDLGenerator.IDL3GeneratorImpl;
import ccmtools.IDLGenerator.IDL3MirrorGeneratorImpl;
import ccmtools.Metamodel.BaseIDL.MContainer;

public class Main
{
    private static Logger logger = Logger.getLogger("ccm.main");
    
    private static final int GENERATE_APPLICATION_FILES = 0x0001;

    private static List language_types = null;

    private static List languages = null;

    private static long gen_mask = ConsoleDriverImpl.M_OUTPUT_FILE; //ConsoleDriverImpl.M_MESSAGE;

    private static long par_mask = 0x00000000;

    private static String include_path;

    private static List filenames;

    private static File output_directory = 
        new File(System.getProperty("user.dir"));

    private static File base_output_directory = new File(output_directory, "");

    private static int generate_flags = 0;

    
    /**
     * Parse and generate code for each input IDL3 file. For each input file, we
     * need to (0) run the C preprocessor on the file to assemble includes and
     * do ifdef parsing and such, then (1) parse the file, then (2) generate
     * output code. Exits with nonzero status if errors are encountered during
     * parsing or generation.
     */
    public static void main(String args[])
    {
        logger.fine("start ccmtools");
        
        // Print out the current version of ccmtools
        printVersion();  
        
        try {
            if(parseArgs(args) == false) {
                return; // No further processing needed        
            }
        }
        catch(IllegalArgumentException e) {
            printError(e.getMessage());
            printUsage();
            logger.fine("stop ccmtools after parseArgs(): " + e.getMessage());
            return; // No further processing needed      
        }

        // Set default values for ccmtools.home and ccmtools.template properties 
        // to the current ccmtools directory (if not set from the command line).
        // This setting is used by ccmtools JUnit tests
        if(System.getProperty("ccmtools.home") == null) {
            System.setProperty("ccmtools.home",System.getProperty("user.dir"));
        }
        if(System.getProperty("ccmtools.templates") == null) {
            System.setProperty("ccmtools.templates", 
                           System.getProperty("ccmtools.home") + 
                           File.separator + "src" +
                           File.separator + "templates");
        }
        logger.config("ccmtools.home=" + System.getProperty("ccmtools.home"));
        logger.config("ccmtools.templates=" + System.getProperty("ccmtools.templates"));
        
        try {
            //Driver driver = createDriver();
            Driver driver = new ConsoleDriverImpl(gen_mask);
            
            GraphTraverser traverser = new CCMMOFGraphTraverserImpl();
            if(traverser == null) {
                printError("Failed to create a graph traverser");
                printUsage();
                return; // No further processing needed
            }

            ParserManager manager = new ParserManager(par_mask);
            if(manager == null) {
                printError("Failed to create a parser manager");
                printUsage();
                return; // No further processing needed
            }

            ArrayList handlers = new ArrayList();
            for(Iterator l = languages.iterator(); l.hasNext();) {
                TemplateHandler handler = createTemplateHandler(driver,(String) l.next());
                if(handler == null) {
                    printUsage();
                    return; // No further processing needed
                }
                handlers.add(handler);
                traverser.addHandler(handler);
            }

            Runtime rt = Runtime.getRuntime();

            MContainer kopf = null;

            for(Iterator f = filenames.iterator(); f.hasNext();) {
                File source = new File((String) f.next());

                // create the name of the temporary idl file generated from the preprocessor cpp
                String tmpFile = "_CCM_" + source.getName();
                File idlfile = new File(System.getProperty("user.dir"), 
                                        tmpFile.substring(0, tmpFile.lastIndexOf(".idl")));
                
                // step (0). run the C preprocessor on the input file.
                try {
                    // Run the GNU preprocessor cpp in a separate process.
                    printMessage(Constants.CPP_PATH + " -o " 
                                       		+ idlfile + " " + include_path 
                                       		+ " " + source);
                    Process preproc = 
                        Runtime.getRuntime().exec(Constants.CPP_PATH + " -o"  
                                                  + idlfile + " " + include_path
                                                  + " " + source);
                    BufferedReader stdInput = 
                        new BufferedReader(new InputStreamReader(preproc.getInputStream()));
                    BufferedReader stdError = 
                        new BufferedReader(new InputStreamReader(preproc.getErrorStream()));

                    // Read the output and any errors from the command
                    String s;
                    while((s = stdInput.readLine()) != null)
                        printMessage(s);
                    while((s = stdError.readLine()) != null)
                        printMessage(s);

                    // Wait for the process to complete and evaluate the return
                    // value of the attempted command
                    preproc.waitFor();
                    if(preproc.exitValue() != 0)
                        throw new RuntimeException();
                }
                catch(Exception e) {
                    printError("Error preprocessing " + source
                            + ": Please verify your include paths.");
                    return; // No further processing needed
                }

                // step (1). parse the resulting preprocessed file.
                printMessage("parse " + idlfile.toString());
                manager.reset();
                manager.setOriginalFile(source.toString());
                try {
                    kopf = manager.parseFile(idlfile.toString());
                    if(kopf == null)
                        throw new RuntimeException("Parser returned a null container");
                }
                catch(Exception e) {
                    printError("Error parsing " + source + ":\n" + e);
                    return; // No further processing needed
                }
                String kopf_name = source.getName().split("\\.")[0];
                kopf_name = kopf_name.replaceAll("[^\\w]", "_");
                kopf.setIdentifier(kopf_name);

                // step (2). traverse the resulting metamodel graph.
                try {
                    printMessage("traverse CCM model");
                    traverser.traverseGraph(kopf);
                }
                catch(Exception e) {
                    printError("Error generating code from " 
                                       + source + ":\n" + e);
                    return; // No further processing needed
                }

                // delete the preprocessed temporary file if everything worked.
                idlfile.deleteOnExit();

                printMessage("done.");
            }
        }
        catch(Exception e) {
            printError("Error: CCM Tools have been finished with an error:");
            printError(e.getMessage());
            printError("Please post a bug report to <ccmtools-devel@lists.sourceforge.net>");
        }
    }



    /**
     * Set up the node handler (i.e. code generator) object based on the output
     * language provided. Use the given driver to control the handler.
     * 
     * @param driver
     *            the user interface driver object to assign to this handler.
     * @param lang
     *            the language to generate.
     * @return the newly created node handler (i.e. code generator), or exit if
     *         there was an error.
     */
    private static TemplateHandler createTemplateHandler(Driver driver, String lang)
    {
        TemplateHandler handler = null;

        try {
            if(lang.equalsIgnoreCase("c++local")) {
                handler = new CppLocalGeneratorImpl(driver, output_directory);
            }
            else if(lang.equalsIgnoreCase("c++local-test")) {
                handler = new CppLocalTestGeneratorImpl(driver,
                                                        output_directory);
            }
            else if(lang.equalsIgnoreCase("c++dbc")) {
                handler = new CppLocalDbcGeneratorImpl(driver, output_directory);
            }
            else if(lang.equalsIgnoreCase("c++remote")) {
                handler = new CppRemoteGeneratorImpl(driver, output_directory);
            }
            else if(lang.equalsIgnoreCase("c++remote-test")) {
                handler = new CppRemoteTestGeneratorImpl(driver,
                                                         output_directory);
            }
            else if(lang.equalsIgnoreCase("idl3")) {
                handler = new IDL3GeneratorImpl(driver, output_directory);
            }
            else if(lang.equalsIgnoreCase("idl3mirror")) {
                handler = new IDL3MirrorGeneratorImpl(driver, output_directory);
            }
            else if(lang.equalsIgnoreCase("idl2")) {
                handler = new IDL2GeneratorImpl(driver, output_directory);
            }
            
            if((generate_flags & GENERATE_APPLICATION_FILES) != 0) {
                handler.setFlag(CodeGenerator.FLAG_APPLICATION_FILES);
            }
        }
        catch(IOException e) {
            printError("Failed to create a language generator for " + lang 
                       + "\n" + e.getMessage());
            handler = null;
        }
        return handler;
    }

    
    // Command line parameter parser ----------------------------------------
    
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
     * @param args
     *            the arguments passed on the command line.
     */
    private static boolean parseArgs(String args[])
    {
        languages = new ArrayList();
        language_types = new ArrayList();
        filenames = new ArrayList();
        include_path = "";

        for(int i = 0; i < Constants.GENERATOR_TYPES.length; i++) {
            language_types.add(Constants.GENERATOR_TYPES[i]);
        }
        
        List argv = new ArrayList();
        for(int i = 0; i < args.length; i++)
            argv.add(args[i]);

        if(argv.contains("-h") || argv.contains("--help")) {
            printUsage();
            return false; // No further processing needed
        }
        else if(argv.contains("-V") || argv.contains("--version")) {
            return false; // No further processing needed
        }

        for(Iterator a = argv.iterator(); a.hasNext();) {
            String arg = (String) a.next();
            if(arg.equals(""))
                continue;
            else if(arg.startsWith("--generator-mask="))
                setGeneratorMask(arg.split("=")[1]);
            else if(arg.startsWith("--parser-mask="))
                setParserMask(arg.split("=")[1]);
            else if(arg.startsWith("--output=")) {
                	setOutputDirectory(arg.split("=")[1]); 
            }
            else if(arg.startsWith("--app"))
                generate_flags |= GENERATE_APPLICATION_FILES;
            else if(arg.charAt(0) == '-')
                do {
                    if(arg.charAt(0) == 'a') {
                        generate_flags |= GENERATE_APPLICATION_FILES;
                    }
                    else if(arg.charAt(0) == 'o') {
                        if(a.hasNext()) {
                            setOutputDirectory((String) a.next());
                        }
                        else {
                            throw new IllegalArgumentException("Unspecified output directory"); 
                        }
                        break;
                    }
                    else if(arg.charAt(0) == 'I') {
                        File path = new File(arg.substring(1));
                        if(path.isDirectory())
                            include_path += " -I" + path;
                        break;
                    }
                    arg = arg.substring(1);
                } while(arg.length() > 0);
            else if(language_types.contains(arg.toLowerCase())
                    && !languages.contains(arg)) {
                languages.add(arg);
            }
            else {
                filenames.add(arg);
            }
        }

        if(languages.size() == 0) {
            throw new IllegalArgumentException("No valid output language specified");
        }
        
        if(include_path.trim().equals(""))
            include_path = " -I" + System.getProperty("user.dir");

        return true;
    }

    private static void setOutputDirectory(String val)
    {
        if(val.trim().equals("")) {
            throw new IllegalArgumentException("Unspecified output directory");
        }
        File test = new File(val);
        if(test.isAbsolute()) {
            output_directory = test;
        }
        else {
            output_directory = new File(base_output_directory, val);
        }
    }

    
    // Some helper methods --------------------------------------------------
    
    private static void setGeneratorMask(String val)
    {
        try {
            if(val.startsWith("0x"))
                gen_mask = Long.parseLong(val.substring(2), 16);
            else if(val.startsWith("0"))
                gen_mask = Long.parseLong(val.substring(1), 8);
            else
                gen_mask = Long.parseLong(val, 10);
        }
        catch(NumberFormatException e) {
            printError("Could not convert " + val
                    + " to a generator mask. Ignoring.");
        }
    }

    private static void setParserMask(String val)
    {
        try {
            if(val.startsWith("0x"))
                par_mask = Long.parseLong(val.substring(2), 16);
            else if(val.startsWith("0"))
                par_mask = Long.parseLong(val.substring(1), 8);
            else
                par_mask = Long.parseLong(val, 10);
        }
        catch(NumberFormatException e) {
            printError("Could not convert " + val + " to a parser mask. Ignoring.");
        }
    }

    
    private static void printVersion()
    {
        // TODO: use a display driver
        System.out.println(Constants.VERSION_TEXT);
    }
    
    private static void printUsage()
    {
        // TODO: use a display driver
        System.out.println(Constants.USAGE_TEXT);
    }

    private static void printError(String error)
    {
        // TODO: use a display driver
        System.err.println("Error: " + error);
    }

    private static void printMessage(String msg)
    {
        // TODO: use a display driver
        System.out.println("> " + msg);
    }
}