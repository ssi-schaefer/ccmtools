/**
 * This file was automatically generated by CCM Tools version 0.6.3
 * <http://ccmtools.sourceforge.net>
 * 
 * CCM_SubTypeImpl facet implementation.
 *
 * // TODO: WRITE YOUR DESCRIPTION HERE !
 * 
 * @author
 * @version
 */

package world.america.ccm.local;
                 
import ccm.local.Components.*;
 
/** 
 * This class implements a component facet's methods.
 *
 * // TODO: WRITE YOUR DESCRIPTION HERE !
 *
 * @author
 * @version
 */
public class Test_mirroriface_mirrorImpl 
    implements world.america.ccm.local.CCM_SubType
{
    /** Reference to the facet's component implementation */
    private world.ccm.local.Test_mirrorImpl component;

    public Test_mirroriface_mirrorImpl(world.ccm.local.Test_mirrorImpl component)
    {
        this.component = component;
    }
    
    private int attr1_;    
    private int attr2_;
    private int attr3_; 


    /** Business logic implementations */
    

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
}
