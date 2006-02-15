import world.europe.austria.ccm.local.*;
import ccm.local.Components.*;

public class Client
{
	public static void main(String[] args)
	{
		// Deploy local Java component
		try
		{
			TestHomeDeployment.deploy("TestHome");
			TestHome_mirrorDeployment.deploy("TestHomeMirror");
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		// Use local Java component
		try
		{
			TestHome home = 
				(TestHome) ccm.local.HomeFinder.instance().find_home_by_name("TestHome");
			TestHome_mirror 
				mirrorHome = (TestHome_mirror) ccm.local.HomeFinder.instance().find_home_by_name("TestHomeMirror");

			Test component = home.create();
			Test_mirror mirrorComponent = mirrorHome.create();

			VoidTypeInterface voidType = mirrorComponent.provide_voidType_mirror();
			BasicTypeInterface basicType = mirrorComponent.provide_basicType_mirror();
			UserTypeInterface userType = mirrorComponent.provide_userType_mirror();

			component.connect_voidType(voidType);
			component.connect_basicType(basicType);
			component.connect_userType(userType);

			mirrorComponent.configuration_complete();
			component.configuration_complete();

			// In this case the component's receptacle calls the
			// component facet.

			component.disconnect_voidType();
			component.disconnect_basicType();
			component.disconnect_userType();

			component.remove();
			mirrorComponent.remove();
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		// Undeploy local Java component
		TestHomeDeployment.undeploy("TestHome");
		TestHome_mirrorDeployment.undeploy("TestHomeMirror");
	}
}
