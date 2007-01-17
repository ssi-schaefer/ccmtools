
package ccmtools.metamodel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import ccmtools.Constants;
import ccmtools.metamodel.BaseIDL.MAliasDef;
import ccmtools.metamodel.BaseIDL.MArrayDef;
import ccmtools.metamodel.BaseIDL.MAttributeDef;
import ccmtools.metamodel.BaseIDL.MConstantDef;
import ccmtools.metamodel.BaseIDL.MContained;
import ccmtools.metamodel.BaseIDL.MContainer;
import ccmtools.metamodel.BaseIDL.MEnumDef;
import ccmtools.metamodel.BaseIDL.MExceptionDef;
import ccmtools.metamodel.BaseIDL.MFieldDef;
import ccmtools.metamodel.BaseIDL.MFixedDef;
import ccmtools.metamodel.BaseIDL.MIDLType;
import ccmtools.metamodel.BaseIDL.MInterfaceDef;
import ccmtools.metamodel.BaseIDL.MModuleDef;
import ccmtools.metamodel.BaseIDL.MOperationDef;
import ccmtools.metamodel.BaseIDL.MParameterDef;
import ccmtools.metamodel.BaseIDL.MPrimitiveDef;
import ccmtools.metamodel.BaseIDL.MPrimitiveKind;
import ccmtools.metamodel.BaseIDL.MSequenceDef;
import ccmtools.metamodel.BaseIDL.MStringDef;
import ccmtools.metamodel.BaseIDL.MStructDef;
import ccmtools.metamodel.BaseIDL.MTyped;
import ccmtools.metamodel.BaseIDL.MTypedefDef;
import ccmtools.metamodel.BaseIDL.MUnionDef;
import ccmtools.metamodel.BaseIDL.MValueBoxDef;
import ccmtools.metamodel.BaseIDL.MValueDef;
import ccmtools.metamodel.BaseIDL.MWstringDef;
import ccmtools.metamodel.ComponentIDL.MComponentDef;
import ccmtools.metamodel.ComponentIDL.MConsumesDef;
import ccmtools.metamodel.ComponentIDL.MEmitsDef;
import ccmtools.metamodel.ComponentIDL.MEventDef;
import ccmtools.metamodel.ComponentIDL.MFactoryDef;
import ccmtools.metamodel.ComponentIDL.MFinderDef;
import ccmtools.metamodel.ComponentIDL.MHomeDef;
import ccmtools.metamodel.ComponentIDL.MPublishesDef;
import ccmtools.ui.UserInterfaceDriver;
import ccmtools.utils.Text;

/**
 * This CCM model checker defines the IDL3 subset used by the CCM Tools.
 * For a given CCM model, the checker reports IDL3 artefacts not supported 
 * by the CCM Tools generator backends, thus, we can reduce roundtrip times
 * for invalid IDL files.
 */
public class CcmModelValidator
{		
    public static final String MODEL_VALIDATOR_ID = "validator";
    public static final String MODEL_VALIDATOR_TEXT = "Validate CCM model";
    
    private static final String TAB = "    ";
    private static final String NL = "\n";
    
    private List<String> errorList = new ArrayList<String>();
    private List<String> warningList = new ArrayList<String>();
    
    /** Command line parameters */
    protected CommandLineParameters parameters;
    
    /** UI driver for generator messages */
    protected UserInterfaceDriver uiDriver;
    
    /** Java logging */
    private Logger logger;
        
	public CcmModelValidator(UserInterfaceDriver uiDriver, CommandLineParameters parameters)
	{
        this.uiDriver = uiDriver;
        this.parameters = (CommandLineParameters)parameters;    
        logger = Logger.getLogger("ccm.metamodel.validator");
		logger.fine("");
        printVersion(uiDriver);
	}

	public static void printVersion(UserInterfaceDriver uiDriver)
	{
	    uiDriver.println("+");
	    uiDriver.println("+ CCM Model Validator, " + Constants.CCMTOOLS_VERSION_TEXT);
	    uiDriver.println("+");
	    uiDriver.println("+");
	    uiDriver.println(Constants.CCMTOOLS_COPYRIGHT_TEXT);
	}
    
    public List<String> getErrorList()
    {
        return errorList;
    }
    
