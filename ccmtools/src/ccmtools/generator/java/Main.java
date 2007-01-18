package ccmtools.generator.java;

import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.logging.Logger;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import ccmtools.CcmtoolsException;
import ccmtools.CodeGenerator.CcmGraphTraverser;
import ccmtools.CodeGenerator.GraphTraverser;
import ccmtools.generator.java.metamodel.ModelRepository;
import ccmtools.metamodel.BaseIDL.MContainer;
import ccmtools.parser.idl.ParserManager;
import ccmtools.ui.UserInterfaceDriver;
import ccmtools.utils.ConfigurationLocator;

public class Main
{
    /** commons CLI options */
    private static Options options;
    
    /** Driver that handles user output */
    private static UserInterfaceDriver uiDriver;
    
	/** Java standard logger object */
	protected static Logger logger;
	
    // If the isExitWithErrorStatus flag is set, the main() function will 
    // call exit(1) to terminate ccmtools and the running Java VM.
    private static boolean isExitWithErrorStatus = true;
    private static final int EXIT_STATUS_FOR_ERROR = -1;
    

    /**
     * Using the main method, we can start the Java client library generator
     * as an application.
     * 
     * @param args
     */
    public static void main(String[] args)
	{
		logger = Logger.getLogger("ccm.generator.java");
		logger.fine("");

		try
		{
			uiDriver = new ccmtools.ui.ConsoleDriver();

			CommandLineParameters parameters = new CommandLineParameters();
			if (parseCommandLineArgs(args, parameters))
			{
				parameters.validate();
				setCcmtoolsProperties();
				JavaComponentGenerator generator = new JavaComponentGenerator(parameters, uiDriver);

				for (Iterator i = parameters.getIdlFiles().iterator(); i.hasNext();)
				{
					String idlFile = (String) i.next();
                    MContainer ccmModel = ParserManager.loadCcmModel(uiDriver, idlFile, parameters.getIncludePaths());

					// Transform CCM Model to Java Implementation Model
					GraphTraverser traverser = new CcmGraphTraverser();
					CcmToJavaModelMapper nodeHandler = new CcmToJavaModelMapper();
					traverser.addHandler(nodeHandler);
					traverser.traverseGraph(ccmModel);

					// Query the Java Implementation Model and generate all source
					// file objects for the Java Client Library
					ModelRepository javaModel = nodeHandler.getJavaModel();

					// Run the Java component generator which can handle all of
					// the different generator flags (-iface, -local, -app, -clientlib, etc.)
					generator.generate(javaModel);
				}
			}
		}
		catch (ParseException e)
		{
			uiDriver.printError(e.getMessage());
			printUsage();
		}
		catch (CcmtoolsException e)
		{
			exitWithErrorStatus(e.getMessage());
		}
		catch (FileNotFoundException e)
		{
			// Can't open uiDriver file
			exitWithErrorStatus(e.getMessage());
		}
		catch (Exception e)
		{
			exitWithErrorStatus(e.getMessage());
		}
	}
    
    
    /**
	 * Set default values in the ccmtools properties list (the items in this
	 * list depends on the content of ccmtools/etc/ccmtools.properties).
	 */
    private static void setCcmtoolsProperties()
	{
    		logger.fine("");		
		if (System.getProperty("ccmtools.home") == null)
		{
			System.setProperty("ccmtools.home", System.getProperty("user.dir"));
		}
		ConfigurationLocator.getInstance().set("ccmtools.home", System.getProperty("ccmtools.home"));
	}

    
    
