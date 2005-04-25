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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import ccmtools.Constants;
import ccmtools.CodeGenerator.CCMGraphTraverser;
import ccmtools.CodeGenerator.CodeGenerator;
import ccmtools.CodeGenerator.GraphTraverser;
import ccmtools.CodeGenerator.TemplateHandler;
import ccmtools.CppGenerator.CppLocalDbcGenerator;
import ccmtools.CppGenerator.CppLocalGenerator;
import ccmtools.CppGenerator.CppLocalTestGenerator;
import ccmtools.CppGenerator.CppRemoteGenerator;
import ccmtools.CppGenerator.CppRemoteTestGenerator;
import ccmtools.IDL3Parser.ParserManager;
import ccmtools.IDLGenerator.IDL2Generator;
import ccmtools.IDLGenerator.IDL3Generator;
import ccmtools.IDLGenerator.IDL3MirrorGenerator;
import ccmtools.Metamodel.BaseIDL.MContainer;


public class Main
{
    private static Logger logger;
    
    private static Driver uiDriver;
    // TODO: replace driver.* methods with Java's Logging API
    //    private static long gen_mask = Driver.M_OUTPUT_FILE; 
    //    private static long par_mask = 0x00000000;

    private static final int GENERATE_APPLICATION_FILES = 0x0001;

    private static List language_types = null;
    private static List languages = null;

    private static String include_path;
    private static List filenames;
    private static File output_directory = 
        new File(System.getProperty("user.dir"));
    private static File base_output_directory = 
        new File(output_directory, "");

    private static int generate_flags = 0;

    
    /**
     * Parse and generate code for each input IDL3 file. For each input file, we
     * need to (0) run the C preprocessor on the file to assemble includes and
     * do ifdef parsing and such, then (1) parse the file, then (2) generate
     * output code. Exits with nonzero status if errors are encountered during
     * parsing or generation.
     */
    public static void main(String[] args)
    {
        logger = Logger.getLogger("ccm.main");
        logger.fine("enter main()");

        try {
            // Create a UI driver that handles user output 
            uiDriver = new ConsoleDriver(Driver.M_NONE);
            
            // Print out the current version of ccmtools
            printVersion();  
            
            if(parseArgs(args) == false) {
                return; // No further processing needed        
            }
        }
        catch(IllegalArgumentException e) {
            logger.info(e.getMessage());
            uiDriver.printError(e.getMessage());
            printUsage();
            return; // No further processing needed      
        }
        catch(FileNotFoundException e) {
            logger.info(e.getMessage());
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
            GraphTraverser traverser = new CCMGraphTraverser();
            if(traverser == null) {
                String error = "failed to create a graph traverser";
                logger.info(error);
                uiDriver.printError(error);
                printUsage();
                return; // No further processing needed
            }

            ParserManager manager = new ParserManager(Driver.M_NONE);
            if(manager == null) {
                String error = "failed to create a parser manager";
                logger.info(error);
                uiDriver.printError(error);
                printUsage();
                return; // No further processing needed
            }

            ArrayList handlers = new ArrayList();
            for(Iterator l = languages.iterator(); l.hasNext();) {
                String generatorType = (String) l.next();
                TemplateHandler handler = createTemplateHandler(uiDriver, generatorType);
                if(handler == null) {
                    logger.info("failed to create " + generatorType + " template handler");
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
                    String cmd = Constants.CPP_PATH + " -o "+ idlfile + " " + include_path 
               						+ " " + source;
                    logger.fine(cmd);
                    uiDriver.printMessage(cmd);
                    Process preproc = Runtime.getRuntime().exec(cmd);
                    BufferedReader stdInput = 
                        new BufferedReader(new InputStreamReader(preproc.getInputStream()));
                    BufferedReader stdError = 
                        new BufferedReader(new InputStreamReader(preproc.getErrorStream()));

                    // Read the output and any errors from the command
                    String s;
                    while((s = stdInput.readLine()) != null)
                        uiDriver.printMessage(s);
                    while((s = stdError.readLine()) != null)
                        uiDriver.printMessage(s);

                    // Wait for the process to complete and evaluate the return
                    // value of the attempted command
                    preproc.waitFor();
                    if(preproc.exitValue() != 0)
                        throw new RuntimeException();
                }
                catch(Exception e) {
                    String error = "Error preprocessing " + source
                    	+ ": Please verify your include paths.";
                    logger.info(error);
                    uiDriver.printError(error);
                    return; // No further processing needed
                }

                // step (1). parse the resulting preprocessed file.
                uiDriver.printMessage("parse " + idlfile.toString());
                manager.reset();
                manager.setOriginalFile(source.toString());
                try {
                    kopf = manager.parseFile(idlfile.toString());
                    if(kopf == null)
                        throw new RuntimeException("Parser returned a null container");
                }
                catch(Exception e) {
                    String error = "Error parsing " + source + ":\n" + e.getMessage();
                    logger.info(error);
                    uiDriver.printError(error);
                    return; // No further processing needed
                }
                String kopf_name = source.getName().split("\\.")[0];
                kopf_name = kopf_name.replaceAll("[^\\w]", "_");
                kopf.setIdentifier(kopf_name);

                // step (2). traverse the resulting metamodel graph.
                try {
                    uiDriver.printMessage("traverse CCM model");
                    traverser.traverseGraph(kopf);
                }
                catch(Exception e) {
                    String error = "Error generating code from " 
                        + source + ":\n" + e.getMessage();
                    logger.info(error);
                    uiDriver.printError(error);
                    return; // No further processing needed
                }

                // delete the preprocessed temporary file if everything worked.
                idlfile.deleteOnExit();

                uiDriver.printMessage("done.");
            }
        }
        catch(Exception e) {
            uiDriver.printError("Error: CCM Tools have been finished with an error:\n" +
                              e.getMessage() + "\n" +
                              "Please post a bug report to <ccmtools-devel@lists.sourceforge.net>");
        }
        logger.fine("leave main()");
    }



