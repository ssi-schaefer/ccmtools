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
                 
import ccm.local.Components.*;
 
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
        System.out.println("+TestifaceImpl.ifaceImpl()");
        this.component = component;
    }

    /** Business logic implementations */
    
    public boolean getBooleanValue()
        throws ccm.local.Components.CCMException
    {
        System.out.println("  getBooleanValue()");
    	return Constants.BOOLEAN_CONST;
    }    

    public byte getOctetValue()
        throws ccm.local.Components.CCMException
    {
        System.out.println("  getOctetValue()");
    	return Constants.OCTET_CONST;
    }    

    public short getShortValue()
        throws ccm.local.Components.CCMException
    {
        System.out.println("  getShortValue()");
    	return Constants.SHORT_CONST;
    }    

    public short getUnsignedShortValue()
        throws ccm.local.Components.CCMException
    {
        System.out.println("  getUnsignedShortValue()");
    	return Constants.USHORT_CONST;
    }    

    public int getLongValue()
        throws ccm.local.Components.CCMException
    {
        System.out.println("  getLongValue()");
    	return Constants.LONG_CONST;
    }    

    public int getUnsignedLongValue()
        throws ccm.local.Components.CCMException
    {
        System.out.println("  getUnsignedLongValue()");
    	return Constants.ULONG_CONST;
    }    

    public char getCharValue()
        throws ccm.local.Components.CCMException
    {
        System.out.println("  getCharValue()");
    	return Constants.CHAR_CONST;
    }    

    public String getStringValue()
        throws ccm.local.Components.CCMException
    {
        System.out.println("  getStringValue()");
    	return Constants.STRING_CONST;
    }    

    public float getFloatValue()
        throws ccm.local.Components.CCMException
    {
        System.out.println("  getFloatValue()");
    	return Constants.FLOAT_CONST;
    }    

    public double getDoubleValue()
        throws ccm.local.Components.CCMException
    {
        System.out.println("  getDoubleValue()");
    	return Constants.DOUBLE_CONST;
    }    
}
