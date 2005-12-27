package ccmtools.Deployment.UI;

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
import ccmtools.Deployment.CDDGenerator;
import ccmtools.Deployment.DeploymentParameters;
import ccmtools.UI.Driver;

public class Main
{
    /** commons CLI options */
    private static Options options;
    
    /** Driver that handles user output */
    private static Driver uiDriver;
    
    /**
     * Using the main method, we can start the component deployment
     * descriptor generator applictaion.
     * 
     * @param args
     */
    public static void main(String[] args)
    {
        try {
            uiDriver = new ccmtools.UI.ConsoleDriver(Driver.M_NONE);
            printVersion();
            
            DeploymentParameters parameters = new DeploymentParameters();
            if(parseCommandLineArgs(args, parameters)) {
                CDDGenerator deployment = new CDDGenerator(parameters, uiDriver);
                deployment.generate();
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
        options.addOption("h", "help", false,"Print this message");
        options.addOption("v", "version", false, "Print the version information and exit");

        // Define single valued options
        OptionBuilder.withArgName("path");
        OptionBuilder.withLongOpt("output");
        OptionBuilder.hasArg();
        OptionBuilder.withDescription("Directory where the generated descriptor is stored to.");
        options.addOption(OptionBuilder.create("o"));
        
        OptionBuilder.withArgName("name");
        OptionBuilder.withLongOpt("assemblyobject");
        OptionBuilder.hasArg();
        OptionBuilder.withDescription("Qualified name of the component assembly object.");
        options.addOption(OptionBuilder.create("ao"));

        OptionBuilder.withArgName("file");
        OptionBuilder.withLongOpt("assemblyfile");
        OptionBuilder.hasArg();
        OptionBuilder.withDescription("Name of the assembly object's header file.");
        options.addOption(OptionBuilder.create("af"));

        OptionBuilder.withArgName("name");
        OptionBuilder.withLongOpt("registrationname");
        OptionBuilder.hasArg();
        OptionBuilder.withDescription("Name used to register a component home object in the HomeFinder.");
        options.addOption(OptionBuilder.create("name"));

        OptionBuilder.withArgName("path");
        OptionBuilder.hasArg(); 
        OptionBuilder.withDescription( "Include paths for IDL files." );        
        options.addOption(OptionBuilder.create( "I" ));
    }
    
    
    private static boolean parseCommandLineArgs(String[] args, DeploymentParameters parameters)
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
        if(cmd.hasOption("h")) {
            printUsage();
            return false; // don't continue program execution
        }
        if(cmd.hasOption("v")) {
            return false; // don't continue program execution
        }
        if(cmd.hasOption("o")) {
            parameters.setOutDir(cmd.getOptionValue("o"));
        }
        if(cmd.hasOption("name")) {
            parameters.setHomeFinderName(cmd.getOptionValue("name"));
        }
        if(cmd.hasOption("ao")) {
            parameters.setAssemblyObject(cmd.getOptionValue("ao"));
        }
        if(cmd.hasOption("af")) {
            parameters.setAssemblyFile(cmd.getOptionValue("af"));
        }
        if(cmd.hasOption("I")) {
            String[] paths = cmd.getOptionValues("I");
            if(paths != null) {
                parameters.getIncludePaths().clear();
                for(int i = 0; i < paths.length; i++) {
                    parameters.getIncludePaths().add(paths[i]);
                }
            }
        }

        // handle parameters (= args without options)
        String[] arguments = cmd.getArgs();
        if(arguments != null && arguments.length > 0) {
            parameters.setHomeIdl(arguments[0]);
        }

        return true; // continue program after cl parsing
    }

        
    private static void printUsage()
    {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("ccmdeploy [options] <home>.idl", options);
    }
        
    private static void printVersion()
    {
        uiDriver.println("\n" + 
                         "Component Deployment Descriptor Generator\n" + 
                         Constants.VERSION_TEXT + "\n");
    }
    
//    private static void printArgs(String[] args)
//    {
//        for(int i=0; i<args.length; i++) {
//            uiDriver.println("[" + i + "] " + args[i]);
//        }            
//    }
}
