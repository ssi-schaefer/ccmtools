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
// implementation of ACommon interface
//==============================================================================

class ACommon_impl 
  : virtual public CCM_Local::ACommon
{
public:
  ACommon_impl() 
  { 
    a_ = 0;
  }
  
  virtual ~ACommon_impl() 
  {}
  
  virtual void set_attrib_a ( const long a )
  {
    a_ = a;
  }

  virtual long get_attrib_a ()
  {
    return a_;
  }
  
private:
  long a_;
};



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

     SmartPtr<ACommon> ac(new ACommon_impl());
     ac.ptr()->set_attrib_a(7);

     myHello.ptr()->the_a(ac);

     SmartPtr<ACommon> result = myHello.ptr()->the_a();
     cout << "result->get_attrib_a() = " << result.ptr()->get_attrib_a() << endl;
	
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
