/***
 * CCM Tools Test Client 
 *
 * This file was automatically generated by CCM Tools version 0.6.1.
 *         <http://ccmtools.sourceforge.net/>
 *
 * This test client is part of the remote component test concept. 
 *
 * To enable debug output use -DWXDEBUG compiler flag and set the
 * WX_DEBUG_LEVELS environment variable to "CCM_REMOTE".
 * (e.g. export WX_DEBUG_LEVELS="CCM_REMOTE")
 ***/

#ifdef HAVE_CONFIG_H
#  include <config.h>
#endif 

#ifdef HAVE_MICO 

#include <cstdlib> 
#include <iostream>
#include <string>
#include <WX/Utils/debug.h>
#include <CCM/CCMContainer.h>

#include <CORBA.h>
#include <coss/CosNaming.h>

#include <world/europe/ccm/remote/component/Test/TestHome_remote.h>
#include <world_europe_Test.h>

using namespace std;
using namespace WX::Utils;

//==============================================================================
// Implementation of remote client test
//==============================================================================

int main (int argc, char *argv[])
{
    cout << "Enter C++ remote test client" << endl;

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


    /**
     * Server-side code
     */ 

    // Register all value type factories with the ORB  
    CCM::register_all_factories (orb);

    // Deploy local and remote component homes	
    int error = 0;
    error += deploy_world_europe_ccm_local_component_Test_TestHome("TestHome");
    error += deploy_world_europe_ccm_remote_component_Test_TestHome(orb, "TestHome:1.0");
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
      CORBA::Boolean initial = true;
      CORBA::Boolean result = constants->getBooleanValue();
      assert(initial == result);
    }

    {
      //  const octet OCTET_CONST = 255;
      CORBA::Octet initial = 255;
      CORBA::Octet result = constants->getOctetValue();
      assert(initial == result);
    }

    {
      //  const short SHORT_CONST = -7+10;
      CORBA::Short initial = -7+10;
      CORBA::Short result = constants->getShortValue();
      assert(initial == result);
    }

    {
      //  const unsigned short USHORT_CONST = 7;
      CORBA::UShort initial = 7;
      CORBA::UShort result = constants->getUnsignedShortValue();
      assert(initial == result);
    }

    {
      //  const long LONG_CONST = -7777;
      CORBA::Long initial = -7777;
      CORBA::Long result = constants->getLongValue();
      assert(initial == result);
    }

    {
      //  const unsigned long ULONG_CONST = 7777;
      CORBA::ULong initial = 7777;
      CORBA::ULong result = constants->getUnsignedLongValue();
      assert(initial == result);
    }

    {
      //  const char CHAR_CONST = 'c';
      CORBA::Char initial = 'c';
      CORBA::Char result = constants->getCharValue();
      assert(initial == result);
    }

    {
      //  const string STRING_CONST = "1234567890";
      char* initial = CORBA::string_dup("1234567890");
      char* result = constants->getStringValue();
      assert(strcmp(initial,result) == 0);
    }

    {
      //  const float FLOAT_CONST = 3.14;
      CORBA::Float initial = 3.14;
      CORBA::Float result = constants->getFloatValue();
      assert(abs(initial - result) < 0.001);
    }

    {
      //  const double DOUBLE_CONST = 3.1415926*2.0;
      CORBA::Double initial = 3.1415926*2.0;
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

