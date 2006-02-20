import world.europe.austria.ccm.local.*;
import ccm.local.Components.*;

public class DynamicClient
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

			VoidTypeInterface voidType = 
			    (VoidTypeInterface)mirrorComponent.provide_facet("voidType_mirror");
			BasicTypeInterface basicType = 
			    (BasicTypeInterface)mirrorComponent.provide_facet("basicType_mirror");
			UserTypeInterface userType = 
			    (UserTypeInterface)mirrorComponent.provide_facet("userType_mirror");

			Cookie ck1 = component.connect("voidType", voidType);
			Cookie ck2 =component.connect("basicType", basicType);
			Cookie ck3 =component.connect("userType", userType);

			mirrorComponent.configuration_complete();
			component.configuration_complete();

			// In this case the component's receptacle calls the
			// component facet.

			component.disconnect("voidType", ck1);
			component.disconnect("basicType", ck2);
			component.disconnect("userType", ck3);

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
