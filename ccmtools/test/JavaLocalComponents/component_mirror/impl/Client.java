import java.util.List;
import java.util.ArrayList;

import world.ccm.local.*;
import Components.ccm.local.*;
import ccm.local.*;

import java.util.logging.*;

import ccm.local.AssemblyFactory;

public class Client
{
	public static void main(String[] args)
	{
	    System.out.println("facet simple mirror test case:");

	    // Configure Logger
	    Logger logger = Logger.getLogger("test");
	    logger.setLevel(Level.FINE);	    
	    Handler handler = new ConsoleHandler();
	    handler.setLevel(Level.ALL);
	    handler.setFormatter(new ccm.local.MinimalFormatter());
	    logger.addHandler(handler);
	    ccm.local.ServiceLocator.instance().setLogger(logger);

	    // Deploy local Java component
         try
        {
            TestHomeDeployment.deploy("TestHome");
            TestHomeMirrorDeployment.deploy("TestHomeMirror");
        }
        catch (java.lang.Exception e)
        {
            e.printStackTrace();
        }
	    
	    // Use local Java component
	    try
	    {
	        TestHome home = (TestHome) ccm.local.HomeFinder.instance().find_home_by_name("TestHome");		
            TestHomeMirror mirrorHome = (TestHomeMirror) ccm.local.HomeFinder.instance().find_home_by_name("TestHomeMirror"); 

            Test component = home.create();
            TestMirror mirrorComponent = mirrorHome.create();
            
            IFace singlePort = mirrorComponent.provide_singlePort();
            component.connect_singlePort(singlePort);
            
            IFace multiPort0 = mirrorComponent.provide_multiPort0();
            IFace multiPort1 = mirrorComponent.provide_multiPort1();
            IFace multiPort2 = mirrorComponent.provide_multiPort2();

            Cookie ck0 = component.connect_multiPort(multiPort0);
            Cookie ck1 = component.connect_multiPort(multiPort1);
            Cookie ck2 = component.connect_multiPort(multiPort2);
                        
            component.configuration_complete();
            mirrorComponent.configuration_complete();

            component.disconnect_multiPort(ck0);
            component.disconnect_multiPort(ck1);
            component.disconnect_multiPort(ck2);
            
            mirrorComponent.remove();
            component.remove();
            System.out.println("OK!");
	    }
	    catch (java.lang.Exception e)
	    {
	        e.printStackTrace();
	    }
	    
	    // Undeploy local Java component
        TestHomeDeployment.undeploy("TestHome");
	    TestHomeMirrorDeployment.undeploy("TestHomeMirror");
	}
}
