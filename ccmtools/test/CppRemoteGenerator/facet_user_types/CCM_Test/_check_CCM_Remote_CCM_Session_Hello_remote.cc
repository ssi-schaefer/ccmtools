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

  int argc_=3;
  char ns[200];
  sprintf(ns,"NameService=%s",getenv("CCM_NAME_SERVICE"));
  char* argv_[] = {"", "-ORBInitRef", ns };
  DEBUGNL(ns);

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

  // orb->run();
	

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
  //Console_var console = myHello->provide_console();
  Console_var Consoleconsole = myHello->provide_console();


	
  myHello->configuration_complete();


  DEBUGNL("==== Begin Test Case =============================================" );    
  
  /* 
   * Test Case for: typedef sequence<long>
   */
  {
    LongList_var list_1 = new LongList;
    LongList_var list_2 = new LongList;
    list_1->length(5);
    list_2->length(5);
    for(int i=0;i<5;i++) {
      (*list_1)[i] = i;
      (*list_2)[i] = i+i;
    }
    
    LongList_var list_3;
    LongList_var list_r;
    
    list_r = Consoleconsole->foo1(list_1,list_2,list_3);
    
    for(CORBA::Long i=0;i<list_r->length();i++) {
      assert((*list_r)[i]== i);
    }
    for(CORBA::Long i=0;i<list_2->length();i++) {
      assert((*list_2)[i]== i);
    }
    for(CORBA::Long i=0;i<list_3->length();i++) {
      assert((*list_3)[i]== i+i);
    }
  }


  /* 
   * Test Case for: typedef sequence<string>
   */
  {
    StringList_var list_1 = new StringList;
    StringList_var list_2 = new StringList;
    list_1->length(5);
    list_2->length(5);
    for(int i=0;i<5;i++) {
      (*list_1)[i] = "egon";
      (*list_2)[i] = "andrea";
    }
    
    StringList_var list_3;
    StringList_var list_r;
    
    list_r = Consoleconsole->foo2(list_1,list_2,list_3);
    
    /*
    for(CORBA::Long i=0;i<list_r->length();i++) {
      assert((*list_r)[i]== i);
    }
    for(CORBA::Long i=0;i<list_2->length();i++) {
      assert((*list_2)[i]== i);
    }
    for(CORBA::Long i=0;i<list_3->length();i++) {
      assert((*list_3)[i]== i+i);
    }
    */
  }


  /*
   * Test Case for: struct Value { long id; string name; };
   */
  {
    Person p1;
    Person_var p2 = new Person;
    Person_var p3;
    Person_var result;
    
    p1.name = CORBA::string_dup("egon");   
    p1.id = 3;
    
    p2->name = CORBA::string_dup("andrea"); 
    p2->id = 23;
    
    result = Consoleconsole->foo3(p1,p2,p3);
    
    assert(strcmp(p3->name, "andrea") == 0);
    assert(strcmp(p2->name, "egon") == 0);
    assert(strcmp(result->name, "egonandrea") == 0);
  }


  /* 
   * Test Case for: typedef sequence<struct>
   */
  {
    PersonMap_var map_1 = new PersonMap;
    PersonMap_var map_2 = new PersonMap;
    map_1->length(5);
    map_2->length(5);
    for(int i=0;i<5;i++) {
      (*map_1)[i].name = "1";
      (*map_1)[i].id   = i;
      (*map_2)[i].name = "2";
      (*map_2)[i].id   = i+i;
    }
    
    PersonMap_var map_3;
    PersonMap_var map_r;
    
    map_r = Consoleconsole->foo4(map_1,map_2,map_3);
    
    for(CORBA::Long i=0;i<map_r->length();i++) {
      assert((*map_r)[i].id == i);
    }
    for(CORBA::Long i=0;i<map_2->length();i++) {
      assert((*map_2)[i].id == i);
    }
    for(CORBA::Long i=0;i<map_3->length();i++) {
      assert((*map_3)[i].id == i+i);
    }
  }


  /*
   * Test Case for: enum Color {red, green, blue, black, orange};
   */
  Color Color_2,Color_3, Color_r;
  Color_2 = Color(blue);
  Color_r = Consoleconsole->foo5(Color(red),Color_2, Color_3);
  assert(Color_2 == Color(red));
  assert(Color_3 == Color(blue));
  assert(Color_r == Color(red));

  
  DEBUGNL("==== End Test Case ===============================================" );    


  // Un-Deployment

  // Destroy component instances
  myHello->remove();
}
