#include <cstdlib> 
#include <iostream>
#include <string>

#include <ccmtools/remote/CCMContainer.h>

#include <CORBA.h>
#include <coss/CosNaming.h>

#include <ccmtools_corba_application_ServerHome.h>

using namespace std;
using namespace ccmtools::corba::application;

//==============================================================================
// Implementation of remote client test
//==============================================================================

int main (int argc, char *argv[])
{
    cout << "Login remote client (C++)" << endl;

    // Initialize ORB 
    //    ostringstream os;
    //    os << "NameService=" << NameServiceLocation;
    //    char* argv_[] = { "", "-ORBInitRef", (char*)os.str().c_str()}; 

    int   argc_   = 3;
    char* argv_[] = { "", 
                    "-ORBInitRef", 
                    "NameService=corbaloc:iiop:1.2@localhost:5050/NameService"};
    cout << "args = [" << argv_[1] << ", " << argv_[2] << "]" << endl;
    CORBA::ORB_var orb = CORBA::ORB_init(argc_, argv_);

    CORBA::Object_var obj = orb->resolve_initial_references("NameService");
    CosNaming::NamingContextExt_var nc =
        CosNaming::NamingContextExt::_narrow(obj);

    // Deployment 

    // Find ComponentHomes in the Naming-Service
    obj = nc->resolve_str("ServerHome");
    ServerHome_var home = ServerHome::_narrow(obj);
    // Create component instances
    Server_var server = home->create();
    Login_var login = server->provide_login();
    server->configuration_complete();

    try {
      PersonData person;
      person.id = 0;
      person.name = CORBA::string_dup(""); // Here we create an error!!!
      person.password = CORBA::string_dup("");   
      person.group = USER;       
      
      login->isValidUser(person);
      assert(false); 
    }
    catch(InvalidPersonData& e)
    {
      cout << "Caught InvalidPersonData exception!" << endl;	
    }

    try {
      PersonData person;
      person.id = 277;
      person.name = CORBA::string_dup("eteinik");
      person.password = CORBA::string_dup("eteinik");   
      person.group =  USER;       
      
      CORBA::Boolean result = login->isValidUser(person);
      
      if(result) {
	cout << "Welcome " << person.name << endl;
      }
      else {
	cout << "We don't know you !!!" << endl;
      }
    }
    catch(InvalidPersonData& e)
    {
      cout << "Error: InvalidPersonData" << endl;	
    }

    // Destroy component instances
    server->remove();

    // Un-Deployment
    cout << "OK!" << endl; 	
}


