package ccmtools.deployment;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import org.jdom.JDOMException;

import ccmtools.CcmtoolsException;
import ccmtools.Constants;
import ccmtools.CodeGenerator.CcmGraphTraverser;
import ccmtools.CodeGenerator.GraphTraverser;
import ccmtools.deployment.metamodel.ComponentAssemblyArtifactDescription;
import ccmtools.deployment.metamodel.ComponentImplementationDescription;
import ccmtools.deployment.metamodel.ComponentInterfaceDescription;
import ccmtools.deployment.metamodel.ComponentPackageDescription;
import ccmtools.deployment.metamodel.DeploymentFactory;
import ccmtools.deployment.metamodel.PackagedComponentImplementation;
import ccmtools.metamodel.CcmModelHelper;
import ccmtools.metamodel.BaseIDL.MContainer;
import ccmtools.ui.UserInterfaceDriver;
import ccmtools.utils.Code;


public class CDDGenerator
{
    /** Parameters to configure the generator */
    private DeploymentParameters parameters;
    
    /** Component CCM Model */
    private MContainer ccmModel;

    /** Java logging */
    private Logger logger;

    /** Driver that handles user output */
    private UserInterfaceDriver uiDriver;

    
    /**
     * Validate parameter values, set the user interface dirver and
     * initialize the Logger.
     * 
     * @param parameters
     * @param uiDriver
     * @throws CcmtoolsException
     */
    public CDDGenerator(DeploymentParameters parameters, UserInterfaceDriver uiDriver) 
        throws CcmtoolsException
    {
        logger = Logger.getLogger("ccm.generator.cdd");
        logger.fine("CCDGenerator()");

        this.parameters = parameters;
        this.uiDriver = uiDriver;
        
        printVersion(uiDriver);
        
        checkIncludePaths(parameters.getIncludePaths());
        checkOutputPath(parameters.getOutDir());
        checkHomeIdl(parameters.getHomeIdl(), parameters.getIncludePaths());
        checkAssemblyObject(parameters.getAssemblyObject());        
    }
        
    public static void printVersion(UserInterfaceDriver uiDriver)
    {
        uiDriver.println("+");
        uiDriver.println("+ Component Descriptor Generator, " + Constants.CCMTOOLS_VERSION_TEXT);
        uiDriver.println("+");
        uiDriver.println("+");
        uiDriver.println(Constants.CCMTOOLS_COPYRIGHT_TEXT);
    }
    
    
    /**
     * Generate a D&C model from the parameters object and the CCM model 
     * defined by the given home IDL file.
     * The D&C model is stored as an XMI file.
     * 
     * @throws CcmtoolsException
     */
    public void generate() 
        throws CcmtoolsException
    {
        logger.fine("generate()");
        
        try {
            ccmModel = CcmModelHelper.loadCcmModel(uiDriver, parameters.getHomeIdl(), parameters.getIncludePaths());
            if(ccmModel == null) {
                throw new CcmtoolsException("Component model can't be created!");
            }
            else {
                ComponentPackageDescription model = buildDeploymentModel();
                String descriptorName = parameters.getHomeFinderName() + ".cdd.xml";
                saveDeploymentModel(descriptorName, model);

                // !!!!!!!!!
                // For testing, we load and output the stored XMI file 
                ComponentPackageDescription loadedModel = loadDeploymentModel(descriptorName);
                printDeploymentModel(loadedModel);
                // !!!!!!!!
            }
        }
        catch(IOException e) {
            logger.info(e.getMessage());
            throw new CcmtoolsException("CCDGenerator.generate():" + e.getMessage());
        }
        catch(JDOMException e) {
            logger.info(e.getMessage());
            throw new CcmtoolsException("CCDGenerator.generate():" + e.getMessage());
        }
    }
    

    /**
     * Save a Deployment model as an XMI 2.1 file
     * 
     * @param descriptorName
     * @param model
     * @throws IOException
     */
    private void saveDeploymentModel(String descriptorName, ComponentPackageDescription model) 
        throws IOException
    {
        logger.fine("enter saveDeploymentModel()");
        File file = new File(parameters.getOutDir(), descriptorName);
        uiDriver.printMessage("write " + parameters.getOutDir() + File.separator + descriptorName);
        DeploymentToXmiMapper mapper = new DeploymentToXmiMapper();
        mapper.saveModel(file, model);
        logger.fine("leave saveDeploymentModel()");
    }
      
    /**
     * Load XMI 2.1 file and establish a Deployment model
     * 
     * @param descriptorName
     * @return
     * @throws JDOMException
     * @throws IOException
     */
    private ComponentPackageDescription loadDeploymentModel(String descriptorName) 
        throws JDOMException, IOException
    {
        logger.fine("enter loadDeploymentModel()");
        XmiToDeploymentMapper mapper = new XmiToDeploymentMapper();
        File file = new File(parameters.getOutDir(), descriptorName);
        uiDriver.printMessage("load " + parameters.getOutDir() + File.separator + descriptorName);
        ComponentPackageDescription loadedModel = mapper.loadModel(file);
        logger.fine("leave loadDeploymentModel()");
        return loadedModel;        
    }
    
