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
    CCM_Local::time_t typedef_value = 3;
    SmartPtr<Hello> myHello1 = myHelloHome.ptr()->create_with_typedef(typedef_value);

    CCM_Local::Color enum_value;
    enum_value = Color(blue);
    SmartPtr<Hello> myHello2 = myHelloHome.ptr()->create_with_enum(enum_value);

    CCM_Local::Value struct_value;
    struct_value.s = "a"; 
    struct_value.dd = 1.0;
    SmartPtr<Hello> myHello3 = myHelloHome.ptr()->create_with_struct(struct_value);

    CCM_Local::map sequence_value;
    for(int i=0;i<5;i++) {
      Value v1, v2;
      v1.s = "1";
      v1.dd = (double)i;
      sequence_value.push_back(v1);
    }
    SmartPtr<Hello> myHello4 = myHelloHome.ptr()->create_with_sequence(sequence_value);

    CCM_Local::doubleArray array_value(10);
    for(int i=0;i<10;i++) {
      array_value.at(i) = i;
    }
    SmartPtr<Hello> myHello5 = myHelloHome.ptr()->create_with_array(array_value);


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
     CCM_Local::time_t typedef_result = myHello1.ptr()->typedef_value();
     assert(typedef_result == typedef_value);

     CCM_Local::Color enum_result = myHello2.ptr()->enum_value();
     assert(enum_result == enum_value);

     CCM_Local::Value struct_result = myHello3.ptr()->struct_value();
     assert(struct_result.s == struct_value.s);
     assert(struct_result.dd == struct_value.dd);

     CCM_Local::map sequence_result =  myHello4.ptr()->sequence_value();
     for(int i=0;i<(int)sequence_result.size();i++) {
       Value v = sequence_result.at(i);
       assert(v.dd == i);
     }

     CCM_Local::doubleArray array_result(10);
     array_result = myHello5.ptr()->array_value();
     for(int i=0;i<10;i++) {
       assert(array_result.at(i) == array_value.at(i));
     }


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
