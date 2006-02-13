
import world.ccm.local.*;
import ccm.local.Components.*;

public class Client
{    
    public static void main(String[] args) 
    {
	// Deploy local Java component
	try
	{
	    world.ccm.local.TestHomeDeployer.deploy("myTest");
	}
	catch(java.lang.Exception e)
	{
	    e.printStackTrace();
	}


	// Use local Java component
	try
	{
	    TestHome home = 
		(TestHome) ccm.local.HomeFinder.instance().find_home_by_name("myTest");
	    
	    Test component = home.create();
	    IFace inPort = component.provide_inPort();
	   
	    {
		System.out.println("Begin Test Case -----");
		String msg = "Hello World!";
		int len = inPort.op1(msg);
		System.out.println("len = " + len);
		assert(len == msg.length());
		System.out.println("End Test Case -------");
	    }

	    component.remove();
	}
	catch (java.lang.Exception e)
	{
	    e.printStackTrace();
	}


	// Undeploy local Java component
	world.ccm.local.TestHomeDeployer.undeploy("myTest");
    }
}
