/**
 * This file was automatically generated by CCM Tools version 0.6.3
 * <http://ccmtools.sourceforge.net>
 * 
 * CCM_VoidTypeInterfaceImpl facet implementation.
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
public class TestvoidTypeImpl 
    implements world.europe.austria.ccm.local.CCM_VoidTypeInterface
{
    /** Reference to the facet's component implementation */
    private world.europe.austria.ccm.local.TestImpl component;


    // This attribute is accessed by explicite set and get methods
    // which are part of VoidTypeInterface.
    int attr;


    public TestvoidTypeImpl(world.europe.austria.ccm.local.TestImpl component)
    {
        this.component = component;
    }

    /** Business logic implementations */
    
    public void f1(int p1)
        throws ccm.local.Components.CCMException
    {
	System.out.println("TestvoidTypeImpl.f1()");
        attr = p1;	
    }    

    public int f2()
        throws ccm.local.Components.CCMException
    {
	System.out.println("TestvoidTypeImpl.f2()");
    	return attr;
    }    
}
