package ccmtools.Deployment;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.jdom.JDOMException;

import ccmtools.CcmtoolsException;
import ccmtools.Constants;
import ccmtools.CodeGenerator.CCMGraphTraverser;
import ccmtools.CodeGenerator.GraphTraverser;
import ccmtools.Deployment.Metamodel.ComponentAssemblyArtifactDescription;
import ccmtools.Deployment.Metamodel.ComponentImplementationDescription;
import ccmtools.Deployment.Metamodel.ComponentInterfaceDescription;
import ccmtools.Deployment.Metamodel.ComponentPackageDescription;
import ccmtools.Deployment.Metamodel.DeploymentFactory;
import ccmtools.Deployment.Metamodel.DeploymentToXmiMapper;
import ccmtools.Deployment.Metamodel.PackagedComponentImplementation;
import ccmtools.Deployment.Metamodel.XmiToDeploymentMapper;
import ccmtools.Deployment.UI.SimpleNodeHandler;
import ccmtools.IDL3Parser.ParserManager;
import ccmtools.Metamodel.BaseIDL.MContainer;
import ccmtools.UI.Driver;


public class CDDGenerator
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

    /** Java logging */
    private Logger logger;


    public CDDGenerator()
    {
        logger = Logger.getLogger("ccm.generator.cdd");
        logger.fine("CCDGenerator()");
    }
            
    
    public String getHomeFinderName()
    {
        return homeFinderName;
    }
    
    
    public void generate(String[] args)
    {
        logger.fine("generate()");
        
        try {
            printVersion();
            if(parseCommandLineArgs(args)) {
                
                ComponentPackageDescription model = buildDeploymentModel();
                String descriptorName = getHomeFinderName() + ".cdd.xml";
                saveDeploymentModel(descriptorName, model);
                
                //!!!!!!!!!
                ComponentPackageDescription loadedModel = 
                    loadDeploymentModel(descriptorName); 
                printDeploymentModel(loadedModel);                
                //!!!!!!!!
            }
        }
        catch(ParseException e) {
            logger.info(e.getMessage());
            printError(e.getMessage());
        }
        catch(CcmtoolsException e) {
            logger.info(e.getMessage());
            e.printStackTrace();
            printError(e.getMessage());
        }
        catch(IOException e) {
            logger.info(e.getMessage());
            e.printStackTrace();
            printError(e.getMessage());
        }
        catch(JDOMException e) {
            logger.info(e.getMessage());
            e.printStackTrace();
            printError(e.getMessage());
        } 
    }
    
    
    // Save a Deployment model as an XMI 2.1 file        
    private void saveDeploymentModel(String descriptorName, ComponentPackageDescription model) 
        throws IOException
    {
        logger.fine("enter saveDeploymentModel()");
        File file = new File(outDir, descriptorName);
        printMessage("write " + outDir + File.separator + descriptorName);
        DeploymentToXmiMapper mapper = new DeploymentToXmiMapper();
        mapper.saveModel(file, model);
        logger.fine("leave saveDeploymentModel()");
    }
        
    private ComponentPackageDescription loadDeploymentModel(String descriptorName) 
        throws JDOMException, IOException
    {
        logger.fine("enter loadDeploymentModel()");
        XmiToDeploymentMapper mapper = new XmiToDeploymentMapper();
        File file = new File(outDir, descriptorName);
        printMessage("load " + outDir + File.separator + descriptorName);
        ComponentPackageDescription loadedModel = mapper.loadModel(file);
        logger.fine("leave loadDeploymentModel()");
        return loadedModel;        
    }
    
    private void printDeploymentModel(ComponentPackageDescription model)
    {
        logger.fine("enter printDeploymentModel()");
        DeploymentToXmiMapper xmiMapper = new DeploymentToXmiMapper();
        String xmi = xmiMapper.modelToString(model);
        System.out.println("loaded model:");
        System.out.println(xmi);
        logger.fine("leave printDeploymentModel()");
    }
    
    
    private ComponentPackageDescription buildDeploymentModel()
    {
        logger.fine("enter buildDeploymentModel()");
        DeploymentFactory factory = DeploymentFactory.instance;
        
        ComponentInterfaceDescription cif = traverseCcmModel();

        ComponentImplementationDescription componentImpl = 
            factory.createComponentImplementationDescription();
        componentImpl.setImplements(cif);
        if(assemblyObject != null) {
            ComponentAssemblyArtifactDescription compAAD = 
                factory.createComponentAssemblyArtifactDescription();
            compAAD.setSpecifcType(getRepoId(assemblyObject));
            compAAD.getLocations().add(assemblyFile);
            componentImpl.setAssemblyImpl(compAAD);
        }
        
        PackagedComponentImplementation packCompImpl = 
            factory.createPackagedComponentImplementation();
        packCompImpl.setName(homeFinderName);
        packCompImpl.setReferencedImplementation(componentImpl);
        
        ComponentPackageDescription compPackageDescription = 
            factory.createComponentPackageDescription();
        compPackageDescription.getImplementations().add(packCompImpl);
        compPackageDescription.setRealizes(cif);
        
        logger.fine("leave buildDeploymentModel()");     
        return compPackageDescription;
    }    
    
    private ComponentInterfaceDescription traverseCcmModel()
    {
        logger.fine("enter traverseCcmModel()");
        GraphTraverser traverser = new CCMGraphTraverser();
        SimpleNodeHandler nodeHandler = new SimpleNodeHandler();
        traverser.addHandler(nodeHandler);
        traverser.traverseGraph(ccmModel);      
        logger.fine("leave traverseCcmModel()");
        return nodeHandler.getComponentInterfaceDescription();
    }
    
    private MContainer loadCcmModel(String fileName, List includes) 
        throws CcmtoolsException
    {
        logger.fine("enter loadCcmModel()");
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
                        + "parser returned an empty CCM model");
            }
            String kopf_name = source.getName();//.split("\\.")[0];
            kopf_name = kopf_name.replaceAll("[^\\w]", "_");
            ccmModel.setIdentifier(kopf_name);
            
            idlfile.deleteOnExit();
        }
        catch(Exception e) {
            throw new CcmtoolsException(e.getMessage());
        }
        logger.fine("leave loadCcmModel()");
        return ccmModel;
    }
    
    
    private boolean parseCommandLineArgs(String[] args) 
        throws ParseException, CcmtoolsException 
    {
        logger.fine("enter parseCommandLineArgs()");
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
            printVersion();
            return false; // don't continue program execution
        }
        if(cmd.hasOption("o")) {
            outDir = cmd.getOptionValue("o");
        }
        if(cmd.hasOption("name")) {
            homeFinderName = cmd.getOptionValue("name");
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
        checkOutputPath();
        checkHomeIdl();
        checkAssemblyObject();
        
        logger.fine("leave parseCommandLineArgs()");
        return true; // continue program after cl parsing
    }
    

    private void defineCommandLineOptions()
    {
        logger.fine("enter defineCommandLineOptions()");
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
        logger.fine("leave defineCommandLineOptions()");
    }
    
    
    /**
     * Check if the given include paths exists.
     * 
     * @throws CcmtoolsException
     */
    private void checkIncludePaths() 
        throws CcmtoolsException
    {
        logger.fine("enter checkIncludePaths()");
        // OK, if any given include directory exists
        for(Iterator i = includePaths.iterator(); i.hasNext();) {
            File path = new File((String) i.next());
            if(!path.exists()) {
                throw new CcmtoolsException("Invalid include path " + path);
            }
        }
        logger.fine("leave checkIncludePaths()");
    }
        
    /**
     * Check if the given output paths exists.
     * 
     * @throws CcmtoolsException
     */
    private void checkOutputPath() 
        throws CcmtoolsException
    {
        logger.fine("enter checkOutputPath()");
        // OK, if any given output directory exists
        File path = new File(outDir);
        if(!path.exists()) {
            throw new CcmtoolsException("Invalid output path " + path);
        }
        logger.fine("leave checkOutputPath()");
    }

    /**
     * Check if the given assembly file exists.
     * 
     * @throws CcmtoolsException
     */
    private void checkAssemblyFile() 
        throws CcmtoolsException
    {
        logger.fine("checkAssemblyFile()");
        // OK, no assembly file defined
        if(assemblyFile == null)
            return;
    
        if(isExistingFile(assemblyFile)) {
            return;
        }
        else {
            throw new CcmtoolsException("Assembly file " + assemblyFile 
                                        + " can't be found!");
        }
    }
    
    
    private void checkAssemblyObject() 
        throws CcmtoolsException
    {
        // TODO: check if the assembly object exists (contains
        // the assembly file a class with the name of the 
        // assembly object?
    }
    
    
    /**
     * Check if the given IDL file exists.
     * @throws CcmtoolsException
     */
    private void checkHomeIdl() 
        throws CcmtoolsException
    {
        logger.fine("checkHomeIdl()");
        if(isExistingFile(homeIdl)) {
            ccmModel = loadCcmModel(homeIdl, includePaths);
            if(ccmModel == null) {
                throw new CcmtoolsException("Component model " + "can't be created!");
            }
        }
        else {
            throw new CcmtoolsException("Component file " + homeIdl 
                                        + " can't be found!");
        }
    }

    
    private boolean isExistingFile(String fileName)
    {
        logger.fine("isExistingFile()");
        // File without name does not exist
        if(fileName == null)
            return false;
        
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
        System.out.println("\n" + Constants.VERSION_TEXT + "\n");
    }
    
    private void printArgs(String[] args)
    {
        for(int i=0; i<args.length; i++) {
            System.out.println("[" + i + "] " + args[i]);
        }            
    }
    
    private void printError(String msg)
    {
        System.out.println("!!! ERROR: " + msg + "\n");
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
    
    private String getRepoId(String qname)
    {
        return "IDL:" + qname + ":1.0";
    }
    
    private String getRepoId(String[] qname)
    {
        StringBuffer buffer = new StringBuffer();
        buffer.append("IDL:");
        if(qname != null && qname.length > 0) {
            buffer.append(qname[0]);
            if(qname.length > 1) {
                for(int i = 1; i < qname.length; i++) {
                    buffer.append("/");
                    buffer.append(qname[i]);
                }
            }
        }
        buffer.append(":1.0");
        return buffer.toString();
    }
    
    private List getListFromQname(String qname)
    {
        List list = new ArrayList();
        if(qname != null) {
            String[] names = qname.split("/");
            for(int i = 0; i < names.length; i++) {
                list.add(names[i]);
            }
        }
        return list;
    }
    
    private String[] getArrayFromQname(String qname)
    {
        return qname.split("/");
    }
    
    private String getQnameFromRepoId(String repoId)
    {
        return repoId.substring(repoId.indexOf(':')+1, repoId.lastIndexOf(':'));
    }
}
