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

package world.europe.austria.ccm.local;
   
import Components.ccm.local.SessionContext; 
import Components.ccm.local.CCMException;
import Components.ccm.local.NoConnection;

   
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

       try
        {
            System.out.println(">> Receptacle exceptions test ...");
            Console iface = ctx.get_connection_port();

            try
            {
                int result = iface.print("0123456789");
                assert(result == 10);
            }
            catch(ErrorException e)
            {
                e.printStackTrace();
                assert(false);
            }
            catch(Exception e)
            {
                e.printStackTrace();
                assert(false);
            }

            try
            {
                int result = iface.print("ErrorException");
                assert(false);
            }
            catch(ErrorException e)
            {
                System.out.println("catched: " + e.getMessage());
                for(int i = 0; i < e.getInfo().size(); i++)
                {
                    System.out.println(e.getInfo().get(i).getCode() + ": " +
                                       e.getInfo().get(i).getMessage());
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
                assert(false);
            }

            try
            {
                int result = iface.print("SuperError");
                assert(false);
            }
            catch(SuperError e)
            {
                System.out.println("catched: " + e.getMessage());
            }
            catch(Exception e)
            {
                e.printStackTrace();
                assert(false);
            }

	    try
            {
                int result = iface.print("FatalError");
                assert(false);
            }
            catch(FatalError e)
            {
                System.out.println("catched: " + e.getMessage());
            }
            catch(Exception e)
            {
                e.printStackTrace();
                assert(false);
            }

            System.out.println(">> OK!");
        }
        catch(NoConnection e)
        {
            e.printStackTrace();
            assert(false);
        }
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
