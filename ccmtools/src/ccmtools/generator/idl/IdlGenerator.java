package ccmtools.generator.idl;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import ccmtools.CcmtoolsException;
import ccmtools.Constants;
import ccmtools.UI.Driver;
import ccmtools.generator.idl.metamodel.ConstantDef;
import ccmtools.generator.idl.metamodel.EnumDef;
import ccmtools.generator.idl.metamodel.ModelRepository;
import ccmtools.generator.idl.metamodel.StructDef;
import ccmtools.generator.idl.metamodel.TypedefDef;
import ccmtools.generator.idl.ui.CommandLineParameters;
import ccmtools.utils.Code;
import ccmtools.utils.SourceFile;

public class IdlGenerator
{
	public static final String IDL3_ID = "idl3";
	public static final String IDL3_MIRROR_ID = "idl3mirror";
	public static final String IDL2_ID = "idl2";
	
	/** UI driver for generator messages */
	protected Driver uiDriver;
	
	/** Command line parameters */
	protected CommandLineParameters parameters;
	
	/** Java standard logger object */
	protected Logger logger;
	
	
	public IdlGenerator(CommandLineParameters parameters, Driver uiDriver)
	{
		this.uiDriver = uiDriver;
		this.parameters = (CommandLineParameters)parameters;		                           
        logger = Logger.getLogger("ccm.generator.idl");
        logger.fine("");
        printVersion();
	}
		
    private void printVersion()
    {
        uiDriver.println("IDL Generator, " + Constants.VERSION_TEXT);
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
			List<SourceFile> sourceFileList = new ArrayList<SourceFile>();
			for(TypedefDef idlTypedef : idlModelRepo.findAllTypedefs())
			{
				sourceFileList.addAll(idlTypedef.generateIdl3SourceFiles());
			}
			for(EnumDef idlEnum : idlModelRepo.findAllEnums())
			{
				sourceFileList.addAll(idlEnum.generateIdl3SourceFiles());
			}
			for(StructDef idlStruct : idlModelRepo.findAllStructs())
			{
				sourceFileList.addAll(idlStruct.generateIdl3SourceFiles());
			}
			for(ConstantDef idlConstant : idlModelRepo.findAllGlobalConstants())
			{
				sourceFileList.addAll(idlConstant.generateIdl3SourceFiles());
			}
			
			// Save all source file objects
			Code.writeSourceCodeFiles(uiDriver, parameters.getOutDir(), sourceFileList);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new CcmtoolsException(e.getMessage());
		}
		logger.fine("end");
	}

	
	public void generateIdl3Mirror(ModelRepository idlModel)
		throws CcmtoolsException
	{
	}
		
	public void generateIdl2(ModelRepository idlModel)
		throws CcmtoolsException
	{
		
	}
}
