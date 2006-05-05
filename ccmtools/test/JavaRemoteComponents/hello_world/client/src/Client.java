import org.omg.CORBA.ORB;
import org.omg.CORBA.StringHolder;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

import world.ccm.local.*;
import ccm.local.ServiceLocator;

import java.util.logging.*;

public class Client
{
    public static void main(String[] args)
    {
	try
        {
            // Set up the ServiceLocator singleton
            ORB orb = ORB.init(args, null);
            ServiceLocator.instance().setCorbaOrb(orb);

	    HelloWorldHomeClientLibDeployment.deploy("HelloWorldHome");
	}
	catch (java.lang.Exception e)
        {
	    e.printStackTrace();
	}


        try 
	{
	    HelloWorldHome home =
		(HelloWorldHome) ccm.local.HomeFinder.instance().find_home_by_name("HelloWorldHome");
            HelloWorld component = home.create();
            Hello facet = component.provide_hello();
            component.configuration_complete();

	    String msg = facet.sayHello();
	    System.out.println(">> " + msg);

	    component.remove();
        }
        catch(Exception e) 
        {
            e.printStackTrace();
        }

	
	try
	{
	    HelloWorldHomeClientLibDeployment.undeploy("HelloWorldHome");
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
