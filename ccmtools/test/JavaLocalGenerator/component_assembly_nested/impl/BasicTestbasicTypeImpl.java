/**
 * This file was automatically generated by CCM Tools version 0.6.5
 * <http://ccmtools.sourceforge.net>
 * 
 * CCM_BasicTypeInterfaceImpl facet implementation.
 *
 * // TODO: WRITE YOUR DESCRIPTION HERE !
 * 
 * @author
 * @version
 */

package world.ccm.local;

import java.util.List;
import java.util.ArrayList;
                 
import ccm.local.Components.*;
 
/** 
 * This class implements a component facet's methods.
 *
 * // TODO: WRITE YOUR DESCRIPTION HERE !
 *
 * @author
 * @version
 */
public class BasicTestbasicTypeImpl 
    implements world.ccm.local.CCM_BasicTypeInterface
{
    /** Reference to the facet's component implementation */
    private world.ccm.local.BasicTestImpl component;

    public BasicTestbasicTypeImpl(world.ccm.local.BasicTestImpl component)
    {
        this.component = component;
    }
 


    /** Business logic implementations */

    public short f1(short p1, ccm.local.Holder<Short> p2, ccm.local.Holder<Short> p3)
        throws ccm.local.Components.CCMException
    {
	p3.setValue(p2.getValue());
	p2.setValue(p1);
        return (short)(p3.getValue() + p1);
    }    

    public int f2(int p1, ccm.local.Holder<Integer> p2, ccm.local.Holder<Integer> p3)
        throws ccm.local.Components.CCMException
    {
        p3.setValue(p2.getValue());
        p2.setValue(p1);
        return p3.getValue() + p1;
    }    

    public short f3(short p1, ccm.local.Holder<Short> p2, ccm.local.Holder<Short> p3)
        throws ccm.local.Components.CCMException
    {
        p3.setValue(p2.getValue());
        p2.setValue(p1);
        return (short)(p3.getValue() + p1);
    }    

    public int f4(int p1, ccm.local.Holder<Integer> p2, ccm.local.Holder<Integer> p3)
        throws ccm.local.Components.CCMException
    {
        p3.setValue(p2.getValue());
        p2.setValue(p1);
        return p3.getValue() + p1;
    }    

    public float f5(float p1, ccm.local.Holder<Float> p2, ccm.local.Holder<Float> p3)
        throws ccm.local.Components.CCMException
    {
	p3.setValue(p2.getValue());
        p2.setValue(p1);
        return p3.getValue() + p1;
    }    

    public double f6(double p1, ccm.local.Holder<Double> p2, ccm.local.Holder<Double> p3)
        throws ccm.local.Components.CCMException
    {
        p3.setValue(p2.getValue());
        p2.setValue(p1);
        return p3.getValue() + p1;
    }    

    public char f7(char p1, ccm.local.Holder<Character> p2, ccm.local.Holder<Character> p3)
        throws ccm.local.Components.CCMException
    {
        p3.setValue(p2.getValue());
        p2.setValue(p1);
        return (char)(p3.getValue() + p1);
    }    

    public String f8(String p1, ccm.local.Holder<String> p2, ccm.local.Holder<String> p3)
        throws ccm.local.Components.CCMException
    {
        p3.setValue(p2.getValue());
        p2.setValue(p1);
        return p3.getValue() + p1;
    }    

    public boolean f9(boolean p1, ccm.local.Holder<Boolean> p2, ccm.local.Holder<Boolean> p3)
        throws ccm.local.Components.CCMException
    {
        p3.setValue(p2.getValue());
        p2.setValue(p1);
        return p3.getValue() && p1;
    }    

    public byte f10(byte p1, ccm.local.Holder<Byte> p2, ccm.local.Holder<Byte> p3)
        throws ccm.local.Components.CCMException
    {
       p3.setValue(p2.getValue());
        p2.setValue(p1);
        return (byte)(p3.getValue() + p1);
    }    
}
