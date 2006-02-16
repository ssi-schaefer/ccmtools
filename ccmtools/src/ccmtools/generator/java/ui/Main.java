package ccmtools.generator.java.ui;

import java.io.FileNotFoundException;
import java.util.Iterator;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import ccmtools.CcmtoolsException;
import ccmtools.UI.Driver;
import ccmtools.generator.java.JavaClientLibGenerator;
import ccmtools.generator.java.JavaLocalComponentGenerator;
import ccmtools.generator.java.JavaApplicationGenerator;
import ccmtools.utils.CcmtoolsProperties;

public class Main
{
    /** commons CLI options */
    private static Options options;
    
    /** Driver that handles user output */
    private static Driver uiDriver;
    
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
        try {
            uiDriver = new ccmtools.UI.ConsoleDriver(Driver.M_NONE);
            
            CommandLineParameters parameters = new CommandLineParameters();
            if(parseCommandLineArgs(args, parameters)) 
            {
            	parameters.validate();
//            	System.out.println(parameters); 
            	setCcmtoolsProperties();
            	
            	for(Iterator i = parameters.getGeneratorIds().iterator(); i.hasNext(); )
            	{
            		String generatorId = (String)i.next();
            		if(generatorId.equals("clientlib"))
            		{
            			JavaClientLibGenerator generator = 
            				new JavaClientLibGenerator(parameters, uiDriver);
            			generator.generate();
            		}
            		else if(generatorId.equals("local"))
            		{
            			JavaLocalComponentGenerator generator = 
            				new JavaLocalComponentGenerator(parameters, uiDriver);
            			generator.generate();
            		}
               		else if(generatorId.equals("impl"))
            		{
            			JavaApplicationGenerator generator = 
            				new JavaApplicationGenerator(parameters, uiDriver);
            			generator.generate();
            		}
            	}            
            }
        }
        catch(ParseException e) {
            uiDriver.printError(e.getMessage());
            printUsage();
        }
        catch(CcmtoolsException e) {
        	exitWithErrorStatus(e.getMessage());
        }
        catch(FileNotFoundException e) {
            // Can't open uiDriver file
        	exitWithErrorStatus(e.getMessage());
        }
        catch(Exception e)
        {
        	exitWithErrorStatus(e.getMessage());
        }
    }
    
    
    /**
     * Set default values in the ccmtools properties list (the items
     * in this list depends on the content of ccmtools/etc/ccmtools.properties). 
     */
    private static void setCcmtoolsProperties()
    {
    	if(System.getProperty("ccmtools.home") == null) 
    	{
    		System.setProperty("ccmtools.home",System.getProperty("user.dir"));
    	}	
    	CcmtoolsProperties.Instance().set("ccmtools.home", System.getProperty("ccmtools.home"));
    }

    
    
    private static void defineCommandLineOptions()
    {
        options = new Options();
        
        // Define boolean options
        options.addOption("h", "help", false,"Display this help");
        options.addOption("V", "version", false, "Display CCM Tools version information");
        options.addOption("clientlib", false, "Run the Java client library generator");
        options.addOption("local", false, "Run the Java local component generator");
        options.addOption("impl", false, "Run the Java local implementation generator");
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
            return false; // don't continue program execution
        }
        
        if(cmd.hasOption("clientlib"))
        {
        	parameters.getGeneratorIds().add("clientlib");
        }        
        
        if(cmd.hasOption("local"))
        {
        	parameters.getGeneratorIds().add("local");
        }    
        
        if(cmd.hasOption("impl"))
        {
        	parameters.getGeneratorIds().add("impl");
        }    
        
        if(cmd.hasOption("noexit"))
        {
        	parameters.setNoExit(true);
        	isExitWithErrorStatus = false;
        }
        else
        {
        	isExitWithErrorStatus = true;
        	parameters.setNoExit(false);
        }
        
        if(cmd.hasOption("o")) 
        {
            parameters.setOutDir(cmd.getOptionValue("o"));
        }
        
        if(cmd.hasOption("I")) 
        {
            String[] paths = cmd.getOptionValues("I");
            if(paths != null) {
                parameters.getIncludePaths().clear();
                for(int i = 0; i < paths.length; i++) 
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
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("ccmjava [options] *.idl", options);
    }
        
    private static void exitWithErrorStatus(String errorMessage)
    {
        uiDriver.printError("CCM Tools have been terminated with an error!\n" + errorMessage);
        if(isExitWithErrorStatus) 
        {
            System.exit(EXIT_STATUS_FOR_ERROR);
        }
    }
}
