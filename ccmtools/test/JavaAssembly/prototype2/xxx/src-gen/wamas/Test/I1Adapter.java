/*
 * This file was automatically generated by CCM Tools version 0.9.0
 * <http://ccmtools.sourceforge.net>
 * DO NOT EDIT!
 */

package wamas.Test;
                 
import java.util.logging.Logger;

import Components.CCMException;  
import ccmtools.local.ServiceLocator;

                  
                  
public class I1Adapter 
    implements I1
{
    private Logger logger = ServiceLocator.instance().getLogger();
    
    private CCM_I1 localInterface;
    
    protected I1Adapter()
    {
        logger.fine("");
    }

    public I1Adapter(CCM_I1 localInterface)
    {
        logger.fine("localInterface = " + localInterface);
        this.localInterface = localInterface;
    }
            
    

    public String value() 
        throws CCMException 
    {
        logger.fine("");
        return localInterface.value();
    }
    
    
}
