#include <cstdlib> 
#include <iostream>
#include <string>
#include <WX/Utils/debug.h>
#include <CCM/CCMContainer.h>

#include <CORBA.h>
#include <coss/CosNaming.h>

#include <CCM_Remote/world/europe/austria/CCM_Session_Test/TestHome_remote.h>
#include <world_europe_austria_Test.h>

using namespace std;
using namespace WX::Utils;

//==============================================================================
// Implementation of remote client test
//==============================================================================

int main (int argc, char *argv[])
{
  Debug::instance().set_global(true); 
  DEBUGNL("Enter C++ remote test client");

  int argc_=3;
  char* NameServiceLocation = getenv("CCM_NAME_SERVICE");
  assert(NameServiceLocation);	
  string ns("NameService=");
  ns += NameServiceLocation;
  char* argv_[] = { "", "-ORBInitRef", (char*)ns.c_str()}; 
  DEBUGNL(">> " << argv_[0] << " "<< argv_[1] << argv_[2]);

  // Initialize ORB 
  CORBA::ORB_var orb = CORBA::ORB_init(argc_, argv_);


  /**
   * Server-side code
   */ 
  CCM::register_all_factories (orb);

  int error = deploy_TestHome(orb, "TestHome:1.0");
  if(!error) {
    cout << "TestHome stand-alone server is running..." << endl;
  }
  else {
    cerr << "ERROR: Can't start components!" << endl;
    assert(0);
  }

  // For testing we use CORBA collocation	
  // orb->run();
	

  /**
   * Client-side code
   */
  CORBA::Object_var obj = orb->resolve_initial_references ("NameService");
  CosNaming::NamingContextExt_var nc =
    CosNaming::NamingContextExt::_narrow (obj);

  // Deployment 

  // Find ComponentHomes in the Naming-Service
  obj = nc->resolve_str ("TestHome:1.0");
  assert (!CORBA::is_nil (obj));
  ::world::europe::austria::TestHome_var myTestHome = ::world::europe::austria::TestHome::_narrow (obj);

  // Create component instances
  ::world::europe::austria::Test_var myTest = myTestHome->create();

  // Provide facets   

	
  myTest->configuration_complete();


  DEBUGNL("==== Begin Test Case =============================================" );    

  char* str = CORBA::string_dup("1234567890");
  myTest->id(str);
  char* result;
  result = myTest->id();
  assert(strcmp(str,result) == 0);

  DEBUGNL("==== End Test Case ===============================================" );    


  // Un-Deployment

  // Destroy component instances
  myTest->remove();

  DEBUGNL("Exit C++ remote test client"); 	
}
