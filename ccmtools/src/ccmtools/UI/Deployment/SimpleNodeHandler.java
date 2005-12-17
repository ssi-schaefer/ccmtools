package ccmtools.UI.Deployment;

import java.util.ArrayList;
import java.util.List;

import ccmtools.CodeGenerator.NodeHandler;
import ccmtools.Deployment.Metamodel.ComponentInterfaceDescription;
import ccmtools.Deployment.Metamodel.impl.ComponentInterfaceDescriptionImpl;
import ccmtools.Metamodel.BaseIDL.MContained;
import ccmtools.Metamodel.BaseIDL.MContainer;
import ccmtools.Metamodel.BaseIDL.MInterfaceDef;
import ccmtools.Metamodel.BaseIDL.MModuleDef;
import ccmtools.Metamodel.ComponentIDL.MComponentDef;
import ccmtools.Metamodel.ComponentIDL.MHomeDef;
import ccmtools.Metamodel.ComponentIDL.MProvidesDef;
import ccmtools.Metamodel.ComponentIDL.MUsesDef;
import ccmtools.utils.Text;


public class SimpleNodeHandler
    implements NodeHandler
{
    private ComponentInterfaceDescription descriptor;
    
    public ComponentInterfaceDescription getComponentInterfaceDescription()
    {
        return descriptor;
    }

    
    
    public void startGraph()
    {
        out("start graph");        
        descriptor = new ComponentInterfaceDescriptionImpl();
    }

    public void endGraph()
    {
        out("end graph");        
    }

    public void startNode(Object node, String scopeId)
    {
    }

    public void endNode(Object node, String scopeId)
    {        
        if(node instanceof MHomeDef) {
            MHomeDef home = (MHomeDef)node;
            descriptor.setSpecificType(getRepoId(home));
            descriptor.getSupportedTypes().add(getRepoId(home));
            out("Home - " + getRepoId(home));
        }
        else if(node instanceof MComponentDef) {
            MComponentDef component = (MComponentDef)node;
            descriptor.getSupportedTypes().add(getRepoId(component));
            
            out("Component - " + getRepoId(component));
        }
        else if(node instanceof MProvidesDef) {
            MProvidesDef provides = (MProvidesDef)node;
            MInterfaceDef iface = provides.getProvides();
            out("Provides - " + getRepoId(iface));
        }
        else if(node instanceof MUsesDef) {
            MUsesDef uses = (MUsesDef)node;
            MInterfaceDef iface = uses.getUses();
            out("Uses - " + getRepoId(iface));
        }
    }

    public void handleNodeData(String fieldType, String fieldId, Object value)
    {
        //out("data: " + fieldType + ", " + fieldId);
        
    }
    
    
    // Helper Methods ------------------------------------------------------

    private String getRepoId(MContained node)
    {
        return "IDL:" + getQName(node, "/") + ":1.0";
    }
    
    private String getQName(MContained node, String sep)
    {
        return getNamespace(node,sep) + sep + node.getIdentifier();
    }
    
    private String getNamespace(MContained node, String sep)
    {
        List nsList = getNamespaceList(node);
        return Text.join(sep, nsList);
    }
    
    /**
     * Calculates the model element namespace by going back from the
     * model element to the top container element and collecting the 
     * names of all module definitions in-between.
     */
    private List getNamespaceList(MContained node)
    {        
        List scope = new ArrayList();
        MContainer c = node.getDefinedIn();
        while(c.getDefinedIn() != null) {
            if(c instanceof MModuleDef)
                scope.add(0, c.getIdentifier());
            c = c.getDefinedIn();
        }
        return scope;
    }

    
    private void out(String s)
    {
        System.out.println("    " + s);
    }
}
