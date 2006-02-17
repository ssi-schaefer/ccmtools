import org.omg.CORBA.ORB;

import world.europe.ccm.local.*;
import ccm.local.ServiceLocator;

public class Client
{
	public static void main(String[] args)
	{
		ORB orb = ORB.init(args, null);
		try
		{
			// Set up the ServiceLocator singleton
			ServiceLocator.instance().setCorbaOrb(orb);

			// Deploy ClientLib component
			TestHomeClientLibDeployment.deploy("myTestHome");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		try
		{
			System.out.println("Client");

			ccm.local.Components.HomeFinder homeFinder = ccm.local.HomeFinder.instance();
			TestHome home = (TestHome) homeFinder.find_home_by_name("myTestHome");

			Test component = home.create();
			component.connect_ifaceOut(new client.myConstantsImpl());
			component.configuration_complete();

			// There is no code needed because the remote component calls
			// the implementations supported by the connected my*Impl objects
			// after component.configuration_complete()
			// Note: configuration_complete() is a synchron remote call, thus,
			// this method blocks untils all tests in the remote ccm_activate()
			// method are executed.
			
			component.disconnect_ifaceOut();
			component.remove();
			System.out.println("OK!");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
		    // Undeploy ClientLib component
		    TestHomeClientLibDeployment.undeploy("myTestHome");
		    
		    // Tear down the ServiceLocator singleton
		    ServiceLocator.instance().destroy();	
		}
	}
}
