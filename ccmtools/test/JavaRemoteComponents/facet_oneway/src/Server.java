import org.omg.CORBA.ORB;
import org.omg.CORBA.StringHolder;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

import world.*;
import ccmtools.local.ServiceLocator;
import Components.HomeFinder;

import java.util.logging.*;

public class Server
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
	    // Setup CORBA component
	    ORB orb = ORB.init(args, null);
	    ServiceLocator.instance().setCorbaOrb(orb);		
	    ccmtools.remote.world.TestHomeDeployment.deploy("TestHome");
	    System.out.println("> Server is running...");
	    orb.run();
	    

	    // Tear down CORBA component
            ccmtools.remote.world.TestHomeDeployment.undeploy("TestHome");
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
