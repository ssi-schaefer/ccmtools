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

  localComponents::HomeFinder* homeFinder;

  SmartPtr<Hello> myHello;
  SmartPtr<Hello_mirror> myHelloMirror;
  SmartPtr<CCM_Local::CCM_Console> console;


  Debug::set_global ( true );

  DEBUGNL ( "test_client_Hello_component_main (  )" );

  // get an instance of the local HomeFinder and register component homes

  homeFinder = HomeFinder::Instance (  );

  try {
    homeFinder->register_home( create_HelloHomeAdapter (  ), "HelloHome" );
    homeFinder->register_home( create_HelloHome_mirrorAdapter (  ), "HelloHome_mirror" );
  } catch ( ... )  {
    cout << "REGISTER: there is something wrong!" << endl;
    return -1;
  }

#ifdef CCM_TEST_PYTHON
  Py_Initialize();
#endif

  /* SET UP / DEPLOYMENT */

  try {
    // find component/mirror homes, instantiate components

    SmartPtr<HelloHome> myHelloHome ( dynamic_cast<HelloHome*>
      ( homeFinder->find_home_by_name ( "HelloHome" ).ptr (  ) ) );
    SmartPtr<HelloHome_mirror> myHelloHomeMirror ( dynamic_cast<HelloHome_mirror*>
      ( homeFinder->find_home_by_name ( "HelloHome_mirror" ).ptr (  ) ) );

    myHello = myHelloHome.ptr (  )->create (  );
    myHelloMirror = myHelloHomeMirror.ptr (  )->create (  );

    // create facets, connect components

    console = myHello.ptr()->provide_console();
    myHelloMirror.ptr (  )->connect_console_mirror(console);


    myHello.ptr (  )->configuration_complete (  );
    myHelloMirror.ptr (  )->configuration_complete (  );
  } catch ( localComponents::HomeNotFound ) {
    cout << "DEPLOY: can't find a home!" << endl;
    result = -1;
  } catch ( localComponents::NotImplemented& e ) {
    cout << "DEPLOY: function not implemented: " << e.what (  ) << endl;
    result = -1;
  } catch ( localComponents::InvalidName& e ) {
    cout << "DEPLOY: invalid name during connection: " << e.what (  ) << endl;
    result = -1;
  } catch ( ... )  {
    cout << "DEPLOY: there is something wrong!" << endl;
    result = -1;
  }

  if (result < 0) return result;

  /* TESTING */

  try {
    // check basic functionality

    cout << "> getComponentVersion (  ) = "
         << myHello.ptr (  )->getComponentVersion (  ) << endl;
    cout << "> getComponentDate (  ) = "
         << myHello.ptr (  )->getComponentDate (  ) << endl;

    DEBUGNL("==== Begin Test Case =============================================" );

    /*
     * Test Case for: struct Value { long id; string name; };
     */
    Person Value_1, Value_2, Value_3, Value_r;
    Value_1.name = "egon";   Value_1.id = 3;
    Value_2.name = "andrea"; Value_2.id = 23;
    Value_r = console.ptr()->foo3(Value_1,Value_2,Value_3);
    assert(Value_3.name == "andrea");
    assert(Value_2.name == "egon");
    assert(Value_r.name == "egonandrea");


    /* 
     * Test Case for: typedef sequence<Value> map;
     */
    PersonMap map_1, map_2, map_3, map_r;
    for(int i=0;i<5;i++) {
      Person v1, v2;
      v1.name = "1";
      v1.id = i;
      map_1.push_back(v1);
      v2.name = "2";
      v2.id = i+i;
      map_2.push_back(v2);
    }

    map_r = console.ptr()->foo4(map_1,map_2,map_3);

    for(int i=0;i<map_r.size();i++) {
      Person v = map_r.at(i);
      assert(v.id == i);
    }
    for(int i=0;i<map_2.size();i++) {
      Person v = map_2.at(i);
      assert(v.id == i);
    }
    for(int i=0;i<map_3.size();i++) {
      Person v = map_3.at(i);
      assert(v.id == i+i);
    }
    
    /*
     * Test Case for: enum Color {red, green, blue, black, orange};
     */
    Color Color_2,Color_3, Color_r;
    Color_2 = Color(blue);
    Color_r = console.ptr()->foo5(Color(red),Color_2, Color_3);
    assert(Color_2 == Color(red));
    assert(Color_3 == Color(blue));
    assert(Color_r == Color(red));
    

    DEBUGNL("==== End Test Case ===============================================" );
  } catch ( localComponents::NotImplemented& e ) {
    cout << "TEST: function not implemented: " << e.what (  ) << endl;
    result = -1;
  } catch ( ... )  {
    cout << "TEST: there is something wrong!" << endl;
    result = -1;
  }

  if (result < 0) return result;

  /* TEAR DOWN */

  try {
    // disconnect components, destroy instances, unregister homes

    myHelloMirror.ptr (  )->disconnect_console_mirror();

    myHello.ptr (  )->remove (  );
    myHelloMirror.ptr (  )->remove (  );

    homeFinder->unregister_home ( "HelloHome" );
    homeFinder->unregister_home ( "HelloHome_mirror" );
  } catch ( localComponents::HomeNotFound ) {
    cout << "TEARDOWN: can't find a home!" << endl;
    result = -1;
  } catch ( localComponents::NotImplemented& e ) {
    cout << "TEARDOWN: function not implemented: " << e.what (  ) << endl;
    result = -1;
  } catch ( ... )  {
    cout << "TEARDOWN: there is something wrong!" << endl;
    result = -1;
  }

#ifdef CCM_TEST_PYTHON
  Py_Finalize();
#endif

  DEBUGNL ( "exit test_client_Hello_component_main (  )" );

  return result;
}
