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

#include <ccm/remote/component/Test/TestHome_remote.h>
#include <Test.h>

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
    error += deploy_ccm_local_component_Test_TestHome("TestHome");
    error += deploy_ccm_remote_component_Test_TestHome(orb, "TestHome:1.0");
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
    ::IFace_var iface = myTest->provide_iface();


	
    myTest->configuration_complete();

    cout << "==== Begin Test Case ===================================" << endl;

    try {
      CORBA::Long result;
      result = iface->foo("0123456789");
      assert(result == 10);
    }
    catch(const ::ErrorException& e) {
      assert(false);
    }

    try {
      iface->foo("Error");
      assert(false);
    }
    catch(const ::ErrorException& e) {
      ::ErrorInfoList infolist = e.info;
      for(unsigned long i = 0; i < infolist.length(); i++) {
      cout << e.info[i].code << ": " 
           << e.info[i].message << endl;
      }
      LDEBUGNL(CCM_REMOTE, ccm::remote::ccmDebug(e));
    }

    try {
      iface->foo("SuperError");
      assert(false);
    }
    catch(const ::SuperError& e) {
      cout << "SuperError" << endl;
      LDEBUGNL(CCM_REMOTE, ccm::remote::ccmDebug(e));
    }

    try {
      iface->foo("FatalError");
      assert(false);
    }
    catch(const ::FatalError& e) {
      cout << e.what << endl;
      LDEBUGNL(CCM_REMOTE, ccm::remote::ccmDebug(e));
    }
    cout << "==== End Test Case =====================================" << endl; 

    // Destroy component instances
    myTest->remove();

    // Un-Deployment
    cout << "Exit C++ remote test client" << endl; 	
}

#endif // HAVE_MICO

