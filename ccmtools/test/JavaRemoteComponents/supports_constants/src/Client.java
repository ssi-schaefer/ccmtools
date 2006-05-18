import org.omg.CORBA.ORB;
import org.omg.CORBA.StringHolder;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

import world.ccm.local.*;
import world.europe.ccm.local.*;
import ccm.local.ServiceLocator;
import Components.ccm.local.HomeFinder;

import java.util.logging.*;

public class Client
{
    private static boolean isTest;

    public static void main(String[] args)
    {
	System.out.println("supports constants test case:");
	// Configure Logger
	Logger logger = Logger.getLogger("test");
	logger.setLevel(Level.FINER);
	Handler handler = new ConsoleHandler();
	handler.setLevel(Level.ALL);
	handler.setFormatter(new ccm.local.MinimalFormatter());
	logger.addHandler(handler);
	ccm.local.ServiceLocator.instance().setLogger(logger);
	
        try 
	{
	    /**
	     * Server-side code (Part 1)
	     */
            if(args.length == 0)
            {
                TestHomeDeployment.deploy("TestHome");
            }
            else
            {
		// Set up the ServiceLocator singleton
		ORB orb = ORB.init(args, null);
		ServiceLocator.instance().setCorbaOrb(orb);
		world.europe.ccm.remote.TestHomeDeployment.deploy("TestHome");
		TestHomeClientLibDeployment.deploy("TestHome");
		System.out.println("> Server is running...");
		// orb.run();
	    }
	}
        catch(Exception e) 
        {
            e.printStackTrace();
        }


        try 
	{
	    /**
	     * Client-side code (co-located with clientlib)
	     **/
	    HomeFinder homeFinder = ccm.local.HomeFinder.instance();
            TestHome home = (TestHome) homeFinder.find_home_by_name("TestHome");
            Test component = home.create();
            component.configuration_complete();

            System.out.println("Supported Interface Constants Test...");
            {
                //  const boolean BOOLEAN_CONST = TRUE;
                boolean result = component.getBooleanValue();
                assert(result == Constants.BOOLEAN_CONST);
            }

            {
                //  const octet OCTET_CONST = 255;
                byte result = component.getOctetValue();
                assert(result == Constants.OCTET_CONST);
            }

            {
                //  const short SHORT_CONST = -7+10;
                short result = component.getShortValue();
                assert(result == Constants.SHORT_CONST);
            }

            {
                //  const unsigned short USHORT_CONST = 7;
                short result = component.getUnsignedShortValue();
                assert(result == Constants.USHORT_CONST);
            }

            {
                //  const long LONG_CONST = -7777;
                long result = component.getLongValue();
                assert(result == Constants.LONG_CONST);
            }

            {
                //  const unsigned long ULONG_CONST = 7777;
                long result = component.getUnsignedLongValue();
                assert(result == Constants.ULONG_CONST);
            }

            {
                //  const char CHAR_CONST = 'c';
                char result = component.getCharValue();
                assert(result == Constants.CHAR_CONST);
            }

            {
                //  const string STRING_CONST = "1234567890";
                String result = component.getStringValue();
                assert(result.equals(Constants.STRING_CONST));
            }

            {
                //  const float FLOAT_CONST = 3.14;
                float result = component.getFloatValue();
                assert(Math.abs(Constants.FLOAT_CONST - result) < 0.001);
            }

            {
                //  const double DOUBLE_CONST = 3.1415926*2.0;
                double result = component.getDoubleValue();
                assert(Math.abs(Constants.DOUBLE_CONST - result) < 0.000001);
            }

	    component.remove();
        }
        catch(Exception e) 
        {
            e.printStackTrace();
        }

	
	try
	{
	    /**
	     * Server-side code (Part 2)
	     */
            if(args.length == 0)
            {
                TestHomeDeployment.undeploy("TestHome");
            }
            else
            {
		TestHomeClientLibDeployment.undeploy("TestHome");
		world.europe.ccm.remote.TestHomeDeployment.undeploy("TestHome");
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
