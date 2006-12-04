/***
 * CCM Tools Test Client 
 *
 * This file was automatically generated by CCM Tools version 0.6.1.
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

#include <CCM/CCMContainer.h>

#include <CORBA.h>
#include <coss/CosNaming.h>

#include <world/europe/ccm/remote/TestHome_remote.h>
#include <world_europe_Test.h>

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
    CCM::register_all_factories (orb);

    // Deploy local and remote component homes	
    int error = 0;
    error += deploy_world_europe_ccm_local_TestHome("TestHome");
    error += deploy_world_europe_ccm_remote_TestHome(orb, "TestHome:1.0");
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
    world::europe::TestHome_var myTestHome = world::europe::TestHome::_narrow (obj);

    // Create component instances
    world::europe::Test_var myTest = myTestHome->create();

    // Provide facets   
    ::world::Constants_var constants = myTest->provide_iface();
	
    myTest->configuration_complete();

    cout << "==== Begin Test Case ===================================" << endl;

    {
      //  const boolean BOOLEAN_CONST = TRUE;
      CORBA::Boolean initial = world::ccm::local::Constants::BOOLEAN_CONST;
      CORBA::Boolean result = constants->getBooleanValue();
      assert(initial == result);
    }

    {
      //  const octet OCTET_CONST = 255;
      CORBA::Octet initial = world::ccm::local::Constants::OCTET_CONST;
      CORBA::Octet result = constants->getOctetValue();
      assert(initial == result);
    }

    {
      //  const short SHORT_CONST = -7+10;
      CORBA::Short initial = world::ccm::local::Constants::SHORT_CONST;
      CORBA::Short result = constants->getShortValue();
      assert(initial == result);
    }

    {
      //  const unsigned short USHORT_CONST = 7;
      CORBA::UShort initial = world::ccm::local::Constants::USHORT_CONST;
      CORBA::UShort result = constants->getUnsignedShortValue();
      assert(initial == result);
    }

    {
      //  const long LONG_CONST = -7777;
      CORBA::Long initial = world::ccm::local::Constants::LONG_CONST;
      CORBA::Long result = constants->getLongValue();
      assert(initial == result);
    }

    {
      //  const unsigned long ULONG_CONST = 7777;
      CORBA::ULong initial = world::ccm::local::Constants::ULONG_CONST;
      CORBA::ULong result = constants->getUnsignedLongValue();
      assert(initial == result);
    }

    {
      //  const char CHAR_CONST = 'c';
      CORBA::Char initial = world::ccm::local::Constants::CHAR_CONST;
      CORBA::Char result = constants->getCharValue();
      assert(initial == result);
    }

    {
      //  const string STRING_CONST = "1234567890";
      string initial = world::ccm::local::Constants::STRING_CONST; 
      char* result = constants->getStringValue();
      assert(strcmp(initial.c_str(),result) == 0);
    }

    {
      //  const float FLOAT_CONST = 3.14;
      CORBA::Float initial = world::ccm::local::Constants::FLOAT_CONST;
      CORBA::Float result = constants->getFloatValue();
      assert(abs(initial - result) < 0.001);
    }

    {
      //  const double DOUBLE_CONST = 3.1415926*2.0;
      CORBA::Double initial = world::ccm::local::Constants::DOUBLE_CONST;
      CORBA::Double result = constants->getDoubleValue();
      assert(abs(initial - result) < 0.000001);
    }

    cout << "==== End Test Case =====================================" << endl; 

    // Destroy component instances
    myTest->remove();

    // Un-Deployment
    cout << "Exit C++ remote test client" << endl; 	
}

#endif // HAVE_MICO

