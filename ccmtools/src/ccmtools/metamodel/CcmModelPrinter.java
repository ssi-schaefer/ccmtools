package ccmtools.metamodel;

import java.util.Iterator;
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
import ccmtools.metamodel.BaseIDL.MSequenceDef;
import ccmtools.metamodel.BaseIDL.MStringDef;
import ccmtools.metamodel.BaseIDL.MStructDef;
import ccmtools.metamodel.BaseIDL.MTyped;
import ccmtools.metamodel.BaseIDL.MTypedefDef;
import ccmtools.metamodel.BaseIDL.MValueDef;
import ccmtools.metamodel.BaseIDL.MValueMemberDef;
import ccmtools.metamodel.BaseIDL.MWstringDef;
import ccmtools.metamodel.ComponentIDL.MComponentDef;
import ccmtools.metamodel.ComponentIDL.MFactoryDef;
import ccmtools.metamodel.ComponentIDL.MFinderDef;
import ccmtools.metamodel.ComponentIDL.MHomeDef;
import ccmtools.metamodel.ComponentIDL.MProvidesDef;
import ccmtools.metamodel.ComponentIDL.MSupportsDef;
import ccmtools.metamodel.ComponentIDL.MUsesDef;
import ccmtools.ui.UserInterfaceDriver;
import ccmtools.utils.Code;


public class CcmModelPrinter
{		
    public static final String MODEL_PRINTER_ID = "print";
    public static final String MODEL_PRINTER_TEXT = "Print CCM model to stdout";
    
	private static final String TAB = "    ";
//	private static final String NL = "\n";
	    
    /** Command line parameters */
    protected CommandLineParameters parameters;
    
    /** UI driver for generator messages */
    protected UserInterfaceDriver uiDriver;
    
    /** Java logging */
    private Logger logger;
        
	public CcmModelPrinter(UserInterfaceDriver uiDriver, CommandLineParameters parameters)
	{
        this.uiDriver = uiDriver;
        this.parameters = (CommandLineParameters)parameters;    
        logger = Logger.getLogger("ccm.metamodel.printer");
		logger.fine("");		
        printVersion();
	}
    
    private void printVersion()
    {
        uiDriver.println("+");
        uiDriver.println("+ CCM Model Printer, " + Constants.CCMTOOLS_VERSION_TEXT);
        uiDriver.println("+");
        uiDriver.println("+");
        uiDriver.println(Constants.CCMTOOLS_COPYRIGHT_TEXT);
    }
    
    public void traverse(MContainer ccmModel)
    {
        if(ccmModel == null)
        {
            return;
        }        
        else
        {
            logger.fine("CCM Model: " + ccmModel.getIdentifier());    
            logger.fine("Number of contained elements = " + ccmModel.getContentss().size());
            // Usually, there is one toplevel container that hosts the CCM Model
            for(Iterator i = ccmModel.getContentss().iterator(); i.hasNext();)
            {
                print((MContained)i.next());
            }
        }
    }
	

    /*************************************************************************
     * Pretty Printer Methods 
     *************************************************************************/

