#include <iostream>
#include <string>
#include <CCM_Utils/Debug.h>

#include <CORBA.h>
#include <coss/CosNaming.h>

#include <CCM_Remote/CCM_Session_Hello/HelloHome_remote.h>
#include <Hello.h>

using namespace std;
using namespace CCM_Utils;

//==============================================================================
// Implementation of remote client test
//==============================================================================

// Default commandline parameter
char* argv_[] = {"", "-ORBInitRef", 
                     "NameService=corbaloc:iiop:1.2@127.0.0.1:5050/NameService" };
int argc_ = 3;


int main (int argc, char *argv[])
{
  Debug::set_global(true); 
  DEBUGNL("C++_remote_test_client()");

  // Initialize ORB 
  CORBA::ORB_var orb = CORBA::ORB_init(argc_, argv_);


  /**
   * Server-side code
   */ 
  CCM::register_all_factories (orb);

  int error = deploy_HelloHome(orb, "HelloHome:1.0");
  if(!error) {
    cout << "HelloHome stand-alone server is running..." << endl;
  }
  else {
    cerr << "ERROR: Can't start components!" << endl;
    assert(0);
  }


  /**
   * Client-side code
   */
  CORBA::Object_var obj = orb->resolve_initial_references ("NameService");
  CosNaming::NamingContextExt_var nc =
    CosNaming::NamingContextExt::_narrow (obj);
  assert (!CORBA::is_nil (nc));

  // Deployment 

  // Find ComponentHomes in the Naming-Service
  obj = nc->resolve_str ("HelloHome:1.0");
  assert (!CORBA::is_nil (obj));
  HelloHome_var myHelloHome = HelloHome::_narrow (obj);

  // Create component instances
  Hello_var myHello =  myHelloHome->create();

  // Provide facets   
  Console_var Consoleconsole = myHello->provide_console();

	
  myHello->configuration_complete();


  DEBUGNL("==== Begin Test Case =============================================" );    

  char* s = CORBA::string_dup("1234567890");
  CORBA::Long size = Consoleconsole->println(s);
  assert(strlen(s) == size);

  DEBUGNL("==== End Test Case ===============================================" );    


  // Un-Deployment

  // Destroy component instances
  myHello->remove();
}
