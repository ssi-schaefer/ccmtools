package ccmtools.metamodel;

import java.io.FileNotFoundException;
import java.util.logging.Logger;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import ccmtools.CcmtoolsException;
import ccmtools.metamodel.BaseIDL.MContainer;
import ccmtools.parser.idl.ParserHelper;
import ccmtools.ui.UserInterfaceDriver;
import ccmtools.utils.CcmtoolsProperties;

public class Main
{
    public static final String PARSER_ID = "parser";
    public static final String PARSER_TEXT = "Only parse IDL files and report errors.";
    
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
        logger = Logger.getLogger("ccm.metamodel");
		logger.fine("");

		try
		{
			uiDriver = new ccmtools.ui.ConsoleDriver();

			CommandLineParameters parameters = new CommandLineParameters();
			if (parseCommandLineArgs(args, parameters))
			{
				parameters.validate();
				setCcmtoolsProperties();

                for(String actionId : parameters.getActionIds())
                {
                    if(actionId.equals(PARSER_ID))
                    {
                        for (String idlFile : parameters.getIdlFiles())
                        {
                            ParserHelper.getInstance().loadCcmModel(uiDriver, idlFile, parameters.getIncludePaths());
                        }
                    }
                    else if(actionId.equals(CcmModelPrinter.MODEL_PRINTER_ID))
                    {
                        for (String idlFile : parameters.getIdlFiles())
                        {
                            MContainer ccmModel = 
                                CcmModelHelper.loadCcmModel(uiDriver, idlFile, parameters.getIncludePaths());
                            CcmModelPrinter printer = new CcmModelPrinter(uiDriver, parameters);
                            printer.traverse(ccmModel);
                        }
                    }
                    else if(actionId.equals(CcmModelChecker.MODEL_CHECKER_ID))
                    {
                        for (String idlFile : parameters.getIdlFiles())
                        {
                            MContainer ccmModel = 
                                CcmModelHelper.loadCcmModel(uiDriver, idlFile, parameters.getIncludePaths());
                            CcmModelChecker checker = new CcmModelChecker(uiDriver, parameters);
                            if(!checker.isValidModel(ccmModel))
                            {
                                uiDriver.printError(checker.getErrorMessage());
                            }
                        } 
                    }
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
			e.printStackTrace();			
			exitWithErrorStatus(e.getMessage());
		}
		catch (FileNotFoundException e)
		{
			// Can't open uiDriver file
			e.printStackTrace();	
			exitWithErrorStatus(e.getMessage());
		}
		catch (Exception e)
		{
			e.printStackTrace();	
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
		CcmtoolsProperties.Instance().set("ccmtools.home", System.getProperty("ccmtools.home"));
	}

    
    
    private static void defineCommandLineOptions()
    {
    		logger.fine("");
        options = new Options();
        
        // Define boolean options
        options.addOption("h", "help", false,"Display this help");
        options.addOption("V", "version", false, "Display CCM Tools version information");
        options.addOption(CcmModelPrinter.MODEL_PRINTER_ID, false, CcmModelPrinter.MODEL_PRINTER_TEXT);
        options.addOption(CcmModelChecker.MODEL_CHECKER_ID, false, CcmModelChecker.MODEL_CHECKER_TEXT);
        options.addOption(PARSER_ID, false, PARSER_TEXT);
        options.addOption("noexit", false, "Don't exit Java VM with error status");
        
        // Define single valued options
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
            CcmModelChecker.printVersion(uiDriver);
            return false; // don't continue program execution
        }
        
        if (cmd.hasOption(CcmModelPrinter.MODEL_PRINTER_ID))
        {
            parameters.getActionIds().add(CcmModelPrinter.MODEL_PRINTER_ID);
        }
        
        if (cmd.hasOption(CcmModelChecker.MODEL_CHECKER_ID))
        {
            parameters.getActionIds().add(CcmModelChecker.MODEL_CHECKER_ID);
        }

        if (cmd.hasOption("parser"))
        {
            parameters.getActionIds().add("parser");
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
        if (arguments != null && arguments.length > 0)
		{
			parameters.getIdlFiles().clear();
			for (int i = 0; i < arguments.length; i++)
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
        formatter.printHelp("ccmmodel [options] *.idl", options);
    }
        
    private static void exitWithErrorStatus(String errorMessage)
    {
    		logger.fine("");
        uiDriver.printError(errorMessage);
        if(isExitWithErrorStatus) 
        {
            System.exit(EXIT_STATUS_FOR_ERROR);
        }
    }
}