    public void addError(String msg)
    {
        getErrorList().add(TAB + Constants.CCMTOOLS_VERSION_TEXT 
                + " does not support " + msg + "!");
    }
    
    public String getErrorMessage()
    {
        return "Failures:" + NL + Text.join(NL, getErrorList());
    }
    
    public List<String> getWarningList()
    {
        return warningList;
    }
    
    public boolean isValidModel(MContainer ccmModel)
    {
        traverse(ccmModel);
        return (getErrorList().size() == 0 && getWarningList().size() == 0);
    }
    
    
    public void traverse(MContainer ccmModel)
    {
        if(ccmModel == null)
        {
            return;
        }        
        else
        {
            logger.fine("CCM Model: " + ccmModel.getIdentifier() + " (" 
                    + ccmModel.getContentss().size() + " top level elements)");    
            for(Iterator i = ccmModel.getContentss().iterator(); i.hasNext();)
            {
                validate((MContained)i.next());
            }
        }
    }

    
    /*************************************************************************
     * CCM Model Checker Methods 
     *************************************************************************/
    
    private void validate(MContained in)
    {
        // Don't handle included files (included ModelElements store their filenames)
        if(!in.getSourceFile().equals(""))
        {
            logger.fine("included " + CcmModelHelper.getRepositoryId(in));
            return;
        }
        
        logger.fine("MContained " +  CcmModelHelper.getRepositoryId(in));    

        if(in instanceof MContainer)
        {
            validate((MContainer)in);
        }
        else if(in instanceof MConstantDef)
        {
            validate((MConstantDef)in);
        }
        else if(in instanceof MTypedefDef)
        {
            validate((MTypedefDef)in);
        }
        else if(in instanceof MAttributeDef)
        {
            validate((MAttributeDef)in);
        }
        else if(in instanceof MExceptionDef)
        {
            validate((MExceptionDef)in);
        }
        else if(in instanceof MOperationDef)
        {
            validate((MOperationDef)in);
        }
        // MValueMemberDef
        else
        {
            throw new RuntimeException("Unknown contained type " + in);
        }
    }
    
    private void validate(MContainer in)
    {    
        logger.fine("MContainer");    
        if(in instanceof MModuleDef)
        {
            validate((MModuleDef)in);
        }
        else if(in instanceof MInterfaceDef)
        {
            validate((MInterfaceDef)in);
        }
        else if(in instanceof MValueDef)
        {
            validate((MValueDef)in);               
        }
        else
        {
            throw new RuntimeException("Unknown container type " + in);
        }
    }
    
    private void validate(MModuleDef in)
    {
        logger.fine("MModuleDef");
        for(Iterator i = in.getContentss().iterator(); i.hasNext();)
        {
            validate((MContained)i.next());
        }
    }

    
    private void validate(MIDLType in)
    {
        logger.fine("MIDLType"); 
        if(in instanceof MPrimitiveDef)
        {
            validate((MPrimitiveDef)in);
        }
        else if(in instanceof MStringDef)
        {
            validate((MStringDef)in);
        }
        else if(in instanceof MWstringDef)
        {
            validate((MWstringDef)in);
        }
        else if(in instanceof MFixedDef)
        {
            validate((MFixedDef)in);
        }       
//      else if(in instanceof MEnumDef)
//      {           
//          validate((MEnumDef)in);
//      }
        else if(in instanceof MSequenceDef)
        {
            validate((MSequenceDef)in);
        }
        else if(in instanceof MArrayDef)
        {
            validate((MArrayDef)in);
        }
//      else if(in instanceof MStructDef)
//      {           
//            validate((MStructDef)in);
//      }
        else if(in instanceof MValueDef)
        {
            validate((MValueDef)in);
        }
        else if(in instanceof MInterfaceDef)
        {
            validate((MInterfaceDef)in);
        }
        else if(in instanceof MTypedefDef)
        {
            validate((MTypedefDef)in);
        }
        else
        {
            throw new RuntimeException("Unknown MIDLType " + in);
        }
    }

    
    private void validate(MTypedefDef in)
    {
        logger.fine("MTypedefDef");    
        if(in instanceof MEnumDef)
        {
            validate((MEnumDef)in);
        }   
        else if(in instanceof MStructDef)
        {
            validate((MStructDef)in);
        }
        else if(in instanceof MUnionDef)
        {
            validate((MUnionDef)in);
        }
        else if(in instanceof MValueBoxDef)
        {
            validate((MValueBoxDef)in);
        }
        else if(in instanceof MAliasDef)
        {
            validate((MAliasDef)in);
        }
        else
        {
            throw new RuntimeException("Unknown MTypedefDef " + in);
        }
    }

