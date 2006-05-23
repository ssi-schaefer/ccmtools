package ccmtools.parser.idl3.ui;

import java.util.logging.Logger;

import ccmtools.CodeGenerator.NodeHandler;
import ccmtools.Metamodel.BaseIDL.MContained;
import ccmtools.Metamodel.BaseIDL.MExceptionDef;
import ccmtools.Metamodel.BaseIDL.MInterfaceDef;
import ccmtools.Metamodel.BaseIDL.MOperationDef;
import ccmtools.Metamodel.BaseIDL.MParameterDef;
import ccmtools.Metamodel.BaseIDL.MPrimitiveDef;
import ccmtools.Metamodel.BaseIDL.MStringDef;
import ccmtools.Metamodel.BaseIDL.MStructDef;
import ccmtools.Metamodel.ComponentIDL.MComponentDef;
import ccmtools.Metamodel.ComponentIDL.MHomeDef;
import ccmtools.Metamodel.ComponentIDL.MProvidesDef;
import ccmtools.Metamodel.ComponentIDL.MUsesDef;


public class SimpleNodeHandler
    implements NodeHandler
{		
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
        	else if (node instanceof MHomeDef)
    		{
    			MHomeDef home = (MHomeDef) node;
    			System.out.println("MHomeDef: " + home.getRepositoryId());
    		}
        	else if(node instanceof MComponentDef) 
        	{
        		MComponentDef component = (MComponentDef)node;
        		System.out.println("MComponentDef: " + component.getRepositoryId());
        	}
        	else if(node instanceof MInterfaceDef)
        	{
        		MInterfaceDef iface = (MInterfaceDef)node;
        		System.out.println("MInterfaceDef: " + iface.getRepositoryId());
        	}
		else if (node instanceof MProvidesDef)
		{
			MProvidesDef provides = (MProvidesDef) node;
			System.out.println("MProvidesDef: " + provides.getIdentifier() + "->" 
					+ provides.getProvides().getRepositoryId());
		}
		else if (node instanceof MUsesDef)
		{
			MUsesDef uses = (MUsesDef) node;
			System.out.println("MUsesDef: " + uses.getIdentifier() + "->" 
					+ uses.getUses().getRepositoryId());
		}        
        	else if(node instanceof MStructDef)
        	{
        		MStructDef struct = (MStructDef)node;
        		System.out.println("MStructDef: " + struct.getIdentifier());
        	}
    		
    		
        	else if(node instanceof MPrimitiveDef)
        	{
        		MPrimitiveDef primitiveType = (MPrimitiveDef)node;
        		System.out.println("MPrimitiveDef: " +  primitiveType.getKind());
        	}
        	else if(node instanceof MStringDef)
        	{
        		MStringDef str = (MStringDef)node;
        		System.out.println("MStringDef: " + str.getBound());
        	}
        	else if(node instanceof MOperationDef)
        	{
        		MOperationDef op = (MOperationDef)node;
        		System.out.println("MOperationDef: " + op.getIdentifier()
        				+ ":" + op.getIdlType());
        	}
        	else if(node instanceof MParameterDef)
        	{
        		MParameterDef parameter = (MParameterDef)node;
        		System.out.println("MParameterDef: " + parameter.getIdentifier() 
        				+ ":" + parameter.getIdlType());
        	}
        	else if(node instanceof MExceptionDef)
        	{
        		MExceptionDef exception = (MExceptionDef)node;
        		System.out.println("MExceptionDef: " + exception.getIdentifier());
        	}


//        	else if(node instanceof MEnumDef)
//        	{
//        		MEnumDef enumeration = (MEnumDef)node;
//        		System.out.println("MEnumDef: " + Code.getRepositoryId(enumeration));
//        	}

//        	else if(node instanceof MExceptionDef)
//        	{
//        		MExceptionDef exception = (MExceptionDef)node;
//        		System.out.println("MExceptionDef: " + Code.getRepositoryId(exception));
//        	}
//        	else if(node instanceof MAliasDef)
//        	{
//        		// Note that only the MAiliasDef object knows the identifier of these
//        		// CCM model elements, thus, we have to handle MAliasDef nodes here! 
//        		MAliasDef alias = (MAliasDef)node;
//        		System.out.println("MAliasDef: " + Code.getRepositoryId(alias));    		
//        		MTyped typed = (MTyped)alias;
//        		MIDLType innerIdlType = typed.getIdlType();			    		
//        		if(innerIdlType instanceof MSequenceDef)
//        		{
//        			MSequenceDef sequence = (MSequenceDef)innerIdlType;
//        			System.out.println("MSequenceDef: " + sequence);
//        		}
//        		if(innerIdlType instanceof MArrayDef)
//        		{
//        			MArrayDef array = (MArrayDef)innerIdlType;
//        			System.out.println("MArrayDef: " + array);
//        		}
//        	}

    
    }

	public void handleNodeData(String fieldType, String fieldId, Object value)
	{
	}
}
