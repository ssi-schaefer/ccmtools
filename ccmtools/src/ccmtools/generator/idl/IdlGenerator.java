package ccmtools.generator.idl;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import ccmtools.CcmtoolsException;
import ccmtools.Constants;
import ccmtools.generator.idl.metamodel.Idl2GeneratorElement;
import ccmtools.generator.idl.metamodel.Idl3GeneratorElement;
import ccmtools.generator.idl.metamodel.Idl3MirrorGeneratorElement;
import ccmtools.generator.idl.metamodel.ModelRepository;
import ccmtools.ui.UserInterfaceDriver;
import ccmtools.utils.ConfigurationLocator;
import ccmtools.utils.SourceFile;
import ccmtools.utils.SourceFileHelper;

public class IdlGenerator
{
	public static final String IDL3_ID = "idl3";
	public static final String IDL3_MIRROR_ID = "idl3mirror";
	public static final String IDL2_ID = "idl2";
	
	/** UI driver for generator messages */
	protected UserInterfaceDriver uiDriver;
	
	/** Command line parameters */
	protected CommandLineParameters parameters;
	
	/** Java standard logger object */
	protected Logger logger;
	
	
	public IdlGenerator(CommandLineParameters parameters, UserInterfaceDriver uiDriver)
	{
		this.uiDriver = uiDriver;
		this.parameters = (CommandLineParameters)parameters;		                           
        logger = Logger.getLogger("ccm.generator.idl");
        logger.fine("");
        printVersion(uiDriver);
	}
		
    public static void printVersion(UserInterfaceDriver uiDriver)
    {
        uiDriver.println("+");
        uiDriver.println("+ IDL Generator, " + Constants.CCMTOOLS_VERSION_TEXT);
        uiDriver.println("+");
        uiDriver.println("+");
        uiDriver.println(Constants.CCMTOOLS_COPYRIGHT_TEXT);
    }


	public void generate(ModelRepository idlModelRepo) 
		throws CcmtoolsException
	{
		logger.fine("begin");
		for(String generatorId : parameters.getGeneratorIds())
		{
			if(generatorId.equals(IDL3_ID))
			{
				generateIdl3(idlModelRepo);
			}
			else if(generatorId.equals(IDL3_MIRROR_ID))
			{
				generateIdl3Mirror(idlModelRepo);
			}
			else if(generatorId.equals(IDL2_ID))
			{
				generateIdl2(idlModelRepo);
			}
		}
		logger.fine("end");
	}
	
	
	public void generateIdl3(ModelRepository idlModelRepo)
		throws CcmtoolsException
	{
		logger.fine("begin");
		try
		{
            ConfigurationLocator.getInstance().setIdlNamespaceExtension(new ArrayList<String>());

            List<Idl3GeneratorElement> idl3ModelElements = new ArrayList<Idl3GeneratorElement>();
            idl3ModelElements.addAll(idlModelRepo.findAllTypedefs());
            idl3ModelElements.addAll(idlModelRepo.findAllEnums());
            idl3ModelElements.addAll(idlModelRepo.findAllStructs());
            idl3ModelElements.addAll(idlModelRepo.findAllGlobalConstants());
            idl3ModelElements.addAll(idlModelRepo.findAllExceptions());
            idl3ModelElements.addAll(idlModelRepo.findAllInterfaces());
            idl3ModelElements.addAll(idlModelRepo.findAllComponents());
            idl3ModelElements.addAll(idlModelRepo.findAllHomes());
            
            List<SourceFile> sourceFileList = new ArrayList<SourceFile>();
            for(Idl3GeneratorElement idl3Element : idl3ModelElements)
            {
                sourceFileList.addAll(idl3Element.generateIdl3SourceFiles());
            }
            
			// Save all source file objects
			SourceFileHelper.writeSourceFiles(uiDriver, parameters.getOutDir(), sourceFileList);
		}
		catch (Exception e)
		{
			throw new CcmtoolsException("[IDL3 Generator] " + e.getMessage());
		}
		logger.fine("end");
	}

	
	public void generateIdl3Mirror(ModelRepository idlModelRepo)
		throws CcmtoolsException
	{
        logger.fine("begin");
        try
        {
            ConfigurationLocator.getInstance().setIdlNamespaceExtension(new ArrayList<String>());

            List<Idl3MirrorGeneratorElement> idl3ModelElements = new ArrayList<Idl3MirrorGeneratorElement>();
            idl3ModelElements.addAll(idlModelRepo.findAllComponents());
            idl3ModelElements.addAll(idlModelRepo.findAllHomes());

            List<SourceFile> sourceFileList = new ArrayList<SourceFile>();
            for(Idl3MirrorGeneratorElement idl3Element : idl3ModelElements)
            {
                sourceFileList.addAll(idl3Element.generateIdl3MirrorSourceFiles());
            }
            
            // Save all source file objects
            SourceFileHelper.writeSourceFiles(uiDriver, parameters.getOutDir(), sourceFileList);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new CcmtoolsException("[IDL3Mirror Generator] " + e.getMessage());
        }
        logger.fine("end"); 
	}
		
    
	public void generateIdl2(ModelRepository idlModelRepo)
		throws CcmtoolsException
	{
        logger.fine("begin");
        try
        {            
            List<String> idlNamespaceExtension = ConfigurationLocator.getInstance().getIdl2NamespaceExtension();
            ConfigurationLocator.getInstance().setIdlNamespaceExtension(idlNamespaceExtension);

            List<Idl2GeneratorElement> idl2ModelElements = new ArrayList<Idl2GeneratorElement>();
            idl2ModelElements.addAll(idlModelRepo.findAllTypedefs());
            idl2ModelElements.addAll(idlModelRepo.findAllEnums());
            idl2ModelElements.addAll(idlModelRepo.findAllStructs());
            idl2ModelElements.addAll(idlModelRepo.findAllGlobalConstants());
            idl2ModelElements.addAll(idlModelRepo.findAllExceptions());
            idl2ModelElements.addAll(idlModelRepo.findAllInterfaces());
            idl2ModelElements.addAll(idlModelRepo.findAllComponents());
            idl2ModelElements.addAll(idlModelRepo.findAllHomes());
            
            List<SourceFile> sourceFileList = new ArrayList<SourceFile>();
            for(Idl2GeneratorElement idl2Element : idl2ModelElements)
            {
                sourceFileList.addAll(idl2Element.generateIdl2SourceFiles());
            }
            
            // Save all source file objects
            SourceFileHelper.writeSourceFiles(uiDriver, parameters.getOutDir(), sourceFileList);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new CcmtoolsException("[IDL2 Generator] " + e.getMessage());
        }
        logger.fine("end"); 	
	}
}
