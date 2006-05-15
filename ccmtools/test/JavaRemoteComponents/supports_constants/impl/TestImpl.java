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
   
import Components.ccm.local.SessionContext; 
import Components.ccm.local.CCMException;

import world.ccm.local.CCM_Constants;
import world.ccm.local.Constants;
  
   
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
    
    public boolean getBooleanValue()
        throws CCMException
    {
	return Constants.BOOLEAN_CONST;
    }    

    public byte getOctetValue()
        throws CCMException
    {
	return Constants.OCTET_CONST;
    }    

    public short getShortValue()
        throws CCMException
    {
	return Constants.SHORT_CONST;
    }    

    public short getUnsignedShortValue()
        throws CCMException
    {
	return Constants.USHORT_CONST;
    }    

    public int getLongValue()
        throws CCMException
    {
	return Constants.LONG_CONST;
    }    

    public int getUnsignedLongValue()
        throws CCMException
    {
	return Constants.ULONG_CONST;
    }    

    public char getCharValue()
        throws CCMException
    {
	return Constants.CHAR_CONST;
    }    

    public String getStringValue()
        throws CCMException
    {
	return Constants.STRING_CONST;
    }    

    public float getFloatValue()
        throws CCMException
    {
	return Constants.FLOAT_CONST;
    }    

    public double getDoubleValue()
        throws CCMException
    {
	return Constants.DOUBLE_CONST;
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