    private static void defineCommandLineOptions()
    {
    		logger.fine("");
        options = new Options();
        
        // Define boolean options
        options.addOption("h", "help", false,"Display this help");
        options.addOption("V", "version", false, "Display CCM Tools version information");
        options.addOption(JavaComponentGenerator.CLIENT_LIB_GENERATOR_ID, 
        					false, "Run the Java client library generator");
        options.addOption(JavaComponentGenerator.LOCAL_COMPONENT_GENERATOR_ID, 
        					false, "Run the Java local component generator");
        options.addOption(JavaComponentGenerator.APPLICATION_GENERATOR_ID, 
        					false, "Run the Java application stubs generator");
        options.addOption(JavaComponentGenerator.INTERFACE_GENERATOR_ID, 
        					false, "Run the Java interface generator");
        options.addOption(JavaComponentGenerator.CORBA_COMPONENT_GENERATOR_ID, 
						false, "Run the Java CORBA component generator");
        options.addOption("noexit", false, "Don't exit Java VM with error status");
        
        // Define single valued options
        OptionBuilder.withArgName("path");
        OptionBuilder.withLongOpt("output");
        OptionBuilder.hasArg();
        OptionBuilder.withDescription("Base output in DIR (default .)");
        options.addOption(OptionBuilder.create("o"));
        
        OptionBuilder.withArgName("path");
        OptionBuilder.hasArg(); 
        OptionBuilder.withDescription( "Add path to the preprocessor include path" );        
        options.addOption(OptionBuilder.create( "I" ));
    }
    
    
    private static boolean parseCommandLineArgs(String[] args, CommandLineParameters parameters)
        throws ParseException, CcmtoolsException
    {
    		logger.fine("");
        defineCommandLineOptions();

        if(args.length == 0) {
            // no parameters are not enough...
            throw new ParseException("Too few parameters!");
        }

        // generate and run the command line parser
        CommandLineParser parser = new GnuParser();
        CommandLine cmd = parser.parse(options, args);

        // store command line parameter values in object variables
        if(cmd.hasOption("h")) 
        {
            printUsage();
            return false; // don't continue program execution
        }
        
        if(cmd.hasOption("V")) 
        {
            JavaComponentGenerator.printVersion(uiDriver);
            return false; // don't continue program execution
        }
        
        if (cmd.hasOption(JavaComponentGenerator.CLIENT_LIB_GENERATOR_ID))
		{
			parameters.getGeneratorIds().add(JavaComponentGenerator.CLIENT_LIB_GENERATOR_ID);
		}

		if (cmd.hasOption(JavaComponentGenerator.LOCAL_COMPONENT_GENERATOR_ID))
		{
			parameters.getGeneratorIds().add(JavaComponentGenerator.LOCAL_COMPONENT_GENERATOR_ID);
		}

		if (cmd.hasOption(JavaComponentGenerator.APPLICATION_GENERATOR_ID))
		{
			parameters.getGeneratorIds().add(JavaComponentGenerator.APPLICATION_GENERATOR_ID);
		}

		if (cmd.hasOption(JavaComponentGenerator.INTERFACE_GENERATOR_ID))
		{
			parameters.getGeneratorIds().add(JavaComponentGenerator.INTERFACE_GENERATOR_ID);
		}

		if (cmd.hasOption(JavaComponentGenerator.CORBA_COMPONENT_GENERATOR_ID))
		{
			parameters.getGeneratorIds().add(JavaComponentGenerator.CORBA_COMPONENT_GENERATOR_ID);
		}

		if (cmd.hasOption("noexit"))
		{
			parameters.setNoExit(true);
			isExitWithErrorStatus = false;
		}
		else
		{
			isExitWithErrorStatus = true;
			parameters.setNoExit(false);
		}

		if (cmd.hasOption("o"))
		{
			parameters.setOutDir(cmd.getOptionValue("o"));
		}

		if (cmd.hasOption("I"))
		{
			String[] paths = cmd.getOptionValues("I");
			if (paths != null)
			{
				parameters.getIncludePaths().clear();
				for (int i = 0; i < paths.length; i++)
				{
					parameters.getIncludePaths().add(paths[i]);
				}
			}
		}

        // handle parameters (= args without options)
        String[] arguments = cmd.getArgs();
        if(arguments != null && arguments.length > 0) {
        	parameters.getIdlFiles().clear();
        	 for(int i = 0; i < arguments.length; i++) 
             {
                 parameters.getIdlFiles().add(arguments[i]);
             }
        	
        }
        return true; // continue program after cl parsing
    }

        
    private static void printUsage()
    {
    		logger.fine("");
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("ccmjava [options] *.idl", options);
    }
        
    private static void exitWithErrorStatus(String errorMessage)
    {
    		logger.fine("");
        uiDriver.printError("CCM Tools have been terminated with an error!\n" + errorMessage);
        if(isExitWithErrorStatus) 
        {
            System.exit(EXIT_STATUS_FOR_ERROR);
        }
    }
}
