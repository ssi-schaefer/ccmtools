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
            TestHome_mirrorDeployment.deploy("TestHomeMirror");
        }
        catch (java.lang.Exception e)
        {
            e.printStackTrace();
        }
	    
	    // Use local Java component
	    try
	    {
	        TestHome home = (TestHome) ccm.local.HomeFinder.instance().find_home_by_name("TestHome");		
            Test component = home.create();
            TestHome_mirror mirrorHome = (TestHome_mirror) ccm.local.HomeFinder.instance().find_home_by_name("TestHomeMirror"); 
            Test_mirror mirrorComponent = mirrorHome.create();
            component.configuration_complete();
            mirrorComponent.configuration_complete();
		
            System.out.println("OK!");
            component.remove();
            mirrorComponent.remove();
	    }
	    catch (java.lang.Exception e)
	    {
	        e.printStackTrace();
	    }
	    
	    // Undeploy local Java component
        TestHomeDeployment.undeploy("TestHome");
	    TestHome_mirrorDeployment.undeploy("TestHomeMirror");
	}
}
