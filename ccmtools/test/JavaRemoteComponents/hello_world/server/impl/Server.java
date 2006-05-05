import org.omg.CORBA.ORB;
import org.omg.CORBA.StringHolder;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

import world.ccm.local.*;
import ccm.local.ServiceLocator;

import java.util.logging.*;

public class Server
{
    public static void main(String[] args)
    {
        try 
	{
	    /**
	     * Server-side code (Part 1)
	     */

	    // Set up the ServiceLocator singleton
	    ORB orb = ORB.init(args, null);
	    ServiceLocator.instance().setCorbaOrb(orb);

       	    world.ccm.remote.HelloWorldHomeDeployment.deploy("HelloWorldHome");
	    System.out.println("> Server is running...");
	    orb.run();
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
	    world.ccm.remote.HelloWorldHomeDeployment.undeploy("HelloWorldHome");
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
