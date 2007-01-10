import org.omg.CORBA.ORB;
import org.omg.CORBA.StringHolder;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

import world.*;
import world.europe.*;
import world.europe.austria.*;

import ccmtools.local.ServiceLocator;
import Components.HomeFinder;

import java.util.logging.*;

public class Client
{
    public static void main(String[] args)
    {
        System.out.println("receptacle attributes test case:");
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
                ccmtools.remote.world.europe.austria.TestHomeDeployment.deploy("TestHome");
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
            HomeFinder homeFinder = Components.HomeFinder.instance();
            TestHome home = (TestHome) homeFinder.find_home_by_name("TestHome");
            Test component = home.create();
            component.connect_basicType(new client.MyBasicTypeImpl());
            component.connect_userType(new client.MyUserTypeImpl());
            component.configuration_complete();

            // now the remote component's business logic calls methods to the
            // connected object.

            component.disconnect_basicType();
            component.disconnect_userType();
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
                ccmtools.remote.world.europe.austria.TestHomeDeployment.undeploy("TestHome");
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
