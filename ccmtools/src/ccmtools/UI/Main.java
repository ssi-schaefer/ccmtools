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

import antlr.RecognitionException;
import antlr.TokenStreamException;
import ccmtools.CcmtoolsException;
import ccmtools.CcmtoolsProperties;
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

    private static final int GENERATE_APPLICATION_FILES = 0x0001;

    // If the isExitWithErrorStatus flag is set, the main() function will 
    // call exit(1) to terminate ccmtools and the running Java VM.
    private static boolean isExitWithErrorStatus;
    private static final int EXIT_STATUS_FOR_ERROR = -1;
    
    private static List availableGeneratorTypes = null;
    private static List usedGeneratorTypes = null;

    private static String includePath;
    private static List filenames;
    private static File outputDirectory = new File(System.getProperty("user.dir"));
    private static File baseOutputDirectory = new File(outputDirectory, "");

    private static int generateFlags = 0;

    
    /**
     * Parse and generate code for each input IDL3 file. For each input file, we
     * need to (0) run the C preprocessor on the file to assemble includes and
     * do ifdef parsing and such, then (1) parse the file, then (2) generate
     * output code. Exits with nonzero status if errors are encountered during
     * parsing or generation.
     */
    public static void main(String[] args)
    {
        try {
            /* 
             * Settings for ccmtools generators 
             * (from command line, properties and default values)
             */
            
            logger = Logger.getLogger("ccm.main");
            logger.fine("enter main()");
        
            isExitWithErrorStatus = true; 

            // Set default values for ccmtools.home and ccmtools.template properties 
            // to the current ccmtools directory (if not set from the command line).
            // These settings are used by ccmtools JUnit tests.
            if(System.getProperty("ccmtools.home") == null) {
                System.setProperty("ccmtools.home",System.getProperty("user.dir"));
            }
            if(System.getProperty("ccmtools.templates") == null) {
                System.setProperty("ccmtools.templates", 
                               System.getProperty("ccmtools.home") + 
                               File.separator + "src" +
                               File.separator + "templates");
            }
            
            // Create a UI driver that handles user output 
            uiDriver = new ConsoleDriver(Driver.M_NONE);
            
            // Print out the current version of ccmtools
            printVersion();  
        
            // Parse command line parameters
            boolean continueProcessing = parseCommandLineArguments(args);
            if(!continueProcessing) {
                return; // terminate main()        
            }
            
            // Log CCM Tools settings
            logger.config("exitOnReturn = " + isExitWithErrorStatus);
            logger.config("ccmtools.home = " + System.getProperty("ccmtools.home"));
            logger.config("ccmtools.templates = " + System.getProperty("ccmtools.templates"));
            logger.config("ccmtools.impl.dir = " + 
                          CcmtoolsProperties.Instance().get("ccmtools.impl.dir"));
            
            GraphTraverser traverser = new CCMGraphTraverser();
            if(traverser == null) {
                printUsage();
                throw new CcmtoolsException("failed to create a graph traverser");
            }

            ParserManager manager = new ParserManager(Driver.M_NONE);
            if(manager == null) {
                printUsage();
                throw new CcmtoolsException("failed to create a parser manager");
            }

            ArrayList handlers = new ArrayList();
            for(Iterator l = usedGeneratorTypes.iterator(); l.hasNext();) {
                String generatorType = (String) l.next();
                TemplateHandler handler = createTemplateHandler(uiDriver, generatorType);
                if(handler == null) {
                    printUsage();
                    throw new CcmtoolsException("failed to create " + generatorType 
                                                + " template handler");
                }
                handlers.add(handler);
                traverser.addHandler(handler);
            }

            MContainer ccmModel = null;
            for(Iterator f = filenames.iterator(); f.hasNext();) {
                File source = new File((String) f.next());

                // create the name of the temporary idl file generated from the preprocessor cpp
                String tmpFile = "_CCM_" + source.getName();
                File idlfile = new File(System.getProperty("user.dir"), 
                                        tmpFile.substring(0, tmpFile.lastIndexOf(".idl")));
                
                // step (0). run the C preprocessor on the input file.
                // Run the GNU preprocessor cpp in a separate process.
                String cmd = Constants.CPP_PATH + " -o "+ idlfile + " " + includePath 
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
                   throw new CcmtoolsException("Preprocessor: "
                                + "Please verify your include paths or file names ("
                                + source + ").");

                
                // step (1). parse the resulting preprocessed file.
                uiDriver.printMessage("parse " + idlfile.toString());
                manager.reset();
                manager.setOriginalFile(source.toString());
                ccmModel = manager.parseFile(idlfile.toString());
                if(ccmModel == null) {
                    throw new CcmtoolsException("Parser error " + source + ":\n" 
                                                + "Parser returned an empty CCM model");
                }
                String kopf_name = source.getName().split("\\.")[0];
                kopf_name = kopf_name.replaceAll("[^\\w]", "_");
                ccmModel.setIdentifier(kopf_name);


                // step (2). traverse the resulting metamodel graph.
                uiDriver.printMessage("traverse CCM model");
                traverser.traverseGraph(ccmModel);

                // delete the preprocessed temporary file if everything worked.
                idlfile.deleteOnExit();
                uiDriver.printMessage("done.");
            }
        }
        catch(IllegalArgumentException e) {
            printUsage();
            exitWithErrorStatus("Usage error: " + e.getMessage());
        }
        catch(RecognitionException e) {
            exitWithErrorStatus("Parser error: " + e.getMessage());
        }
        catch(TokenStreamException e) {
            exitWithErrorStatus("Parser error: " + e.getMessage());
        }
        catch(CcmtoolsException e) {
            exitWithErrorStatus(e.getMessage());
        }
        catch(IOException e) {
            exitWithErrorStatus(e.getMessage());
        }
        catch(Exception e) {
            exitWithErrorStatus("Unknown error: " + e.getMessage());
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
                handler = new CppLocalGenerator(driver, outputDirectory);
            }
            else if(generatorType.equalsIgnoreCase("c++local-test")) {
                handler = new CppLocalTestGenerator(driver,
                                                        outputDirectory);
            }
            else if(generatorType.equalsIgnoreCase("c++dbc")) {
                handler = new CppLocalDbcGenerator(driver, outputDirectory);
            }
            else if(generatorType.equalsIgnoreCase("c++remote")) {
                handler = new CppRemoteGenerator(driver, outputDirectory);
            }
            else if(generatorType.equalsIgnoreCase("c++remote-test")) {
                handler = new CppRemoteTestGenerator(driver,
                                                         outputDirectory);
            }
            else if(generatorType.equalsIgnoreCase("idl3")) {
                handler = new IDL3Generator(driver, outputDirectory);
            }
            else if(generatorType.equalsIgnoreCase("idl3mirror")) {
                handler = new IDL3MirrorGenerator(driver, outputDirectory);
            }
            else if(generatorType.equalsIgnoreCase("idl2")) {
                handler = new IDL2Generator(driver, outputDirectory);
            }
            
            if((generateFlags & GENERATE_APPLICATION_FILES) != 0) {
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
    private static boolean parseCommandLineArguments(String args[])
    {
        logger.fine("enter parseArgs()");
        usedGeneratorTypes = new ArrayList();
        availableGeneratorTypes = new ArrayList();
        filenames = new ArrayList();
        includePath = "";

        for(int i = 0; i < Constants.GENERATOR_TYPES.length; i++) {
            availableGeneratorTypes.add(Constants.GENERATOR_TYPES[i]);
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
            else if(arg.startsWith("--output=")) {
                setOutputDirectory(arg.split("=")[1]); 
            }
            else if(arg.startsWith("--noexit")) {
                isExitWithErrorStatus = false;
            }    
            else if(arg.startsWith("--app"))
                generateFlags |= GENERATE_APPLICATION_FILES;
            else if(arg.charAt(0) == '-')
                do {
                    if(arg.charAt(0) == 'a') {
                        generateFlags |= GENERATE_APPLICATION_FILES;
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
                            includePath += " -I" + path;
                        break;
                    }
                    arg = arg.substring(1);
                } while(arg.length() > 0);
            else if(availableGeneratorTypes.contains(arg.toLowerCase())
                    && !usedGeneratorTypes.contains(arg)) {
                usedGeneratorTypes.add(arg);
            }
            else {
                filenames.add(arg);
            }
        }

        if(usedGeneratorTypes.size() == 0) {
            throw new IllegalArgumentException("No valid output language specified");
        }
        
        if(includePath.trim().equals(""))
            includePath = " -I" + System.getProperty("user.dir");

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
            outputDirectory = test;
        }
        else {
            outputDirectory = new File(baseOutputDirectory, val);
        }
        logger.fine("leave setOutputDirectory()");
    }

    
    private static void printVersion()
    {
        uiDriver.println(Constants.VERSION_TEXT);
    }
    

    private static void printUsage()
    {
        uiDriver.println(Constants.USAGE_TEXT);
    }


    private static void exitWithErrorStatus(String errorMessage)
    {
        logger.info(errorMessage);
        uiDriver.printError("CCM Tools have been terminated with an error!\n" +
                            errorMessage);
        if(isExitWithErrorStatus) {
            System.exit(EXIT_STATUS_FOR_ERROR);
        }
    }
}