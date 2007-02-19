/**
 * This file was automatically generated by CCM Tools version 0.9.0
 * <http://ccmtools.sourceforge.net>
 *
 * CCM_C3 component business logic.
 *
 * // TODO: WRITE YOUR DESCRIPTION HERE !
 *
 * @author
 * @version
 */

package wamas;

import Components.CCMException;
import Components.CCMExceptionReason;
import Components.SessionContext;



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
public class C3Impl
    implements CCM_C3
{
    /** Supported interface attribute variables */



    /** Component attribute variables */

    private int b_;

    public CCM_C3_Context ctx;


    // component Test::C1 comp1;
    private wamas.Test.C1 comp1_;

    // component Test::C2 comp2;
    private wamas.Test.C2 comp2_;


    public C3Impl()
    {
        // OPTIONAL: IMPLEMENT ME HERE !
    }


    /*
     * Supported interface methods
     */

    /** Supported interface attributes */



    /** Supported interface methods */



    /** Component attribute accessor methods */


    public int b()
        throws CCMException
    {
        return this.b_;
    }

    public void b(int value)
        throws CCMException
    {
        this.b_ = value;
    }



    /** Facet implementation factory methods */


    private wamas.C3i1Impl i1_;

    public wamas.Test.CCM_I1 get_i1()
    {
        if(i1_==null)
            i1_ = new wamas.C3i1Impl(this);
        return i1_;
    }


    /** Component callback methods */

    public void set_session_context(SessionContext ctx)
        throws CCMException
    {
        this.ctx = (CCM_C3_Context)ctx;
    }

    public void ccm_activate()
        throws CCMException
    {
        try
        {
            {
                // component Test::C1 comp1;
                wamas.Test.H1 home = (wamas.Test.H1)wamas.Test.H1Deployment.create();
                comp1_ = home.create();
            }

            {
                // component wamas::Test::C2 comp2;
                wamas.Test.H2 home = (wamas.Test.H2)wamas.Test.H2Deployment.create();
                comp2_ = home.create();
            }

            // connect comp2.i2 to comp1.i2;
            comp1_.connect_i2(comp2_.provide_i2());

            // connect comp1.i1 to this.i1;
            if(i1_!=null)
                i1_.target = comp1_.provide_i1();

            // connect this.i3 to comp2.i3;
            comp2_.connect_i3(ctx.get_connection_i3());

            // constant comp1.a1 = "Hello World";
            comp1_.a1("Hello World");

            // constant comp1.a2 = 642;
            comp1_.a2(642);

            // attribute comp2.b = this.b;
            comp2_.b(this.b_);

            // finish configuration
            comp1_.configuration_complete();
            comp2_.configuration_complete();
        }
        catch(Exception e)
        {
            throw new CCMException(e.getMessage(), CCMExceptionReason.CREATE_ERROR);
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
