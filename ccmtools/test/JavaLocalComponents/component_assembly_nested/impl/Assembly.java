package world;

import Components.AssemblyState;
import Components.CreateFailure;
import Components.RemoveFailure;
import Components.HomeNotFound;
import Components.InvalidConfiguration;
import Components.CCMObject;
import Components.HomeFinder;

import world.SuperTest;
import world.SuperTestHome;
import world.BasicTypeInterface;
import world.UserTypeInterface;

import world.BasicTest;
import world.BasicTestHome;
import world.BasicTypeInterface;

import world.UserTest;
import world.UserTestHome;
import world.UserTypeInterface;

import world.SuperTestHome;
import world.SuperTest;

public class Assembly implements Components.Assembly
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

    public void build() throws CreateFailure
    {
        System.out.println("+Assembly.build()");
        throw new CreateFailure();
    }

    public void build(CCMObject facadeComponent) throws CreateFailure
    {

        System.out.println("+Assembly.build(" + facadeComponent + ")");
        int error = 0;
        HomeFinder homeFinder = HomeFinder.instance();

        try
        {
            // find home ob components
            BasicTestHome basicTestHome = (BasicTestHome) homeFinder.find_home_by_name("BasicTestHome");
            UserTestHome userTestHome = (UserTestHome) homeFinder.find_home_by_name("UserTestHome");

            // create components
            superTest = (SuperTest) facadeComponent;
            basicTest = basicTestHome.create();
            userTest = userTestHome.create();

            // provide facets
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
        catch (Exception e)
        {
            e.printStackTrace();
            throw new CreateFailure();
        }
    }

    public void configuration_complete() throws InvalidConfiguration
    {
        System.out.println("+Assembly.configuration_complete()");
        basicTest.configuration_complete();
        userTest.configuration_complete();
    }

    public void tear_down() throws RemoveFailure
    {
        System.out.println("+Assembly.tear_down()");
        int error = 0;
        try
        {
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