    /**
     * Print a given Deployment model to the uiDriver.
     * This method is only used for debugging reasons.
     * 
     * @param model
     */
    private void printDeploymentModel(ComponentPackageDescription model)
    {
        logger.fine("enter printDeploymentModel()");
        DeploymentToXmiMapper xmiMapper = new DeploymentToXmiMapper();
        String xmi = xmiMapper.modelToString(model);
        uiDriver.println("loaded model:");
        uiDriver.println(xmi);
        logger.fine("leave printDeploymentModel()");
    }
    
    
    private ComponentPackageDescription buildDeploymentModel()
    {
        logger.fine("enter buildDeploymentModel()");
        
        ComponentInterfaceDescription cif = traverseCcmModel();

        DeploymentFactory factory = DeploymentFactory.instance;

        ComponentImplementationDescription componentImpl = 
            factory.createComponentImplementationDescription();
        componentImpl.setImplements(cif);
        if(parameters.getAssemblyObject() != null) {
            ComponentAssemblyArtifactDescription compAAD = 
                factory.createComponentAssemblyArtifactDescription();
            compAAD.setSpecifcType(Code.getRepositoryId(parameters.getAssemblyObject()));
            compAAD.getLocations().add(parameters.getAssemblyFile());
            componentImpl.setAssemblyImpl(compAAD);
        }
        
        PackagedComponentImplementation packCompImpl = 
            factory.createPackagedComponentImplementation();
        packCompImpl.setName(parameters.getHomeFinderName());
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
        GraphTraverser traverser = new CcmGraphTraverser();
        CcmModelNodeHandler nodeHandler = new CcmModelNodeHandler();
        traverser.addHandler(nodeHandler);
        traverser.traverseGraph(ccmModel);      
        logger.fine("leave traverseCcmModel()");
        return nodeHandler.getComponentInterfaceDescription();
    }
    
    
//    private MContainer loadCcmModel(String fileName, List includes) 
//        throws CcmtoolsException
//    {
//        logger.fine("enter loadCcmModel()");
//        File source = new File(fileName);
//        MContainer ccmModel = null;
//        try {
//            ParserManager manager = new ParserManager();
//
//            // create the name of the temporary idl file generated from the
//            // preprocessor cpp
//            String tmpFile = "_CCM_" + source.getName();
//            File idlfile = new File(System.getProperty("user.dir"), tmpFile
//                    .substring(0, tmpFile.lastIndexOf(".idl")));
//
//            // step (0). run the C preprocessor on the input file.
//            // Run the GNU preprocessor cpp in a separate process.
//            StringBuffer cmd = new StringBuffer();
//            cmd.append(Constants.CPP_PATH);
//            cmd.append(" -o ").append(idlfile).append(" ");
//            for(Iterator i = includes.iterator(); i.hasNext();) {
//                cmd.append("-I").append((String) i.next()).append(" ");
//            }
//            cmd.append(source);
//
//            uiDriver.printMessage(cmd.toString());
//            Process preproc = Runtime.getRuntime().exec(cmd.toString());
//            BufferedReader stdInput = 
//                new BufferedReader(new InputStreamReader(preproc.getInputStream()));
//            BufferedReader stdError = 
//                new BufferedReader(new InputStreamReader(preproc.getErrorStream()));
//
//            // Read the output and any errors from the command
//            String s;
//            while((s = stdInput.readLine()) != null)
//                uiDriver.printMessage(s);
//            while((s = stdError.readLine()) != null)
//                uiDriver.printMessage(s);
//
//            // Wait for the process to complete and evaluate the return
//            // value of the attempted command
//            preproc.waitFor();
//            if(preproc.exitValue() != 0)
//                throw new CcmtoolsException("Preprocessor: "
//                        + "Please verify your include paths or file names ("
//                        + source + ").");
//
//            // step (1). parse the resulting preprocessed file.
//            uiDriver.printMessage("parse " + idlfile.toString());
//            manager.reset();
//            manager.setOriginalFile(source.toString());
//            ccmModel = manager.parseFile(idlfile.toString());
//            if(ccmModel == null) {
//                throw new CcmtoolsException("Parser error " + source + ":\n"
//                        + "parser returned an empty CCM model");
//            }
//            String kopf_name = source.getName();//.split("\\.")[0];
//            kopf_name = kopf_name.replaceAll("[^\\w]", "_");
//            ccmModel.setIdentifier(kopf_name);
//            
//            idlfile.deleteOnExit();
//        }
//        catch(Exception e) {
//            throw new CcmtoolsException("CCDGenerator.loadCcmModel():" + e.getMessage());
//        }
//        logger.fine("leave loadCcmModel()");
//        return ccmModel;
//    }


    // Parameter validation methods -------------------------------------------
    
    /**
     * Check if the given include paths exists.
     * 
     * @throws CcmtoolsException
     */
    private void checkIncludePaths(List includePaths) 
        throws CcmtoolsException
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
     * Check if the given output paths exists.
     * 
     * @throws CcmtoolsException
     */
    private void checkOutputPath(String outDir) 
        throws CcmtoolsException
    {
        // OK, if any given output directory exists
        File path = new File(outDir);
        if(!path.exists()) {
            throw new CcmtoolsException("Invalid output path " + path);
        }
    }

    /**
     * Check if the given assembly file exists.
     * 
     * @throws CcmtoolsException
     */
    private void checkAssemblyFile(String assemblyFile, List includePaths) 
        throws CcmtoolsException
    {
        // OK, no assembly file defined
        if(assemblyFile == null)
            return;
    
        if(isExistingFile(assemblyFile, includePaths)) {
            return;
        }
        else {
            throw new CcmtoolsException("Assembly file can't be found!");
        }
    }
        
    private void checkAssemblyObject(String asselblyObject) 
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
    private void checkHomeIdl(String homeIdl, List includePaths) 
        throws CcmtoolsException
    {
        if(!isExistingFile(homeIdl, includePaths)) {
            throw new CcmtoolsException("Component home file can't be found!");
        }
    }
    
    private boolean isExistingFile(String fileName, List includePaths)
    {
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
}
