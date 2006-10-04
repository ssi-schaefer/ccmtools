/***
 * CCM Tools Test Client 
 *
 * This file was automatically generated by CCM Tools version 0.5.3-pre3.
 *         <http://ccmtools.sourceforge.net/>
 *
 * This test client is part of the remote component test concept. 
 *
 * To enable debug output use -DWXDEBUG compiler flag and set the
 * WX_DEBUG_LEVELS environment variable to "CCM_REMOTE".
 * (e.g. export WX_DEBUG_LEVELS="CCM_REMOTE")
 ***/

#ifdef HAVE_CONFIG_H
#  include <config.h>
#endif 

#ifdef HAVE_MICO 

#include <cstdlib> 
#include <iostream>
#include <string>
#include <wx/utils/debug.h>
#include <CCM/CCMContainer.h>

#include <CORBA.h>
#include <coss/CosNaming.h>

#include <world/europe/austria/ccm/remote/component/Test/TestHome_remote.h>
#include <world_europe_austria_Test.h>

using namespace std;
using namespace wx::utils;

//==============================================================================
// Implementation of remote client test
//==============================================================================

int main (int argc, char *argv[])
{
    cout << "Enter C++ remote test client" << endl;

    char* NameServiceLocation = getenv("CCM_NAME_SERVICE");
    if(NameServiceLocation == NULL) { 
        cerr << "Error: Environment variable CCM_NAME_SERVICE not set!" << endl;
        return -1;
    }

    // Initialize ORB 
    ostringstream os;
    os << "NameService=" << NameServiceLocation;
    char* argv_[] = { "", "-ORBInitRef", (char*)os.str().c_str()}; 
    int   argc_   = 3;
    DEBUGNL(">> " << argv_[0] << " "<< argv_[1] << argv_[2]);
    CORBA::ORB_var orb = CORBA::ORB_init(argc_, argv_);


    /**
     * Server-side code
     */ 

    // Register all value type factories with the ORB  
    CCM::register_all_factories (orb);

    // Deploy local and remote component homes	
    int error = 0;
    error += deploy_world_europe_austria_ccm_local_component_Test_TestHome("TestHome");
    error += deploy_world_europe_austria_ccm_remote_component_Test_TestHome(orb, "TestHome:1.0");
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
    world::europe::austria::TestHome_var myTestHome = world::europe::austria::TestHome::_narrow (obj);

    // Create component instances
    world::europe::austria::Test_var myTest = myTestHome->create();

    // Provide facets   
    ::world::europe::austria::BasicTypeInterface_var inBasicType = 
	myTest->provide_inBasicType();

    ::world::europe::austria::UserTypeInterface_var inUserType = 
	myTest->provide_inUserType();

    ::world::europe::austria::VoidTypeInterface_var inVoidType = 
	myTest->provide_inVoidType();

    // Connect receptacle
    myTest->connect_outBasicType(inBasicType);
    myTest->connect_outUserType(inUserType);
    myTest->connect_outVoidType(inVoidType);
	
    myTest->configuration_complete();

    cout << "==== Begin Test Case ===================================" << endl;

    // TODO : IMPLEMENT ME HERE !       

    cout << "==== End Test Case =====================================" << endl; 

    // Un-Deployment
    myTest->disconnect_outBasicType();
    myTest->disconnect_outUserType();
    myTest->disconnect_outVoidType();

    // Destroy component instances
    myTest->remove();

    // Un-Deployment
    cout << "Exit C++ remote test client" << endl; 	
}

#endif // HAVE_MICO

