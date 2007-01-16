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
            // IDL3 model elements don't have a namespace extension
            ConfigurationLocator.getInstance().setIdlNamespaceExtension(new ArrayList<String>());

            List<Idl3GeneratorElement> generatorElements = new ArrayList<Idl3GeneratorElement>();
            generatorElements.addAll(idlModelRepo.findAllTypedefs());
            generatorElements.addAll(idlModelRepo.findAllEnums());
            generatorElements.addAll(idlModelRepo.findAllStructs());
            generatorElements.addAll(idlModelRepo.findAllGlobalConstants());
            generatorElements.addAll(idlModelRepo.findAllExceptions());
            generatorElements.addAll(idlModelRepo.findAllInterfaces());
            generatorElements.addAll(idlModelRepo.findAllComponents());
            generatorElements.addAll(idlModelRepo.findAllHomes());
            
            // Save all source file objects
            for(Idl3GeneratorElement element : generatorElements)
            {
                SourceFileHelper.writeSourceFiles(uiDriver, parameters.getOutDir(), 
                        element.generateIdl3SourceFiles()); 
            }
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
            // IDL3Mirror model elements don't have a namespace extension
            ConfigurationLocator.getInstance().setIdlNamespaceExtension(new ArrayList<String>());

            List<Idl3MirrorGeneratorElement> generatorElements = new ArrayList<Idl3MirrorGeneratorElement>();
            generatorElements.addAll(idlModelRepo.findAllComponents());
            generatorElements.addAll(idlModelRepo.findAllHomes());

            // Save all source file objects
            for(Idl3MirrorGeneratorElement element : generatorElements)
            {
                SourceFileHelper.writeSourceFiles(uiDriver, parameters.getOutDir(),
                        element.generateIdl3MirrorSourceFiles());
            }
        }
        catch (Exception e)
        {
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
            // Inject a namespace extension for IDL2 model elements, e.g. "ccmtools::corba"
            List<String> idlNamespaceExtension = ConfigurationLocator.getInstance().getIdl2NamespaceExtension();
            ConfigurationLocator.getInstance().setIdlNamespaceExtension(idlNamespaceExtension);

            List<Idl2GeneratorElement> generatorElements = new ArrayList<Idl2GeneratorElement>();
            generatorElements.addAll(idlModelRepo.findAllTypedefs());
            generatorElements.addAll(idlModelRepo.findAllEnums());
            generatorElements.addAll(idlModelRepo.findAllStructs());
            generatorElements.addAll(idlModelRepo.findAllGlobalConstants());
            generatorElements.addAll(idlModelRepo.findAllExceptions());
            generatorElements.addAll(idlModelRepo.findAllInterfaces());
            generatorElements.addAll(idlModelRepo.findAllComponents());
            generatorElements.addAll(idlModelRepo.findAllHomes());
            
            // Save all source file objects
            for(Idl2GeneratorElement element : generatorElements)
            {
                SourceFileHelper.writeSourceFiles(uiDriver, parameters.getOutDir(), 
                        element.generateIdl2SourceFiles());
            }            
        }
        catch (Exception e)
        {
            throw new CcmtoolsException("[IDL2 Generator] " + e.getMessage());
        }
        logger.fine("end"); 	
	}
}