    /**
     * Set up the node handler (i.e. code generator) object based on the output
     * language provided. Use the given driver to control the handler.
     * 
     * @param driver
     *            the user interface driver object to assign to this handler.
     * @param generatorType
     *            the language to generate.
     * @return the newly created node handler (i.e. code generator), or exit if
     *         there was an error.
     */
    private static TemplateHandler createTemplateHandler(Driver driver, String generatorType)
    {
        logger.fine("enter createTemplateHandler()");
        TemplateHandler handler = null;
        try {
            if(generatorType.equalsIgnoreCase("c++local")) {
                handler = new CppLocalGenerator(driver, output_directory);
            }
            else if(generatorType.equalsIgnoreCase("c++local-test")) {
                handler = new CppLocalTestGenerator(driver,
                                                        output_directory);
            }
            else if(generatorType.equalsIgnoreCase("c++dbc")) {
                handler = new CppLocalDbcGenerator(driver, output_directory);
            }
            else if(generatorType.equalsIgnoreCase("c++remote")) {
                handler = new CppRemoteGenerator(driver, output_directory);
            }
            else if(generatorType.equalsIgnoreCase("c++remote-test")) {
                handler = new CppRemoteTestGenerator(driver,
                                                         output_directory);
            }
            else if(generatorType.equalsIgnoreCase("idl3")) {
                handler = new IDL3Generator(driver, output_directory);
            }
            else if(generatorType.equalsIgnoreCase("idl3mirror")) {
                handler = new IDL3MirrorGenerator(driver, output_directory);
            }
            else if(generatorType.equalsIgnoreCase("idl2")) {
                handler = new IDL2Generator(driver, output_directory);
            }
            
            if((generate_flags & GENERATE_APPLICATION_FILES) != 0) {
                handler.setFlag(CodeGenerator.FLAG_APPLICATION_FILES);
            }
        }
        catch(IOException e) {
            String error = "Failed to create a language generator for " + generatorType 
            + "\n" + e.getMessage();
            logger.info(error);
            driver.printError(error);
            handler = null;
        }
        logger.fine("leave createTemplateHandler()");
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
        logger.fine("enter parseArgs()");
        languages = new ArrayList();
        language_types = new ArrayList();
        filenames = new ArrayList();
        include_path = "";

        for(int i = 0; i < Constants.GENERATOR_TYPES.length; i++) {
            language_types.add(Constants.GENERATOR_TYPES[i]);
        }
        
        List argv = new ArrayList();
        for(int i = 0; i < args.length; i++) {
            argv.add(args[i]);
        }
        logger.fine(argv.toString());
        
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
//            else if(arg.startsWith("--generator-mask="))
//                setGeneratorMask(arg.split("=")[1]);
//            else if(arg.startsWith("--parser-mask="))
//                setParserMask(arg.split("=")[1]);
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

        logger.fine("leave parseArgs()");
        return true;
    }


    private static void setOutputDirectory(String val)
    {
        logger.fine("enter setOutputDirectory()");
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
        logger.fine("leave setOutputDirectory()");
    }

    
    // Some helper methods --------------------------------------------------
    
//    private static void setGeneratorMask(String val)
//    {
//        logger.fine("enter setGeneratorMask()");
//        try {
//            if(val.startsWith("0x"))
//                gen_mask = Long.parseLong(val.substring(2), 16);
//            else if(val.startsWith("0"))
//                gen_mask = Long.parseLong(val.substring(1), 8);
//            else
//                gen_mask = Long.parseLong(val, 10);
//        }
//        catch(NumberFormatException e) {
//            String error = "Could not convert " + val
//            	+ " to a generator mask. Ignoring.";
//            logger.info(error);
//            driver.printError(error);
//        }
//        logger.fine("leave setGeneratorMask()");
//    }

//    private static void setParserMask(String val)
//    {
//        logger.fine("enter setParserMask()");
//        try {
//            if(val.startsWith("0x"))
//                par_mask = Long.parseLong(val.substring(2), 16);
//            else if(val.startsWith("0"))
//                par_mask = Long.parseLong(val.substring(1), 8);
//            else
//                par_mask = Long.parseLong(val, 10);
//        }
//        catch(NumberFormatException e) {
//            String error = "Could not convert " + val + " to a parser mask. Ignoring.";
//            logger.info(error);
//            driver.printError(error);
//        }
//        logger.fine("leave setParserMask()");
//    }

    
    private static void printVersion()
    {
        uiDriver.println(Constants.VERSION_TEXT);
    }
    
    private static void printUsage()
    {
        uiDriver.println(Constants.USAGE_TEXT);
    }
}