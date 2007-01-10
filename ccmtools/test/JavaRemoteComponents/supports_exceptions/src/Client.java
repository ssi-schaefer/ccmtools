import org.omg.CORBA.ORB;
import org.omg.CORBA.StringHolder;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

import world.europe.austria.*;
import ccmtools.local.ServiceLocator;
import Components.HomeFinder;

import java.util.logging.*;

public class Client
{
    private static boolean isTest;

    public static void main(String[] args)
    {
        System.out.println("supports exceptions test case:");

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
            HomeFinder homeFinder = HomeFinder.instance();
            TestHome home = (TestHome) homeFinder.find_home_by_name("TestHome");
            Test component = home.create();
            component.configuration_complete();

            /*
             * Facet Exception Test Cases
             */

            System.out.println("Supported Interface Exception Test ...");
            try
            {
                int result = component.foo("0123456789");
                assert (result == 10);
            }
            catch (ErrorException e)
            {
                e.printStackTrace();
                assert (false);
            }

            try
            {
                int result = component.foo("ErrorException");
                assert (false);
            }
            catch (ErrorException e)
            {
                System.out.println("catched: " + e.getMessage());
                for (int i = 0; i < e.getInfo().size(); i++)
                {
                    System.out.println(e.getInfo().get(i).getCode() + ": " + e.getInfo().get(i).getMessage());
                }
            }

            try
            {
                int result = component.foo("SuperError");
                assert (false);
            }
            catch (SuperError e)
            {
                System.out.println("catched: " + e.getMessage());
            }

            try
            {
                int result = component.foo("FatalError");
                assert (false);
            }
            catch (FatalError e)
            {
                System.out.println("catched: " + e.getMessage());
            }

            System.out.println("OK!");

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
