/**
 * This file was automatically generated by CCM Tools version 0.7.0
 * <http://ccmtools.sourceforge.net>
 * 
 * CCM_LoginImpl facet implementation.
 *
 * // TODO: WRITE YOUR DESCRIPTION HERE !
 * 
 * @author
 * @version
 */

package system.ccm.local; 

import Components.ccm.local.CCMException;
   

/** 
 * This class implements a component facet's methods.
 *
 * // TODO: WRITE YOUR DESCRIPTION HERE !
 *
 * @author
 * @version
 */
public class ServerloginImpl 
    implements CCM_Login
{
    /** Reference to the facet's component implementation */
    private ServerImpl component;

    public ServerloginImpl(ServerImpl component)
    {
        this.component = component;
    }
 


    /** Business logic implementations */

   
    
     

    public boolean login(PersonData person)
        throws CCMException,
            InvalidData
    {
    	// TODO: IMPLEMENT ME HERE !
    	return false;
    }    

}