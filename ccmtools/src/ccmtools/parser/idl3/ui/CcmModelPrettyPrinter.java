package ccmtools.parser.idl3.ui;

import java.util.Iterator;
import java.util.logging.Logger;

import ccmtools.CodeGenerator.NodeHandler;
import ccmtools.Metamodel.BaseIDL.MAliasDef;
import ccmtools.Metamodel.BaseIDL.MArrayDef;
import ccmtools.Metamodel.BaseIDL.MAttributeDef;
import ccmtools.Metamodel.BaseIDL.MConstantDef;
import ccmtools.Metamodel.BaseIDL.MContained;
import ccmtools.Metamodel.BaseIDL.MEnumDef;
import ccmtools.Metamodel.BaseIDL.MExceptionDef;
import ccmtools.Metamodel.BaseIDL.MFieldDef;
import ccmtools.Metamodel.BaseIDL.MFixedDef;
import ccmtools.Metamodel.BaseIDL.MIDLType;
import ccmtools.Metamodel.BaseIDL.MInterfaceDef;
import ccmtools.Metamodel.BaseIDL.MOperationDef;
import ccmtools.Metamodel.BaseIDL.MParameterDef;
import ccmtools.Metamodel.BaseIDL.MPrimitiveDef;
import ccmtools.Metamodel.BaseIDL.MSequenceDef;
import ccmtools.Metamodel.BaseIDL.MStringDef;
import ccmtools.Metamodel.BaseIDL.MStructDef;
import ccmtools.Metamodel.BaseIDL.MTyped;
import ccmtools.Metamodel.BaseIDL.MValueDef;
import ccmtools.Metamodel.BaseIDL.MValueMemberDef;
import ccmtools.Metamodel.BaseIDL.MWstringDef;
import ccmtools.Metamodel.ComponentIDL.MComponentDef;
import ccmtools.Metamodel.ComponentIDL.MFactoryDef;
import ccmtools.Metamodel.ComponentIDL.MFinderDef;
import ccmtools.Metamodel.ComponentIDL.MHomeDef;
import ccmtools.Metamodel.ComponentIDL.MProvidesDef;
import ccmtools.Metamodel.ComponentIDL.MSupportsDef;
import ccmtools.Metamodel.ComponentIDL.MUsesDef;
import ccmtools.utils.Code;


