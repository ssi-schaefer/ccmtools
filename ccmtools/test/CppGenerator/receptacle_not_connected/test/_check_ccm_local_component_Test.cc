/***
 * CCM Tools Test Client 
 *
 * This file was automatically generated by CCM Tools version 0.5.3-pre2.
 *         <http://ccmtools.sourceforge.net/>
 *
 * This test client is part of the mirror component test concept. For each
 * component a corresponding mirror component will be instantiated. 
 * All component ports will be connected to the mirror component's ports. 
 * Additionally, developers can add some testing code to validate supported
 * interfaces as well as component attribute access.
 *
 * To enable debug output use -DWXDEBUG compiler flag and set the
 * WX_DEBUG_LEVELS environment variable to "CCM_LOCAL"
 * (e.g. export WX_DEBUG_LEVELS="CCM_LOCAL").
 *
 * To enable DbC adapter use -DCCM_USE_DBC compiler flag.
 ***/

#include <cassert>
#include <iostream>

#include <WX/Utils/debug.h>
#include <WX/Utils/smartptr.h>

#include <ccm/local/Components/CCM.h>
#include <ccm/local/HomeFinder.h>

#include <ccm/local/component/Test_mirror_gen.h>
#include <ccm/local/component/TestHome_mirror_gen.h>

#ifdef CCM_USE_DBC
#include <ccm/local/component/Test_dbc.h>
#include <ccm/local/component/TestHome_dbc.h>
#else
#include <ccm/local/component/Test_gen.h>
#include <ccm/local/component/TestHome_gen.h>
#endif

using namespace std;
using namespace WX::Utils;
using namespace ccm;
using namespace local;
using namespace component;

int main(int argc, char *argv[])
{
    cout << ">>>> Start Test Client: " << __FILE__ << endl;

    SmartPtr<Test> myTest;
    SmartPtr<Test_mirror> myTestMirror;

    SmartPtr<Components::Object> Test_uses_console;

    Components::Cookie Test_ck_console;

    // Component bootstrap:
    // We get an instance of the local HomeFinder and register the deployed
    // component- and mirror component home.
    // Here we can also decide to use a Design by Contract component.  	
    int error = 0;
    Components::HomeFinder* homeFinder;
    homeFinder = HomeFinder::Instance();
#ifdef CCM_USE_DBC
    error  = deploy_dbc_ccm_local_TestHome("TestHome", false);
#else
    error  = deploy_ccm_local_TestHome("TestHome");
#endif
    error += deploy_ccm_local_TestHome_mirror("TestHome_mirror");	
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

        SmartPtr<TestHome_mirror> 
            myTestHomeMirror(dynamic_cast<TestHome_mirror*>
            (homeFinder->find_home_by_name("TestHome_mirror").ptr()));

        myTest = myTestHome->create();
        myTestMirror = myTestHomeMirror->create();

        Test_uses_console = 
            myTestMirror->provide_facet("console_mirror");

	/*
	 * Don't connect the receptacle to activate a Components::NoConnection 
	 * exception.
	 * Test_ck_console = myTest->connect("console", Test_uses_console);
	 */
        myTest->configuration_complete();
        myTestMirror->configuration_complete();
    } 
    catch(Components::HomeNotFound ) {
        cout << "DEPLOYMENT ERROR: can't find a home!" << endl;
        error = -1;
    } 
    catch(Components::NotImplemented& e ) {
        cout << "DEPLOYMENT ERROR: function not implemented: " 
	     << e.what (  ) << endl;
        error = -1;
    }  
    catch(Components::InvalidName& e ) {
        cout << "DEPLOYMENT ERROR: invalid name during connection: " 
             << e.what (  ) << endl;
        error = -1;
    }
#ifdef CCM_USE_DBC
    catch(ccm::OCL::OclException& e)
    {
        cout << "DEPLOYMENT ERROR: 'design by contract' error:" 
             << endl << e.what();
        error = -1;
    }
#endif
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
    catch(Components::NotImplemented& e ) {
        cout << "TEST: function not implemented: " << e.what (  ) << endl;
        error = -1;
    }
#ifdef CCM_USE_DBC
    catch(ccm::OCL::OclException& e)
    {
        cout << "TEST: 'design by contract' error:" << endl << e.what();
        error = -1;
    }
#endif
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

      /*
       * Don't disconnect a unconnected receptacle
       * myTest->disconnect("console", Test_ck_console);
       */
        myTest->remove();
        myTestMirror->remove();
    } 
    catch(Components::HomeNotFound ) {
        cout << "TEARDOWN ERROR: can't find a home!" << endl;
        error = -1;
    } 
    catch(Components::NotImplemented& e ) {
        cout << "TEARDOWN ERROR: function not implemented: " 
	     << e.what (  ) << endl;
        error = -1;
    } 
    catch(...) {
        cout << "TEARDOWN ERROR: there is something wrong!" << endl;
        error = -1;
    }
    error += undeploy_ccm_local_TestHome("TestHome");
    error += undeploy_ccm_local_TestHome_mirror("TestHome_mirror");
    if(error) {
        cerr << "TEARDOWN ERROR: Can't undeploy component homes!" << endl;
        return error;
    }
    cout << ">>>> Stop Test Client: " << __FILE__ << endl;
}
