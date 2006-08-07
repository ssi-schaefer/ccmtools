/***
 * CCM Tools Test Client 
 *
 * This file was automatically generated by .
 *         <http://ccmtools.sourceforge.net/>
 *
 * This test client is part of the mirror component test concept. For each
 * component a corresponding mirror component will be instantiated. 
 * All component ports will be connected to the mirror component's ports. 
 * Additionally, developers can add some testing code to validate supported
 * interfaces as well as component attribute access.
 ***/

#include <cassert>
#include <iostream>

#include <WX/Utils/debug.h>
#include <WX/Utils/smartptr.h>

#include <ccm/local/Components/CCM.h>
#include <ccm/local/HomeFinder.h>

#include <ccm/local/component/SuperTestMirror/SuperTestMirror_gen.h>
#include <ccm/local/component/SuperTestMirror/SuperTestHomeMirror_gen.h>

#include <ccm/local/component/SuperTest/SuperTest_gen.h>
#include <ccm/local/component/SuperTest/SuperTestHome_gen.h>

#include <ccm/local/component/BasicTest/BasicTest_gen.h>
#include <ccm/local/component/BasicTest/BasicTestHome_gen.h>

#include <ccm/local/component/UserTest/UserTest_gen.h>
#include <ccm/local/component/UserTest/UserTestHome_gen.h>

#include <ccm/local/assembly_factory.h>

using namespace std;
using namespace WX::Utils;
using namespace ccm::local;
using namespace component;

int main(int argc, char *argv[])
{
    cout << ">>>> Start Test Client: " << __FILE__ << endl;

    SmartPtr<component::SuperTest::SuperTest> mySuperTest;
    SmartPtr<component::SuperTestMirror::SuperTestMirror> mySuperTestMirror;
    SmartPtr<Components::Object> SuperTest_provides_basicType;
    SmartPtr<Components::Object> SuperTest_provides_userType;

    Components::Cookie SuperTest_ck_basicType;
    Components::Cookie SuperTest_ck_userType;

    SmartPtr<Components::Object> SuperTest_uses_innerBasicType;
    SmartPtr<Components::Object> SuperTest_uses_innerUserType;

    Components::Cookie SuperTest_ck_innerBasicType;
    Components::Cookie SuperTest_ck_innerUserType;

    // Component bootstrap:
    // We get an instance of the local HomeFinder and register the deployed
    // component- and mirror component home.
    // Here we can also decide to use a Design by Contract component.  	
    int error = 0;
    Components::HomeFinder* homeFinder;
    homeFinder = HomeFinder::Instance();

    error  += deploy_ccm_local_component_BasicTest_BasicTestHome("BasicTestHome");
    error  += deploy_ccm_local_component_UserTest_UserTestHome("UserTestHome");

    SmartPtr<Components::AssemblyFactory> assembly_factory(new AssemblyFactory());
    error += deploy_with_assembly_ccm_local_component_SuperTest_SuperTestHome("SuperTestHome", assembly_factory);

    error += deploy_ccm_local_component_SuperTestMirror_SuperTestHomeMirror("SuperTestHomeMirror");	
    if(error) {
        cerr << "BOOTSTRAP ERROR: Can't deploy component homes!" << endl;
        return(error);
    }

    // Component deployment:
    // We use the HomeFinder method find_home_by_name() to get a smart pointer 
    // to a component home. From a component home, we get a smart pointer to a 
    // component instance using the create() method.
    // Component and mirror component are connected via provide_facet() and 
    // connect() methods.
    // The last step of deployment is to call configuration_complete() that 
    // forces components to run the ccm_set_session_context() and ccm_activate() 
    // callback methods.
    try {
        SmartPtr<component::SuperTest::SuperTestHome> 
	  mySuperTestHome(dynamic_cast<component::SuperTest::SuperTestHome*>
            (homeFinder->find_home_by_name("SuperTestHome").ptr()));

        SmartPtr<component::SuperTestMirror::SuperTestHomeMirror> 
            mySuperTestHomeMirror(
		dynamic_cast<component::SuperTestMirror::SuperTestHomeMirror*>
            (homeFinder->find_home_by_name("SuperTestHomeMirror").ptr()));

        mySuperTest = mySuperTestHome->create();
        mySuperTestMirror = mySuperTestHomeMirror->create();

        SuperTest_provides_basicType = mySuperTest->provide_facet("basicType");
        SuperTest_provides_userType = mySuperTest->provide_facet("userType");

		mySuperTestMirror->connect("basicType", SuperTest_provides_basicType);
        mySuperTestMirror->connect("userType", SuperTest_provides_userType);

        mySuperTest->configuration_complete();
        mySuperTestMirror->configuration_complete();
    } 
    catch ( Components::HomeNotFound ) {
        cout << "DEPLOYMENT ERROR: can't find a home!" << endl;
        error = -1;
    } 
    catch ( Components::NotImplemented& e ) {
        cout << "DEPLOYMENT ERROR: function not implemented: " 
	     << e.what (  ) << endl;
        error = -1;
    }  
    catch ( Components::InvalidName& e ) {
        cout << "DEPLOYMENT ERROR: invalid name during connection: " 
             << e.what (  ) << endl;
        error = -1;
    }
    catch ( ... )  {
        cout << "DEPLOYMENT ERROR: there is something wrong!" << endl;
        error = -1;
    }
    if (error < 0) {
        return error;
    }

    // Component test:
    // After component deployment, we can access components and their facets.
    // Usually, the test cases for facets and receptacles are implemened in the
    // mirror component. But for supported interfaces and component attributes, 
    // we can realize test cases in the following section.
    try {

        // OPTIONAL : IMPLEMENT TEST CASES HERE !

    } 
    catch ( Components::NotImplemented& e ) {
        cout << "TEST: function not implemented: " << e.what (  ) << endl;
        error = -1;
    }
    catch(...) {
        cout << "TEST: there is something wrong!" << endl;
        error = -1;
    }
    if(error < 0) {
	return error;
    }
  

    // Component tear down:
    // Finally, the component and mirror component instances are disconnected 
    // and removed. Thus component homes can be undeployed.
    try {
      mySuperTestMirror->disconnect("basicType", SuperTest_ck_basicType);
      mySuperTestMirror->disconnect("userType", SuperTest_ck_userType);

      mySuperTest->remove();
      mySuperTestMirror->remove();
    } 
    catch ( Components::HomeNotFound ) {
        cout << "TEARDOWN ERROR: can't find a home!" << endl;
        error = -1;
    } 
    catch ( Components::NotImplemented& e ) {
        cout << "TEARDOWN ERROR: function not implemented: " 
	     << e.what (  ) << endl;
        error = -1;
    } 
    catch(...) {
        cout << "TEARDOWN ERROR: there is something wrong!" << endl;
        error = -1;
    }
    error += undeploy_ccm_local_component_BasicTest_BasicTestHome("BasicTestHome");
    error += undeploy_ccm_local_component_UserTest_UserTestHome("UserTestHome");
    error += undeploy_ccm_local_component_SuperTest_SuperTestHome("SuperTestHome");
    error += undeploy_ccm_local_component_SuperTestMirror_SuperTestHomeMirror("SuperTestHomeMirror");
    if(error) {
        cerr << "TEARDOWN ERROR: Can't undeploy component homes!" << endl;
        return error;
    }
    cout << ">>>> Stop Test Client: " << __FILE__ << endl;
}
