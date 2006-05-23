package ccmtools.parser.idl3.ui;

import java.util.logging.Logger;

import ccmtools.CodeGenerator.NodeHandler;
import ccmtools.Metamodel.BaseIDL.MAliasDef;
import ccmtools.Metamodel.BaseIDL.MArrayDef;
import ccmtools.Metamodel.BaseIDL.MContained;
import ccmtools.Metamodel.BaseIDL.MEnumDef;
import ccmtools.Metamodel.BaseIDL.MExceptionDef;
import ccmtools.Metamodel.BaseIDL.MIDLType;
import ccmtools.Metamodel.BaseIDL.MInterfaceDef;
import ccmtools.Metamodel.BaseIDL.MSequenceDef;
import ccmtools.Metamodel.BaseIDL.MStructDef;
import ccmtools.Metamodel.BaseIDL.MTyped;
import ccmtools.Metamodel.ComponentIDL.MComponentDef;
import ccmtools.Metamodel.ComponentIDL.MHomeDef;
import ccmtools.Metamodel.ComponentIDL.MProvidesDef;
import ccmtools.Metamodel.ComponentIDL.MUsesDef;
import ccmtools.utils.Code;


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
        		System.out.println("> included from: " + ((MContained) node).getSourceFile());
        		return;
        	}
        	else if (node instanceof MHomeDef)
    		{
    			MHomeDef home = (MHomeDef) node;
    			System.out.println("MHomeDef: " + Code.getRepositoryId(home));
    		}
        	else if(node instanceof MComponentDef) 
        	{
        		MComponentDef component = (MComponentDef)node;
        		System.out.println("MComponentDef: " + Code.getRepositoryId(component));
        	}
        	else if(node instanceof MInterfaceDef)
        	{
        		MInterfaceDef iface = (MInterfaceDef)node;
        		System.out.println("MInterfaceDef: " + Code.getRepositoryId(iface));
        	}   
        	else if(node instanceof MProvidesDef)
        	{
        		MProvidesDef provides = (MProvidesDef)node;
        		System.out.println("MProvidesDef: " + Code.getRepositoryId(provides));
        	}
        	else if(node instanceof MUsesDef)
        	{
        		MUsesDef uses = (MUsesDef)node;
        		System.out.println("MUsesDef: " + Code.getRepositoryId(uses));
        	}
        	else if(node instanceof MEnumDef)
        	{
        		MEnumDef enumeration = (MEnumDef)node;
        		System.out.println("MEnumDef: " + Code.getRepositoryId(enumeration));
        	}
        	else if(node instanceof MStructDef)
        	{
        		MStructDef struct = (MStructDef)node;
        		System.out.println("MStructDef: " + Code.getRepositoryId(struct));
        	}
        	else if(node instanceof MExceptionDef)
        	{
        		MExceptionDef exception = (MExceptionDef)node;
        		System.out.println("MExceptionDef: " + Code.getRepositoryId(exception));
        	}
        	else if(node instanceof MAliasDef)
        	{
        		// Note that only the MAiliasDef object knows the identifier of these
        		// CCM model elements, thus, we have to handle MAliasDef nodes here! 
        		MAliasDef alias = (MAliasDef)node;
        		System.out.println("MAliasDef: " + Code.getRepositoryId(alias));    		
        		MTyped typed = (MTyped)alias;
        		MIDLType innerIdlType = typed.getIdlType();			    		
        		if(innerIdlType instanceof MSequenceDef)
        		{
        			MSequenceDef sequence = (MSequenceDef)innerIdlType;
        			System.out.println("MSequenceDef: " + sequence);
        		}
        		if(innerIdlType instanceof MArrayDef)
        		{
        			MArrayDef array = (MArrayDef)innerIdlType;
        			System.out.println("MArrayDef: " + array);
        		}
        	}

    
    }

	public void handleNodeData(String fieldType, String fieldId, Object value)
	{
	}
}
