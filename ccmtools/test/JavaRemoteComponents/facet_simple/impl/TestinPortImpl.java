/**
 * This file was automatically generated by CCM Tools version 0.6.6
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

import ccm.local.Components.CCMException;
   

/** 
 * This class implements a component facet's methods.
 *
 * // TODO: WRITE YOUR DESCRIPTION HERE !
 *
 * @author
 * @version
 */
public class TestinPortImpl 
    implements CCM_IFace
{
    /** Reference to the facet's component implementation */
    private TestImpl component;

    public TestinPortImpl(TestImpl component)
    {
        this.component = component;
    }
 


    /** Business logic implementations */

   
    
     

    public int op1(String str)
        throws CCMException
    {
	System.out.println(">>> " + str);
	return str.length();
    }    

}
