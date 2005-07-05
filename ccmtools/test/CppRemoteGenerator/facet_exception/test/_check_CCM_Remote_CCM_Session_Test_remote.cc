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
    ::TestHome_var myTestHome = ::TestHome::_narrow (obj);

    // Create component instances
    ::Test_var myTest = myTestHome->create();

    // Provide facets   
    ::IFace_var IFaceiface = myTest->provide_iface();


	
    myTest->configuration_complete();

    cout << "==== Begin Test Case ===================================" << endl;

    try {
      CORBA::Long result;
      result = IFaceiface->foo("0123456789");
      assert(result == 10);
    }
    catch(const ::ErrorException& e) {
      assert(false);
    }

    try {
      IFaceiface->foo("Error");
      assert(false);
    }
    catch(const ::ErrorException& e) {
      ::ErrorInfoList infolist = e.info;
      for(unsigned long i = 0; i < infolist.length(); i++) {
      cout << e.info[i].code << ": " 
           << e.info[i].message << endl;
      }
      LDEBUGNL(CCM_REMOTE, CCM_Remote::ccmDebug(e));
    }

    try {
      IFaceiface->foo("SuperError");
      assert(false);
    }
    catch(const ::SuperError& e) {
      cout << "SuperError" << endl;
      LDEBUGNL(CCM_REMOTE, CCM_Remote::ccmDebug(e));
    }

    try {
      IFaceiface->foo("FatalError");
      assert(false);
    }
    catch(const ::FatalError& e) {
      cout << e.what << endl;
      LDEBUGNL(CCM_REMOTE, CCM_Remote::ccmDebug(e));
    }

    cout << "==== End Test Case ======================================" << endl;

    // Un-Deployment

    // Destroy component instances
    myTest->remove();

    DEBUGNL("Exit C++ remote test client"); 	
}
