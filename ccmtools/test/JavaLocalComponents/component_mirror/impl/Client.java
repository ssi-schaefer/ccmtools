import java.util.List;
import java.util.ArrayList;

import world.*;
import Components.*;
import ccmtools.local.ServiceLocator;

import java.util.logging.*;

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
	    handler.setFormatter(new ccmtools.utils.SimpleFormatter());
	    logger.addHandler(handler);
	    ServiceLocator.instance().setLogger(logger);

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
	        TestHome home = (TestHome) HomeFinder.instance().find_home_by_name("TestHome");		
            TestHomeMirror mirrorHome = (TestHomeMirror) HomeFinder.instance().find_home_by_name("TestHomeMirror"); 

            Test component = home.create();
            TestMirror mirrorComponent = mirrorHome.create();
            
            IFace inPort = component.provide_inPort();
            mirrorComponent.connect_inPort(inPort);
            
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
