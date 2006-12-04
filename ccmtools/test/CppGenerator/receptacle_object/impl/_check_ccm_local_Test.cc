/***
 * CCM Tools Test Client 
 *
 * This file was automatically generated by the CCM Tools.
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

#include <wamas/platform/utils/smartptr.h>

#include <Components/ccm/local/CCM.h>
#include <ccm/local/HomeFinder.h>

#include <ccm/local/Test_gen.h>
#include <ccm/local/TestHome_gen.h>

#include "ReceptacleObject.h"

using namespace std;
using namespace wamas::platform::utils;
using namespace ccm::local;

int main(int argc, char *argv[])
{
    cout << ">>>> Start Test Client: " << __FILE__ << endl;

    SmartPtr<Test> myTest;
    SmartPtr<IFace> iface;

    // Component bootstrap:
    // We get an instance of the local HomeFinder and register the deployed
    // component- and mirror component home.
    // Here we can also decide to use a Design by Contract component.  	
    int error = 0;
    Components::ccm::local::HomeFinder* homeFinder;
    homeFinder = HomeFinder::Instance();
    error  = deploy_ccm_local_TestHome("TestHome");
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
        SmartPtr<TestHome> myTestHome(dynamic_cast<TestHome*>
            (homeFinder->find_home_by_name("TestHome").ptr()));

        myTest = myTestHome->create();

	iface = SmartPtr<IFace>(new ReceptacleObject());
	
        myTest->connect_iface(iface);

        myTest->configuration_complete();
    } 
    catch ( ::Components::ccm::local::HomeNotFound ) {
        cout << "DEPLOYMENT ERROR: can't find a home!" << endl;
        error = -1;
    } 
    catch ( ::Components::ccm::local::NotImplemented& e ) {
        cout << "DEPLOYMENT ERROR: function not implemented: " 
	     << e.what (  ) << endl;
        error = -1;
    }  
    catch ( ::Components::ccm::local::InvalidName& e ) {
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

    } 
    catch ( ::Components::ccm::local::NotImplemented& e ) {
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

        myTest->disconnect_iface();

        myTest->remove();
    } 
    catch ( ::Components::ccm::local::HomeNotFound ) {
        cout << "TEARDOWN ERROR: can't find a home!" << endl;
        error = -1;
    } 
    catch ( ::Components::ccm::local::NotImplemented& e ) {
        cout << "TEARDOWN ERROR: function not implemented: " 
	     << e.what (  ) << endl;
        error = -1;
    } 
    catch(...) {
        cout << "TEARDOWN ERROR: there is something wrong!" << endl;
        error = -1;
    }
    error += undeploy_ccm_local_TestHome("TestHome");
    if(error) {
        cerr << "TEARDOWN ERROR: Can't undeploy component homes!" << endl;
        return error;
    }
    cout << ">>>> Stop Test Client: " << __FILE__ << endl;
}
