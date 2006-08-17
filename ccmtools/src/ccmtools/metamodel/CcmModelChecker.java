
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
import ccmtools.metamodel.ComponentIDL.MSupportsDef;
import ccmtools.ui.Driver;
import ccmtools.utils.Code;
import ccmtools.utils.Text;

/**
 * This CCM model checker defines the IDL3 subset used by the CCM Tools.
 * For a given CCM model, the checker reports IDL3 artefacts not supported 
 * by the CCM Tools generator backends, thus, we can reduce roundtrip times
 * for invalid IDL files.
 */
public class CcmModelChecker
{		
    public static final String MODEL_CHECKER_ID = "check";
    public static final String MODEL_CHECKER_TEXT = "Check CCM model";
    
    private static final String TAB = "    ";
    private static final String NL = "\n";
    
    private List<String> errorList = new ArrayList<String>();
    private List<String> warningList = new ArrayList<String>();
    
    /** Command line parameters */
    protected CommandLineParameters parameters;
    
    /** UI driver for generator messages */
    protected Driver uiDriver;
    
    /** Java logging */
    private Logger logger;
        
	public CcmModelChecker(Driver uiDriver, CommandLineParameters parameters)
	{
        this.uiDriver = uiDriver;
        this.parameters = (CommandLineParameters)parameters;    
        logger = Logger.getLogger("ccm.metamodel.checker");
		logger.fine("");
        printVersion(uiDriver);
	}

