import world.ccm.local.*;
import world.europe.ccm.local.*;
import world.america.ccm.local.*;
import ccm.local.Components.*;

import java.util.logging.*;

public class Client
{
	public static void main(String[] args)
	{
	    // Configure Logger
	    Logger logger = Logger.getLogger("ccm.local");
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
			TestHome home = 
				(TestHome) ccm.local.HomeFinder.instance().find_home_by_name("TestHome");
			TestHome_mirror 
				mirrorHome = (TestHome_mirror) ccm.local.HomeFinder.instance().find_home_by_name("TestHomeMirror");

			Test component = home.create();
			Test_mirror mirrorComponent = mirrorHome.create();

			SubType iface = mirrorComponent.provide_iface_mirror();

			component.connect_iface(iface);

			mirrorComponent.configuration_complete();
			component.configuration_complete();

			// In this case the component's receptacle calls the
			// component facet.

			component.disconnect_iface();

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
