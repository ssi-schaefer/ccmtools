import org.omg.CORBA.ShortHolder;
import org.omg.CORBA.IntHolder;
import org.omg.CORBA.FloatHolder;
import org.omg.CORBA.DoubleHolder;
import org.omg.CORBA.CharHolder;
import org.omg.CORBA.BooleanHolder;
import org.omg.CORBA.ByteHolder;

import org.omg.CORBA.ORB;
import org.omg.CORBA.StringHolder;

import world.ccm.local.*;
import world.europe.ccm.local.*;

import ccm.local.ServiceLocator;
import java.util.logging.*;

public class Client
{
    public static void main(String[] args)
    {
        try {
	    // Configure Logger
	    Logger logger = Logger.getLogger("ccm.local");
	    logger.setLevel(Level.FINE);	    
	    Handler handler = new ConsoleHandler();
	    handler.setLevel(Level.ALL);
	    handler.setFormatter(new ccm.local.MinimalFormatter());
	    logger.addHandler(handler);
	    ccm.local.ServiceLocator.instance().setLogger(logger);

            // Deploy ClientLib component
	    TestHomeDeployment.deploy("myTestHome");
        }
        catch(Exception e) {
            e.printStackTrace();
        }

	
	try
	{
	    System.out.println("Begin Test Case -----");
	    
	    ccm.local.Components.HomeFinder homeFinder = 
		ccm.local.HomeFinder.instance();
	    TestHome home = 
		(TestHome) homeFinder.find_home_by_name("myTestHome");

	    Test component = home.create();	    
	    component.configuration_complete();
	    Constants constants = component.provide_iface();

	    {
		//  const boolean BOOLEAN_CONST = TRUE;
		boolean result = constants.getBooleanValue();
		assert(result == Constants.BOOLEAN_CONST);
	    }

	    {
		//  const octet OCTET_CONST = 255;
		byte result = constants.getOctetValue();
		assert(result == Constants.OCTET_CONST);
	    }

	    {
		//  const short SHORT_CONST = -7+10;
		short result = constants.getShortValue();
		assert(result == Constants.SHORT_CONST);
	    }
	    
	    {
		//  const unsigned short USHORT_CONST = 7;
		short result = constants.getUnsignedShortValue();
		assert(result == Constants.USHORT_CONST);
	    }
	    
	    {
		//  const long LONG_CONST = -7777;
		long result = constants.getLongValue();
		assert(result == Constants.LONG_CONST);
	    }

	    {
		//  const unsigned long ULONG_CONST = 7777;
		long result = constants.getUnsignedLongValue();
		assert(result == Constants.ULONG_CONST);
	    }

	    {
		//  const char CHAR_CONST = 'c';
		char result = constants.getCharValue();
		assert(result == Constants.CHAR_CONST);
	    }

	    {
		//  const string STRING_CONST = "1234567890";
		String result = constants.getStringValue();
		assert(result.equals(Constants.STRING_CONST));
	    }
	    
	    {
		//  const float FLOAT_CONST = 3.14;
		float result = constants.getFloatValue();
		assert(Math.abs(Constants.FLOAT_CONST - result) < 0.001);
	    }

	    {
		//  const double DOUBLE_CONST = 3.1415926*2.0;
		double result = constants.getDoubleValue();
		assert(Math.abs(Constants.DOUBLE_CONST - result) < 0.000001);
	    }
	    
	    component.remove();
	    System.out.println("End Test Case -------");
	}
	catch (Exception e)
	{
	    e.printStackTrace();
	}
	finally
	{
	    // Undeploy ClientLib component
	    TestHomeDeployment.undeploy("myTestHome");
	}
    }
}
