#include <cstdlib> 
#include <iostream>
#include <string>
#include <WX/Utils/debug.h>
#include <CCM/CCMContainer.h>

#include <CORBA.h>
#include <coss/CosNaming.h>

#include <application_Server.h>
#include <application_ServerHome.h>

using namespace std;
using namespace WX::Utils;

//==============================================================================
// Implementation of remote client test
//==============================================================================

int main (int argc, char *argv[])
{
    cout << "Login remote client (C++)" << endl;

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

    CORBA::Object_var obj = orb->resolve_initial_references("NameService");
    CosNaming::NamingContextExt_var nc =
        CosNaming::NamingContextExt::_narrow(obj);

    // Deployment 

    // Find ComponentHomes in the Naming-Service
    obj = nc->resolve_str("ServerHome");
    assert (!CORBA::is_nil (obj));
    ::application::ServerHome_var home = ::application::ServerHome::_narrow(obj);

    // Create component instances
    ::application::Server_var server = home->create();
    ::application::Login_var login = server->provide_login();
    server->configuration_complete();

    try {
      ::application::PersonData person;
      person.id = 0;
      person.name = CORBA::string_dup(""); // Here we create an error!!!
      person.password = CORBA::string_dup("");   
      person.group =  ::application::USER;       
      
      login->isValidUser(person);
      assert(false); 
    }
    catch(::application::InvalidPersonData& e)
    {
      cout << "Caught InvalidPersonData exception!" << endl;	
    }


    try {
      ::application::PersonData person;
      person.id = 277;
      person.name = CORBA::string_dup("eteinik");
      person.password = CORBA::string_dup("eteinik");   
      person.group =  ::application::USER;       
      
      CORBA::Boolean result = login->isValidUser(person);
      
      if(result) {
	cout << "Welcome " << person.name << endl;
      }
      else {
	cout << "We don't know you !!!" << endl;
      }
    }
    catch(::application::InvalidPersonData& e)
    {
      cout << "Error: InvalidPersonData" << endl;	
    }

    // Destroy component instances
    server->remove();

    // Un-Deployment
    cout << "OK!" << endl; 	
}


