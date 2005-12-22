package ccmtools.Deployment.UI;

import java.util.Iterator;

import ccmtools.Metamodel.BaseIDL.MContained;
import ccmtools.Metamodel.BaseIDL.MContainer;
import ccmtools.Metamodel.BaseIDL.MModuleDef;
import ccmtools.Metamodel.ComponentIDL.MComponentDef;
import ccmtools.Metamodel.ComponentIDL.MHomeDef;


public class CcmToDeployMapper
{
    private MContainer ccmModel;
    
    public CcmToDeployMapper(MContainer ccmModel)
    {
        this.ccmModel = ccmModel;
    }
    
    private void out(String s)
    {
        System.out.println(s);
    }
    
    public String transform(MContainer container)
    {
        StringBuffer buffer = new StringBuffer();
        
        for(Iterator i = container.getContentss().iterator(); i.hasNext();) {
            MContained contained = (MContained) i.next();

            if(contained instanceof MModuleDef) {
                MModuleDef module = (MModuleDef) contained;
                out("module " + module.getIdentifier());
                out(transform(module));
                out("}");
            }
            else if(contained instanceof MHomeDef) {
                MHomeDef home = (MHomeDef) contained;
                out("home " + home.getIdentifier());
            }
            else if(contained instanceof MComponentDef) {
                MComponentDef component = (MComponentDef) contained;
                out("component " + component.getIdentifier());
            }
            else {
                // other model elements
                // out("other element: " + contained);
            }
        }
        return buffer.toString(); 
    }

    public String transform(MModuleDef module)
    {
        StringBuffer buffer = new StringBuffer();
        for(Iterator i=module.getContentss().iterator(); i.hasNext();) {
            MContained contained = (MContained)i.next();
            
            if(contained instanceof MContainer) {
                MContainer container = (MContainer)contained;
                transform(container);                
            }
            else if(contained instanceof MModuleDef) {
                MModuleDef m = (MModuleDef) contained;
                transform(m);
            }
            
        }
        
        return buffer.toString(); 
    }
    
}
