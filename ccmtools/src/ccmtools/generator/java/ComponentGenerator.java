package ccmtools.generator.java;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import ccmtools.CcmtoolsException;
import ccmtools.Constants;
import ccmtools.UI.Driver;
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
import ccmtools.generator.java.ui.CommandLineParameters;
import ccmtools.utils.Code;

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
		for (Iterator i = parameters.getGeneratorIds().iterator(); i.hasNext();)
		{
			String generatorId = (String) i.next();
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
			List sourceFileList = new ArrayList();
			for (Iterator j = javaModel.findAllInterfaces().iterator(); j.hasNext();)
			{
				InterfaceDef javaIface = (InterfaceDef) j.next();
				sourceFileList.addAll(javaIface.generateLocalInterfaceSourceFiles());
			}
			for (Iterator j = javaModel.findAllComponents().iterator(); j.hasNext();)
			{
				ComponentDef javaComponent = (ComponentDef) j.next();
				sourceFileList.addAll(javaComponent.generateLocalInterfaceSourceFiles());
			}
			for (Iterator j = javaModel.findAllHomes().iterator(); j.hasNext();)
			{
				HomeDef javaHome = (HomeDef) j.next();
				sourceFileList.addAll(javaHome.generateLocalInterfaceSourceFiles());
			}
			for (Iterator j = javaModel.findAllGlobalConstants().iterator(); j.hasNext();)
			{
				ConstantDef javaConstant = (ConstantDef) j.next();
				sourceFileList.addAll(javaConstant.generateLocalInterfaceSourceFiles());
			}
			for (Iterator j = javaModel.findAllEnums().iterator(); j.hasNext();)
			{
				EnumDef javaEnum = (EnumDef) j.next();
				sourceFileList.addAll(javaEnum.generateLocalInterfaceSourceFiles());
			}
			for (Iterator j = javaModel.findAllStructs().iterator(); j.hasNext();)
			{
				StructDef javaStruct = (StructDef) j.next();
				sourceFileList.addAll(javaStruct.generateLocalInterfaceSourceFiles());
			}
			for (Iterator j = javaModel.findAllExceptions().iterator(); j.hasNext();)
			{
				ExceptionDef javaException = (ExceptionDef) j.next();
				sourceFileList.addAll(javaException.generateLocalInterfaceSourceFiles());
			}

			// Save all source file objects
			Code.writeSourceCodeFiles(uiDriver, parameters.getOutDir(), sourceFileList);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new CcmtoolsException("Error in generateInterface: " + e.getMessage());
		}
		logger.fine("leave");
	}
	
	
	public void generateLocalComponent(ModelRepository javaModel)
		throws CcmtoolsException
	{
		logger.fine("enter");
		try
		{
				List sourceFileList = new ArrayList();
				for (Iterator j = javaModel.findAllInterfaces().iterator(); j.hasNext();)
				{
					InterfaceDef javaIface = (InterfaceDef) j.next();
					sourceFileList.addAll(javaIface.generateLocalComponentSourceFiles());
				}
				for (Iterator j = javaModel.findAllComponents().iterator(); j.hasNext();)
				{
					ComponentDef javaComponent = (ComponentDef) j.next();
					sourceFileList.addAll(javaComponent.generateLocalComponentSourceFiles());
				}
				for (Iterator j = javaModel.findAllHomes().iterator(); j.hasNext();)
				{
					HomeDef javaHome = (HomeDef) j.next();
					sourceFileList.addAll(javaHome.generateLocalComponentSourceFiles());
				}

				// Save all source file objects
				Code.writeSourceCodeFiles(uiDriver, parameters.getOutDir(), sourceFileList);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new CcmtoolsException("Error in generateLocalComponent: " + e.getMessage());
		}
		logger.fine("leave");
	}
	
	
	public void generateApplication(ModelRepository javaModel)
		throws CcmtoolsException
	{
		logger.fine("enter");
		try
		{
				List sourceFileList = new ArrayList();
				for (Iterator j = javaModel.findAllProvides().iterator(); j.hasNext();)
				{
					ProvidesDef javaProvides = (ProvidesDef) j.next();
					sourceFileList.addAll(javaProvides.generateApplicationSourceFiles());
				}
				for (Iterator j = javaModel.findAllComponents().iterator(); j.hasNext();)
				{
					ComponentDef javaComponent = (ComponentDef) j.next();
					sourceFileList.addAll(javaComponent.generateApplicationSourceFiles());
				}
				for (Iterator j = javaModel.findAllHomes().iterator(); j.hasNext();)
				{
					HomeDef javaHome = (HomeDef) j.next();
					sourceFileList.addAll(javaHome.generateApplicationSourceFiles());
				}
				
				// Save all source file objects
				Code.writeJavaApplicationFiles(uiDriver, parameters.getOutDir(), sourceFileList);
		}
		catch (Exception e)
		{
			throw new CcmtoolsException("Error in generateApplication: " + e.getMessage());
		}
		logger.fine("leave");
	}
	
	
	public void generateClientLibComponent(ModelRepository javaModel)
		throws CcmtoolsException
	{
		logger.fine("enter");
		try
		{
			List sourceFileList = generateCorbaConverterList(javaModel);

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
			Code.writeSourceCodeFiles(uiDriver, parameters.getOutDir(), sourceFileList);
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
			List sourceFileList = generateCorbaConverterList(javaModel);

			for (Iterator j = javaModel.findAllComponents().iterator(); j.hasNext();)
			{
				ComponentDef javaComponent = (ComponentDef) j.next();
				sourceFileList.addAll(javaComponent.generateCorbaComponentSourceFiles());
			}
			for (Iterator j = javaModel.findAllHomes().iterator(); j.hasNext();)
			{
				HomeDef javaHome = (HomeDef) j.next();
				sourceFileList.addAll(javaHome.generateCorbaComponentSourceFiles());
			}

			// Save all source file objects
			Code.writeSourceCodeFiles(uiDriver, parameters.getOutDir(), sourceFileList);
			logger.fine("leave");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new CcmtoolsException("Error in generateCorbaComponent: " + e.getMessage());
		}	
	}


	private List generateCorbaConverterList(ModelRepository javaModel) 
		throws CcmtoolsException
	{
		logger.fine("enter");
		try
		{
			List sourceFileList = new ArrayList();
			for (Iterator j = javaModel.findAllInterfaces().iterator(); j.hasNext();)
			{
				InterfaceDef javaIface = (InterfaceDef) j.next();
				sourceFileList.addAll(javaIface.generateCorbaComponentSourceFiles());
			}

			for (Iterator j = javaModel.findAllEnums().iterator(); j.hasNext();)
			{
				EnumDef javaEnum = (EnumDef) j.next();
				sourceFileList.addAll(javaEnum.generateCorbaComponentSourceFiles());
			}
			for (Iterator j = javaModel.findAllStructs().iterator(); j.hasNext();)
			{
				StructDef javaStruct = (StructDef) j.next();
				sourceFileList.addAll(javaStruct.generateCorbaComponentSourceFiles());
			}
			for (Iterator j = javaModel.findAllExceptions().iterator(); j.hasNext();)
			{
				ExceptionDef javaException = (ExceptionDef) j.next();
				sourceFileList.addAll(javaException.generateCorbaComponentSourceFiles());
			}
			for (Iterator j = javaModel.findAllSequences().iterator(); j.hasNext();)
			{
				SequenceDef javaSequence = (SequenceDef) j.next();
				sourceFileList.addAll(javaSequence.generateCorbaComponentSourceFiles());
			}
			for (Iterator j = javaModel.findAllArrays().iterator(); j.hasNext();)
			{
				ArrayDef javaArray = (ArrayDef) j.next();
				sourceFileList.addAll(javaArray.generateCorbaComponentSourceFiles());
			}
			return sourceFileList;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new CcmtoolsException("Error in generateClientLib: " + e.getMessage());
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
