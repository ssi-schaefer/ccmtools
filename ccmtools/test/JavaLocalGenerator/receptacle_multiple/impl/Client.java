import world.ccm.local.*;
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
			TestHome_mirror mirrorHome = 
				(TestHome_mirror) ccm.local.HomeFinder.instance().find_home_by_name("TestHomeMirror");

			Test component = home.create();
			Test_mirror mirrorComponent = mirrorHome.create();

			IFace f1 = mirrorComponent.provide_port_mirror();
			IFace f2 = mirrorComponent.provide_port_mirror();
			IFace f3 = mirrorComponent.provide_port_mirror();

			Cookie ck1 = component.connect_port(f1);
			Cookie ck2 = component.connect_port(f2);
			Cookie ck3 = component.connect_port(f3);

			mirrorComponent.configuration_complete();
			component.configuration_complete(); // start test cases in ccm_activate()

			// In this case the component's receptacle calls the
			// component facet.

			component.disconnect_port(ck3);
			component.disconnect_port(ck2);
			component.disconnect_port(ck1);

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
