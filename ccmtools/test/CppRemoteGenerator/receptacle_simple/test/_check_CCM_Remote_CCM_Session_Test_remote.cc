/***
 * CCM Tools Test Client 
 *
 * This file was automatically generated by the CCM Tools.
 *         <http://ccmtools.sourceforge.net/>
 *
 * This test client is part of the remote component test concept. 
 *
 * To enable debug output use -DWXDEBUG compiler flag
 ***/

#include <cstdlib> 
#include <iostream>
#include <string>
#include <WX/Utils/debug.h>
#include <CCM/CCMContainer.h>

#include <CORBA.h>
#include <coss/CosNaming.h>

#include <CCM_Remote/CCM_Session_Test/TestHome_remote.h>
#include <Test.h>

using namespace std;
using namespace WX::Utils;

//==============================================================================
// Implementation of remote client test
//==============================================================================

int 
main (int argc, char *argv[])
{
    DEBUGNL("Enter C++ remote test client");

    char* NameServiceLocation = getenv("CCM_NAME_SERVICE");
    if(NameServiceLocation == NULL) { 
        cerr << "Error: Environment variable CCM_NAME_SERVICE is not set!" << endl;
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
    CCM::register_all_factories (orb);
    int error = 0;
    error += deploy_CCM_Local_TestHome("TestHome");
    error += deploy_CCM_Remote_TestHome(orb, "TestHome:1.0");
    if(!error) {
        cout << "TestHome stand-alone server is running..." << endl;
    }
    else {
        cerr << "ERROR: Can't start components!" << endl;
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
    ::TestHome_var myTestHome = 
	::TestHome::_narrow (obj);

    // Create component instances
    ::Test_var myTest = myTestHome->create();

    // Provide facets   
    ::I2_var I2in_port = 
        myTest->provide_in_port();

    myTest->connect_out_port(I2in_port);
	
    myTest->configuration_complete();

    DEBUGNL("==== Begin Test Case ========================================" );


    DEBUGNL("==== End Test Case ==========================================" );

    // Un-Deployment
    myTest->disconnect_out_port();
    
    // Destroy component instances
    myTest->remove();

    DEBUGNL("Exit C++ remote test client"); 	
}
