package ccmtools.UI;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import ccmtools.CcmtoolsException;
import ccmtools.Constants;


public class DeploymentMain
{
    /** commons CLI options */
    private Options options;
    
    /** Qualified name of the assembly object */ 
    private String assemblyObject;
    
    /** Name of the assembly header file */
    private String assemblyFile;
    
    /** Name used to register a component home object to the HomeFinder */
    private String homeFinderName;
    
    /** List of possible include paths */    
    private String[] includePaths;
    
    /** Component home IDL file */
    private String homeIdl;
        
    
    public static void main(String[] args)
    {
        DeploymentMain deployment = new DeploymentMain();        
        try {
            deployment.printVersion();
            deployment.parseCommandLineArgs(args);
            
            // TODO: Deployment Modell aufbauen und XMI schreiben
            
            System.out.println(deployment);
        }
        catch(ParseException e) {
            deployment.printError(e.getMessage());
            // e.printStackTrace();
        }
        catch(CcmtoolsException e) {
            deployment.printError(e.getMessage());
            // e.printStackTrace();
        }
    }
    
        
    private void parseCommandLineArgs(String[] args) 
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
        }
        if(cmd.hasOption("v")) {
            printVersion();
        }
        if(cmd.hasOption("ao")) {
            assemblyObject = cmd.getOptionValue("ao");
        }
        if(cmd.hasOption("af")) {
            assemblyFile = cmd.getOptionValue("af");
        }
        if(cmd.hasOption("I")) {
            includePaths = cmd.getOptionValues("I");
        }

        // handle parameters (= args without options) 
        String[] arguments = cmd.getArgs();
        if(arguments != null && arguments.length > 0) {
            homeIdl = arguments[0];
        }
        
        // validate stored command line parameter values
        checkAssemblyObject();
        checkAssemblyFile();
        checkHomeFinderName();
        checkIncludePaths();
        checkHomeIdl();
    }
    

    private void defineCommandLineOptions()
    {
        options = new Options();
        
        // Define boolean options
        options.addOption("h", "help", false,"Print this message");
        options.addOption("v", "version", false, "Print the version information and exit");

        // Define single valued options
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
    
    
    
    private void checkAssemblyObject() throws CcmtoolsException
    {
        // TODO
    }

    private void checkAssemblyFile() throws CcmtoolsException
    {
        // TODO: Assembly-File suchen (stimmt der Pfad) ?Include Path?
    }

    private void checkHomeFinderName() throws CcmtoolsException
    {
        // TODO
    }

    private void checkIncludePaths() throws CcmtoolsException
    {
        // TODO
    }

    private void checkHomeIdl() throws CcmtoolsException
    {
        // TODO: Home.idl (via Includes) suchen und CCM Modell lesen
    }


    
    private void printUsage()
    {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("ccmdeploy [options] <home>.idl", options);
    }
    
    private void printVersion()
    {
        System.out.println(Constants.VERSION_TEXT + "\n");
    }
    
    private void printArgs(String[] args)
    {
        for(int i=0; i<args.length; i++) {
            System.out.println("[" + i + "] " + args[i]);
        }            
    }
    
    private void printError(String msg)
    {
        System.out.println("ERROR: " + msg + "\n");
        printUsage();
    }
    
    public String toString()
    {
        StringBuffer buffer = new StringBuffer();
        if(homeIdl != null) {
             buffer.append(homeIdl).append("\n");
        }
        if(homeFinderName != null) {
             buffer.append(homeFinderName).append("\n");
        }
        if(assemblyObject != null) {
            buffer.append(assemblyObject).append("\n");
        }
        if(assemblyFile != null) {
            buffer.append(assemblyFile).append("\n");
        }
        if(includePaths != null) {
            for(int i = 0; i < includePaths.length; i++) {
                buffer.append(includePaths[i]).append("\n");
            }
        }
        return buffer.toString();
    }
}