    private void validate(MAliasDef in)
    {
        MTyped typed = (MTyped) in;
        MIDLType innerIdlType = typed.getIdlType();
        validate(innerIdlType);
    }
    	


    /*
     * Basic Types
     */
    
    private void validate(MPrimitiveDef in)
	{
        logger.fine("MPrimitiveDef"); 		
        StringBuilder msg = new StringBuilder();
        msg.append("CCM Tools does not support 64bit '");
        if(in.getKind() == MPrimitiveKind.PK_LONGLONG)
        {
            addError("64bit 'long long' types");
        }
        else if(in.getKind() == MPrimitiveKind.PK_ULONGLONG)
        {
            addError("64bit 'unsigned long long' types");
        }
        else if(in.getKind() == MPrimitiveKind.PK_LONGDOUBLE)
        {
            addError("64bit 'long double' types");
        }
	}
	
    
    
    
    /*
     * Template Types
     */
    
	private void validate(MStringDef in)
	{
        logger.fine("MStringDef"); 
		if(in.getBound() != null)
		{
            addError("'bounded string' types");
		}
	}
	
	private void validate(MWstringDef in)
	{
        logger.fine("MWstringDef"); 
		if(in.getBound() != null)
		{
            addError("'bounded wstring' types");
		}
	}
	
	private void validate(MFixedDef in)
	{
        logger.fine("MFixedDef"); 
        addError("'fixed-point' types");
	}
	
    
    /*
     * Constructed Types
     */
    
    private void validate(MStructDef in)
    {
        logger.fine("MStructDef"); 
        for(Iterator i = in.getMembers().iterator(); i.hasNext();)
        {
            MFieldDef member = (MFieldDef)i.next();
            validate(member.getIdlType());
        }       
    }
        
    private void validate(MUnionDef in)
    {
        logger.fine("MUnionDef");
        addError("'union' types like " + in.getIdentifier());
    }
    
	private void validate(MEnumDef in)
	{
        logger.fine("MEnumDef"); 
	}
	

    
    private void validate(MConstantDef in)
    {
        logger.fine("MConstantDef"); 
        MIDLType idlType = in.getIdlType();
        validate(idlType);
    }
    

    
	private void validate(MExceptionDef in)
	{
        logger.fine("MExceptionDef"); 
		for(Iterator i = in.getMembers().iterator(); i.hasNext();)
		{
			MFieldDef member = (MFieldDef)i.next();
			validate(member.getIdlType());
        }		
	}

	private void validate(MArrayDef in)
	{
        logger.fine("MArrayDef"); 
        addError("'array' types");
	}
	
	private void validate(MSequenceDef in)
	{
        logger.fine("MSequenceDef"); 
		if(in.getBound() != null)
		{
            addError("'bounded sequence' types");
		}
		validate(in.getIdlType());
	}

    private void validate(MAttributeDef in)
    {
        logger.fine("MAttributeDef"); 
        if(in.isReadonly())
        {
            addError("'readonly' attributes like " + 
                    in.getDefinedIn().getIdentifier() + "::" +
                    in.getIdentifier());
        }
        validate(in.getIdlType());
        for(Iterator i = in.getGetExceptions().iterator(); i.hasNext();)
        {
            MExceptionDef ex = (MExceptionDef)i.next();
            validate(ex);
        }
        for(Iterator i = in.getSetExceptions().iterator(); i.hasNext();)
        {
            MExceptionDef ex = (MExceptionDef)i.next();
            validate(ex);
        }        
    }
    
