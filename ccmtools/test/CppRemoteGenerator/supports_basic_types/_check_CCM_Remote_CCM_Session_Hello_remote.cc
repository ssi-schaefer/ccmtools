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

	
  myHello->configuration_complete();


  DEBUGNL("==== Begin Test Case =============================================" );    

     CORBA::Short short_2=3, short_3, short_r;
     short_r = myHello->println1(7,short_2, short_3);
     assert(short_2 == 7);
     assert(short_3 == 3);
     assert(short_r == 3+7);

     CORBA::Long long_2=3, long_3, long_r;
     long_r = myHello->println2(7,long_2, long_3);
     assert(long_2 == 7);
     assert(long_3 == 3);
     assert(long_r == 3+7);

     CORBA::UShort ushort_2=3, ushort_3, ushort_r;
     ushort_r = myHello->println3(7,ushort_2, ushort_3);
     assert(ushort_2 == 7);
     assert(ushort_3 == 3);
     assert(ushort_r == 3+7);

     CORBA::ULong ulong_2=3, ulong_3, ulong_r;
     ulong_r = myHello->println4(7,ulong_2, ulong_3);
     assert(ulong_2 == 7);
     assert(ulong_3 == 3);
     assert(ulong_r == 3+7);

     CORBA::Float float_2=3.0, float_3, float_r;
     float_r = myHello->println5(7.0,float_2, float_3);
     assert(float_2 == 7.0);
     assert(float_3 == 3.0);
     assert(float_r == 3.0+7.0);
     
     CORBA::Double double_2=3.0, double_3, double_r;
     double_r = myHello->println6(7.0,double_2, double_3);
     assert(double_2 == 7.0);
     assert(double_3 == 3.0);
     assert(double_r == 3.0+7.0);

     CORBA::Char char_2=3, char_3, char_r;
     char_r = myHello->println7(7,char_2, char_3);
     assert(char_2 == 7);
     assert(char_3 == 3);
     assert(char_r == 3+7);

     char* string_2 = CORBA::string_dup("drei");
     char* string_3;
     char* string_r;
     string_r = myHello->println8("sieben",string_2, string_3);
     assert(strcmp(string_2,"sieben") == 0);
     assert(strcmp(string_3,"drei") == 0);
     assert(strcmp(string_r,"dreisieben") == 0);

     CORBA::Boolean bool_2=false, bool_3, bool_r;
     bool_r = myHello->println9(true, bool_2, bool_3);
     assert(bool_2 == true);
     assert(bool_3 == false);
     assert(bool_r == false && true);

     CORBA::Octet octet_2=3, octet_3, octet_r;
     octet_r = myHello->println10(7,octet_2, octet_3);
     assert(octet_2 == 7);
     assert(octet_3 == 3);
     assert(octet_r == 3+7);

  DEBUGNL("==== End Test Case ===============================================" );    


  // Un-Deployment

  // Destroy component instances
  myHello->remove();
}
