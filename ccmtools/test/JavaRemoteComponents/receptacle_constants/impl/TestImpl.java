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

package world.europe.ccm.local;
   
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
            System.out.println(">> Receptacle attributes (Basic Types) Test...");
            world.ccm.local.Constants constants = ctx.get_connection_port();
            {
                //  const boolean BOOLEAN_CONST = TRUE;
                boolean result = constants.getBooleanValue();
                assert(result == world.ccm.local.Constants.BOOLEAN_CONST);
            }
            {
                //  const octet OCTET_CONST = 255;
                byte result = constants.getOctetValue();
                assert(result == world.ccm.local.Constants.OCTET_CONST);
            }
            {
                //  const short SHORT_CONST = -7+10;
                short result = constants.getShortValue();
                assert(result == world.ccm.local.Constants.SHORT_CONST);
            }
            {
                //  const unsigned short USHORT_CONST = 7;
                short result = constants.getUnsignedShortValue();
                assert(result == world.ccm.local.Constants.USHORT_CONST);
            }
            {
                //  const long LONG_CONST = -7777;
                long result = constants.getLongValue();
                assert(result == world.ccm.local.Constants.LONG_CONST);
            }
            {
                //  const unsigned long ULONG_CONST = 7777;
                long result = constants.getUnsignedLongValue();
                assert(result == world.ccm.local.Constants.ULONG_CONST);
            }
            {
                //  const char CHAR_CONST = 'c';
                char result = constants.getCharValue();
                assert(result == world.ccm.local.Constants.CHAR_CONST);
            }
            {
                //  const string STRING_CONST = "1234567890";
                String result = constants.getStringValue();
                assert(result.equals(world.ccm.local.Constants.STRING_CONST));
            }
            {
                //  const float FLOAT_CONST = 3.14;
                float result = constants.getFloatValue();
                assert(Math.abs(world.ccm.local.Constants.FLOAT_CONST - result) < 0.001);
            }
            {
                //  const double DOUBLE_CONST = 3.1415926*2.0;
                double result = constants.getDoubleValue();
                assert(Math.abs(world.ccm.local.Constants.DOUBLE_CONST - result) < 0.000001);
            }
        }
        catch(ccm.local.Components.NoConnection e)
        {
            e.printStackTrace();
            assert(false);
        }
        System.out.println("OK!");


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
