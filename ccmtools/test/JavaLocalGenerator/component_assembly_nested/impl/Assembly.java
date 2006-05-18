package world;

import Components.ccm.local.AssemblyState;
import Components.ccm.local.CreateFailure;
import Components.ccm.local.RemoveFailure;
import Components.ccm.local.HomeNotFound;
import Components.ccm.local.InvalidConfiguration;
import Components.ccm.local.CCMObject;
import Components.ccm.local.HomeFinder;

import world.ccm.local.SuperTest;
import world.ccm.local.SuperTestHome;
import world.ccm.local.BasicTypeInterface;
import world.ccm.local.UserTypeInterface;

import world.ccm.local.BasicTest;
import world.ccm.local.BasicTestHome;
import world.ccm.local.BasicTypeInterface;

import world.ccm.local.UserTest;
import world.ccm.local.UserTestHome;
import world.ccm.local.UserTypeInterface;

import world.ccm.local.SuperTestHome;
import world.ccm.local.SuperTest;

public class Assembly
    implements Components.ccm.local.Assembly
{
    private AssemblyState state;

    // Super Component: SuperTest
    private SuperTest superTest;
    private BasicTypeInterface innerBasicType;
    private UserTypeInterface innerUserType;

    // Inner Component: BasicTest
    private BasicTest basicTest;
    private BasicTypeInterface basicType;

    // Inner Component: UserTest
    private UserTest userTest;
    private UserTypeInterface userType;



    public Assembly()
    {
	state = AssemblyState.INACTIVE;
    }

    public void build()
        throws CreateFailure
    {
	System.out.println("+Assembly.build()");
	throw new CreateFailure();
    }


    public void build(CCMObject facadeComponent)
        throws CreateFailure
    {

	System.out.println("+Assembly.build(" + facadeComponent + ")");
	int error = 0;
	HomeFinder homeFinder = ccm.local.HomeFinder.instance();
  
	try {
	    // find home ob components
	    BasicTestHome basicTestHome = (BasicTestHome)homeFinder.find_home_by_name("BasicTestHome");
	    UserTestHome userTestHome =  (UserTestHome)homeFinder.find_home_by_name("UserTestHome");

	    // create components
	    superTest = (SuperTest)facadeComponent;
	    basicTest = basicTestHome.create();
	    userTest = userTestHome.create();
    
	    //provide facets
	    basicType = basicTest.provide_basicType();
	    userType = userTest.provide_userType();

	    // connect components
	    superTest.connect_innerBasicType(basicType);
	    superTest.connect_innerUserType(userType);

	    state = AssemblyState.INSERVICE;
	}
	catch (HomeNotFound e) 
	{
	    e.printStackTrace();
	    throw new CreateFailure();
	}
	catch (Exception e) {
	    e.printStackTrace();
	    throw new CreateFailure();
	}
    }


    public void configuration_complete()
	throws InvalidConfiguration
    {
	System.out.println("+Assembly.configuration_complete()");
	basicTest.configuration_complete();
	userTest.configuration_complete();
    }


    public void tear_down()
        throws RemoveFailure
    {
	System.out.println("+Assembly.tear_down()");
	int error = 0;
	try {
	    // disconnect components
	    superTest.disconnect_innerBasicType();
	    superTest.disconnect_innerUserType();
	    
	    // remove components
	    basicTest.remove();
	    userTest.remove();
	
	    state = AssemblyState.INACTIVE;
	}
	catch (Exception e) 
	{
	    e.printStackTrace();
	    throw new RemoveFailure();
	}
    }


    public AssemblyState get_state()
    {
	return state;
    }
}
