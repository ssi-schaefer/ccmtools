/**
 * This file was automatically generated by CCM Tools version 0.6.3
 * <http://ccmtools.sourceforge.net>
 * 
 * CCM_BasicTypeInterfaceImpl facet implementation.
 *
 * // TODO: WRITE YOUR DESCRIPTION HERE !
 * 
 * @author
 * @version
 */

package world.europe.austria.ccm.local;
                 
import ccm.local.Components.*;
 
/** 
 * This class implements a component facet's methods.
 *
 * // TODO: WRITE YOUR DESCRIPTION HERE !
 *
 * @author
 * @version
 */
public class TestbasicTypeImpl 
    implements world.europe.austria.ccm.local.CCM_BasicTypeInterface
{
    /** Reference to the facet's component implementation */
    private world.europe.austria.ccm.local.TestImpl component;

    public TestbasicTypeImpl(world.europe.austria.ccm.local.TestImpl component)
    {
        this.component = component;
    }

    /** Business logic implementations */
    
    public short f1(short p1, org.omg.CORBA.ShortHolder p2, org.omg.CORBA.ShortHolder p3)
        throws ccm.local.Components.CCMException
    {
	p3.value = p2.value;
	p2.value = p1;
	return (short)(p3.value + p1);
    }    

    public int f2(int p1, org.omg.CORBA.IntHolder p2, org.omg.CORBA.IntHolder p3)
        throws ccm.local.Components.CCMException
    {
	p3.value = p2.value;
	p2.value = p1;
	return p3.value + p1;
    }    

    public short f3(short p1, org.omg.CORBA.ShortHolder p2, org.omg.CORBA.ShortHolder p3)
        throws ccm.local.Components.CCMException
    {
	p3.value = p2.value;
	p2.value = p1;
	return (short)(p3.value + p1);
    }    

    public int f4(int p1, org.omg.CORBA.IntHolder p2, org.omg.CORBA.IntHolder p3)
        throws ccm.local.Components.CCMException
    {
	p3.value = p2.value;
	p2.value = p1;
	return p3.value + p1;
    }    

    public float f5(float p1, org.omg.CORBA.FloatHolder p2, org.omg.CORBA.FloatHolder p3)
        throws ccm.local.Components.CCMException
    {
	p3.value = p2.value;
	p2.value = p1;
	return p3.value + p1;
    }    

    public double f6(double p1, org.omg.CORBA.DoubleHolder p2, org.omg.CORBA.DoubleHolder p3)
        throws ccm.local.Components.CCMException
    {
	p3.value = p2.value;
	p2.value = p1;
	return p3.value + p1;
    }    

    public char f7(char p1, org.omg.CORBA.CharHolder p2, org.omg.CORBA.CharHolder p3)
        throws ccm.local.Components.CCMException
    {
	p3.value = p2.value;
	p2.value = p1;
	return (char)(p3.value + p1);
    }    

    public String f8(String p1, org.omg.CORBA.StringHolder p2, org.omg.CORBA.StringHolder p3)
        throws ccm.local.Components.CCMException
    {
	p3.value = p2.value;
	p2.value = p1;
	return p3.value +p1;
    }    

    public boolean f9(boolean p1, org.omg.CORBA.BooleanHolder p2, org.omg.CORBA.BooleanHolder p3)
        throws ccm.local.Components.CCMException
    {
	p3.value = p2.value;
	p2.value = p1;
	return p3.value && p1;
    }    

    public byte f10(byte p1, org.omg.CORBA.ByteHolder p2, org.omg.CORBA.ByteHolder p3)
        throws ccm.local.Components.CCMException
    {
	p3.value = p2.value;
	p2.value = p1;
	return (byte)(p3.value + p1);
    }    
}
