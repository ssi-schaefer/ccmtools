package ccmtools.Deployment;

import ccmtools.CodeGenerator.NodeHandler;
import ccmtools.Deployment.Metamodel.ComponentInterfaceDescription;
import ccmtools.Deployment.Metamodel.ComponentPortDescription;
import ccmtools.Deployment.Metamodel.DeploymentFactory;
import ccmtools.Deployment.Metamodel.impl.CCMComponentPortKind;
import ccmtools.Metamodel.BaseIDL.MInterfaceDef;
import ccmtools.Metamodel.ComponentIDL.MComponentDef;
import ccmtools.Metamodel.ComponentIDL.MHomeDef;
import ccmtools.Metamodel.ComponentIDL.MProvidesDef;
import ccmtools.Metamodel.ComponentIDL.MUsesDef;
import ccmtools.utils.Code;


public class CcmModelNodeHandler
    implements NodeHandler
{
    DeploymentFactory factory = DeploymentFactory.instance;
    
    /** This class is part of the D&C Model */
    private ComponentInterfaceDescription descriptor;
        
    public ComponentInterfaceDescription getComponentInterfaceDescription()
    {
        return descriptor;
    }

    // Callback methods for the CCM GraphTraverser ----------------------------
    
    public void startGraph()
    {
        descriptor = factory.createComponentInterfaceDescription();
    }

    public void endGraph()
    {
    }

    public void startNode(Object node, String scopeId)
    {
    }

    public void endNode(Object node, String scopeId)
    {        
        if(node instanceof MHomeDef) {
            MHomeDef home = (MHomeDef)node;
            descriptor.setSpecificType(Code.getRepositoryId(home));
            descriptor.getSupportedType().add(Code.getRepositoryId(home));
        }
        else if(node instanceof MComponentDef) {
            MComponentDef component = (MComponentDef)node;
            descriptor.getSupportedType().add(Code.getRepositoryId(component));
        }
        else if(node instanceof MProvidesDef) {
            MProvidesDef provides = (MProvidesDef)node;
            MInterfaceDef iface = provides.getProvides();
            ComponentPortDescription facet = factory.createComponentPortDescription();
            facet.setKind(CCMComponentPortKind.Facet);
            facet.setName(provides.getIdentifier());
            facet.setSpecificType(Code.getRepositoryId(iface));
            facet.getSupportedType().add(Code.getRepositoryId(iface));
            // TODO: add base interface types
            facet.setProvider(true);
            facet.setExclusiveProvider(false);
            facet.setExclusiveUser(false);
            facet.setOptional(false);
            descriptor.getPort().add(facet);
        }
        else if(node instanceof MUsesDef) {
            MUsesDef uses = (MUsesDef)node;
            MInterfaceDef iface = uses.getUses();
            ComponentPortDescription receptacle = factory.createComponentPortDescription();
            if(uses.isMultiple()) {
                receptacle.setKind(CCMComponentPortKind.MultiplexReceptacle);
            }
            else {
                receptacle.setKind(CCMComponentPortKind.SimplexReceptacle);                
            }
            receptacle.setName(uses.getIdentifier());
            receptacle.setSpecificType(Code.getRepositoryId(iface));
            receptacle.getSupportedType().add(Code.getRepositoryId(iface));
            // TODO: add base interface types
            receptacle.setProvider(false);
            receptacle.setExclusiveProvider(false);
            receptacle.setExclusiveUser(false);
            receptacle.setOptional(false);
            descriptor.getPort().add(receptacle);
        }
    }

    public void handleNodeData(String fieldType, String fieldId, Object value)
    {
        //out("data: " + fieldType + ", " + fieldId);
        
    }
}
