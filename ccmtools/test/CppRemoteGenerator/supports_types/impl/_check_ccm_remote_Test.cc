/***
 * CCM Tools Test Client 
 *
 * This file was automatically generated by CCM Tools 
 *         <http://ccmtools.sourceforge.net/>
 *
 * This test client is part of the remote component test concept. 
 ***/

#ifdef HAVE_CONFIG_H
#  include <config.h>
#endif 

#ifdef HAVE_MICO 

#include <cmath>
#include <cstdlib> 
#include <iostream>
#include <string>

#include <ccmtools/remote/CCMContainer.h>

#include <CORBA.h>
#include <coss/CosNaming.h>

#include <ccmtools/remote/TestHome_remote.h>
#include <ccmtools_corba_Test.h>

using namespace std;
using namespace wamas::platform::utils;

//==============================================================================
// Implementation of remote client test
//==============================================================================

int main (int argc, char *argv[])
{
    cout << "Enter C++ remote test client" << endl;

    // Initialize ORB 
    int argc_ = 3;
    char* argv_[] = { "", "-ORBInitRef", "NameService=corbaloc:iiop:1.2@localhost:5050/NameService" }; 
    CORBA::ORB_var orb = CORBA::ORB_init(argc_, argv_);

    /**
     * Server-side code
     */ 

    // Register all value type factories with the ORB  
    ::ccmtools::remote::register_all_factories (orb);

    // Deploy local and remote component homes	
    int error = 0;
    error  = deploy_TestHome("TestHome");
    error += deploy_ccmtools_remote_TestHome(orb, "TestHome");
    if(!error) 
    {
        cout << "TestHome server is running..." << endl;
    }
    else 
    {
        cerr << "ERROR: Can't deploy components!" << endl;
        return -1;
    }

    // For testing we use CORBA collocation	
    // orb->run();
	

    /**
     * Client-side code
     */
    CORBA::Object_var obj = orb->resolve_initial_references("NameService");
    CosNaming::NamingContextExt_var nc = CosNaming::NamingContextExt::_narrow(obj);

    // Deployment 

    // Find ComponentHomes in the Naming-Service
    obj = nc->resolve_str("TestHome");
    assert (!CORBA::is_nil (obj));
    ::ccmtools::corba::TestHome_var myTestHome = ::ccmtools::corba::TestHome::_narrow (obj);

    // Create component instances
    ::ccmtools::corba::Test_var myTest = myTestHome->create();

    // Provide facets   

	
    myTest->configuration_complete();

    cout << "==== Begin Test Case ===================================" << endl;


    // ------------------------------------------------------------------
    // Void Type Check
    // ------------------------------------------------------------------
    {
      cout << "Void Type Check ...";

      long value = 7;
      long result;
      myTest->fv1(value);
      result = myTest->fv2();
      assert(value == result);

      cout << "OK" << endl;
    }


    // ------------------------------------------------------------------
    // Basic Types Check
    // ------------------------------------------------------------------
    {
      cout << "Basic Types Check ...";

      {
        CORBA::Short short_2=3, short_3, short_r;
        short_r = myTest->fb1(7,short_2, short_3);
        assert(short_2 == 7);
        assert(short_3 == 3);
        assert(short_r == 3+7);
      }

      {
        CORBA::Long long_2=3, long_3, long_r;
        long_r = myTest->fb2(7,long_2, long_3);
        assert(long_2 == 7);
        assert(long_3 == 3);
        assert(long_r == 3+7);
      }

      {
        CORBA::UShort ushort_2=3, ushort_3, ushort_r;
        ushort_r = myTest->fb3(7,ushort_2, ushort_3);
        assert(ushort_2 == 7);
        assert(ushort_3 == 3);
        assert(ushort_r == 3+7);
      }

      {
        CORBA::ULong ulong_2=3, ulong_3, ulong_r;
        ulong_r = myTest->fb4(7,ulong_2, ulong_3);
        assert(ulong_2 == 7);
        assert(ulong_3 == 3);
        assert(ulong_r == 3+7);
      }

      {
        CORBA::Float float_2=3.0, float_3, float_r;
        float_r = myTest->fb5(7.0,float_2, float_3);
        assert(abs(float_2 - 7.0) < 0.001);
        assert(abs(float_3 - 3.0) < 0.001);
        assert(abs(float_r - (3.0+7.0)) < 0.001);
      }
      {
        CORBA::Double double_2=3.0, double_3, double_r;
        double_r = myTest->fb6(7.0,double_2, double_3);
        assert(abs(double_2 - 7.0) < 0.000001);
        assert(abs(double_3 - 3.0) < 0.000001);
        assert(abs(double_r - (3.0+7.0)) < 0.000001);
      }

      {
        CORBA::Char char_2=3, char_3, char_r;
        char_r = myTest->fb7(7,char_2, char_3);
        assert(char_2 == 7);
        assert(char_3 == 3);
        assert(char_r == 3+7);
      }

      {
        char* string_2 = CORBA::string_dup("drei");
        char* string_3;
        char* string_r;
        string_r = myTest->fb8("sieben",string_2, string_3);
        assert(strcmp(string_2,"sieben") == 0);
        assert(strcmp(string_3,"drei") == 0);
        assert(strcmp(string_r,"dreisieben") == 0);
      }

      {
        CORBA::Boolean bool_2=false, bool_3, bool_r;
        bool_r = myTest->fb9(true, bool_2, bool_3);
        assert(bool_2 == true);
        assert(bool_3 == false);
        assert(bool_r == false && true);
      }

      {
        CORBA::Octet octet_2=3, octet_3, octet_r;
        octet_r = myTest->fb10(7,octet_2, octet_3);
        assert(octet_2 == 7);
        assert(octet_3 == 3);
        assert(octet_r == 3+7);
      }

      cout << " OK" << endl;
    }


    // ------------------------------------------------------------------
    // User Types Check
    // ------------------------------------------------------------------
    {
      cout << "User Types Check ...";

      {
        // enum Color {red, green, blue, black, orange}

        ::ccmtools::corba::Color Color_2,Color_3, Color_r;
        Color_2 = ::ccmtools::corba::blue;

        Color_r = myTest->fu1(::ccmtools::corba::red,Color_2, Color_3);

        assert(Color_2 == ::ccmtools::corba::red);
        assert(Color_3 == ::ccmtools::corba::blue);
        assert(Color_r == ::ccmtools::corba::orange);
      }
      
      {
        // struct Person { long id; string name; }   
        ::ccmtools::corba::Person p1;
        ::ccmtools::corba::Person_var p2 = new ::ccmtools::corba::Person;
        ::ccmtools::corba::Person_var p3;
        ::ccmtools::corba::Person_var result;
        
        p1.name = CORBA::string_dup("Egon");   
        p1.id = 3;
        
        p2->name = CORBA::string_dup("Andrea"); 
        p2->id = 23;
        
        result = myTest->fu2(p1,p2,p3);
        
        assert(strcmp(p3->name, "Andrea") == 0);
        assert(strcmp(p2->name, "Egon") == 0);
        assert(strcmp(result->name, "EgonAndrea") == 0);
      }


      {
        // struct Address { string street; long number; Person resident; }
        ::ccmtools::corba::Address p1;
        ::ccmtools::corba::Address_var p2 = new  ::ccmtools::corba::Address;
        ::ccmtools::corba::Address_var p3;
        ::ccmtools::corba::Address_var result;
        ::ccmtools::corba::Person person;

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
        
        result = myTest->fu3(p1,p2,p3);
      
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

      {
        // typedef sequence<long> LongList
        ::ccmtools::corba::LongList_var list_1 = new ::ccmtools::corba::LongList;
        ::ccmtools::corba::LongList_var list_2 = new ::ccmtools::corba::LongList;
        list_1->length(5);
        list_2->length(5);
        for(int i=0;i<5;i++) 
        {
          (*list_1)[i] = i;
          (*list_2)[i] = i+i;
        }
        
        ::ccmtools::corba::LongList_var list_3;
        ::ccmtools::corba::LongList_var list_r;
        
        list_r = myTest->fu4(list_1,list_2,list_3);
        
        for(unsigned long i=0; i < list_r->length(); i++) 
        {
          assert((*list_r)[i]== (CORBA::Long)i);
        }
        for(unsigned long i=0; i < list_2->length(); i++) 
        {
          assert((*list_2)[i]== (CORBA::Long)i);
        }
        for(unsigned long i=0; i < list_3->length(); i++) 
        {
          assert((*list_3)[i]== (CORBA::Long)(i+i));
        }
      }

      {
        // typedef sequence<string> StringList
        ::ccmtools::corba::StringList_var list_1 = new ::ccmtools::corba::StringList;
        ::ccmtools::corba::StringList_var list_2 = new ::ccmtools::corba::StringList;
        list_1->length(5);
        list_2->length(5);
        for(int i=0;i<5;i++) 
        {
          (*list_1)[i] = "Egon";
          (*list_2)[i] = "Andrea";
        }
        
        ::ccmtools::corba::StringList_var list_3;
        ::ccmtools::corba::StringList_var list_r;
        
        list_r = myTest->fu5(list_1,list_2,list_3);
        
        for(unsigned long i=0;i<list_r->length();i++) 
        {
          assert(strcmp((*list_r)[i],"Test") == 0);
        }
        for(unsigned long i=0;i<list_2->length();i++) 
        {
          assert(strcmp((*list_2)[i],"Egon") == 0);
        }
        for(unsigned long i=0;i<list_3->length();i++) 
        {
          assert(strcmp((*list_3)[i],"Andrea") == 0);
        }
      }

      {
        // typedef sequence<Person> PersonList
        ::ccmtools::corba::PersonList_var list_1 = new ::ccmtools::corba::PersonList;
        ::ccmtools::corba::PersonList_var list_2 = new ::ccmtools::corba::PersonList;
        list_1->length(5);
        list_2->length(5);
        for(int i=0;i<5;i++) 
        {
          (*list_1)[i].name = "Andrea";
          (*list_1)[i].id   = i;
          (*list_2)[i].name = "Egon";
          (*list_2)[i].id   = i+i;
        }
        
        ::ccmtools::corba::PersonList_var list_3;
        ::ccmtools::corba::PersonList_var list_r;
        
        list_r = myTest->fu6(list_1,list_2,list_3);
        
        for(unsigned long i=0; i < list_r->length(); i++) 
        {
          assert(strcmp((*list_r)[i].name,"Test") == 0);
          assert((*list_r)[i].id == (CORBA::Long)i);
        }
        for(unsigned long i=0; i < list_2->length(); i++) 
        {
          assert(strcmp((*list_2)[i].name,"Andrea") == 0);
          assert((*list_2)[i].id == (CORBA::Long)i);
        }
        for(unsigned long i=0; i < list_3->length(); i++) 
        {
          assert(strcmp((*list_3)[i].name,"Egon") == 0);
          assert((*list_3)[i].id == (CORBA::Long)(i+i));
        }
      }

      {
        // typedef long time_t
        ::ccmtools::corba::time_t time_2=3, time_3, time_r;
        time_r = myTest->fu7(7,time_2, time_3);
        assert(time_2 == 7);
        assert(time_3 == 3);
        assert(time_r == 3+7);
      }

      cout << " OK" << endl;
    }

    cout << "==== End Test Case =====================================" << endl; 

    // Destroy component instances
    myTest->remove();

    // Un-Deployment
    error  = deploy_TestHome("TestHome");
    error += deploy_ccmtools_remote_TestHome(orb, "TestHome");
    if(!error) 
    {
        cout << "Exit C++ remote test client" << endl; 	
    }
    else 
    {
        cerr << "ERROR: Can't undeploy components!" << endl;
        return -1;
    }
}

#endif // HAVE_MICO

