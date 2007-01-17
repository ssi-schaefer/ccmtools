import org.omg.CORBA.ORB;
import org.omg.CORBA.StringHolder;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

import world.*;
import ccmtools.local.ServiceLocator;
import Components.HomeFinder;

import java.util.logging.*;

public class Client
{
    private static boolean isTest;

    public static void main(String[] args)
    {
        // Configure Logger
        Logger logger = Logger.getLogger("test");
        logger.setLevel(Level.FINER);
        Handler handler = new ConsoleHandler();
        handler.setLevel(Level.ALL);
        handler.setFormatter(new ccmtools.utils.SimpleFormatter());
        logger.addHandler(handler);
        ccmtools.local.ServiceLocator.instance().setLogger(logger);

	try
	{
	    // Setup client library component
            ORB orb = ORB.init(args, null);
            ServiceLocator.instance().setCorbaOrb(orb);
            TestHomeClientLibDeployment.deploy("TestHome");
	    

	    // Use client library component to access a corba component
            HomeFinder homeFinder = Components.HomeFinder.instance();
            TestHome home = (TestHome) homeFinder.find_home_by_name("TestHome");
            Test component = home.create();
            component.configuration_complete();

            IFace port = component.provide_port();
	    
	    System.out.println(">>> before foo()");
            port.foo("11111111111");
	    System.out.println(">>> after first foo()");
            port.foo("22222222222");
	    System.out.println(">>> after second foo()");

            component.remove();


	    // Tear down client library component
	    TestHomeClientLibDeployment.undeploy("TestHome");
            System.out.println("OK!");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            // Tear down the ServiceLocator singleton
            ServiceLocator.instance().destroy();
        }
    }
}
