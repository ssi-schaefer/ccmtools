package ccmtools.generator.java.clientlib.ui;

import java.io.FileNotFoundException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import ccmtools.CcmtoolsException;
import ccmtools.Constants;
import ccmtools.UI.Driver;

public class Main
{
    /** commons CLI options */
    private static Options options;
    
    /** Driver that handles user output */
    private static Driver uiDriver;
    
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
            printVersion();
            
            JavaClientLibParameters parameters = new JavaClientLibParameters();
            if(parseCommandLineArgs(args, parameters)) 
            {
//                JavaClientLibGenerator generator = new JavaClientLibGenerator(parameters, uiDriver);
//                generator.generate();
            	
            	
            	System.out.println(parameters); //!!!!!!!!!!!
            
            }
        }
        catch(ParseException e) {
            uiDriver.printError(e.getMessage());
            printUsage();
        }
        catch(CcmtoolsException e) {
            uiDriver.printError(e.getMessage());
        }
        catch(FileNotFoundException e) {
            // Can't open uiDriver file
            System.err.println(e.getMessage());
        }
    }
    
    
    private static void defineCommandLineOptions()
    {
        options = new Options();
        
        // Define boolean options
        options.addOption("h", "help", false,"Display this help");
        options.addOption("V", "version", false, "Display CCM Tools version information");
        options.addOption("clientlib", false, "Run the Java client library generator");
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
    
    
    private static boolean parseCommandLineArgs(String[] args, JavaClientLibParameters parameters)
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
        
        if(cmd.hasOption("noexit"))
        {
        	parameters.setNoExit(true);
        }
        else
        {
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
        
    private static void printVersion()
    {
        uiDriver.println("\n" + "Java Client Library Generator\n" + 
                         Constants.VERSION_TEXT + "\n");
    }
    
//    private static void printArgs(String[] args)
//    {
//        for(int i=0; i<args.length; i++) {
//            uiDriver.println("[" + i + "] " + args[i]);
//        }            
//    }
}
