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
    /** Supported interface attribute variables */

    private int attr1_;
    private int attr2_;
    private int attr3_;    
    

    /** Component attribute variables */
     

    public world.ccm.local.CCM_Test_Context ctx;
    
    
    public TestImpl()
    {
        // OPTIONAL: IMPLEMENT ME HERE !
    }


    /* 
     * Supported interface methods 
     */

    /** Supported interface constants */
    
    
    

    /** Supported interface attributes */


    public int attr1()
        throws ccm.local.Components.CCMException
    {
        return this.attr1_;
    }     

    public void attr1(int value)
        throws ccm.local.Components.CCMException
    {
        this.attr1_ = value;
    }


    public int attr2()
        throws ccm.local.Components.CCMException
    {
        return this.attr2_;
    }     

    public void attr2(int value)
        throws ccm.local.Components.CCMException
    {
        this.attr2_ = value;
    }


    public int attr3()
        throws ccm.local.Components.CCMException
    {
        return this.attr3_;
    }     

    public void attr3(int value)
        throws ccm.local.Components.CCMException
    {
        this.attr3_ = value;
    }
    
    
    
    /** Supported interface methods */
    
    public int op1(String str)
        throws ccm.local.Components.CCMException
    {
	System.out.println("app> " + str);
	return str.length();
    }    

    public int op2(String str)
        throws ccm.local.Components.CCMException
    {
	System.out.println("app> " + str);
	return str.length();
    }    

    public int op3(String str)
        throws ccm.local.Components.CCMException
    {
	System.out.println("app> " + str);
        return str.length();
    }    
    


    /** Component attribute accessor methods */
    


    /** Facet implementation factory methods */
        

    /** Component callback methods */
    
    public void set_session_context(ccm.local.Components.SessionContext ctx) 
        throws ccm.local.Components.CCMException
    {
        this.ctx = (world.ccm.local.CCM_Test_Context)ctx; 
    }

    public void ccm_activate() 
        throws ccm.local.Components.CCMException
    {
        // OPTIONAL: IMPLEMENT ME HERE !
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
