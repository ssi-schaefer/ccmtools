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
     short short_2=3, short_3, short_r;
     short_r = myHello.ptr()->println1(7,short_2, short_3);
     assert(short_2 == 7);
     assert(short_3 == 3);
     assert(short_r == 3+7);

     long long_2=3, long_3, long_r;
     long_r = myHello.ptr()->println2(7,long_2, long_3);
     assert(long_2 == 7);
     assert(long_3 == 3);
     assert(long_r == 3+7);

     unsigned short ushort_2=3, ushort_3, ushort_r;
     ushort_r = myHello.ptr()->println3(7,ushort_2, ushort_3);
     assert(ushort_2 == 7);
     assert(ushort_3 == 3);
     assert(ushort_r == 3+7);

     unsigned long ulong_2=3, ulong_3, ulong_r;
     ulong_r = myHello.ptr()->println4(7,ulong_2, ulong_3);
     assert(ulong_2 == 7);
     assert(ulong_3 == 3);
     assert(ulong_r == 3+7);

     float float_2=3.0, float_3, float_r;
     float_r = myHello.ptr()->println5(7.0,float_2, float_3);
     assert(float_2 == 7.0);
     assert(float_3 == 3.0);
     assert(float_r == 3.0+7.0);
     
     double double_2=3.0, double_3, double_r;
     double_r = myHello.ptr()->println6(7.0,double_2, double_3);
     assert(double_2 == 7.0);
     assert(double_3 == 3.0);
     assert(double_r == 3.0+7.0);

     char char_2=3, char_3, char_r;
     char_r = myHello.ptr()->println7(7,char_2, char_3);
     assert(char_2 == 7);
     assert(char_3 == 3);
     assert(char_r == 3+7);

     string string_2="drei", string_3, string_r;
     string_r = myHello.ptr()->println8("sieben",string_2, string_3);
     cout << string_2 << endl;
     cout << string_3 << endl;
     cout << string_r << endl;
     assert(string_2 == "sieben");
     assert(string_3 == "drei");
     assert(string_r == "dreisieben");

     bool bool_2=false, bool_3, bool_r;
     bool_r = myHello.ptr()->println9(true, bool_2, bool_3);
     assert(bool_2 == true);
     assert(bool_3 == false);
     assert(bool_r == false && true);

     unsigned char uchar_2=3, uchar_3, uchar_r;
     uchar_r = myHello.ptr()->println10(7,uchar_2, uchar_3);
     assert(uchar_2 == 7);
     assert(uchar_3 == 3);
     assert(uchar_r == 3+7);
	
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
