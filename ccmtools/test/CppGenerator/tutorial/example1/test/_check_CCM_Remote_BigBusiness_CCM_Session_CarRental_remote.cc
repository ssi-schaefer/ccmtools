#include <cstdlib> 
#include <iostream>
#include <string>
#include <WX/Utils/debug.h>
#include <CCM/CCMContainer.h>

#include <CORBA.h>
#include <coss/CosNaming.h>

#include <CCM_Remote/BigBusiness/CCM_Session_CarRental/CarRentalHome_remote.h>
#include <BigBusiness_CarRental.h>

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

  int error = deploy_CarRentalHome(orb, "CarRentalHome:1.0");
  if(!error) {
    cout << "CarRentalHome stand-alone server is running..." << endl;
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
  obj = nc->resolve_str ("CarRentalHome:1.0");
  assert (!CORBA::is_nil (obj));
  ::BigBusiness::CarRentalHome_var myCarRentalHome = ::BigBusiness::CarRentalHome::_narrow (obj);

  // Create component instances
  ::BigBusiness::CarRental_var myCarRental = myCarRentalHome->create();

  // Provide facets   
  ::BigBusiness::CustomerMaintenance_var maintenance = myCarRental->provide_maintenance();

  ::BigBusiness::CustomerBusiness_var business = myCarRental->provide_business();


	
  myCarRental->configuration_complete();


  cout << "==== Begin Test Case =============================================" << endl;    

  try {
    {
      ::BigBusiness::Customer person;
      person.id = 1;
      person.first_name = CORBA::string_dup("Franz"); 
      person.last_name = CORBA::string_dup("Kafka");
      person.mileage = 0.0;
      maintenance->createCustomer(person);
    }
    {
      ::BigBusiness::Customer person;
      person.id = 2;
      person.first_name = CORBA::string_dup("Thomas");
      person.last_name = CORBA::string_dup("Bernhard");
      person.mileage = 0.0;
      maintenance->createCustomer(person);
    }
    
    {
      ::BigBusiness::Customer person;
      person.id = 3;
      person.first_name = CORBA::string_dup("Karl");
      person.last_name = CORBA::string_dup("Kraus");
      person.mileage = 0.0;
      maintenance->createCustomer(person);
    }

    {
      ::BigBusiness::Customer_var person;
      CORBA::Long id = 2;
      person = maintenance->retrieveCustomer(id);
      
      cout << "person.id = " << person->id << endl;
      cout << "person.first_name = " << person->first_name << endl;
      cout << "person.last_name = " << person->last_name << endl;
      
      assert(person->id == 2);
      assert(strcmp(person->first_name,"Thomas") == 0);
      assert(strcmp(person->last_name,"Bernhard") == 0);
    }
    
    {
      ::BigBusiness::CustomerList_var person_list;
      person_list = maintenance->retrieveAllCustomers();
      assert((*person_list)[2].id == 3);
      assert(strcmp((*person_list)[2].first_name,"Karl") == 0);
      assert(strcmp((*person_list)[2].last_name,"Kraus") == 0);
      
      assert((*person_list)[1].id == 2);
      assert(strcmp((*person_list)[1].first_name,"Thomas") == 0);
      assert(strcmp((*person_list)[1].last_name,"Bernhard") == 0);
      
      assert((*person_list)[0].id == 1);
      assert(strcmp((*person_list)[0].first_name,"Franz") == 0);
      assert(strcmp((*person_list)[0].last_name,"Kafka") == 0);
    }      
    
    {
      ::BigBusiness::Customer person;
      person.id = 1;
      person.first_name = CORBA::string_dup("Werner");
      person.last_name = CORBA::string_dup("Schwab");
      person.mileage = 0.0;
      maintenance->updateCustomer(person);      
      
      ::BigBusiness::Customer_var another_person;
      another_person = maintenance->retrieveCustomer(person.id);
      assert(another_person->id == 1);
      assert(strcmp(another_person->first_name,"Werner") == 0);
      assert(strcmp(another_person->last_name,"Schwab") == 0);
    }
    
    {
      long id = 1;
      maintenance->deleteCustomer(id);
      cout << "Customer removed" << endl;
      
      ::BigBusiness::Customer person;
      maintenance->retrieveCustomer(id);
      assert(false); // Customer found => failure
    }
  }
  catch(::BigBusiness::CreateCustomerException) {
    cerr << "Maintenance Exception: can't create customer!" << endl;
  }
  catch(::BigBusiness::NoCustomerException) {
    cerr << "Maintenance Exception: no customer found!" << endl;
  }
  
  
  try {
    {
      CORBA::Long id = 2;
      CORBA::Double miles = 7.7;
      business->addCustomerMiles(id, miles); 
      
      CORBA::Double other_miles;
      other_miles = business->getCustomerMiles(id); 
      cout << other_miles << endl;
      cout << miles << endl;
      assert( abs(other_miles - miles) < 0.001);
    }
    
    {
      CORBA::Long id = 2;
      CORBA::Double dollars;
      CORBA::Double miles = 1.1;
      CORBA::Double other_miles;
      CORBA::Double factor = 5.3;
      business->addCustomerMiles(id, miles); 
      business->dollars_per_mile(factor);
      other_miles = business->getCustomerMiles(id);
      dollars = business->getCustomerDollars(id); 
      assert( abs(dollars - other_miles*factor) < 0.001);
    }
    
    {
      CORBA::Long id = 2;
      CORBA::Double dollars;
      business->resetCustomerMiles(id);
      dollars = business->getCustomerDollars(id); 
      assert( abs(dollars) < 0.001);
    }
  }
  catch(CCM_Local::BigBusiness::NoCustomerException) {
    cerr << "Maintenance Exception: no customer found!" << endl;
    assert(false);
  }
  
  cout << "==== End Test Case ===============================================" << endl;    


  // Un-Deployment

  // Destroy component instances
  myCarRental->remove();

  DEBUGNL("Exit C++ remote test client"); 	
}
