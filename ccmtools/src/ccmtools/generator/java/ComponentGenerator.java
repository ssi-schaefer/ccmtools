package ccmtools.generator.java;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import ccmtools.CcmtoolsException;
import ccmtools.Constants;
import ccmtools.generator.java.metamodel.ArrayDef;
import ccmtools.generator.java.metamodel.ComponentDef;
import ccmtools.generator.java.metamodel.ConstantDef;
import ccmtools.generator.java.metamodel.EnumDef;
import ccmtools.generator.java.metamodel.ExceptionDef;
import ccmtools.generator.java.metamodel.HomeDef;
import ccmtools.generator.java.metamodel.InterfaceDef;
import ccmtools.generator.java.metamodel.ModelRepository;
import ccmtools.generator.java.metamodel.ProvidesDef;
import ccmtools.generator.java.metamodel.SequenceDef;
import ccmtools.generator.java.metamodel.StructDef;
import ccmtools.ui.Driver;
import ccmtools.utils.SourceFile;
import ccmtools.utils.Utility;

public class ComponentGenerator
{
    /** String constants used for generator selection */
    public static final String INTERFACE_GENERATOR_ID = "iface";
    public static final String APPLICATION_GENERATOR_ID = "app";
    public static final String LOCAL_COMPONENT_GENERATOR_ID = "local";
    public static final String CLIENT_LIB_GENERATOR_ID = "clientlib";
    public static final String CORBA_COMPONENT_GENERATOR_ID = "remote";
	
	/** UI driver for generator messages */
	protected Driver uiDriver;
	
	/** Command line parameters */
	protected CommandLineParameters parameters;
	
