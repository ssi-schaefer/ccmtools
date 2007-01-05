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

#include <cstdlib> 
#include <iostream>
#include <string>

#include <ccmtools/remote/CCMContainer.h>

#include <CORBA.h>
#include <coss/CosNaming.h>

#include <ccmtools/remote/world/europe/austria/TestHome_remote.h>
#include <ccmtools_corba_world_europe_austria_Test.h>

using namespace std;

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
    error += deploy_world_europe_austria_TestHome("TestHome");
    error += deploy_ccmtools_remote_world_europe_austria_TestHome(orb, "TestHome");
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
    ::ccmtools::corba::world::europe::austria::TestHome_var myTestHome = 
    		::ccmtools::corba::world::europe::austria::TestHome::_narrow (obj);

    // Create component instances
    ::ccmtools::corba::world::europe::austria::Test_var myTest = myTestHome->create();

    // Provide facets   
    ::ccmtools::corba::world::europe::austria::IFace_var iface = myTest->provide_iface();
	
    myTest->configuration_complete();

    cout << "==== Begin Test Case ===================================" << endl;

    try 
    {
      CORBA::Long result;
      result = iface->foo("0123456789");
      assert(result == 10);
    }
    catch(const ::ccmtools::corba::world::europe::austria::ErrorException& e) 
    {
      assert(false);
    }

    try {
      iface->foo("Error");
      assert(false);
    }
    catch(const ::ccmtools::corba::world::europe::austria::ErrorException& e) 
    {
      ::ccmtools::corba::world::europe::austria::ErrorInfoList infolist = e.info;
      for(unsigned long i = 0; i < infolist.length(); i++) 
      {
      	cout << e.info[i].code << ": " << e.info[i].message << endl;
      }
    }

    try 
    {
      iface->foo("SuperError");
      assert(false);
    }
    catch(const ::ccmtools::corba::world::europe::austria::SuperError& e) 
    {
      cout << "SuperError" << endl;
    }

    try 
    {
      iface->foo("FatalError");
      assert(false);
    }
    catch(const ::ccmtools::corba::world::europe::austria::FatalError& e) 
    {
       cout << e.what << endl;
    }

    cout << "==== End Test Case =====================================" << endl; 

    // Destroy component instances
    myTest->remove();

    // Un-Deployment
    error  = undeploy_world_europe_austria_TestHome("TestHome");
    error += undeploy_ccmtools_remote_world_europe_austria_TestHome(orb, "TestHome");
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

