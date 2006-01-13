/***
 * CCM Tools Test Server
 * 
 ***/

#include <cstdlib> 
#include <iostream>
#include <string>
#include <ccm/remote/Debug.h>
#include <CCM/CCMContainer.h>

#include <CORBA.h>
#include <coss/CosNaming.h>

#include <benchmark/ccm/remote/component/Test/TestHome_remote.h>
#include <benchmark_Test.h>

using namespace std;
using namespace WX::Utils;

//==============================================================================
// Implementation of remote client test
//==============================================================================

int main (int argc, char *argv[])
{
    cout << ">>>> Start Test Client: " << __FILE__ << endl;
  
    char* NameServiceLocation = getenv("CCM_NAME_SERVICE");
    if(NameServiceLocation == NULL) { 
        cerr << "Error: Environment variable CCM_NAME_SERVICE is not set!" 
	     << endl;
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
    error += deploy_benchmark_ccm_local_component_Test_TestHome("TestHome");
    error += deploy_benchmark_ccm_remote_component_Test_TestHome(orb, "TestHome:1.0");
    if(!error) {
        cout << "TestHome server is running..." << endl;
    }
    else {
        cerr << "ERROR: Can't deploy components!" << endl;
        return -1;
    }

    // For testing we use CORBA collocation	
    orb->run();
}
