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
    public world.ccm.local.CCM_Test_Context ctx;
    
    public TestImpl()
    {
        // OPTIONAL: IMPLEMENT ME HERE !
    }
    

    public void set_session_context(ccm.local.Components.SessionContext ctx) 
        throws ccm.local.Components.CCMException
    {
        this.ctx = (world.ccm.local.CCM_Test_Context)ctx; 
    }

    public void ccm_activate() 
        throws ccm.local.Components.CCMException
    {
	try
	{
	    System.out.println(">> Receptacle inheritance test ...");
	    world.america.ccm.local.SubType iface = ctx.get_connection_iface();
	    
	    {
		int value = 1;
		iface.attr1(value);
		int result = iface.attr1();
		assert(value == result);
	    }
	    {
		int value = 2;
		iface.attr2(value);
		int result = iface.attr2();
		assert(value == result);
	    }
	    {
		int value = 3;
		iface.attr3(value);
		int result = iface.attr3();
		assert(value == result);
	    }
	    
	    {
		String s = "1234567890";
		int size = iface.op1(s);
		assert(s.length() == size);
	    }
	    {
		String s = "1234567890";
		int size = iface.op2(s);
		assert(s.length() == size);
	    }
	    {
		String s = "1234567890";
		int size = iface.op3(s);
		assert(s.length() == size);
	    }
	    System.out.println(">> OK!");
	}
	catch(ccm.local.Components.NoConnection e)
	{
	    e.printStackTrace();
	    assert(false);
	}
    }

    public void ccm_passivate() 
        throws ccm.local.Components.CCMException
    {
        // OPTIONAL: IMPLEMENT ME HERE !
    }

    public void ccm_remove() 
        throws ccm.local.Components.CCMException
    {
        // OPTIONAL: IMPLEMENT ME HERE !
    }
}
