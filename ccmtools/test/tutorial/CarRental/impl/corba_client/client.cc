#include <CORBA.h>
#include <coss/CosNaming.h>

#include <CCM/CCMContainer.h>

#include <BigBusiness_CarRentalHome.h>
#include <BigBusiness_CarRental.h>

using namespace std;
using namespace WX::Utils;

int main (int argc, char *argv[])
{
  try {
    CORBA::ORB_var orb = CORBA::ORB_init(argc, argv);
    
    CORBA::Object_var obj = orb->resolve_initial_references("NameService");
    CosNaming::NamingContextExt_var nc =
      CosNaming::NamingContextExt::_narrow(obj);
    
    // Deployment 
    
    // Find ComponentHomes in the Naming-Service
    obj = nc->resolve_str("CarRentalHome:1.0");
    assert (!CORBA::is_nil (obj));
    ::BigBusiness::CarRentalHome_var myCarRentalHome = 
	::BigBusiness::CarRentalHome::_narrow (obj);
    
    // Create component instances
    ::BigBusiness::CarRental_var myCarRental = 
	myCarRentalHome->create();
    
    // Provide facets   
    ::BigBusiness::CustomerMaintenance_var maintenance = 
        myCarRental->provide_maintenance();
    
    ::BigBusiness::CustomerBusiness_var business = 
        myCarRental->provide_business();
    
    myCarRental->configuration_complete();
    
    cout << "==== Begin Test Case ===================================" << endl;
    
    business->dollars_per_mile(5.5);

    {
      CORBA::Long id = 1;
      ::BigBusiness::Customer person;
      person.id = id;
      person.first_name = "Franz";
      person.last_name = "Kafka";
      maintenance->createCustomer(person);
    
      business->resetCustomerMiles(id);
      business->addCustomerMiles(id, 120.0);
    }

    {
      CORBA::Long id = 1;
      ::BigBusiness::Customer_var person = 
	  maintenance->retrieveCustomer(id);
      double dollars = business->getCustomerDollars(id); 

      cout << " Customer: " << person->first_name << " " 
	   << person->last_name << endl;
      cout << " Miles : " <<  person->mileage << endl;
      cout << " To pay: " << dollars << " Dollars" << endl;
    }

    cout << "==== End Test Case =====================================" << endl; 

    // Destroy component instances
    myCarRental->remove();
  }
  catch(...) {
    cout << "Client: there is something wrong!" << endl;
    return -1;
  }
}
