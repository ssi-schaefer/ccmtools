package ccmtools.generator.confix;

import java.io.FileNotFoundException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import ccmtools.CcmtoolsException;
import ccmtools.ui.Driver;
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
        try 
        {
            uiDriver = new ccmtools.ui.ConsoleDriver(Driver.M_NONE);
            
            CommandLineParameters parameters = new CommandLineParameters();
            if(parseCommandLineArgs(args, parameters)) 
            {
                parameters.validate();
            	    setCcmtoolsProperties();
            	    ConfixGenerator generator = new ConfixGenerator(parameters, uiDriver);
            	    generator.generate();
            }
        }
        catch(ParseException e) 
        {
            uiDriver.printError(e.getMessage());
            printUsage();
            exitWithErrorStatus(e.getMessage());
        }
        catch(CcmtoolsException e) 
        {
        	    exitWithErrorStatus(e.getMessage());
        }
        catch(FileNotFoundException e) 
        {
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
            System.setProperty("ccmtools.home", System.getProperty("user.dir"));
        }
        CcmtoolsProperties.Instance().set("ccmtools.home", System.getProperty("ccmtools.home"));
    }

    
    
    private static void defineCommandLineOptions()
    {
        options = new Options();
        
        // Define boolean options
        options.addOption("h", "help", false,"Display this help");
        options.addOption("V", "version", false, "Display CCM Tools version information");
        options.addOption("noexit", false, "Don't exit Java VM with error status");
        options.addOption(ConfixGenerator.MAKEFILE_PY_GENERATOR_ID, false, 
        					"Run the Makefile.py generator");
        
        // Define single valued options
        
        OptionBuilder.withArgName("name");
        OptionBuilder.hasArg();
        OptionBuilder.withValueSeparator();
        OptionBuilder.withDescription("Confix package name");
        OptionBuilder.withLongOpt("packagename");
        options.addOption(OptionBuilder.create(ConfixGenerator.PACKAGE_NAME));
        
        OptionBuilder.withArgName("version");
        OptionBuilder.hasArg();
        OptionBuilder.withDescription("Confix package version number (e.g. 1.0.0)");
        OptionBuilder.withLongOpt("packageversion");
        options.addOption(OptionBuilder.create(ConfixGenerator.PACKAGE_VERSION));
        
        OptionBuilder.withArgName("path");
        OptionBuilder.withLongOpt("output");
        OptionBuilder.hasArg();
        OptionBuilder.withDescription("Base output in DIR (default .)");
        options.addOption(OptionBuilder.create("o"));
    }
    
    
    private static boolean parseCommandLineArgs(String[] args, CommandLineParameters parameters)
        throws ParseException, CcmtoolsException
    {
        defineCommandLineOptions();

        if(args.length == 0) 
        {
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
        
        if(cmd.hasOption(ConfixGenerator.MAKEFILE_PY_GENERATOR_ID))
        {
            parameters.getGeneratorIds().add(ConfixGenerator.MAKEFILE_PY_GENERATOR_ID);
        }
        
        if(cmd.hasOption(ConfixGenerator.PACKAGE_VERSION))
        {
            parameters.setPackageVersion(cmd.getOptionValue(ConfixGenerator.PACKAGE_VERSION));
        }        
        
        if(cmd.hasOption(ConfixGenerator.PACKAGE_NAME))
        {
            parameters.setPackageName(cmd.getOptionValue(ConfixGenerator.PACKAGE_NAME));
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
        
        return true; // continue program after cl parsing
    }

        
    private static void printUsage()
    {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("ccmconfix [options]", options);
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
