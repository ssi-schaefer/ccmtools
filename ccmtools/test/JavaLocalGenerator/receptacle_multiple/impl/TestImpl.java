/**
 * This file was automatically generated by CCM Tools version 0.6.3
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

import java.util.Map;
import java.util.Iterator;
   
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
    /** Component attribute variables */
     

    public world.ccm.local.CCM_Test_Context ctx;
    
    
    public TestImpl()
    {
    	System.out.println("+TestImpl.TestImpl()");
        // OPTIONAL: IMPLEMENT ME HERE !
    }
    


    /** Facet implementation factory methods */
        

    /** Component callback methods */
    
    public void set_session_context(ccm.local.Components.SessionContext ctx) 
        throws ccm.local.Components.CCMException
    {
        System.out.println(" TestImpl.set_session_context()");
        this.ctx = (world.ccm.local.CCM_Test_Context)ctx; 
    }

    public void ccm_activate() 
        throws ccm.local.Components.CCMException
    {
        System.out.println(" TestImpl.ccm_activate()");

	Map receptacleMap = ctx.get_connections_port();

	for(Iterator i = receptacleMap.values().iterator(); i.hasNext();)
	{
	    world.ccm.local.IFace receptacle = (world.ccm.local.IFace)i.next();
	    String s = "01234567890";
	    int len = receptacle.println(s);
	    assert(len == s.length());
	}
    }

    public void ccm_passivate() 
        throws ccm.local.Components.CCMException
    {
        System.out.println(" TestImpl.ccm_passivate()");
        // OPTIONAL: IMPLEMENT ME HERE !
    }

    public void ccm_remove() 
        throws ccm.local.Components.CCMException
    {
        System.out.println(" TestImpl.ccm_remove()");
        // OPTIONAL: IMPLEMENT ME HERE !
    }
}