public class CcmModelPrettyPrinter
    implements NodeHandler
{		
	private static final String TAB = "\t";
//	private static final String NL = "\n";
	
    /** Java logging */
    private Logger logger;
    
    
	public CcmModelPrettyPrinter()
	{
		logger = Logger.getLogger("ccm.parser.idl3");
		logger.fine("");		
	}
			
	
    /*
     * Callback methods for the CCMGraphTraverser 
     */ 
    
    public void startGraph()
	{
		System.out.println("> Start Graph");
	}

	public void endGraph()
	{
		System.out.println("> End Graph");
	}

	public void startNode(Object node, String scopeId)
	{
	}
    
    public void endNode(Object node, String scopeId)
    {
    		if(node == null)
    		{    			
    			return;
    		}
        	else if(node instanceof MContained 
        			&& !((MContained) node).getSourceFile().equals(""))
        	{
        		return; // included file
        	}
        	else if(node instanceof MStringDef)
        	{
        		MStringDef str = (MStringDef)node;
        		print(str);
        	}
        	else if(node instanceof MWstringDef)
        	{
        		MWstringDef str = (MWstringDef)node;
        		print(str);
        	}
        	else if(node instanceof MPrimitiveDef)
        	{
        		MPrimitiveDef type = (MPrimitiveDef)node;
        		print(type);
        	}
    		else if(node instanceof MFixedDef)
    		{
    			MFixedDef fixed = (MFixedDef)node;
    			print(fixed);
    		}	
        	else if(node instanceof MEnumDef)
        	{
        		MEnumDef enumeration = (MEnumDef)node;
        		if(enumeration.getDefinedIn() instanceof MInterfaceDef)
        		{
        			// If an exception is defined within an interface,
        			// the interface is responsable for printing it.
        			return;
        		}
        		print(enumeration);
        	}	
        	else if(node instanceof MConstantDef)
        	{
        		MConstantDef constant = (MConstantDef)node;
        		if(constant.getDefinedIn() instanceof MInterfaceDef)
        		{
        			// If an exception is defined within an interface,
        			// the interface is responsable for printing it.
        			return;
        		}
        		print(constant);
        	}
        	else if(node instanceof MStructDef)
        	{
        		MStructDef struct = (MStructDef)node;
        		if(struct.getDefinedIn() instanceof MInterfaceDef)
        		{
        			// If an exception is defined within an interface,
        			// the interface is responsable for printing it.
        			return;
        		}
        		print(struct);
        	}
        	else if(node instanceof MExceptionDef)
        	{
        		MExceptionDef ex = (MExceptionDef)node;
        		if(ex.getDefinedIn() instanceof MInterfaceDef)
        		{
        			// If an exception is defined within an interface,
        			// the interface is responsable for printing it.
        			return;
        		}
        		print(ex);
        	}
        	else if(node instanceof MSequenceDef)
        	{
        		MSequenceDef sequence = (MSequenceDef)node;
        		print(sequence);
        	}
        	else if(node instanceof MArrayDef)
        	{
        		MArrayDef array = (MArrayDef)node;
        		print(array);
        	}
        	else if(node instanceof MAliasDef)
        	{
        		// Note that only the MAiliasDef object knows the identifier of these
        		// CCM model elements, thus, we have to handle MAliasDef nodes here! 
        		MAliasDef alias = (MAliasDef)node;
        		if(alias.getDefinedIn() instanceof MInterfaceDef)
        		{
        			// If an exception is defined within an interface,
        			// the interface is responsable for printing it.
        			return;
        		}
        		print(alias);
        	}
        	else if(node instanceof MHomeDef)
    		{
    			MHomeDef home = (MHomeDef) node;
    			print(home);
    		}
        	else if(node instanceof MComponentDef) 
        	{
        		MComponentDef component = (MComponentDef)node;
        		print(component);
        	}
        	else if(node instanceof MInterfaceDef)
        	{
        		MInterfaceDef iface = (MInterfaceDef)node;
        		print(iface);
        	}
        	else if(node instanceof MValueDef)
        	{
        		MValueDef value = (MValueDef)node;
        		print(value);        		
        	}
    		
    }

	public void handleNodeData(String fieldType, String fieldId, Object value)
	{
	}
	
	
	/*************************************************************************
	 * Pretty Printer Methods 
	 *************************************************************************/

	private void print(String in)
	{
		System.out.print(in);
	}
	
	private void println(String in)
	{
		System.out.println(in);
	}	

	private void print(MIDLType in)
	{
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
		else if(in instanceof MStructDef)
		{			
			println(":" + ((MStructDef)in).getIdentifier());
		}
		else if(in instanceof MInterfaceDef)
		{
			println(":" + ((MInterfaceDef)in).getRepositoryId());
		}
		else if(in instanceof MAliasDef)
		{
			print((MAliasDef)in);
		}
		else
		{
			throw new RuntimeException("print(MIDLType): unknown idl type " + in);
		}
	}
	
	private void print(MAliasDef in)
	{		
		print(Code.getRepositoryId(in) + ":(MAliasDef)");    		
		MTyped typed = (MTyped)in;
		MIDLType innerIdlType = typed.getIdlType();			    		
		endNode(innerIdlType, "");        		
	}
	
	private void print(MPrimitiveDef in)
	{
		println(":PrimitiveDef(" + in.getKind().toString() + ")");
	}
	
	private void print(MStringDef in)
	{
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
		println(":FixedDef <digits = " + in.getDigits() + ", scale = " + in.getScale() + ">");
	}
	
	private void print(MConstantDef in)
	{
		print(Code.getRepositoryId(in) + ":ConstantDef");
		MIDLType idlType = in.getIdlType();
		Object value = in.getConstValue();
		print(idlType);
		println(" = " + value);
	}
	
	private void print(MEnumDef in)
	{
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
		println(Code.getRepositoryId(in) + ":InterfaceDef");
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
                throw new RuntimeException("Unhandled containment in component " + in.getRepositoryId());
            }
        }
	}
	
	private void print(MFactoryDef in)
	{
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
}
