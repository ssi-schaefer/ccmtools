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

     string s = "Salomon.Automation";
     long len =  myHello.ptr()->println(s);
     assert(len == s.length());

     try {
       string s = "error";
       myHello.ptr()->println(s);
       assert(0);
     }
     catch(CCM_Local::error& e) {
       cout << "OK: error exception catched!" << endl;
     }

     try {
       string s = "super_error";
       myHello.ptr()->println(s);
       assert(0);
     }
     catch(CCM_Local::super_error& e) {
       cout << "OK: super_error exception catched!" << endl;
     }
     
     try {
       string s = "fatal_error";
       myHello.ptr()->println(s);
       assert(0);
     }
     catch(CCM_Local::fatal_error& e) {
       cout << "OK: fatal_error exception catched!" << endl;
     }

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
