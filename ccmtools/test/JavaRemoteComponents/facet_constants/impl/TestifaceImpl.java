/**
 * This file was automatically generated by CCM Tools version 0.6.3
 * <http://ccmtools.sourceforge.net>
 * 
 * CCM_ConstantsImpl facet implementation.
 *
 * // TODO: WRITE YOUR DESCRIPTION HERE !
 * 
 * @author
 * @version
 */

package world.ccm.local;
                 
import Components.ccm.local.CCMException;
 
/** 
 * This class implements a component facet's methods.
 *
 * // TODO: WRITE YOUR DESCRIPTION HERE !
 *
 * @author
 * @version
 */
public class TestifaceImpl 
    implements world.ccm.local.CCM_Constants
{
    /** Reference to the facet's component implementation */
    private world.europe.ccm.local.TestImpl component;

    public TestifaceImpl(world.europe.ccm.local.TestImpl component)
    {
        this.component = component;
    }

    /** Business logic implementations */
    
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
}