	/** Java standard logger object */
	protected Logger logger;
	
	
	public ComponentGenerator(CommandLineParameters parameters, Driver uiDriver)
	{
		this.uiDriver = uiDriver;
		this.parameters = (CommandLineParameters)parameters;		                           
        logger = Logger.getLogger("ccm.generator.java");
        logger.fine("");
        printVersion();
	}
	
	
	public void generate(ModelRepository javaModel) throws CcmtoolsException
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
		}
		logger.fine("leave");
	}
	
	
	public void generateInterface(ModelRepository javaModel)
		throws CcmtoolsException
	{
		logger.fine("enter");
		try
		{
			List<SourceFile> sourceFileList = new ArrayList<SourceFile>();
			for(InterfaceDef javaIface : javaModel.findAllInterfaces())
			{
				sourceFileList.addAll(javaIface.generateLocalInterfaceSourceFiles());
			}
			for(ComponentDef javaComponent : javaModel.findAllComponents())
			{
				sourceFileList.addAll(javaComponent.generateLocalInterfaceSourceFiles());
			}
			for(HomeDef javaHome : javaModel.findAllHomes())
			{
				sourceFileList.addAll(javaHome.generateLocalInterfaceSourceFiles());
			}
			for(ConstantDef javaConstant : javaModel.findAllGlobalConstants())
			{
				sourceFileList.addAll(javaConstant.generateLocalInterfaceSourceFiles());
			}
			for(EnumDef javaEnum : javaModel.findAllEnums())
			{
				sourceFileList.addAll(javaEnum.generateLocalInterfaceSourceFiles());
			}
			for(StructDef javaStruct : javaModel.findAllStructs())
			{
				sourceFileList.addAll(javaStruct.generateLocalInterfaceSourceFiles());
			}
			for(ExceptionDef javaException : javaModel.findAllExceptions())
			{
				sourceFileList.addAll(javaException.generateLocalInterfaceSourceFiles());
			}

			// Save all source file objects
			Utility.writeSourceFiles(uiDriver, parameters.getOutDir(), sourceFileList);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new CcmtoolsException("[Java Interface Generator] " + e.getMessage());
		}
		logger.fine("leave");
	}
	
	
	public void generateLocalComponent(ModelRepository javaModel)
		throws CcmtoolsException
	{
		logger.fine("enter");
		try
		{
				List<SourceFile> sourceFileList = new ArrayList<SourceFile>();
				for(InterfaceDef javaIface : javaModel.findAllInterfaces())
				{
					sourceFileList.addAll(javaIface.generateLocalComponentSourceFiles());
				}
				for(ComponentDef javaComponent : javaModel.findAllComponents())
				{
					sourceFileList.addAll(javaComponent.generateLocalComponentSourceFiles());
				}
				for(HomeDef javaHome : javaModel.findAllHomes())
				{
					sourceFileList.addAll(javaHome.generateLocalComponentSourceFiles());
				}

				// Save all source file objects
				Utility.writeSourceFiles(uiDriver, parameters.getOutDir(), sourceFileList);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new CcmtoolsException("[Java Local Component Generator] " + e.getMessage());
		}
		logger.fine("leave");
	}
	
	
	public void generateApplication(ModelRepository javaModel)
		throws CcmtoolsException
	{
		logger.fine("enter");
		try
        {
            List<SourceFile> sourceFileList = new ArrayList<SourceFile>();
            for (ProvidesDef javaProvides : javaModel.findAllProvides())
            {
                sourceFileList.addAll(javaProvides.generateApplicationSourceFiles());
            }
            for (ComponentDef javaComponent : javaModel.findAllComponents())
            {
                sourceFileList.addAll(javaComponent.generateApplicationSourceFiles());
            }
            for (HomeDef javaHome : javaModel.findAllHomes())
            {
                sourceFileList.addAll(javaHome.generateApplicationSourceFiles());
            }

            // Save all source file objects
            Utility.writeApplicationFiles(uiDriver, parameters.getOutDir(), sourceFileList);
        }
		catch (Exception e)
		{
			throw new CcmtoolsException("[Java Application Skeleton Generator] " + e.getMessage());
		}
		logger.fine("leave");
	}
	
	
	public void generateClientLibComponent(ModelRepository javaModel)
		throws CcmtoolsException
	{
		logger.fine("enter");
		try
		{
			List<SourceFile> sourceFileList = generateCorbaConverterList(javaModel);

			for (Iterator j = javaModel.findAllComponents().iterator(); j.hasNext();)
			{
				ComponentDef javaComponent = (ComponentDef) j.next();
				sourceFileList.addAll(javaComponent.generateClientLibComponentSourceFiles());
			}
			for (Iterator j = javaModel.findAllHomes().iterator(); j.hasNext();)
			{
				HomeDef javaHome = (HomeDef) j.next();
				sourceFileList.addAll(javaHome.generateClientLibComponentSourceFiles());
			}

			// Save all source file objects
			Utility.writeSourceFiles(uiDriver, parameters.getOutDir(), sourceFileList);
			logger.fine("leave");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new CcmtoolsException("Error in generateClientLib: " + e.getMessage());
		}
	}

	
	public void generateCorbaComponent(ModelRepository javaModel) 
		throws CcmtoolsException
	{
		logger.fine("enter");
		try
		{
			List<SourceFile> sourceFileList = generateCorbaConverterList(javaModel);

			for(ComponentDef javaComponent : javaModel.findAllComponents())
			{
				sourceFileList.addAll(javaComponent.generateCorbaComponentSourceFiles());
			}
			for(HomeDef javaHome : javaModel.findAllHomes())
			{
				sourceFileList.addAll(javaHome.generateCorbaComponentSourceFiles());
			}

			// Save all source file objects
			Utility.writeSourceFiles(uiDriver, parameters.getOutDir(), sourceFileList);
			logger.fine("leave");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new CcmtoolsException("[Java CORBA Component Generator] " + e.getMessage());
		}	
	}


	private List<SourceFile> generateCorbaConverterList(ModelRepository javaModel) 
		throws CcmtoolsException
	{
		logger.fine("enter");
		try
		{
			List<SourceFile> sourceFileList = new ArrayList<SourceFile>();
			for(InterfaceDef javaIface : javaModel.findAllInterfaces())
			{
				sourceFileList.addAll(javaIface.generateCorbaComponentSourceFiles());
			}

			for(EnumDef javaEnum : javaModel.findAllEnums())
			{
				sourceFileList.addAll(javaEnum.generateCorbaComponentSourceFiles());
			}
			for(StructDef javaStruct : javaModel.findAllStructs())
			{
				sourceFileList.addAll(javaStruct.generateCorbaComponentSourceFiles());
			}
			for (ExceptionDef javaException : javaModel.findAllExceptions())
			{
				sourceFileList.addAll(javaException.generateCorbaComponentSourceFiles());
			}
			for (SequenceDef javaSequence : javaModel.findAllSequences())
			{
				sourceFileList.addAll(javaSequence.generateCorbaComponentSourceFiles());
			}
			for (ArrayDef javaArray : javaModel.findAllArrays())
			{
				sourceFileList.addAll(javaArray.generateCorbaComponentSourceFiles());
			}
			return sourceFileList;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new CcmtoolsException("[Java Client Library Generator] " + e.getMessage());
		}
		finally
		{
			logger.fine("leave");
		}
	}
	
    private void printVersion()
    {
        uiDriver.println("Java Component Generator, " + Constants.VERSION_TEXT);
    }
}