	private void validate(MOperationDef in)
	{
        logger.fine("MOperationDef"); 
//		if(in.isOneway())
//		{
//            addError("'oneway' operations like " + 
//                    in.getDefinedIn().getIdentifier() + "::" +
//                    in.getIdentifier() + "()'");
//		}
		validate(in.getIdlType());
		for(Iterator i = in.getParameters().iterator(); i.hasNext();)
		{
			MParameterDef parameter = (MParameterDef)i.next();						
			validate(parameter.getIdlType());
		}
		for(Iterator i = in.getExceptionDefs().iterator(); i.hasNext();)
		{
			MExceptionDef ex = (MExceptionDef)i.next();
			validate(ex);
		}
		if(in.getContexts() != null)
		{
            addError("operations with 'context' like '" +
                    in.getDefinedIn().getIdentifier() + "::" +
                    in.getIdentifier() + "()'");
		}
	}
	
	private void validate(MInterfaceDef in)
    {
        logger.fine("MInterfaceDef");
        if (in instanceof MHomeDef)
        {
            validate((MHomeDef) in);
        }
        else if (in instanceof MComponentDef)
        {
            validate((MComponentDef) in);
        }
        else // MInterfaceDef
        {
            if (in.isAbstract())
            {
                addError("'abstract' interfaces like '" + in.getIdentifier() + "'");
            }
            if (in.isLocal())
            {
                addError("'local' interfaces like '" + in.getIdentifier() + "'");
            }
//            for (Iterator i = in.getBases().iterator(); i.hasNext();)
//            {
//                MInterfaceDef baseInterface = (MInterfaceDef) i.next();
//
//            }
            for (Iterator i = in.getContentss().iterator(); i.hasNext();)
            {
                MContained contained = (MContained) i.next();
                if (contained instanceof MAttributeDef)
                {
                    validate((MAttributeDef) contained);
                }
                else if (contained instanceof MOperationDef)
                {
                    validate((MOperationDef) contained);
                }
                else if (contained instanceof MConstantDef)
                {
                    validate((MConstantDef) contained);
                }
                else if (contained instanceof MIDLType)
                {
                    addError("type definitions within interfaces like '" + 
                            in.getIdentifier() + "::" +
                            contained.getIdentifier() + "'");
                }
                else if (contained instanceof MExceptionDef)
                {
                    addError("'exception' definitions within interfaces like '" + 
                            in.getIdentifier() + "::" +
                            contained.getIdentifier() + "'");
                }
                else
                {
                    throw new RuntimeException("Unhandled containment in MInterfaceDef " + in.getRepositoryId());
                }
            }
        }
    }

	private void validate(MComponentDef in)
	{
        logger.fine("MComponentDef"); 		
        
        if(in.getBases().size() != 0)
        {
            addError("component inheritance like " + in.getIdentifier());
        }

        for(Iterator i = in.getEmitss().iterator(); i.hasNext();)
        {
            MEmitsDef emits = (MEmitsDef)i.next();
            validate(emits);
        }
        for(Iterator i = in.getPublishess().iterator(); i.hasNext();)
        {
            MPublishesDef publishes = (MPublishesDef)i.next();
            validate(publishes);
        }
        for(Iterator i = in.getConsumess().iterator(); i.hasNext();)
        {
            MConsumesDef consumes = (MConsumesDef)i.next();
            validate(consumes);
        }

        for(Iterator i = in.getContentss().iterator(); i.hasNext();)
		{
			MContained contained = (MContained)i.next();					
			if(contained instanceof MAttributeDef)
			{
				validate((MAttributeDef)contained);
			}
            else if(contained instanceof MOperationDef)
            {               
                addError("operation definitions within components (e.g. " 
                        + in.getIdentifier() + "::" + contained.getIdentifier() + ")");
            }
            else if(contained instanceof MConstantDef)
            {
                addError("constant definitions within components (e.g. " 
                        + in.getIdentifier() + "::" + contained.getIdentifier() + ")");
            }
            else if(contained instanceof MIDLType)
            {
                addError("type definitions within components (e.g. " 
                        + in.getIdentifier() + "::" + contained.getIdentifier() + ")");
            }
            else if(contained instanceof MExceptionDef)
            {
                addError("exception definitions within components (e.g. " 
                        + in.getIdentifier() + "::" + contained.getIdentifier() + ")");
            }
			else
			{
				throw new RuntimeException("Unhandled containment in MComponentDef " + in.getRepositoryId());
			}
		}
	}

