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
    short short_value = -7;
    SmartPtr<Hello> myHello1 = myHelloHome.ptr()->create_with_short(short_value);
    long long_value = -7777;
    SmartPtr<Hello> myHello2 = myHelloHome.ptr()->create_with_long(long_value);
    unsigned short ushort_value = 7;
    SmartPtr<Hello> myHello3 = myHelloHome.ptr()->create_with_ushort(ushort_value);
    unsigned long ulong_value = 7777;
    SmartPtr<Hello> myHello4 = myHelloHome.ptr()->create_with_ulong(ulong_value);
    float float_value = 0.777;
    SmartPtr<Hello> myHello5 = myHelloHome.ptr()->create_with_float(float_value);
    double double_value = 7.77777;
    SmartPtr<Hello> myHello6 = myHelloHome.ptr()->create_with_double(double_value);
    char char_value = 'z';
    SmartPtr<Hello> myHello7 = myHelloHome.ptr()->create_with_char(char_value);
    string string_value = "1234567890";
    SmartPtr<Hello> myHello8 = myHelloHome.ptr()->create_with_string(string_value);
    bool boolean_value = true;
    SmartPtr<Hello> myHello9 = myHelloHome.ptr()->create_with_boolean(boolean_value);
    unsigned char octet_value = 0xff;
    SmartPtr<Hello> myHello10 = myHelloHome.ptr()->create_with_octet(octet_value);

    SmartPtr<Hello_mirror> myHelloMirror = 
      myHelloHomeMirror.ptr()->create();

    // Create provided and used (mirror) facets



    // Connect components



    // End of deployment phase
    myHello1.ptr()->configuration_complete();
    myHello2.ptr()->configuration_complete();
    myHello3.ptr()->configuration_complete();
    myHello4.ptr()->configuration_complete();
    myHello5.ptr()->configuration_complete();
    myHello6.ptr()->configuration_complete();
    myHello7.ptr()->configuration_complete();
    myHello8.ptr()->configuration_complete();
    myHello9.ptr()->configuration_complete();
    myHello10.ptr()->configuration_complete();

    myHelloMirror.ptr()->configuration_complete();

    // Use Hello component standard functionality
    cout << "> getComponentVersion() = " 
         << myHello1.ptr()->getComponentVersion () << endl;
    cout << "> getComponentDate() = " 
         << myHello1.ptr()->getComponentDate() << endl;

    /*
     * TESTING
     */
	
     DEBUGNL("==== Begin Test Case =============================================" );	

     // TODO : IMPLEMENT ME HERE !	
     short short_result = myHello1.ptr()->short_value();
     assert(short_result == short_value);

     long long_result = myHello2.ptr()->long_value();
     assert(long_result == long_value);

     unsigned short ushort_result = myHello3.ptr()->ushort_value();
     assert(ushort_result == ushort_value);

     unsigned long ulong_result = myHello4.ptr()->ulong_value();
     assert(ulong_result == ulong_value);

     float float_result = myHello5.ptr()->float_value();
     assert(float_result == float_value);

     double double_result = myHello6.ptr()->double_value();
     assert(double_result == double_value);

     char char_result = myHello7.ptr()->char_value();
     assert(char_result == char_value);

     string string_result = myHello8.ptr()->string_value();
     assert(string_result == string_value);

     bool boolean_result =  myHello9.ptr()->boolean_value();
     assert(boolean_result == boolean_value);

     unsigned char octet_result = myHello10.ptr()->octet_value();
     assert(octet_result == octet_value);

     DEBUGNL("==== End Test Case ===============================================" );	


    /*
     * TEAR DOWN
     */

    // Disconnect components



    // Destroy component instances
    myHello1.ptr (  )->remove (  );
    myHello2.ptr (  )->remove (  );
    myHello3.ptr (  )->remove (  );
    myHello4.ptr (  )->remove (  );
    myHello5.ptr (  )->remove (  );
    myHello6.ptr (  )->remove (  );
    myHello7.ptr (  )->remove (  );
    myHello8.ptr (  )->remove (  );
    myHello9.ptr (  )->remove (  );
    myHello10.ptr (  )->remove (  );

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
