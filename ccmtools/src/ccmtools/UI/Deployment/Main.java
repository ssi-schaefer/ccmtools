package ccmtools.UI.Deployment;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import ccmtools.CcmtoolsException;
import ccmtools.Constants;
import ccmtools.CodeGenerator.CCMGraphTraverser;
import ccmtools.CodeGenerator.GraphTraverser;
import ccmtools.IDL3Parser.ParserManager;
import ccmtools.Metamodel.BaseIDL.MContainer;
import ccmtools.UI.Driver;

public class Main
{
    /** commons CLI options */
    private Options options;
    
    /** Path to the output directory */
    private String outDir;
    
    /** Qualified name of the assembly object */ 
    private String assemblyObject;
    
    /** Name of the assembly header file */
    private String assemblyFile;
    
    /** Name used to register a component home object to the HomeFinder */
    private String homeFinderName;
    
    /** List of possible include paths */    
    private List includePaths = new ArrayList();
    
    /** Component home IDL file */
    private String homeIdl;
        
    /** Component CCM Model */
    private MContainer ccmModel;
    
        
    public static void main(String[] args)
    {
        Main deployment = new Main();        
        try {
            deployment.printVersion();
            deployment.parseCommandLineArgs(args);
            
            
            deployment.printCcmModel();
            
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
        if(cmd.hasOption("o")) {
            outDir = cmd.getOptionValue("o");
        }
        if(cmd.hasOption("ao")) {
            assemblyObject = cmd.getOptionValue("ao");
        }
        if(cmd.hasOption("af")) {
            assemblyFile = cmd.getOptionValue("af");
        }
        if(cmd.hasOption("I")) {
            String[] paths = cmd.getOptionValues("I");
            if(paths != null) {
                includePaths.clear();
                for(int i=0; i<paths.length; i++) {
                    includePaths.add(paths[i]);
                }
            }
        }

        // handle parameters (= args without options) 
        String[] arguments = cmd.getArgs();
        if(arguments != null && arguments.length > 0) {
            homeIdl = arguments[0];
        }
        
        // validate stored command line parameter values
        checkIncludePaths();
        checkAssemblyFile();
        checkHomeIdl();

        checkOutputDir();
        checkAssemblyObject();
        checkHomeFinderName();
    }
    

    private void defineCommandLineOptions()
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
    
    
    /**
     * Check if the given include paths exists.
     * 
     * @throws CcmtoolsException
     */
    private void checkIncludePaths() throws CcmtoolsException
    {
        // OK, if any given include directory exists
        for(Iterator i = includePaths.iterator(); i.hasNext();) {
            File path = new File((String) i.next());
            if(!path.exists()) {
                throw new CcmtoolsException("Invalid include path " + path);
            }
        }
    }
    

    /**
     * Check if the given assembly file exists.
     * @throws CcmtoolsException
     */
    private void checkAssemblyFile() throws CcmtoolsException
    {
        // OK, no assembly file defined
        if(assemblyFile == null)
            return;
    
        if(isExistingFile(assemblyFile)) {
            return;
        }
        else {
            throw new CcmtoolsException("Assembly file " 
                                        + assemblyFile 
                                        + " can't be found!");
        }
    }
    
    private void checkAssemblyObject() throws CcmtoolsException
    {
        // TODO: check if the assembly object exists (contains
        // the assembly file a class with the name of the 
        // assembly object?
    }
    
    /**
     * Check if the given IDL file exists.
     * @throws CcmtoolsException
     */
    private void checkHomeIdl() throws CcmtoolsException
    {
        if(isExistingFile(homeIdl)) {
            ccmModel = loadCcmModel(homeIdl, includePaths);
            if(ccmModel == null) {
                throw new CcmtoolsException("Component model " 
                                            + "can't be created!");
            }
        }
        else {
            throw new CcmtoolsException("Component file " 
                                        + homeIdl 
                                        + " can't be found!");
        }
    }

    private void checkOutputDir() throws CcmtoolsException
    {
        // TODO
    }
    
    private void checkHomeFinderName() throws CcmtoolsException
    {
        // TODO
    }

    
    private boolean isExistingFile(String fileName)
    {
        // OK, if the given file exists
        File file = new File(fileName);
        if(file.exists()) 
            return true;
        
        // OK, if the assembly file can be found in any given
        // include path
        boolean hasFound = false;
        for(Iterator i = includePaths.iterator(); i.hasNext();) {
            File path = new File((String) i.next(), fileName);
            if(path.exists()) {
                hasFound = true;
            }
        }    
        return hasFound; 
    }

    
    private MContainer loadCcmModel(String fileName, List includes) 
        throws CcmtoolsException
    {
        File source = new File(fileName);
        MContainer ccmModel = null;
        try {
            ParserManager manager = new ParserManager(Driver.M_NONE);

            // create the name of the temporary idl file generated from the
            // preprocessor cpp
            String tmpFile = "_CCM_" + source.getName();
            File idlfile = new File(System.getProperty("user.dir"), tmpFile
                    .substring(0, tmpFile.lastIndexOf(".idl")));

            // step (0). run the C preprocessor on the input file.
            // Run the GNU preprocessor cpp in a separate process.
            StringBuffer cmd = new StringBuffer();
            cmd.append(Constants.CPP_PATH);
            cmd.append(" -o ").append(idlfile).append(" ");
            for(Iterator i = includes.iterator(); i.hasNext();) {
                cmd.append("-I").append((String) i.next()).append(" ");
            }
            cmd.append(source);

            printMessage(cmd.toString());
            Process preproc = Runtime.getRuntime().exec(cmd.toString());
            BufferedReader stdInput = 
                new BufferedReader(new InputStreamReader(preproc.getInputStream()));
            BufferedReader stdError = 
                new BufferedReader(new InputStreamReader(preproc.getErrorStream()));

            // Read the output and any errors from the command
            String s;
            while((s = stdInput.readLine()) != null)
                printMessage(s);
            while((s = stdError.readLine()) != null)
                printMessage(s);

            // Wait for the process to complete and evaluate the return
            // value of the attempted command
            preproc.waitFor();
            if(preproc.exitValue() != 0)
                throw new CcmtoolsException("Preprocessor: "
                        + "Please verify your include paths or file names ("
                        + source + ").");

            // step (1). parse the resulting preprocessed file.
            printMessage("parse " + idlfile.toString());
            manager.reset();
            manager.setOriginalFile(source.toString());
            ccmModel = manager.parseFile(idlfile.toString());
            if(ccmModel == null) {
                throw new CcmtoolsException("Parser error " + source + ":\n"
                        + "Parser returned an empty CCM model");
            }
            String kopf_name = source.getName();//.split("\\.")[0];
            kopf_name = kopf_name.replaceAll("[^\\w]", "_");
            ccmModel.setIdentifier(kopf_name);
            
            idlfile.deleteOnExit();
            printMessage("done.");
        }
        catch(Exception e) {
            throw new CcmtoolsException(e.getMessage());
        }
        return ccmModel;
    }
    
    private void saveDeploymentModel()
    {
        // TODO: build and save the model to an XMI file
    }
    
    
    private void printCcmModel()
    {
        System.out.println("CCM-Model:");
        
        GraphTraverser traverser = new CCMGraphTraverser();
        traverser.addHandler(new SimpleNodeHandler());
        traverser.traverseGraph(ccmModel);
        
    }
    
    
    private void printUsage()
    {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("ccmdeploy [options] <home>.idl", options);
    }
    
    private void printMessage(String msg) 
    {
        System.out.println("> " + msg);
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
             buffer.append("Component Home = ");
             buffer.append(homeIdl).append("\n");
        }
        if(homeFinderName != null) {
             buffer.append("HomeFinder Name = ");
             buffer.append(homeFinderName).append("\n");
        }
        if(assemblyObject != null) {
            buffer.append("Assembly Object = ");
            buffer.append(assemblyObject).append("\n");
        }
        if(assemblyFile != null) {
            buffer.append("Assembly File = ");
            buffer.append(assemblyFile).append("\n");
        }
        
        for(Iterator i=includePaths.iterator(); i.hasNext();) {
            buffer.append("Include Path = ");
            buffer.append((String)i.next()).append("\n");
        }
        
        if(outDir != null) {
            buffer.append("Output Path = ");
            buffer.append(outDir).append("\n");
        }
        return buffer.toString();
    }
}
