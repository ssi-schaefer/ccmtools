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
     
     /* 
      * Test Case for: typedef long time_t;
      */
     CCM_Local::time_t time_t_2 = 3, time_t_3, time_t_r;
     time_t_r = myHello.ptr()->foo1(7,time_t_2, time_t_3);
     assert(time_t_2 == 7);
     assert(time_t_3 == 3);
     assert(time_t_r == 3+7);
     
     /*
      * Test Case for: enum Color {red, green, blue, black, orange};
      */
     Color Color_2,Color_3, Color_r;
     Color_2 = Color(blue);
     Color_r = myHello.ptr()->foo2(Color(red),Color_2, Color_3);
     assert(Color_2 == Color(red));
     assert(Color_3 == Color(blue));
     assert(Color_r == Color(red));
    
     /*
      * Test Case for: struct Value { string s; double dd; };
      */
     Value Value_1, Value_2, Value_3, Value_r;
     Value_1.s = "a"; Value_1.dd = 1.0;
     Value_2.s = "b"; Value_2.dd = 2.0;
     Value_r = myHello.ptr()->foo3(Value_1,Value_2,Value_3);
     assert(Value_3.s == "b");
     assert(Value_2.s == "a");
     assert(Value_r.s == "ab");

     /* 
      * Test Case for: typedef sequence<Value> map;
      */
     CCM_Local::map map_1, map_2, map_3, map_r;
     for(int i=0;i<5;i++) {
       Value v1, v2;
       v1.s = "1";
       v1.dd = (double)i;
       map_1.push_back(v1);
       v2.s = "2";
       v2.dd = (double)(i+i);
       map_2.push_back(v2);
     }
     map_r = myHello.ptr()->foo4(map_1,map_2,map_3);
     for(int i=0;i<(int)map_r.size();i++) {
       Value v = map_r.at(i);
       assert((int)v.dd == i);
     }
     for(int i=0;i<(int)map_2.size();i++) {
       Value v = map_2.at(i);
       assert((int)v.dd == i);
     }
     for(int i=0;i<(int)map_3.size();i++) {
       Value v = map_3.at(i);
       assert((int)v.dd == i+i);
     }

     /*
      *  Test Case for: typedef double doubleArray[10];
      */
     doubleArray Array_1(10), Array_2(10), Array_3(10), Array_r(10);
     for(int i=0;i<10;i++) {
       Array_1.at(i) = i;
       Array_2.at(i) = i+i;
     }
     Array_r = myHello.ptr()->foo5(Array_1,Array_2,Array_3);
     for(int i=0;i<10;i++) {
       assert(Array_r.at(i) == i);
       assert(Array_2.at(i) == i);
       assert(Array_3.at(i) == i+i);
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
