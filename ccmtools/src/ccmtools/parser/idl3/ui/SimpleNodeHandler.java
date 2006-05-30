package ccmtools.parser.idl3.ui;

import java.util.Iterator;
import java.util.logging.Logger;

import ccmtools.CodeGenerator.NodeHandler;
import ccmtools.Metamodel.BaseIDL.MAliasDef;
import ccmtools.Metamodel.BaseIDL.MArrayDef;
import ccmtools.Metamodel.BaseIDL.MContained;
import ccmtools.Metamodel.BaseIDL.MEnumDef;
import ccmtools.Metamodel.BaseIDL.MFieldDef;
import ccmtools.Metamodel.BaseIDL.MFixedDef;
import ccmtools.Metamodel.BaseIDL.MIDLType;
import ccmtools.Metamodel.BaseIDL.MPrimitiveDef;
import ccmtools.Metamodel.BaseIDL.MSequenceDef;
import ccmtools.Metamodel.BaseIDL.MStringDef;
import ccmtools.Metamodel.BaseIDL.MStructDef;
import ccmtools.Metamodel.BaseIDL.MTyped;
import ccmtools.Metamodel.BaseIDL.MWstringDef;
import ccmtools.utils.Code;


public class SimpleNodeHandler
    implements NodeHandler
{		
	private static final String TAB = "\t";
	private static final String NL = "\n";
	
    /** Java logging */
    private Logger logger;
    
    
	public SimpleNodeHandler()
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
        	else if(node instanceof MEnumDef)
        	{
        		MEnumDef enumeration = (MEnumDef)node;
        		print(enumeration);
        	}	
        	else if(node instanceof MStructDef)
        	{
        		MStructDef struct = (MStructDef)node;
        		print(struct);
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
        		print(Code.getRepositoryId(alias) + ":(MAliasDef)");    		
        		MTyped typed = (MTyped)alias;
        		MIDLType innerIdlType = typed.getIdlType();			    		
        		endNode(innerIdlType, scopeId);        		
        	}
    		
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
//        	else if(node instanceof MInterfaceDef)
//        	{
//        		MInterfaceDef iface = (MInterfaceDef)node;
//        		System.out.println("MInterfaceDef: " + iface.getRepositoryId());
//        	}
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
//        	else if(node instanceof MOperationDef)
//        	{
//        		MOperationDef op = (MOperationDef)node;
//        		System.out.println("MOperationDef: " + op.getIdentifier()
//        				+ ":" + op.getIdlType());
//        	}
//        	else if(node instanceof MParameterDef)
//        	{
//        		MParameterDef parameter = (MParameterDef)node;
//        		System.out.println("MParameterDef: " + parameter.getIdentifier() 
//        				+ ":" + parameter.getIdlType());
//        	}
//        	else if(node instanceof MExceptionDef)
//        	{
//        		MExceptionDef exception = (MExceptionDef)node;
//        		System.out.println("MExceptionDef: " + exception.getIdentifier());
//        	}
    }

	public void handleNodeData(String fieldType, String fieldId, Object value)
	{
	}
	
	
	/*************************************************************************
	 * Pretty Printer Methods 
	 *************************************************************************/

	private void print(MPrimitiveDef type)
	{
		println(":PrimitiveDef(" + type.getKind().toString() + ")");
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
	
	private void print(MEnumDef in)
	{
		println(Code.getRepositoryId(in) + ":EnumDef");
		for(Iterator i = in.getMembers().iterator(); i.hasNext();)
		{
			String member = (String)i.next();
			println(TAB + member);
		}	
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

	private void print(MArrayDef in)
	{
		println(":ArrayDef");
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
			print((MStructDef)in);
		}
//		else if(in instanceof MAliasDef)
//		{
//			return transform((MAliasDef)in);
//		}
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
