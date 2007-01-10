import org.omg.CORBA.ORB;
import org.omg.CORBA.StringHolder;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

import world.*;
import ccmtools.local.ServiceLocator;
import Components.HomeFinder;
import Components.Cookie;

import java.util.logging.*;

public class Client
{
    private static boolean isTest;

    public static void main(String[] args)
    {
        System.out.println("receptacle multiple test case:");

        // Configure Logger
        Logger logger = Logger.getLogger("test");
        logger.setLevel(Level.FINER);
        Handler handler = new ConsoleHandler();
        handler.setLevel(Level.ALL);
        handler.setFormatter(new ccmtools.utils.SimpleFormatter());
        logger.addHandler(handler);
        ServiceLocator.instance().setLogger(logger);

        try
        {
            /**
             * Server-side code (Part 1)
             */
            if (args.length == 0)
            {
                TestHomeDeployment.deploy("TestHome");
            }
            else
            {
                // Set up the ServiceLocator singleton
                ORB orb = ORB.init(args, null);
                ServiceLocator.instance().setCorbaOrb(orb);
                ccmtools.remote.world.TestHomeDeployment.deploy("TestHome");
                TestHomeClientLibDeployment.deploy("TestHome");
                System.out.println("> Server is running...");
                // orb.run();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        try
        {
            /**
             * Client-side code (co-located with clientlib)
             */
            HomeFinder homeFinder = HomeFinder.instance();
            TestHome home = (TestHome) homeFinder.find_home_by_name("TestHome");
            Test component = home.create();
            Cookie ck1 = component.connect_port(new client.MyIFace("one"));
            Cookie ck2 = component.connect_port(new client.MyIFace("two"));
            Cookie ck3 = component.connect_port(new client.MyIFace("three"));

            component.configuration_complete();

            // now the remote component's business logic calls methods to the
            // connected object.

            component.disconnect_port(ck1);
            component.disconnect_port(ck2);
            component.disconnect_port(ck3);
            component.remove();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        try
        {
            /**
             * Server-side code (Part 2)
             */
            if (args.length == 0)
            {
                TestHomeDeployment.undeploy("TestHome");
            }
            else
            {
                TestHomeClientLibDeployment.undeploy("TestHome");
                ccmtools.remote.world.TestHomeDeployment.undeploy("TestHome");
            }
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
