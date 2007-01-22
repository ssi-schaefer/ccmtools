package ccmtools.generator.deployment;

import ccmtools.CodeGenerator.NodeHandler;
import ccmtools.generator.deployment.metamodel.ComponentInterfaceDescription;
import ccmtools.generator.deployment.metamodel.ComponentPortDescription;
import ccmtools.generator.deployment.metamodel.DeploymentFactory;
import ccmtools.generator.deployment.metamodel.impl.CCMComponentPortKind;
import ccmtools.parser.idl.metamodel.CcmModelHelper;
import ccmtools.parser.idl.metamodel.BaseIDL.MInterfaceDef;
import ccmtools.parser.idl.metamodel.ComponentIDL.MComponentDef;
import ccmtools.parser.idl.metamodel.ComponentIDL.MHomeDef;
import ccmtools.parser.idl.metamodel.ComponentIDL.MProvidesDef;
import ccmtools.parser.idl.metamodel.ComponentIDL.MUsesDef;


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
            descriptor.setSpecificType(CcmModelHelper.getRepositoryId(home));
            descriptor.getSupportedType().add(CcmModelHelper.getRepositoryId(home));
        }
        else if(node instanceof MComponentDef) {
            MComponentDef component = (MComponentDef)node;
            descriptor.getSupportedType().add(CcmModelHelper.getRepositoryId(component));
        }
        else if(node instanceof MProvidesDef) {
            MProvidesDef provides = (MProvidesDef)node;
            MInterfaceDef iface = provides.getProvides();
            ComponentPortDescription facet = factory.createComponentPortDescription();
            facet.setKind(CCMComponentPortKind.Facet);
            facet.setName(provides.getIdentifier());
            facet.setSpecificType(CcmModelHelper.getRepositoryId(iface));
            facet.getSupportedType().add(CcmModelHelper.getRepositoryId(iface));
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
            receptacle.setSpecificType(CcmModelHelper.getRepositoryId(iface));
            receptacle.getSupportedType().add(CcmModelHelper.getRepositoryId(iface));
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
