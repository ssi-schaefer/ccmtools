package ccmtools.generator.java;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import ccmtools.CcmtoolsException;
import ccmtools.Constants;
import ccmtools.generator.java.metamodel.JavaApplicationGeneratorElement;
import ccmtools.generator.java.metamodel.JavaClientLibGeneratorElement;
import ccmtools.generator.java.metamodel.JavaCorbaAdapterGeneratorElement;
import ccmtools.generator.java.metamodel.JavaLocalAdapterGeneratorElement;
import ccmtools.generator.java.metamodel.JavaLocalInterfaceGeneratorElement;
import ccmtools.generator.java.metamodel.ModelRepository;
import ccmtools.parser.assembly.metamodel.Model;
import ccmtools.ui.UserInterfaceDriver;
import ccmtools.utils.SourceFileHelper;

public class JavaComponentGenerator
{
    /** String constants used for generator selection */
    public static final String INTERFACE_GENERATOR_ID = "iface";
    public static final String APPLICATION_GENERATOR_ID = "app";
    public static final String LOCAL_COMPONENT_GENERATOR_ID = "local";
    public static final String CLIENT_LIB_GENERATOR_ID = "clientlib";
    public static final String CORBA_COMPONENT_GENERATOR_ID = "remote";
    public static final String ASSEMBLY_GENERATOR_ID = "assembly";
	
	/** UI driver for generator messages */
	protected UserInterfaceDriver uiDriver;
	
	/** Command line parameters */
	protected CommandLineParameters parameters;
	
	/** Java standard logger object */
	protected Logger logger;
	
	
	public JavaComponentGenerator(CommandLineParameters parameters, UserInterfaceDriver uiDriver)
	{
		this.uiDriver = uiDriver;
		this.parameters = (CommandLineParameters)parameters;		                           
        logger = Logger.getLogger("ccm.generator.java");
        logger.fine("");
        printVersion(uiDriver);
	}
	
    public static void printVersion(UserInterfaceDriver uiDriver)
    {
        uiDriver.println("+");
        uiDriver.println("+ Java Component Generator, " + Constants.CCMTOOLS_VERSION_TEXT);
        uiDriver.println("+");
        uiDriver.println("+");
        uiDriver.println(Constants.CCMTOOLS_COPYRIGHT_TEXT);
    }
    
    
	public void generate(ModelRepository javaModel, Model assemblies) throws CcmtoolsException
	{
		logger.fine("enter");
		for (String generatorId : parameters.getGeneratorIds())
		{
			if (generatorId.equals(INTERFACE_GENERATOR_ID))
			{
				generateInterface(javaModel);
			}
			else if (generatorId.equals(LOCAL_COMPONENT_GENERATOR_ID))
			{
				generateLocalComponent(javaModel);
			}
			else if (generatorId.equals(CLIENT_LIB_GENERATOR_ID))
			{
				generateClientLibComponent(javaModel);
			}
			else if (generatorId.equals(CORBA_COMPONENT_GENERATOR_ID))
			{
				generateCorbaComponent(javaModel);
			}
			else if (generatorId.equals(APPLICATION_GENERATOR_ID))
			{
				generateApplication(javaModel);
			}
            else if (generatorId.equals(ASSEMBLY_GENERATOR_ID))
            {
                generateAssembly(javaModel, assemblies);
            }
		}
		logger.fine("leave");
	}
	
	
	public void generateInterface(ModelRepository javaModelRepo)
		throws CcmtoolsException
	{
		logger.fine("enter");
		try
		{
            List<JavaLocalInterfaceGeneratorElement> generatorElements = 
                new ArrayList<JavaLocalInterfaceGeneratorElement>();
            generatorElements.addAll(javaModelRepo.findAllInterfaces());
            generatorElements.addAll(javaModelRepo.findAllComponents());
            generatorElements.addAll(javaModelRepo.findAllHomes());
            generatorElements.addAll(javaModelRepo.findAllGlobalConstants());
            generatorElements.addAll(javaModelRepo.findAllEnums());
            generatorElements.addAll(javaModelRepo.findAllStructs());
            generatorElements.addAll(javaModelRepo.findAllExceptions());
            
			// Save all source file objects
            for(JavaLocalInterfaceGeneratorElement element : generatorElements)
            {
                SourceFileHelper.writeSourceFiles(uiDriver, parameters.getOutDir(), 
                        element.generateLocalInterfaceSourceFiles());
            }
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new CcmtoolsException("[Java Interface Generator] " + e.getMessage());
		}
		logger.fine("leave");
	}
	
	
	public void generateLocalComponent(ModelRepository javaModelRepo)
		throws CcmtoolsException
	{
		logger.fine("enter");
		try
		{
            List<JavaLocalAdapterGeneratorElement> generatorElements = 
                new ArrayList<JavaLocalAdapterGeneratorElement>();
            generatorElements.addAll(javaModelRepo.findAllInterfaces());
            generatorElements.addAll(javaModelRepo.findAllComponents());
            generatorElements.addAll(javaModelRepo.findAllHomes());
            
            // Save all source file objects
            for(JavaLocalAdapterGeneratorElement element : generatorElements)
            {            
				SourceFileHelper.writeSourceFiles(uiDriver, parameters.getOutDir(), 
                        element.generateLocalAdapterSourceFiles());
            }
		}
		catch (Exception e)
		{
			throw new CcmtoolsException("[Java Local Component Generator] " + e.getMessage());
		}
		logger.fine("leave");
	}
	
	
	public void generateApplication(ModelRepository javaModelRepo)
		throws CcmtoolsException
	{
		logger.fine("enter");
		try
        {
            List<JavaApplicationGeneratorElement> generatorElements = 
                new ArrayList<JavaApplicationGeneratorElement>();
		    generatorElements.addAll(javaModelRepo.findAllProvides());
		    generatorElements.addAll(javaModelRepo.findAllComponents());
		    generatorElements.addAll(javaModelRepo.findAllHomes());
		    
            // Save all source file objects
            for(JavaApplicationGeneratorElement element : generatorElements)
            {
                SourceFileHelper.writeApplicationFiles(uiDriver, parameters.getOutDir(), 
                        element.generateApplicationSourceFiles());
            }
        }
		catch (Exception e)
		{
			throw new CcmtoolsException("[Java Application Skeleton Generator] " + e.getMessage());
		}
		logger.fine("leave");
	}
    
