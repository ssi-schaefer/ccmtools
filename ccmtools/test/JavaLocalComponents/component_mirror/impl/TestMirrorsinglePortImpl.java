/**
 * This file was automatically generated by CCM Tools version 0.7.0
 * <http://ccmtools.sourceforge.net>
 * 
 * CCM_IFaceImpl facet implementation.
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
public class TestMirrorsinglePortImpl 
    implements CCM_IFace
{
    /** Reference to the facet's component implementation */
    private TestMirrorImpl component;

    public TestMirrorsinglePortImpl(TestMirrorImpl component)
    {
        this.component = component;
    }
 

    /** Business logic implementations */

    public int foo(String str)
        throws CCMException
    {
        System.out.println(">> singlePort: " + str);
        return str.length();
    }    

}
