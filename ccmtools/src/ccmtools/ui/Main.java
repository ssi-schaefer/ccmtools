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

package ccmtools.ui;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;
import ccmtools.CcmtoolsException;
import ccmtools.Constants;
import ccmtools.CodeGenerator.CcmGraphTraverser;
import ccmtools.CodeGenerator.CodeGenerator;
import ccmtools.CodeGenerator.GraphTraverser;
import ccmtools.CodeGenerator.TemplateHandler;
import ccmtools.CppGenerator.CppAssemblyGenerator;
import ccmtools.CppGenerator.CppLocalGenerator;
import ccmtools.CppGenerator.CppLocalTestGenerator;
import ccmtools.CppGenerator.CppRemoteGenerator;
import ccmtools.CppGenerator.CppRemoteTestGenerator;
import ccmtools.parser.assembly.metamodel.Model;
import ccmtools.parser.idl.metamodel.CcmModelHelper;
import ccmtools.parser.idl.metamodel.BaseIDL.MContainer;
import ccmtools.parser.idl3.ParserManager;
import ccmtools.utils.ConfigurationLocator;


public class Main
{
    private static Logger logger;
    private static UserInterfaceDriver uiDriver;

    private static final int GENERATE_APPLICATION_FILES = 0x0001;

    // If the isExitWithErrorStatus flag is set, the main() function will 
    // call exit(1) to terminate ccmtools and the running Java VM.
    private static boolean isExitWithErrorStatus;
    private static final int EXIT_STATUS_FOR_ERROR = -1;
    
    private static List<String> availableGeneratorTypes = null;
    private static List<String> usedGeneratorTypes = null;

    private static String includePath;
    private static List<String> idl_filenames;
    private static List<String> assembly_filenames;
    private static File outputDirectory = new File(System.getProperty("user.dir"));
    private static File baseOutputDirectory = new File(outputDirectory, "");

    private static int generateFlags = 0;
    
