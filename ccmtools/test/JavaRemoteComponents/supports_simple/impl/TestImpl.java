/**
 * This file was automatically generated by CCM Tools version 0.6.6
 * <http://ccmtools.sourceforge.net>
 * 
 * CCM_Test component business logic.
 *
 * // TODO: WRITE YOUR DESCRIPTION HERE !
 *
 * @author
 * @version
 */

package world.ccm.local;
   
import ccm.local.Components.SessionContext; 

import ccm.local.Components.CCMException;
  
   
/**
 * This class implements component equivalent and supported interfaces
 * as well as component attributes.
 * Additionally, session component callback methods must be implemented.
 *
 * // TODO: WRITE YOUR DESCRIPTION HERE !
 *
 * @author
 * @version
 */   
public class TestImpl 
    implements CCM_Test
{
    /** Supported interface attribute variables */
    
    

    /** Component attribute variables */
     

    public CCM_Test_Context ctx;
    
    
    public TestImpl()
    {
        // OPTIONAL: IMPLEMENT ME HERE !
    }


    /* 
     * Supported interface methods 
     */

    /** Supported interface attributes */
    
    
    
    /** Supported interface methods */
    
    public int foo(String str)
        throws CCMException
    {
        System.out.println("app> " + str);
        return str.length();
    }    
    


    /** Component attribute accessor methods */
    


    /** Facet implementation factory methods */
        

    /** Component callback methods */
    
    public void set_session_context(SessionContext ctx) 
        throws CCMException
    {
        this.ctx = (CCM_Test_Context)ctx; 
    }

    public void ccm_activate() 
        throws CCMException
    {
        // OPTIONAL: IMPLEMENT ME HERE !
    }

    public void ccm_passivate() 
        throws CCMException
    {
        // OPTIONAL: IMPLEMENT ME HERE !
    }

    public void ccm_remove() 
        throws CCMException
    {
        // OPTIONAL: IMPLEMENT ME HERE !
    }
}
