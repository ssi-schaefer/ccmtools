
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
	    TestHome_mirrorDeployer.deploy("TestHomeMirror");
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
	    TestHome_mirror mirrorHome = 
		(TestHome_mirror) ccm.local.HomeFinder.instance().find_home_by_name("TestHomeMirror");

	    Test component = home.create();
	    Test_mirror mirrorComponent = mirrorHome.create();

	    IFace port = mirrorComponent.provide_port_mirror();
	    component.connect_port(port);

	    mirrorComponent.configuration_complete();
	    component.configuration_complete(); // start test cases in ccm_activate()


	    // In this case the component's receptacle calls the 
	    // component facet.

	    component.disconnect_port();

	    component.remove();
	    mirrorComponent.remove();
	}
	catch (java.lang.Exception e)
	{
	    e.printStackTrace();
	}


	// Undeploy local Java component
	TestHomeDeployer.undeploy("TestHome");
	TestHome_mirrorDeployer.undeploy("TestHomeMirror");
    }
}
