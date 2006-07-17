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
import ccmtools.Metamodel.BaseIDL.MWstringDef;
import ccmtools.utils.Code;


public class CcmPrettyPrinter
    implements NodeHandler
{		
	private static final String TAB = "\t";
	private static final String NL = "\n";
	
    /** Java logging */
    private Logger logger;
    
    
	public CcmPrettyPrinter()
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
        	else if(node instanceof MInterfaceDef)
        	{
        		MInterfaceDef iface = (MInterfaceDef)node;
        		print(iface);
        	}

    		
//		else if (node instanceof MProvidesDef)
//		{
//			MProvidesDef provides = (MProvidesDef) node;
//			System.out.println("MProvidesDef: " + provides.getIdentifier() + "->" 
//					+ provides.getProvides().getRepositoryId());
//		}
//		else if (node instanceof MUsesDef)
//		{
//			MUsesDef uses = (MUsesDef) node;
//			System.out.println("MUsesDef: " + uses.getIdentifier() + "->" 
//					+ uses.getUses().getRepositoryId());
//		}        
//    		
//        	else if (node instanceof MHomeDef)
//    		{
//    			MHomeDef home = (MHomeDef) node;
//    			System.out.println("MHomeDef: " + home.getRepositoryId());
//    		}
//        	else if(node instanceof MComponentDef) 
//        	{
//        		MComponentDef component = (MComponentDef)node;
//        		System.out.println("MComponentDef: " + component.getRepositoryId());
//        	}
    }

	public void handleNodeData(String fieldType, String fieldId, Object value)
	{
	}
	
	
	/*************************************************************************
	 * Pretty Printer Methods 
	 *************************************************************************/

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
				MAttributeDef attr = (MAttributeDef)contained;
				print(TAB);
				if(attr.isReadonly())
				{
					print("readonly ");
				}
				print(attr.getIdentifier());
				print(attr.getIdlType());
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

	
	private void print(String in)
	{
		System.out.print(in);
	}
	
	private void println(String in)
	{
		System.out.println(in);
	}	
}
