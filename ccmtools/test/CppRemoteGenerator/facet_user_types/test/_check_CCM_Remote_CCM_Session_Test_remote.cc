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
    error += deploy_CCM_Local_TestHome("TestHome");
    error += deploy_CCM_Remote_TestHome(orb, "TestHome:1.0");
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
    ::TestHome_var myTestHome = 
	::TestHome::_narrow (obj);

    // Create component instances
    ::Test_var myTest = myTestHome->create();

    // Provide facets   
    ::Console_var Consoleconsole = 
        myTest->provide_console();

    myTest->configuration_complete();

    cout << "==== Begin Test Case ===================================" << endl;
    /*
     * Test Case for: enum Color {red, green, blue, black, orange};
     */
    {
      const ::Color p1 = ::red;
      ::Color p2 = ::green;
      ::Color p3;
      ::Color result;

      result = Consoleconsole->f1(p1, p2, p3);
      cout <<  CCM_Remote::ccmDebug(result);

      assert(p2 == ::red);
      assert(p3 == ::green);
      assert(result == ::red);
    }


    /*
     * Test Case for: struct Person { long id; string name; };
     */
    {
      ::Person p1;
      ::Person_var p2 = new  ::Person;
      ::Person_var p3;
      ::Person_var result;
      
      p1.name = CORBA::string_dup("Egon");   
      p1.id = 3;
      
      p2->name = CORBA::string_dup("Andrea"); 
      p2->id = 23;
      
      result = Consoleconsole->f2(p1,p2,p3);
      cout << CCM_Remote::ccmDebug(result);

      assert(strcmp(p3->name, "Andrea") == 0);
      assert(strcmp(p2->name, "Egon") == 0);
      assert(strcmp(result->name, "EgonAndrea") == 0);
    }

    /*
     * Test Case for: struct Address { long id; string name; Person resident };
     */
    {
      ::Address p1;
      ::Address_var p2 = new  ::Address;
      ::Address_var p3;
      ::Address_var result;
      ::Person person;

      p1.street = CORBA::string_dup("Waltendorf");   
      p1.number = 7;
      person.name = CORBA::string_dup("Egon");   
      person.id   = 3;
      p1.resident = person;

      p2->street   = CORBA::string_dup("Petersgasse"); 
      p2->number   =17;
      person.name = CORBA::string_dup("Andrea");   
      person.id   = 23;
      p2->resident = person;

      result = Consoleconsole->f3(p1,p2,p3);
      cout << CCM_Remote::ccmDebug(result);

      assert(strcmp(p3->street, "Petersgasse") == 0);
      assert(p3->number == 17);
      assert(strcmp(p3->resident.name, "Andrea") == 0);
      assert(p3->resident.id == 23);

      assert(strcmp(p2->street, "Waltendorf") == 0);
      assert(p2->number == 7);
      assert(strcmp(p2->resident.name, "Egon") == 0);
      assert(p2->resident.id == 3);

      assert(strcmp(result->street, "WaltendorfPetersgasse") == 0);
      assert(result->number == 24);
      assert(strcmp(result->resident.name, "EgonAndrea") == 0);
      assert(result->resident.id == 26);
    }


    /* 
     * Test Case for: typedef sequence<long>
     */
    {
      ::LongList_var list_1 = new ::LongList;
      ::LongList_var list_2 = new ::LongList;
      list_1->length(5);
      list_2->length(5);
      for(int i=0;i<5;i++) {
        (*list_1)[i] = i;
        (*list_2)[i] = i+i;
      }
      
      ::LongList_var list_3;
      ::LongList_var list_r;
      
      list_r = Consoleconsole->f4(list_1,list_2,list_3);
      cout << CCM_Remote::ccmDebug(list_r);

      for(unsigned long i=0; i < list_r->length(); i++) {
        assert((*list_r)[i]== (CORBA::Long)i);
      }
      for(unsigned long i=0; i < list_2->length(); i++) {
        assert((*list_2)[i]== (CORBA::Long)i);
      }
      for(unsigned long i=0; i < list_3->length(); i++) {
        assert((*list_3)[i]== (CORBA::Long)(i+i));
      }
    }

    
    /* 
     * Test Case for: typedef sequence<string>
     */
    {
      ::StringList_var list_1 = new ::StringList;
      ::StringList_var list_2 = new ::StringList;
      list_1->length(5);
      list_2->length(5);
      for(int i=0;i<5;i++) {
        (*list_1)[i] = "Egon";
        (*list_2)[i] = "Andrea";
      }
      
      ::StringList_var list_3;
      ::StringList_var list_r;
      
      list_r = Consoleconsole->f5(list_1,list_2,list_3);
      cout << CCM_Remote::ccmDebug(list_r);      

      for(unsigned long i=0;i<list_r->length();i++) {
        assert(strcmp((*list_r)[i],"Test") == 0);
      }
      for(unsigned long i=0;i<list_2->length();i++) {
        assert(strcmp((*list_2)[i],"Egon") == 0);
      }
      for(unsigned long i=0;i<list_3->length();i++) {
        assert(strcmp((*list_3)[i],"Andrea") == 0);
      }
    }

    /* 
     * Test Case for: typedef sequence<struct>
     */
    {
      ::PersonList_var list_1 = new ::PersonList;
      ::PersonList_var list_2 = new ::PersonList;
      list_1->length(5);
      list_2->length(5);
      for(int i=0;i<5;i++) {
        (*list_1)[i].name = "Andrea";
        (*list_1)[i].id   = i;
        (*list_2)[i].name = "Egon";
        (*list_2)[i].id   = i+i;
      }
      
      ::PersonList_var list_3;
      ::PersonList_var list_r;
      
      list_r = Consoleconsole->f6(list_1,list_2,list_3);
      cout << CCM_Remote::ccmDebug(list_r);      

      for(unsigned long i=0; i < list_r->length(); i++) {
        assert(strcmp((*list_r)[i].name,"Test") == 0);
        assert((*list_r)[i].id == (CORBA::Long)i);
      }
      for(unsigned long i=0; i < list_2->length(); i++) {
        assert(strcmp((*list_2)[i].name,"Andrea") == 0);
        assert((*list_2)[i].id == (CORBA::Long)i);
      }
      for(unsigned long i=0; i < list_3->length(); i++) {
        assert(strcmp((*list_3)[i].name,"Egon") == 0);
        assert((*list_3)[i].id == (CORBA::Long)(i+i));
      }
    }
      
    /* 
     * Test Case for: typedef long time_t; 
     */
    {
      ::time_t time_2=3, time_3, time_r;

      time_r = Consoleconsole->f7(7,time_2, time_3);
      cout << CCM_Remote::ccmDebug(time_r); 

      assert(time_2 == 7);
      assert(time_3 == 3);
      assert(time_r == 3+7);
    }

    cout << "==== End Test Case =====================================" << endl;
 
    // Destroy component instances
    myTest->remove();

    // Un-Deployment

    DEBUGNL("Exit C++ remote test client"); 	
}
