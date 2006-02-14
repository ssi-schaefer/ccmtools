
import world.europe.austria.ccm.local.*;
import ccm.local.Components.*;

public class Client
{    
    public static void main(String[] args) 
    {
	// Deploy local Java component
	try
	{
	    TestHomeDeployer.deploy("TestHome");
	}
	catch(java.lang.Exception e)
	{
	    e.printStackTrace();
	}


	// Use local Java component
	try
	{
	    TestHome home = 
		(TestHome) ccm.local.HomeFinder.instance().find_home_by_name("TestHome");
	    
	    Test component = home.create();
	    IFace inPort = component.provide_inPort();
	    component.connect_outPort(inPort);
	    component.configuration_complete();
   
	    // In this case the component's receptacle calls the 
	    // component facet.

	    component.disconnect_outPort();
	    component.remove();
	}
	catch (java.lang.Exception e)
	{
	    e.printStackTrace();
	}


	// Undeploy local Java component
	TestHomeDeployer.undeploy("TestHome");
    }
}