	public static void printVersion(Driver uiDriver)
	{
	    uiDriver.println("+");
	    uiDriver.println("+ CCM Model Checker, " + Constants.CCMTOOLS_VERSION_TEXT);
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
        return "Invalid CCM Model" + NL + Text.join(NL, getErrorList());
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
                check((MContained)i.next());
            }
        }
    }

    
    /*************************************************************************
     * CCM Model Checker Methods 
     *************************************************************************/

    private void check(MContained in)
    {
        // Don't handle included files (included ModelElements store their filenames)
        if(!in.getSourceFile().equals(""))
        {
            logger.fine("included " + Code.getRepositoryId(in));
            return;
        }
        
        logger.fine("MContained " +  Code.getRepositoryId(in));    
        if(in instanceof MContainer)
        {
            check((MContainer)in);
        }
        else if(in instanceof MConstantDef)
        {
            check((MConstantDef)in);
        }
        else if(in instanceof MTypedefDef)
        {
            check((MTypedefDef)in);
        }
        else if(in instanceof MExceptionDef)
        {
            check((MExceptionDef)in);
        }
        else
        {
            throw new RuntimeException("Unknown contained type " + in);
        }
    }
    
    private void check(MContainer in)
    {    
        logger.fine("MContainer");    
        if(in instanceof MModuleDef)
        {
            check((MModuleDef)in);
        }
        else if(in instanceof MInterfaceDef)
        {
            check((MInterfaceDef)in);
        }
        else if(in instanceof MValueDef)
        {
            check((MValueDef)in);               
        }
        else
        {
            throw new RuntimeException("Unknown container type " + in);
        }
    }
    
    private void check(MModuleDef in)
    {
        logger.fine("MModuleDef");
        for(Iterator i = in.getContentss().iterator(); i.hasNext();)
        {
            check((MContained)i.next());
        }
    }
    
    private void check(MTypedefDef in)
    {
        logger.fine("MTypedefDef");    
        if(in instanceof MEnumDef)
        {
            check((MEnumDef)in);
        }   
        else if(in instanceof MStructDef)
        {
            check((MStructDef)in);
        }
        else if(in instanceof MAliasDef)
        {
            check((MAliasDef)in);
        }
        // TODO: MUnionDef
        else
        {
            throw new RuntimeException("Unknown MTypedefDef " + in);
        }
    }

    private void check(MAliasDef in)
    {
        MTyped typed = (MTyped) in;
        MIDLType innerIdlType = typed.getIdlType();
        check(innerIdlType);
    }
    	
	private void check(MIDLType in)
	{
        logger.fine("MIDLType"); 
		if(in instanceof MPrimitiveDef)
		{
			check((MPrimitiveDef)in);
		}
		else if(in instanceof MStringDef)
		{
			check((MStringDef)in);
		}
		else if(in instanceof MWstringDef)
		{
			check((MWstringDef)in);
		}
		else if(in instanceof MFixedDef)
		{
			check((MFixedDef)in);
		}		
		else if(in instanceof MEnumDef)
		{			
			check((MEnumDef)in);
		}
		else if(in instanceof MSequenceDef)
		{
			check((MSequenceDef)in);
		}
        else if(in instanceof MArrayDef)
        {
            check((MArrayDef)in);
        }
		else if(in instanceof MStructDef)
		{			
            check((MStructDef)in);
		}
		else if(in instanceof MInterfaceDef)
		{
            check((MInterfaceDef)in);
		}
		else if(in instanceof MAliasDef)
		{
			check((MAliasDef)in);
		}
		else
		{
			throw new RuntimeException("Unknown MIDLType " + in);
		}
	}


    private void check(MPrimitiveDef in)
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
	
    
	private void check(MStringDef in)
	{
        logger.fine("MStringDef"); 
		if(in.getBound() != null)
		{
            addError("bounded string types");
		}
	}
	
	private void check(MWstringDef in)
	{
        logger.fine("MWstringDef"); 
		if(in.getBound() != null)
		{
            addError("bounded wstring types");
		}
	}
	
	private void check(MFixedDef in)
	{
        logger.fine("MFixedDef"); 
        addError("fixed-point types");
	}
	
	private void check(MConstantDef in)
	{
        logger.fine("MConstantDef"); 
		MIDLType idlType = in.getIdlType();
//		Object value = in.getConstValue();
		check(idlType);
    }
	
	private void check(MEnumDef in)
	{
        logger.fine("MEnumDef"); 
//		for(Iterator i = in.getMembers().iterator(); i.hasNext();)
//		{
//			String member = (String)i.next();
//        }	
	}
	
	private void check(MStructDef in)
	{
        logger.fine("MStructDef"); 
		for(Iterator i = in.getMembers().iterator(); i.hasNext();)
		{
			MFieldDef member = (MFieldDef)i.next();
			check(member.getIdlType());
        }		
	}

	private void check(MExceptionDef in)
	{
        logger.fine("MExceptionDef"); 
		for(Iterator i = in.getMembers().iterator(); i.hasNext();)
		{
			MFieldDef member = (MFieldDef)i.next();
			check(member.getIdlType());
        }		
	}

	private void check(MArrayDef in)
	{
        logger.fine("MArrayDef"); 
//		check(in.getIdlType());
//		for(int i=0; i < in.getBounds().size(); i++)
//		{
//			Long bound = (Long)in.getBounds().get(i);
//            
//        }
	}
	
	private void check(MSequenceDef in)
	{
        logger.fine("MSequenceDef"); 
		if(in.getBound() != null)
		{
            addError("bounded sequence types");
		}
		check(in.getIdlType());
	}

    private void check(MAttributeDef in)
    {
        logger.fine("MAttributeDef"); 
//        if(in.isReadonly())
//        {
//
//        }
        check(in.getIdlType());
        for(Iterator i = in.getGetExceptions().iterator(); i.hasNext();)
        {
            MExceptionDef ex = (MExceptionDef)i.next();
            check(ex);
        }
        for(Iterator i = in.getSetExceptions().iterator(); i.hasNext();)
        {
            MExceptionDef ex = (MExceptionDef)i.next();
            check(ex);
        }        
    }
    
	private void check(MOperationDef in)
	{
        logger.fine("MOperationDef"); 
		if(in.isOneway())
		{
            addError("'oneway' operations (e.g. " + in.getIdentifier() + "())");
		}
		check(in.getIdlType());
		for(Iterator i = in.getParameters().iterator(); i.hasNext();)
		{
			MParameterDef parameter = (MParameterDef)i.next();						
			check(parameter.getIdlType());
		}
		for(Iterator i = in.getExceptionDefs().iterator(); i.hasNext();)
		{
			MExceptionDef ex = (MExceptionDef)i.next();
			check(ex);
		}
		if(in.getContexts() != null)
		{
            addError("operations with 'context' (e.g. " + in.getIdentifier() + "())");
		}
	}
	
	private void check(MInterfaceDef in)
    {
        logger.fine("MInterfaceDef");
        if (in instanceof MHomeDef)
        {
            check((MHomeDef) in);
        }
        else if (in instanceof MComponentDef)
        {
            check((MComponentDef) in);
        }
        else // MInterfaceDef
        {
            if (in.isAbstract())
            {
                addError("'abstract' interfaces (e.g. " + in.getIdentifier() + ")");
            }
            if (in.isLocal())
            {
                addError("'local' interfaces (e.g. " + in.getIdentifier() + ")");
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
                    check((MAttributeDef) contained);
                }
                else if (contained instanceof MOperationDef)
                {
                    check((MOperationDef) contained);
                }
                else if (contained instanceof MConstantDef)
                {
                    check((MConstantDef) contained);
                }
                else if (contained instanceof MIDLType)
                {
                    addError("type definitions within interfaces (e.g. " + in.getIdentifier() + "::"
                            + contained.getIdentifier() + ")");
                }
                else if (contained instanceof MExceptionDef)
                {
                    addError("exception definitions within interfaces (e.g. " + in.getIdentifier() + "::"
                            + contained.getIdentifier() + ")");
                }
                else
                {
                    throw new RuntimeException("Unhandled containment in MInterfaceDef " + in.getRepositoryId());
                }
            }
        }
    }

	private void check(MComponentDef in)
	{
        logger.fine("MComponentDef"); 		
//		for(Iterator i = in.getBases().iterator(); i.hasNext();)
//		{
//			MInterfaceDef baseInterface = (MInterfaceDef)i.next();
//
//		}
//		for(Iterator i = in.getHomes().iterator(); i.hasNext();)
//		{
//			MHomeDef home = (MHomeDef)i.next();
//
//        }
//		for(Iterator i = in.getSupportss().iterator(); i.hasNext();)
//		{
//			MSupportsDef supports = (MSupportsDef)i.next();
//			MInterfaceDef supportedInterface = (MInterfaceDef)supports.getSupports();
//
//        }
//		for(Iterator i = in.getFacets().iterator(); i.hasNext();)
//		{
//			MProvidesDef provides = (MProvidesDef)i.next();
//			
//		}
//		for(Iterator i = in.getReceptacles().iterator(); i.hasNext();)
//		{
//			MUsesDef uses = (MUsesDef)i.next();
//			if(uses.isMultiple())
//			{
//			
//			}
//			else
//			{
//			
//			}
//		}
        
        for(Iterator i = in.getEmitss().iterator(); i.hasNext();)
        {
            MEmitsDef emits = (MEmitsDef)i.next();
            check(emits);
        }
        for(Iterator i = in.getPublishess().iterator(); i.hasNext();)
        {
            MPublishesDef publishes = (MPublishesDef)i.next();
            check(publishes);
        }
        for(Iterator i = in.getConsumess().iterator(); i.hasNext();)
        {
            MConsumesDef consumes = (MConsumesDef)i.next();
            check(consumes);
        }

        for(Iterator i = in.getContentss().iterator(); i.hasNext();)
		{
			MContained contained = (MContained)i.next();					
			if(contained instanceof MAttributeDef)
			{
				check((MAttributeDef)contained);
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

	private void check(MHomeDef in)
	{
        logger.fine("MHomeDef"); 
//		for(Iterator i = in.getBases().iterator(); i.hasNext();)
//		{
//			MHomeDef baseHome = (MHomeDef)i.next();
//
//        }
//		MComponentDef component = in.getComponent();		
		for(Iterator i = in.getSupportss().iterator(); i.hasNext();)
		{
			MSupportsDef supports = (MSupportsDef)i.next();
			MInterfaceDef supportedInterface = (MInterfaceDef)supports.getSupports();
            addError("supported interfaces for homes (e.g. " 
                    + in.getIdentifier() + "::" + supportedInterface.getIdentifier() + ")");
		}
        MValueDef primaryKey = in.getPrimary_Key();
        if(primaryKey != null)
        {
            addError("primary keys for homes (e.g. " 
                    + in.getIdentifier() + " primarykey " + Code.getRepositoryId(primaryKey) + ")");
        }
		for(Iterator i = in.getFactories().iterator(); i.hasNext();)
		{
			MFactoryDef factory = (MFactoryDef)i.next();
			check(factory);
		}
		for(Iterator i = in.getFinders().iterator(); i.hasNext();)
		{
			MFinderDef finder = (MFinderDef)i.next();
			check(finder);
        }
        for(Iterator i = in.getContentss().iterator(); i.hasNext();)
        {
            MContained contained = (MContained)i.next();                    
            if(contained instanceof MAttributeDef)
            {
                check((MAttributeDef)contained);
            }
            else if(contained instanceof MOperationDef)
            {               
                addError("operation definitions within homes (e.g. " 
                        + in.getIdentifier() + "::" + contained.getIdentifier() + ")");
            }
            else if(contained instanceof MConstantDef)
            {
                addError("constant definitions within homes (e.g. " 
                        + in.getIdentifier() + "::" + contained.getIdentifier() + ")");
            }
            else if(contained instanceof MIDLType)
            {
                addError("type definitions within homes (e.g. " 
                        + in.getIdentifier() + "::" + contained.getIdentifier() + ")");
            }
            else if(contained instanceof MExceptionDef)
            {
                addError("exception definitions within homes (e.g. " 
                        + in.getIdentifier() + "::" + contained.getIdentifier() + ")");
            }            
            else
            {
                throw new RuntimeException("Unhandled containment in MHomeDef " + in.getRepositoryId());
            }
        }
	}
	
	private void check(MFactoryDef in)
	{
        logger.fine("MFactoryDef"); 
		for(Iterator i = in.getParameters().iterator(); i.hasNext();)
		{
			MParameterDef parameter = (MParameterDef)i.next();
			check(parameter.getIdlType());
		}
		for(Iterator i = in.getExceptionDefs().iterator(); i.hasNext();)
		{
			MExceptionDef ex = (MExceptionDef)i.next();
			check(ex);
		}
		if(in.getContexts() != null)
		{
            addError("factory methods with 'context' (e.g. " + in.getIdentifier() + "())");
		}
	}
	
	private void check(MFinderDef in)
	{
        logger.fine("MFinderDef"); 
        addError("finder methods within homes (e.g. " 
                + in.getHome().getIdentifier() + "::" + in.getIdentifier() + "())");
	}
	
	private void check(MValueDef in)
	{       
        logger.fine("MValueDef");
        if(in instanceof MEventDef)
        {            
            check((MEventDef)in);
        }
        else // MValueDef
        {
            addError("'valuetype' definitions (e.g. " + Code.getRepositoryId(in.getIdentifier()) + ")");
        }
    }

    private void check(MEventDef in)
    {
        logger.fine("MEventDef");
        addError("'eventtype' definitions (e.g. " + Code.getRepositoryId(in.getIdentifier()) + ")");
    }
    
    private void check(MEmitsDef in)
    {
        logger.fine("MEmitsDef");
        addError("'emits' definitions within components (e.g. " 
                + Code.getRepositoryId(in.getComponent().getIdentifier()) + ")");
    }
    
    private void check(MPublishesDef in)
    {
        logger.fine("MPublishesDef");
        addError("'publishes' definitions within components (e.g. " 
                + Code.getRepositoryId(in.getComponent().getIdentifier()) + ")");
    }
    
    private void check(MConsumesDef in)
    {
        logger.fine("MConsumesDef");
        addError("'consumes' definitions within components (e.g. " 
                + Code.getRepositoryId(in.getComponent().getIdentifier()) + ")");
    }
    
    
    
    /*************************************************************************
     * Utility Methods 
     *************************************************************************/

}
