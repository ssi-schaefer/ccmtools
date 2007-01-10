import org.omg.CORBA.ORB;
import org.omg.CORBA.StringHolder;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

import world.*;
import world.europe.*;
import ccmtools.local.ServiceLocator;
import Components.HomeFinder;

import java.util.logging.*;

public class Client
{
    private static boolean isTest;

    public static void main(String[] args)
    {
        System.out.println("facet constants test case:");

        // Configure Logger
        Logger logger = Logger.getLogger("ccm.local");
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

                ccmtools.remote.world.europe.TestHomeDeployment.deploy("TestHome");
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
            component.configuration_complete();
            Constants constants = component.provide_iface();

            {
                // const boolean BOOLEAN_CONST = TRUE;
                boolean result = constants.getBooleanValue();
                assert (result == Constants.BOOLEAN_CONST);
            }

            {
                // const octet OCTET_CONST = 255;
                byte result = constants.getOctetValue();
                assert (result == Constants.OCTET_CONST);
            }

            {
                // const short SHORT_CONST = -7+10;
                short result = constants.getShortValue();
                assert (result == Constants.SHORT_CONST);
            }

            {
                // const unsigned short USHORT_CONST = 7;
                short result = constants.getUnsignedShortValue();
                assert (result == Constants.USHORT_CONST);
            }

            {
                // const long LONG_CONST = -7777;
                long result = constants.getLongValue();
                assert (result == Constants.LONG_CONST);
            }

            {
                // const unsigned long ULONG_CONST = 7777;
                long result = constants.getUnsignedLongValue();
                assert (result == Constants.ULONG_CONST);
            }

            {
                // const char CHAR_CONST = 'c';
                char result = constants.getCharValue();
                assert (result == Constants.CHAR_CONST);
            }

            {
                // const string STRING_CONST = "1234567890";
                String result = constants.getStringValue();
                assert (result.equals(Constants.STRING_CONST));
            }

            {
                // const float FLOAT_CONST = 3.14;
                float result = constants.getFloatValue();
                assert (Math.abs(Constants.FLOAT_CONST - result) < 0.001);
            }

            {
                // const double DOUBLE_CONST = 3.1415926*2.0;
                double result = constants.getDoubleValue();
                assert (Math.abs(Constants.DOUBLE_CONST - result) < 0.000001);
            }

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
                ccmtools.remote.world.europe.TestHomeDeployment.undeploy("TestHome");
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
