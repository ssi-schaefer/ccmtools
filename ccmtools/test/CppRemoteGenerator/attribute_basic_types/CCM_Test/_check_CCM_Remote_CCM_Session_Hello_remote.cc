#include <cstdlib> 
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

int main (int argc, char *argv[])
{
  Debug::set_global(true); 
  DEBUGNL("C++_remote_test_client()");

  // Initialize ORB
  int argc_=3;
  char ns[200];
  sprintf(ns,"NameService=%s",getenv("CCM_NAME_SERVICE"));
  char* argv_[] = {"", "-ORBInitRef", ns };
  DEBUGNL(ns);
 
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

	
  myHello->configuration_complete();


  DEBUGNL("==== Begin Test Case =============================================" );    

  CORBA::Short short_value = -7;
  myHello->short_value(short_value);
  CORBA::Short short_result = myHello->short_value();
  assert(short_value == short_result);

  CORBA::Long long_value = -7777;
  myHello->long_value(long_value);
  CORBA::Long long_result = myHello->long_value();
  assert(long_result == long_value);
  
  CORBA::UShort ushort_value = 7;
  myHello->ushort_value(ushort_value);
  CORBA::UShort ushort_result = myHello->ushort_value();
  assert(ushort_result == ushort_value);
  
  CORBA::ULong ulong_value = 7777;
  myHello->ulong_value(ulong_value);
  CORBA::ULong ulong_result = myHello->ulong_value();
  assert(ulong_result == ulong_value);

  CORBA::Float float_value = -77.77;
  myHello->float_value(float_value);
  CORBA::Float float_result = myHello->float_value();
  assert(float_result == float_value);

  CORBA::Double double_value = -77.7777;
  myHello->double_value(double_value);
  CORBA::Double double_result = myHello->double_value();
  assert(double_result == double_value);

  CORBA::Char char_value = 'x';
  myHello->char_value(char_value);
  CORBA::Char char_result = myHello->char_value();
  assert(char_result == char_value);

  char* string_value = "0123456789";
  myHello->string_value(string_value);
  char* string_result = myHello->string_value();
  assert(strcmp(string_value, string_result)==0);

  CORBA::Boolean boolean_value = true;
  myHello->boolean_value(boolean_value);
  CORBA::Boolean boolean_result = myHello->boolean_value();
  assert(boolean_result == boolean_value);

  CORBA::Octet octet_value = 0xff;
  myHello->octet_value(octet_value);
  CORBA::Octet octet_result = myHello->octet_value();
  assert(octet_result == octet_value);

  DEBUGNL("==== End Test Case ===============================================" );    


  // Un-Deployment

  // Destroy component instances
  myHello->remove();
}
