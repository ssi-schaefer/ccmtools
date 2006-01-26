import org.omg.CORBA.ORB;

import world.europe.austria.ccm.local.Test;
import world.europe.austria.ccm.local.TestHome;
import world.europe.austria.ccm.local.TestHomeFactory;
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

			// Register homes to the HomeFinder
			ccm.local.HomeFinder.instance().register_home(TestHomeFactory.create(), "myTestHome");
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
			component.connect_outPort(new client.myConsoleImpl());
			component.configuration_complete();

			// There is no code needed because the remote component calls
			// the implementations supported by the connected my*Impl objects
			// after component.configuration_complete()
			// Note: configuration_complete() is a synchron remote call, thus,
			// this method blocks untils all tests i nthe remote ccm_activate()
			// method are executed.
			
			component.disconnect_outPort();
			component.remove();
			System.out.println("OK!");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		// Unregister homes from the HomeFinder
		ccm.local.HomeFinder.instance().unregister_home("myTestHome");
		System.exit(0);
	}
}
