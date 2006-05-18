import world.ccm.local.*;
import ccm.local.Components.*;

import java.util.logging.*;

public class Client
{
	public static void main(String[] args)
	{
	    // Deploy local Java component
	    try
	    {
		HelloWorldHomeDeployment.deploy("HelloWorldHome");
	    }
	    catch (java.lang.Exception e)
	    {
		e.printStackTrace();
	    }

	    // Use local Java component
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
	    catch (java.lang.Exception e)
	    {
		e.printStackTrace();
	    }
	    finally
	    {
		// Undeploy local Java component
		HelloWorldHomeDeployment.undeploy("HelloWorldHome");
	    }
	}
}