	private void validate(MHomeDef in)
	{
        logger.fine("MHomeDef"); 
        
        if(in.getBases().size() > 0)
        {
            addError("home inheritance like " + in.getIdentifier());
        }

        for(Iterator i = in.getSupportss().iterator(); i.hasNext();)
		{
			MInterfaceDef supports = (MInterfaceDef)i.next();
            addError("supported interfaces for homes like " 
                    + in.getIdentifier() + "::" + supports.getIdentifier());
		}
        MValueDef primaryKey = in.getPrimary_Key();
        if(primaryKey != null)
        {
            addError("primary keys for homes like " 
                    + in.getIdentifier() + " primarykey " + CcmModelHelper.getRepositoryId(primaryKey) + ")");
        }
		for(Iterator i = in.getFactories().iterator(); i.hasNext();)
		{
			MFactoryDef factory = (MFactoryDef)i.next();
			validate(factory);
		}
		for(Iterator i = in.getFinders().iterator(); i.hasNext();)
		{
			MFinderDef finder = (MFinderDef)i.next();
			validate(finder);
        }
        for(Iterator i = in.getContentss().iterator(); i.hasNext();)
        {
            MContained contained = (MContained)i.next();                    
            if(contained instanceof MAttributeDef)
            {
                validate((MAttributeDef)contained);
            }
            else if(contained instanceof MOperationDef)
            {               
                addError("operation definitions within homes like " 
                        + in.getIdentifier() + "::" + contained.getIdentifier());
            }
            else if(contained instanceof MConstantDef)
            {
                addError("constant definitions within homes like " 
                        + in.getIdentifier() + "::" + contained.getIdentifier());
            }
            else if(contained instanceof MIDLType)
            {
                addError("type definitions within homes like " 
                        + in.getIdentifier() + "::" + contained.getIdentifier());
            }
            else if(contained instanceof MExceptionDef)
            {
                addError("exception definitions within homes like " 
                        + in.getIdentifier() + "::" + contained.getIdentifier());
            }            
            else
            {
                throw new RuntimeException("Unhandled containment in MHomeDef " + in.getRepositoryId());
            }
        }
	}
	
	private void validate(MFactoryDef in)
	{
        logger.fine("MFactoryDef"); 
		for(Iterator i = in.getParameters().iterator(); i.hasNext();)
		{
			MParameterDef parameter = (MParameterDef)i.next();
			validate(parameter.getIdlType());
		}
		for(Iterator i = in.getExceptionDefs().iterator(); i.hasNext();)
		{
			MExceptionDef ex = (MExceptionDef)i.next();
			validate(ex);
		}
		if(in.getContexts() != null)
		{
            addError("factory methods with 'context' like " + in.getIdentifier());
		}
	}
	
	private void validate(MFinderDef in)
	{
        logger.fine("MFinderDef"); 
        addError("finder methods within homes like " +
                in.getHome().getIdentifier() + "::" + 
                in.getIdentifier() + "())");
	}
	
	private void validate(MValueDef in)
	{       
        logger.fine("MValueDef");
        if(in instanceof MEventDef)
        {            
            validate((MEventDef)in);
        }
        else // MValueDef
        {
            addError("'valuetype' definitions like " + in.getIdentifier());
        }
    }
    
    private void validate(MValueBoxDef in)
    {
        logger.fine("MValueBoxDef");
        addError("boxed 'valuetype' definitions like " + in.getIdentifier());        
    }

    private void validate(MEventDef in)
    {
        logger.fine("MEventDef");
        addError("'eventtype' definitions like " + in.getIdentifier());
    }
    
    private void validate(MEmitsDef in)
    {
        logger.fine("MEmitsDef");
        addError("'emits' definitions within components like " + in.getComponent().getIdentifier());
    }
    
    private void validate(MPublishesDef in)
    {
        logger.fine("MPublishesDef");
        addError("'publishes' definitions within components like " + in.getComponent().getIdentifier());
    }
    
    private void validate(MConsumesDef in)
    {
        logger.fine("MConsumesDef");
        addError("'consumes' definitions within components like " + in.getComponent().getIdentifier());
    }
    
    
    
    /*************************************************************************
     * Utility Methods 
     *************************************************************************/

}