    private void print(MContained in)
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
            print((MContainer)in);
        }
        else if(in instanceof MConstantDef)
        {
            print((MConstantDef)in);
        }
        else if(in instanceof MTypedefDef)
        {
            print((MTypedefDef)in);
        }
        else if(in instanceof MExceptionDef)
        {
            print((MExceptionDef)in);
        }
        else
        {
            throw new RuntimeException("Unknown contained type " + in);
        }
    }
    
    private void print(MContainer in)
    {    
        logger.fine("MContainer");    
        if(in instanceof MModuleDef)
        {
            print((MModuleDef)in);
        }
        else if(in instanceof MInterfaceDef)
        {
            print((MInterfaceDef)in);
        }
        else if(in instanceof MValueDef)
        {
            print((MValueDef)in);               
        }
        else
        {
            throw new RuntimeException("Unknown container type " + in);
        }
    }
    
    private void print(MModuleDef in)
    {
        logger.fine("MModuleDef");
        println("ModuleDef: " + in.getAbsoluteName());
        for(Iterator i = in.getContentss().iterator(); i.hasNext();)
        {
            print((MContained)i.next());
        }
    }
    
    private void print(MTypedefDef in)
    {
        logger.fine("MTypedefDef");    
        if(in instanceof MEnumDef)
        {
            print((MEnumDef)in);
        }   
        else if(in instanceof MStructDef)
        {
            print((MStructDef)in);
        }
        else if(in instanceof MAliasDef)
        {
            print((MAliasDef)in);
        }
        // TODO: MUnionDef
        else
        {
            throw new RuntimeException("Unknown MTypedefDef " + in);
        }
    }

    private void print(MAliasDef in)
    {
        print(Code.getRepositoryId(in) + ":(MAliasDef)");
        MTyped typed = (MTyped) in;
        MIDLType innerIdlType = typed.getIdlType();
        print(innerIdlType);
    }
    

	
	private void print(MIDLType in)
	{
        logger.fine("MIDLType"); 
		if(in instanceof MPrimitiveDef)
		{
			print((MPrimitiveDef)in);
		}
		else if(in instanceof MStringDef)
		{
			print((MStringDef)in);
		}
		else if(in instanceof MWstringDef)
		{
			print((MWstringDef)in);
		}
		else if(in instanceof MFixedDef)
		{
			print((MFixedDef)in);
		}		
		else if(in instanceof MEnumDef)
		{			
			print((MEnumDef)in);
		}
		else if(in instanceof MSequenceDef)
		{
			print((MSequenceDef)in);
		}
        else if(in instanceof MArrayDef)
        {
            print((MArrayDef)in);
        }
		else if(in instanceof MStructDef)
		{			
			println(":" + Code.getRepositoryId((MStructDef)in));
		}
		else if(in instanceof MInterfaceDef)
		{
			println(":" + Code.getRepositoryId((MInterfaceDef)in));
		}
		else if(in instanceof MAliasDef)
		{
			print((MAliasDef)in);
		}
		else
		{
			throw new RuntimeException("Unknown MIDLType " + in);
		}
	}


    private void print(MPrimitiveDef in)
	{
        logger.fine("MPrimitiveDef"); 
		println(":PrimitiveDef(" + in.getKind().toString() + ")");
	}
	
	private void print(MStringDef in)
	{
        logger.fine("MStringDef"); 
		if(in.getBound() != null)
		{
			println(":StringDef (bound = " + in.getBound() + ")");
		}
		else
		{
			println(":StringDef");
		}
	}
	
	private void print(MWstringDef in)
	{
        logger.fine("MWstringDef"); 
		if(in.getBound() != null)
		{
			println(":WstringDef (bound = " + in.getBound() + ")");
		}
		else
		{
			println(":WstringDef");
		}
	}
	
	private void print(MFixedDef in)
	{
        logger.fine("MFixedDef"); 
		println(":FixedDef <digits = " + in.getDigits() + ", scale = " + in.getScale() + ">");
	}
	
	private void print(MConstantDef in)
	{
        logger.fine("MConstantDef"); 
		print(Code.getRepositoryId(in) + ":ConstantDef");
		MIDLType idlType = in.getIdlType();
		Object value = in.getConstValue();
		print(idlType);
		println(" = " + value);
	}
	
	private void print(MEnumDef in)
	{
        logger.fine("MEnumDef"); 
		print(Code.getRepositoryId(in) + ":EnumDef {");
		for(Iterator i = in.getMembers().iterator(); i.hasNext();)
		{
			String member = (String)i.next();
			print(" " + member);
		}	
		println("}");
	}
	
	private void print(MStructDef in)
	{
        logger.fine("MStructDef"); 
		println(Code.getRepositoryId(in) + ":StructDef");
		for(Iterator i = in.getMembers().iterator(); i.hasNext();)
		{
			MFieldDef member = (MFieldDef)i.next();
			print(TAB + member.getIdentifier());
			print(member.getIdlType());
		}		
	}

	private void print(MExceptionDef in)
	{
        logger.fine("MExceptionDef"); 
		println(Code.getRepositoryId(in) + ":ExceptionDef");
		for(Iterator i = in.getMembers().iterator(); i.hasNext();)
		{
			MFieldDef member = (MFieldDef)i.next();
			print(TAB + member.getIdentifier());
			print(member.getIdlType());
		}		
	}

	private void print(MArrayDef in)
	{
        logger.fine("MArrayDef"); 
		print(":ArrayDef");
		print(in.getIdlType());
		for(int i=0; i < in.getBounds().size(); i++)
		{
			Long bound = (Long)in.getBounds().get(i);
			println(TAB + "bound[" + i + "] = " + bound);
		}
	}
	
	private void print(MSequenceDef in)
	{
        logger.fine("MSequenceDef"); 
		if(in.getBound() == null)
		{
			print(":SequenceDef");
		}
		else
		{
			print(":SequenceDef(bound = " + in.getBound() + ")");
		}
		print(in.getIdlType());
	}

    private void print(MAttributeDef in)
    {
        logger.fine("MAttributeDef"); 
        print(TAB);
        if(in.isReadonly())
        {
            print("readonly ");
        }
        print(in.getIdentifier());
        print(in.getIdlType());
        for(Iterator i = in.getGetExceptions().iterator(); i.hasNext();)
        {
            MExceptionDef ex = (MExceptionDef)i.next();
            println(TAB + "get raises: " + Code.getRepositoryId(ex));
        }
        for(Iterator i = in.getSetExceptions().iterator(); i.hasNext();)
        {
            MExceptionDef ex = (MExceptionDef)i.next();
            println(TAB + "set raises: " + Code.getRepositoryId(ex));
        }        
    }
    
	private void print(MOperationDef in)
	{
        logger.fine("MOperationDef"); 
		if(in.isOneway())
		{
			print("oneway ");
		}
		print(in.getIdentifier() + " ");
		print(in.getIdlType());
		for(Iterator i = in.getParameters().iterator(); i.hasNext();)
		{
			MParameterDef parameter = (MParameterDef)i.next();
			print(TAB + "parameter: ");
			print(parameter.getIdentifier());
			print(parameter.getIdlType());
		}
		for(Iterator i = in.getExceptionDefs().iterator(); i.hasNext();)
		{
			MExceptionDef ex = (MExceptionDef)i.next();
			print(TAB + "rainses: ");
			println(ex.getIdentifier());
		}
		if(in.getContexts() != null)
		{
			print(TAB + "context: ");
			println(in.getContexts());
		}
	}
	
	private void print(MInterfaceDef in)
	{
        logger.fine("MInterfaceDef"); 
		println(Code.getRepositoryId(in) + ":InterfaceDef");
        if(in instanceof MHomeDef)
        {
            print((MHomeDef)in);
        }
        else if(in instanceof MComponentDef) 
        {
            print((MComponentDef)in);
        }
        
        if(in.isAbstract())
        {
            println(TAB + "abstract");
        }
        if(in.isLocal())
        {
            println(TAB + "local");
        }
		for(Iterator i = in.getBases().iterator(); i.hasNext();)
		{
			MInterfaceDef baseInterface = (MInterfaceDef)i.next();
			println(TAB + "extends " + baseInterface.getRepositoryId());
		}
		for(Iterator i = in.getContentss().iterator(); i.hasNext();)
		{
			MContained contained = (MContained)i.next();					
			if(contained instanceof MAttributeDef)
			{
				print((MAttributeDef)contained);
			}
			else if(contained instanceof MOperationDef)
			{				
				print((MOperationDef)contained);
			}
			else if(contained instanceof MConstantDef)
			{
				print((MConstantDef)contained);
			}
			else if(contained instanceof MIDLType)
			{
				print((MIDLType)contained);
			}
			else if(contained instanceof MExceptionDef)
			{
				MExceptionDef ex = (MExceptionDef)contained;
				print(ex);
			}
			else
			{
				throw new RuntimeException("Unhandled containment in interface " + in.getRepositoryId());
			}
		}		
	}

	private void print(MComponentDef in)
	{
        logger.fine("MComponentDef"); 
		println(Code.getRepositoryId(in) + ":ComponentDef");
		for(Iterator i = in.getBases().iterator(); i.hasNext();)
		{
			MInterfaceDef baseInterface = (MInterfaceDef)i.next();
			println(TAB + "extends " + baseInterface.getRepositoryId());
		}
		for(Iterator i = in.getHomes().iterator(); i.hasNext();)
		{
			MHomeDef home = (MHomeDef)i.next();
			println(TAB + "home: " + home.getRepositoryId());
		}
		for(Iterator i = in.getSupportss().iterator(); i.hasNext();)
		{
			MSupportsDef supports = (MSupportsDef)i.next();
			MInterfaceDef supportedInterface = (MInterfaceDef)supports.getSupports();
			println(TAB + "supports " + supportedInterface.getRepositoryId());
		}
		for(Iterator i = in.getFacets().iterator(); i.hasNext();)
		{
			MProvidesDef provides = (MProvidesDef)i.next();
			println(TAB + "provides: " + provides.getProvides().getRepositoryId());
		}
		for(Iterator i = in.getReceptacles().iterator(); i.hasNext();)
		{
			MUsesDef uses = (MUsesDef)i.next();
			if(uses.isMultiple())
			{
				print(TAB + "uses(multiple): ");
			}
			else
			{
				print(TAB + "uses: ");
			}
			println(uses.getUses().getRepositoryId());
		}
		
		for(Iterator i = in.getContentss().iterator(); i.hasNext();)
		{
			MContained contained = (MContained)i.next();					
			if(contained instanceof MAttributeDef)
			{
				print((MAttributeDef)contained);
			}
			else
			{
				throw new RuntimeException("Unhandled containment in component " + in.getRepositoryId());
			}
		}
	}

	private void print(MHomeDef in)
	{
        logger.fine("MHomeDef"); 
		println(Code.getRepositoryId(in) + ":HomeDef");
		for(Iterator i = in.getBases().iterator(); i.hasNext();)
		{
			MInterfaceDef baseInterface = (MInterfaceDef)i.next();
			println(TAB + "extends " + baseInterface.getRepositoryId());
		}
		MComponentDef component = in.getComponent();
		println(TAB + "manages: " + component.getRepositoryId());
		for(Iterator i = in.getSupportss().iterator(); i.hasNext();)
		{
			MSupportsDef supports = (MSupportsDef)i.next();
			MInterfaceDef supportedInterface = (MInterfaceDef)supports.getSupports();
			println(TAB + "supports " + supportedInterface.getRepositoryId());
		}
		for(Iterator i = in.getFactories().iterator(); i.hasNext();)
		{
			MFactoryDef factory = (MFactoryDef)i.next();
			print(factory);
		}
		for(Iterator i = in.getFinders().iterator(); i.hasNext();)
		{
			MFinderDef finder = (MFinderDef)i.next();
			print(finder);
		}
        for(Iterator i = in.getContentss().iterator(); i.hasNext();)
        {
            MContained contained = (MContained)i.next();                    
            if(contained instanceof MAttributeDef)
            {
                print((MAttributeDef)contained);
            }
            else
            {
                throw new RuntimeException("Unhandled containment in home " + in.getRepositoryId());
            }
        }
	}
	
	private void print(MFactoryDef in)
	{
        logger.fine("MFactoryDef"); 
		println("factory: " + in.getIdentifier());
		for(Iterator i = in.getParameters().iterator(); i.hasNext();)
		{
			MParameterDef parameter = (MParameterDef)i.next();
			print(TAB + "parameter: ");
			print(parameter.getIdentifier());
			print(parameter.getIdlType());
		}
		for(Iterator i = in.getExceptionDefs().iterator(); i.hasNext();)
		{
			MExceptionDef ex = (MExceptionDef)i.next();
			print(TAB + "rainses: ");
			println(ex.getIdentifier());
		}
		if(in.getContexts() != null)
		{
			print(TAB + "context: ");
			println(in.getContexts());
		}
	}
	
	private void print(MFinderDef in)
	{
        logger.fine("MFinderDef"); 
		println("finder: " + in.getIdentifier());
		for(Iterator i = in.getParameters().iterator(); i.hasNext();)
		{
			MParameterDef parameter = (MParameterDef)i.next();
			print(TAB + "parameter: ");
			print(parameter.getIdentifier());
			print(parameter.getIdlType());
		}
		for(Iterator i = in.getExceptionDefs().iterator(); i.hasNext();)
		{
			MExceptionDef ex = (MExceptionDef)i.next();
			print(TAB + "rainses: ");
			println(ex.getIdentifier());
		}
		if(in.getContexts() != null)
		{
			print(TAB + "context: ");
			println(in.getContexts());
		}
	}
	
	private void print(MValueDef in)
	{
        logger.fine("MValueDef"); 
		println(Code.getRepositoryId(in) + ":ValueDef");
		println(TAB + "isAbstract   : " + in.isAbstract());
		println(TAB + "isCustom     : " + in.isCustom());
		println(TAB + "isTruncatable: " + in.isTruncatable());
		if(in.getBase() != null)
		{
			println("extends: " + Code.getRepositoryId(in.getBase()));
		}
		for(Iterator i = in.getAbstractBases().iterator(); i.hasNext();)
		{
			MInterfaceDef baseInterface = (MInterfaceDef)i.next();
			println(TAB + "extends abstract: " + baseInterface.getRepositoryId());
		}
		if(in.getInterfaceDef() != null)
		{
			println("supports: " + Code.getRepositoryId(in.getInterfaceDef()));
		}
		for(Iterator i = in.getContentss().iterator(); i.hasNext();)
		{
			MContained contained = (MContained)i.next();					
			if(contained instanceof MValueMemberDef)
			{
				MValueMemberDef member = (MValueMemberDef)contained;
				print(TAB);
				if(member.isPublicMember())
				{
					print("public ");
				}
				else
				{
					print("private ");
				}
				print(member.getIdentifier());
				print(member.getIdlType());
			}
			else if(contained instanceof MOperationDef)
			{				
				print((MOperationDef)contained);
			}
			else
			{
				println(contained.toString());
				throw new RuntimeException("Unhandled containment in valuedef "
						+ Code.getRepositoryId(in));
			}
		}
	}

    
    /*************************************************************************
     * Utility Methods 
     *************************************************************************/

    private void print(String in)
    {
        System.out.print(in);
    }
    
    private void println(String in)
    {
        System.out.println(in);
    }   
}