    public void generateAssembly(ModelRepository javaModelRepo, Model assemblies)
        throws CcmtoolsException
    {
        logger.fine("enter");
        try
        {
            List<JavaApplicationGeneratorElement> generatorElements = 
                new ArrayList<JavaApplicationGeneratorElement>();
            generatorElements.addAll(javaModelRepo.findAllProvides());
            generatorElements.addAll(javaModelRepo.findAllComponents());
            generatorElements.addAll(javaModelRepo.findAllHomes());
            
            // Save all source file objects
            for(JavaApplicationGeneratorElement element : generatorElements)
            {
                SourceFileHelper.writeSourceFiles(uiDriver, parameters.getOutDir(), 
                        element.generateAssemblySourceFiles(assemblies));
            }
        }
        catch (Exception e)
        {
            throw new CcmtoolsException("[Java Assembly Generator] " + e.getMessage());
        }
        logger.fine("leave");
    }
	
	public void generateClientLibComponent(ModelRepository javaModelRepo)
		throws CcmtoolsException
	{
		logger.fine("enter");
		try
		{
            List<JavaClientLibGeneratorElement> generatorElements = 
                new ArrayList<JavaClientLibGeneratorElement>();
            generatorElements.addAll(javaModelRepo.findAllComponents());
            generatorElements.addAll(javaModelRepo.findAllHomes());
            
			// Save all source file objects
            for(JavaClientLibGeneratorElement element : generatorElements)
            {
                SourceFileHelper.writeSourceFiles(uiDriver, parameters.getOutDir(), 
                        element.generateClientLibSourceFiles());
            }
			logger.fine("leave");
		}
		catch (Exception e)
		{
			throw new CcmtoolsException("[Java Client Library Generator] " + e.getMessage());
		}
	}

	
	public void generateCorbaComponent(ModelRepository javaModelRepo) 
		throws CcmtoolsException
	{
		logger.fine("enter");
		try
		{
            List<JavaCorbaAdapterGeneratorElement> generatorElements = 
                new ArrayList<JavaCorbaAdapterGeneratorElement>();
            generatorElements.addAll(javaModelRepo.findAllComponents());
            generatorElements.addAll(javaModelRepo.findAllHomes());
            
            generatorElements.addAll(javaModelRepo.findAllInterfaces());
            generatorElements.addAll(javaModelRepo.findAllEnums());
            generatorElements.addAll(javaModelRepo.findAllStructs());
            generatorElements.addAll(javaModelRepo.findAllExceptions());
            generatorElements.addAll(javaModelRepo.findAllSequences());
            generatorElements.addAll(javaModelRepo.findAllArrays());
            
			// Save all source file objects
            for(JavaCorbaAdapterGeneratorElement element : generatorElements)
            {
                SourceFileHelper.writeSourceFiles(uiDriver, parameters.getOutDir(), 
                        element.generateCorbaAdapterSourceFiles());
            }
			logger.fine("leave");
		}
		catch (Exception e)
		{
			throw new CcmtoolsException("[Java CORBA Component Generator] " + e.getMessage());
		}	
	}
}