    // assembly model
    private static Model assemblies;

    
    // Begin Hack!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!   
    private static void showNewIdlGeneratorWarning(UserInterfaceDriver uiDriver, String id)
    {
        uiDriver.println("!!!!!!!!!!!!!!!!!!! W A R N I N G !!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        uiDriver.println("         YOU BETTER USE THE NEW IDL GENERATOR SCRIPT");
        uiDriver.println("");
        uiDriver.println("        use 'ccmidl -" + id + "'  instead of 'ccmtools " + id + "'");
        uiDriver.println("");
        uiDriver.println("!!!!!!!!!!!!!!!!!!! W A R N I N G !!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }
    // End Hack!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!            
    
        
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
        try 
        {
            // Create a UI driver that handles user output 
            uiDriver = new ConsoleDriver();
            
            // Print out the current version of ccmtools
            printVersion();  
            
            // Begin Hack!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!            
            // This method is a hack to delegate 'ccmtools idlx' calls to the new
            // IDL code generator script (e.g. 'ccmidl -idlx').
            // We should remove this as fast as possible!!!!!
            if(args.length > 1)
            {                
                if(args[0].equals("idl3"))
                {
                    showNewIdlGeneratorWarning(uiDriver,args[0]);
                    args[0] = "-idl3";
                    ccmtools.generator.idl.Main.main(args);
                    return;
                }
                else if(args[0].equals("idl3mirror"))
                {   
                    showNewIdlGeneratorWarning(uiDriver,args[0]);
                    args[0] = "-idl3mirror";
                    ccmtools.generator.idl.Main.main(args);
                    return;
                }
                else if(args[0].equals("idl2"))
                {
                    showNewIdlGeneratorWarning(uiDriver,args[0]);
                    args[0] = "-idl2";
                    ccmtools.generator.idl.Main.main(args);
                    return;
                }
            }        
            // End Hack!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!            
                      
            setPropertyDefaultValues();
                    
            // Parse command line parameters
            boolean continueProcessing = parseCommandLineArguments(args);
            if(!continueProcessing) 
            {
                return; // terminate main()        
            }
            
            GraphTraverser traverser = new CcmGraphTraverser();
            if(traverser == null) 
            {
                printUsage();
                throw new CcmtoolsException("failed to create a graph traverser");
            }

            ParserManager manager = new ParserManager();
            if(manager == null) 
            {
                printUsage();
                throw new CcmtoolsException("failed to create a parser manager");
            }

            List<TemplateHandler> handlers = new ArrayList<TemplateHandler>();
            for(Iterator l = usedGeneratorTypes.iterator(); l.hasNext();) 
            {
                String generatorType = (String) l.next();
                TemplateHandler handler = createTemplateHandler(uiDriver, generatorType);
                if(handler == null) 
                {
                    printUsage();
                    throw new CcmtoolsException("failed to create " + generatorType + " template handler");
                }                
                handlers.add(handler);
                traverser.addHandler(handler);
            }
            
            List<MContainer> models = CcmModelHelper.loadCcmModels(uiDriver, idl_filenames,
                includePath);
            
            if (assemblies != null)
                assemblies.updateCcmModels(models);

            for (MContainer ccmModel : models)
            {
                uiDriver.printMessage("traverse CCM model");
                traverser.traverseGraph(ccmModel);
                uiDriver.printMessage("done.");
            }
        }
        catch(IllegalArgumentException e) 
        {
            printUsage();
            exitWithErrorStatus("Usage error: " + e.getMessage());
        }
        catch(CcmtoolsException e) 
        {
            exitWithErrorStatus(e.getMessage());
        }
        catch(IOException e) 
        {
            exitWithErrorStatus(e.getMessage());
        }
        catch(Exception e) 
        {
        		e.printStackTrace();
            exitWithErrorStatus("Error: " + e.getMessage());
        }
        logger.fine("leave main()");
    }

    
    private static void setPropertyDefaultValues()
    {
        // By default, exit with an error status
        isExitWithErrorStatus = true;
        
        // Set default values for ccmtools.home and ccmtools.template properties 
        // to the current ccmtools directory (if not set from the command line).
        if(System.getProperty("ccmtools.home") == null) 
        {
            System.setProperty("ccmtools.home",System.getProperty("user.dir"));
        }
        if(System.getProperty("ccmtools.templates") == null) 
        {
            System.setProperty("ccmtools.templates", 
                           System.getProperty("ccmtools.home") + 
                           File.separator + "src" +
                           File.separator + "templates");
        }
        
        // Set C preprocessor 
        if(ConfigurationLocator.getInstance().get("ccmtools.cpp") == null)
        {
            ConfigurationLocator.getInstance().set("ccmtools.cpp", Constants.CPP_PATH);
        }
        
        if(!ConfigurationLocator.getInstance().isDefined("ccmtools.dir.plugin.any.types")) 
        {
            String s = System.getProperty("ccmtools.templates") + File.separator + "AnyTypes";
            ConfigurationLocator.getInstance().set("ccmtools.dir.plugin.any.types", s);
        }
        
        if(!ConfigurationLocator.getInstance().isDefined("ccmtools.dir.plugin.any.templates")) 
        {
            String s = System.getProperty("ccmtools.templates") + File.separator + "AnyPlugins";
            ConfigurationLocator.getInstance().set("ccmtools.dir.plugin.any.templates", s);
        }

        // Log CCM Tools settings
        logger.config("exitOnReturn = " + isExitWithErrorStatus);
        logger.config("ccmtools.home = " + System.getProperty("ccmtools.home"));
        logger.config("ccmtools.cpp = " + ConfigurationLocator.getInstance().get("ccmtools.cpp"));
        logger.config("ccmtools.templates = " + System.getProperty("ccmtools.templates"));
        logger.config("ccmtools.impl.dir = " + ConfigurationLocator.getInstance().get("ccmtools.impl.dir"));
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
    private static TemplateHandler createTemplateHandler(UserInterfaceDriver driver, String generatorType)
        throws CcmtoolsException
    {
        logger.fine("enter createTemplateHandler()");
        TemplateHandler handler = null;
        try 
        {
            if(generatorType.equalsIgnoreCase("c++local")) 
            {
                handler = new CppLocalGenerator(driver, outputDirectory);
            }
            else if(generatorType.equalsIgnoreCase("c++assembly")) 
            {
                if(assemblies==null)
                {
                    assemblies = ccmtools.parser.assembly.Main.parse(assembly_filenames);
                }
                handler = new CppAssemblyGenerator(driver, outputDirectory, assemblies);
            }
            else if(generatorType.equalsIgnoreCase("c++local-test")) 
            {
                handler = new CppLocalTestGenerator(driver, outputDirectory);
            }
            else if(generatorType.equalsIgnoreCase("c++remote")) 
            {
                handler = new CppRemoteGenerator(driver, outputDirectory);
            }
            else if(generatorType.equalsIgnoreCase("c++remote-test")) 
            {
                handler = new CppRemoteTestGenerator(driver,outputDirectory);
            }
            // !!!!!!!!!!!!!!!!!!!!!!!
            else if(generatorType.equalsIgnoreCase("idl3")) 
            {
                throw new RuntimeException("Please use 'ccmidl -idl3' instead of 'ccmtools idl3'.");
            }
            else if(generatorType.equalsIgnoreCase("idl3mirror")) 
            {
                throw new RuntimeException("Please use 'ccmidl -idl3mirror' instead of 'ccmtools idl3mirror'.");
            }
            else if(generatorType.equalsIgnoreCase("idl2")) 
            {
                throw new RuntimeException("Please use 'ccmidl -idl2' instead of 'ccmtools idl2'.");
            }
            //!!!!!!!!!!!!!!!!!!!!!!!
            
            if((generateFlags & GENERATE_APPLICATION_FILES) != 0) {
                handler.setFlag(CodeGenerator.FLAG_APPLICATION_FILES);
            }
        }
        catch(Exception e) {
            String msg = "Failed to create a language generator for " + generatorType + ": " + e.getMessage();
            throw new CcmtoolsException(msg);
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
        usedGeneratorTypes = new ArrayList<String>();
        availableGeneratorTypes = new ArrayList<String>();
        idl_filenames = new ArrayList<String>();
        assembly_filenames = new ArrayList<String>();
        includePath = "";

        for(int i = 0; i < Constants.GENERATOR_TYPES.length; i++) {
            availableGeneratorTypes.add(Constants.GENERATOR_TYPES[i]);
        }
        
        List<String> argv = new ArrayList<String>();
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
            else if(arg.startsWith("--anytypes=")) {
                 setAnyTypesFile(arg.split("=")[1]);
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
                String x = arg.toLowerCase();
                if(x.endsWith(".idl"))
                    idl_filenames.add(arg);
                else if(x.endsWith(".assembly"))
                    assembly_filenames.add(arg);
                else
                    throw new IllegalArgumentException("unknown file type: "+arg);
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
        if(val.trim().equals("")) 
        {
            throw new IllegalArgumentException("Unspecified output directory");
        }
        File test = new File(val);
        if(test.isAbsolute()) 
        {
            outputDirectory = test;
        }
        else 
        {
            outputDirectory = new File(baseOutputDirectory, val);
        }
        logger.fine("leave setOutputDirectory()");
    }

    private static void setAnyTypesFile(String name)
    {
        logger.fine("enter setAnyTypesFile()");
        if(name.trim().equals("")) {
            throw new IllegalArgumentException("Unspecified any types file");
        }
        File test = new File(name);
        if(test.isAbsolute()) 
        {
            ConfigurationLocator.getInstance().set("ccmtools.dir.plugin.any.types", test.toString());
        }
        else 
        {
            File f = new File(System.getProperty("user.dir"), name);
            ConfigurationLocator.getInstance().set("ccmtools.dir.plugin.any.types", f.toString());            
        }
        checkAnyTypesFile(ConfigurationLocator.getInstance().get("ccmtools.dir.plugin.any.types"));
        logger.fine("leave setAnyTypesFile()");
    }
    
    private static void checkAnyTypesFile(String path)
    {
    		File file = new File(path);
    		if(file.exists())
    		{
    			return;
    		}
    		else
    		{
    			throw new RuntimeException("Any-Types file (" + path + " does not exist!");
    		}
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
        logger.fine(errorMessage);
        uiDriver.printError(errorMessage);
        if(isExitWithErrorStatus) 
        {
            System.exit(EXIT_STATUS_FOR_ERROR);
        }
    }
}