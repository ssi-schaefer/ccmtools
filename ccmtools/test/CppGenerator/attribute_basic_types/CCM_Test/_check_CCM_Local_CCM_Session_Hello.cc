#include <cassert>
#include <localComponents/CCM.h>
#include <CCM_Local/HomeFinder.h>
#include <CCM_Utils/Debug.h>
#include <CCM_Utils/SmartPointer.h>

#ifdef CCM_TEST_PYTHON
#include <Python.h>
#endif

#include <CCM_Local/CCM_Session_Hello_mirror/Hello_mirror_gen.h>
#include <CCM_Local/CCM_Session_Hello_mirror/HelloHome_mirror_gen.h>
#include <CCM_Local/CCM_Session_Hello/Hello_gen.h>
#include <CCM_Local/CCM_Session_Hello/HelloHome_gen.h>

using namespace std;
using namespace CCM_Utils;
using namespace CCM_Local;
using namespace CCM_Session_Hello;
using namespace CCM_Session_Hello_mirror;


//==============================================================================
// implementation of local client test
//==============================================================================

int main ( int argc, char *argv[] )
{
  int result = 0;
  Debug::set_global ( true );

  DEBUGNL ( "test_client_Hello_component_main (  )" );

  // Get in instance of the local HomeFinder and register component homes
  localComponents::HomeFinder* homeFinder = HomeFinder::Instance();
  try {
    homeFinder->register_home( create_HelloHomeAdapter(), "HelloHome" );
    homeFinder->register_home( create_HelloHome_mirrorAdapter(), "HelloHome_mirror" );
  } catch ( ... )  {
    cout << "Aut'sch: there is something wrong while register homes!" << endl;
    return -1;
  }

#ifdef CCM_TEST_PYTHON
  Py_Initialize();
#endif

  try {
    /*
     * SET UP / DEPLOYMENT
     */

    // Find component and mirror component homes
    SmartPtr<HelloHome> myHelloHome ( dynamic_cast<HelloHome*>
      ( homeFinder->find_home_by_name ( "HelloHome" ).ptr (  ) ) );

    SmartPtr<HelloHome_mirror> myHelloHomeMirror ( dynamic_cast<HelloHome_mirror*>
      ( homeFinder->find_home_by_name ( "HelloHome_mirror" ).ptr (  ) ) );

    // Create component and mirror component instances
    SmartPtr<Hello> myHello = 
      myHelloHome.ptr (  )->create (  );
    SmartPtr<Hello_mirror> myHelloMirror = 
      myHelloHomeMirror.ptr()->create();

    // Create provided and used (mirror) facets



    // Connect components



    // End of deployment phase
    myHello.ptr()->configuration_complete();
    myHelloMirror.ptr()->configuration_complete();

    // Use Hello component standard functionality
    cout << "> getComponentVersion() = " 
         << myHello.ptr()->getComponentVersion () << endl;
    cout << "> getComponentDate() = " 
         << myHello.ptr()->getComponentDate() << endl;

    /*
     * TESTING
     */
	
     DEBUGNL("==== Begin Test Case =============================================" );	

    // TODO : IMPLEMENT ME HERE !	
     short short_value = -7;
     myHello.ptr()->short_value(short_value);
     short short_result = myHello.ptr()->short_value();
     assert(short_result == short_value);

     long long_value = -7777;
     myHello.ptr()->long_value(long_value);
     long long_result = myHello.ptr()->long_value();
     assert(long_result == long_value);

     unsigned short ushort_value = 7;
     myHello.ptr()->ushort_value(ushort_value);
     unsigned short ushort_result = myHello.ptr()->ushort_value();
     assert(ushort_result == ushort_value);

     unsigned long ulong_value = 7777;
     myHello.ptr()->ulong_value(ulong_value);
     unsigned long ulong_result = myHello.ptr()->ulong_value();
     assert(ulong_result == ulong_value);

     float float_value = -77.77;
     myHello.ptr()->float_value(float_value);
     float float_result = myHello.ptr()->float_value();
     assert(float_result == float_value);

     double double_value = -77.7777;
     myHello.ptr()->double_value(double_value);
     double double_result = myHello.ptr()->double_value();
     assert(double_result == double_value);

     char char_value = 'x';
     myHello.ptr()->char_value(char_value);
     char char_result = myHello.ptr()->char_value();
     assert(char_result == char_value);

     string string_value = "0123456789";
     myHello.ptr()->string_value(string_value);
     string string_result = myHello.ptr()->string_value();
     assert(string_result == string_value);

     bool boolean_value = true;
     myHello.ptr()->boolean_value(boolean_value);
     bool boolean_result = myHello.ptr()->boolean_value();
     assert(boolean_result == boolean_value);

     unsigned char octet_value = 0xff;
     myHello.ptr()->octet_value(octet_value);
     unsigned char octet_result = myHello.ptr()->octet_value();
     assert(octet_result == octet_value);

     DEBUGNL("==== End Test Case ===============================================" );	


    /*
     * TEAR DOWN
     */

    // Disconnect components



    // Destroy component instances
    myHello.ptr (  )->remove (  );
    myHelloMirror.ptr()->remove();

    // Unregister component homes
    homeFinder->unregister_home ( "HelloHome" );
    homeFinder->unregister_home ( "HelloHome_mirror" );

    DEBUGNL ( "exit test_client_Hello_component_main (  )" );
  } catch ( localComponents::HomeNotFound ) {
    cout << "Aut'sch: can't find a home!" << endl;
    result = -1;
  } catch ( localComponents::NotImplemented& e ) {
    cout << "Aut'sch: " << e.what (  ) << endl;
    result = -1;
  } catch ( ... )  {
    cout << "Aut'sch: there is something wrong!" << endl;
    result = -1;
  }

#ifdef CCM_TEST_PYTHON
  Py_Finalize();
#endif

  return result;
}
