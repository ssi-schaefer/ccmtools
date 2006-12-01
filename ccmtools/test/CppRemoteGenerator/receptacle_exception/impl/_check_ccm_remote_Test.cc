/***
 * CCM Tools Test Client 
 *
 * This file was automatically generated by CCM Tools version 0.5.3-pre3.
 *         <http://ccmtools.sourceforge.net/>
 *
 * This test client is part of the remote component test concept. 
 ***/

#ifdef HAVE_CONFIG_H
#  include <config.h>
#endif 

#ifdef HAVE_MICO 

#include <cstdlib> 
#include <iostream>
#include <string>
#include <wamas/platform/utils/debug.h>
#include <CCM/CCMContainer.h>

#include <CORBA.h>
#include <coss/CosNaming.h>

#include <ccm/remote/TestHome_remote.h>
#include <Test.h>

using namespace std;
using namespace wamas::platform::utils;

//==============================================================================
// Implementation of remote client test
//==============================================================================

int main (int argc, char *argv[])
{
    cout << "Enter C++ remote test client" << endl;

    // Initialize ORB 
    int argc_ = 3;
    char* argv_[] = { "", "-ORBInitRef", "NameService=corbaloc:iiop:1.2@localhost:5050/NameService" }; 
    CORBA::ORB_var orb = CORBA::ORB_init(argc_, argv_);

    /**
     * Server-side code
     */ 

    // Register all value type factories with the ORB  
    CCM::register_all_factories (orb);

    // Deploy local and remote component homes	
    int error = 0;
    error += deploy_ccm_local_TestHome("TestHome");
    error += deploy_ccm_remote_TestHome(orb, "TestHome:1.0");
    if(!error) {
        cout << "TestHome server is running..." << endl;
    }
    else {
        cerr << "ERROR: Can't deploy components!" << endl;
        return -1;
    }

    // For testing we use CORBA collocation	
    // orb->run();
	

    /**
     * Client-side code
     */
    CORBA::Object_var obj = orb->resolve_initial_references("NameService");
    CosNaming::NamingContextExt_var nc =
        CosNaming::NamingContextExt::_narrow(obj);

    // Deployment 

    // Find ComponentHomes in the Naming-Service
    obj = nc->resolve_str("TestHome:1.0");
    assert (!CORBA::is_nil (obj));
    TestHome_var myTestHome = TestHome::_narrow (obj);

    // Create component instances
    Test_var myTest = myTestHome->create();

    // Provide facets   
    ::Console_var inPort = myTest->provide_inPort();

    // Connect receptacle
    myTest->connect_outPort(inPort);
	
    myTest->configuration_complete();

    cout << "==== Begin Test Case ===================================" << endl;

    // TODO : IMPLEMENT ME HERE !       

    cout << "==== End Test Case =====================================" << endl; 

    // Un-Deployment
    myTest->disconnect_outPort();

    // Destroy component instances
    myTest->remove();

    // Un-Deployment
    cout << "Exit C++ remote test client" << endl; 	
}

#endif // HAVE_MICO

