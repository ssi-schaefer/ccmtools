#include <localComponents/CCM.h>
#include <CCM_Local/HomeFinder.h>
#include <CCM_Utils/Debug.h>
#include <CCM_Utils/SmartPointer.h>

#include <CCM_Local/Hello_user_types.h>  //!!!!!

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
     /* 
      * Test Case for: typedef long time_t;
      */
     CCM_Local::time_t time_value = 3;
     myHello.ptr()->typedef_value(time_value);
     CCM_Local::time_t time_result = myHello.ptr()->typedef_value();
     assert(time_result == time_value);
     
     /*
      * Test Case for: enum Color {red, green, blue, black, orange};
      */
     Color Color_value;
     Color_value = Color(blue);
     myHello.ptr()->enum_value(Color_value);
     Color Color_result = myHello.ptr()->enum_value();
     assert(Color_result == Color_value);
    
     /*
      * Test Case for: struct Value { string s; double dd; };
      */
     CCM_Local::Value struct_value;
     struct_value.s = "a"; 
     struct_value.dd = 1.0;
     myHello.ptr()->struct_value(struct_value);
     CCM_Local::Value struct_result = myHello.ptr()->struct_value();
     assert(struct_result.s == struct_value.s);
     assert(struct_result.dd == struct_value.dd);


     /* 
      * Test Case for: typedef sequence<Value> map;
      */
     CCM_Local::map map_value;
     for(int i=0;i<5;i++) {
       Value v1, v2;
       v1.s = "1";
       v1.dd = (double)i;
       map_value.push_back(v1);
     }
     myHello.ptr()->sequence_value(map_value);
     CCM_Local::map map_result = myHello.ptr()->sequence_value();
     for(int i=0;i<(int)map_result.size();i++) {
       Value v = map_result.at(i);
       assert((int)v.dd == i);
     }

     /*
      *  Test Case for: typedef double doubleArray[10];
      */
     CCM_Local::doubleArray array_value(10);
     for(int i=0;i<10;i++) {
       array_value.at(i) = i;
     } 
     myHello.ptr()->array_value(array_value);
     CCM_Local::doubleArray array_result = myHello.ptr()->array_value();
     for(int i=0;i<10;i++) {
       assert(array_result.at(i) == i);
     }

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
