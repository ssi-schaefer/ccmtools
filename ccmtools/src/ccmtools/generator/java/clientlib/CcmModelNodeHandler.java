package ccmtools.generator.java.clientlib;

import java.util.ArrayList;
import java.util.List;

import ccmtools.CodeGenerator.NodeHandler;
import ccmtools.Metamodel.BaseIDL.MAliasDef;
import ccmtools.Metamodel.BaseIDL.MConstantDef;
import ccmtools.Metamodel.BaseIDL.MEnumDef;
import ccmtools.Metamodel.BaseIDL.MExceptionDef;
import ccmtools.Metamodel.BaseIDL.MInterfaceDef;
import ccmtools.Metamodel.BaseIDL.MStructDef;
import ccmtools.Metamodel.BaseIDL.MUnionDef;
import ccmtools.Metamodel.ComponentIDL.MComponentDef;
import ccmtools.Metamodel.ComponentIDL.MHomeDef;
import ccmtools.generator.java.clientlib.metamodel.ComponentDef;
import ccmtools.generator.java.clientlib.metamodel.HomeDef;
import ccmtools.generator.java.clientlib.metamodel.InterfaceDef;
import ccmtools.utils.Code;


public class CcmModelNodeHandler
    implements NodeHandler
{
	private CcmToJavaMapper mapper = new CcmToJavaMapper();
	List sourceFileList = new ArrayList();
	
	public List getSourceFileList()
	{
		return sourceFileList;
	}
	
    // Callback methods for the CCM GraphTraverser ----------------------------
    
    public void startGraph()
    {
    }

    public void endGraph()
    {
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
    	else if(node instanceof MHomeDef) 
    	{
    		MHomeDef home = (MHomeDef)node;
    		System.out.println("MHomeDef: " + Code.getRepositoryId(home));
    		HomeDef javaHome = mapper.transform(home);
    		sourceFileList.addAll(javaHome.generateSourceFiles());
    	}
    	else if(node instanceof MComponentDef) 
    	{
    		MComponentDef component = (MComponentDef)node;
    		System.out.println("MComponentDef: " + Code.getRepositoryId(component));
    		ComponentDef javaComponent = mapper.transform(component);
    		sourceFileList.addAll(javaComponent.generateSourceFiles());
    	}
    	else if(node instanceof MInterfaceDef)
    	{
    		MInterfaceDef iface = (MInterfaceDef)node;
    		System.out.println("MInterfaceDef: " + Code.getRepositoryId(iface));
    		InterfaceDef javaIface = mapper.transform(iface);   		
    		sourceFileList.addAll(javaIface.generateSourceFiles());    		
    	}    
    	// -----------------------------------
    	else if(node instanceof MExceptionDef)
    	{
    		MExceptionDef exc = (MExceptionDef)node;
    		System.out.println("MExceptionDef: " + Code.getRepositoryId(exc));
    	}
    	else if(node instanceof MStructDef)
    	{
    		MStructDef struct = (MStructDef)node;
    		System.out.println("MStructDef: " + Code.getRepositoryId(struct));
    	}
    	else if(node instanceof MUnionDef)
    	{
    		MUnionDef union = (MUnionDef)node;
    		System.out.println("MUnionDef: " + Code.getRepositoryId(union));
    	}
    	else if(node instanceof MEnumDef)
    	{
    		MEnumDef en = (MEnumDef)node;
    		System.out.println("MEnumDef: " + Code.getRepositoryId(en));
    	}
    	else if(node instanceof MConstantDef)
    	{
    		MConstantDef constant = (MConstantDef)node;
    		System.out.println("MConstantDef: " + constant.getIdentifier());
    	}                
    	else if(node instanceof MAliasDef)
    	{
    		MAliasDef alias = (MAliasDef)node;
    		System.out.println("MAliasDef: " + Code.getRepositoryId(alias));
    	}
    }

    public void handleNodeData(String fieldType, String fieldId, Object value)
    {
        //out("data: " + fieldType + ", " + fieldId);
        
    }
}
