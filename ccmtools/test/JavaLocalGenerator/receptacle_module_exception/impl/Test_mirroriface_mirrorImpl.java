/**
 * This file was automatically generated by CCM Tools version 0.6.3
 * <http://ccmtools.sourceforge.net>
 * 
 * CCM_IFaceImpl facet implementation.
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
public class Test_mirroriface_mirrorImpl 
    implements world.europe.austria.ccm.local.CCM_IFace
{
    /** Reference to the facet's component implementation */
    private world.europe.austria.ccm.local.Test_mirrorImpl component;

    public Test_mirroriface_mirrorImpl(world.europe.austria.ccm.local.Test_mirrorImpl component)
    {
        this.component = component;
    }

    /** Business logic implementations */

    public int foo(String msg)
        throws ccm.local.Components.CCMException, 
	       world.europe.austria.ErrorException, 
	       world.europe.austria.SuperError, 
	       world.europe.austria.FatalError
    {
        if(msg.equals("Error"))
        {
            world.europe.austria.ErrorException error =
                new world.europe.austria.ErrorException();
            world.europe.austria.ErrorInfo[] errorInfoList =
                new world.europe.austria.ErrorInfo[1];
            world.europe.austria.ErrorInfo errorInfo =
                new world.europe.austria.ErrorInfo(7, "A simple error!");
            errorInfoList[0] = errorInfo;
            error.info = errorInfoList;
            throw error;
        }

        if(msg.equals("SuperError"))
            throw new world.europe.austria.SuperError();

        if(msg.equals("FatalError"))
            throw new world.europe.austria.FatalError();

        return msg.length();
    }    
}
