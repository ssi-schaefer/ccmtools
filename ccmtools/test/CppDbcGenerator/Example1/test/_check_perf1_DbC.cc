/*
    $Id$
*/

#include <cassert>
#include <iostream>

#include <LocalComponents/CCM.h>
#include <CCM_Local/HomeFinder.h>
#include <WX/Utils/debug.h>
#include <WX/Utils/smartptr.h>
#include <CCM_OCL/OclException.h>


#include <CCM_Local/CCM_Session_Math_mirror/Math_mirror_gen.h>
#include <CCM_Local/CCM_Session_Math_mirror/MathHome_mirror_gen.h>
#include <CCM_Local/CCM_Session_Math/Math_dbc.h>
#include <CCM_Local/CCM_Session_Math/MathHome_dbc.h>

using namespace std;
using namespace WX::Utils;
using namespace CCM_Local;
using namespace CCM_Session_Math;
using namespace CCM_Session_Math_mirror;


//==============================================================================
// implementation of local client test
//==============================================================================

int main ( int argc, char *argv[] )
{
  int result = 0;
  int error = 0;

  LocalComponents::HomeFinder* homeFinder;

  SmartPtr<Math> myMath;
  SmartPtr<Math_mirror> myMathMirror;

  SmartPtr<LocalComponents::Object> Math_provides_stack;
  LocalComponents::Cookie Math_ck_stack;

  Debug::instance().set_global(true);

  DEBUGNL ( "test_client_Math_component_main (  )" );

  // get an instance of the local HomeFinder and register component homes

  homeFinder = HomeFinder::Instance (  );
  error  = DbC_deploy_MathHome("MathHome",false);
  error += local_deploy_MathHome_mirror("MathHome_mirror");	
  if(error) {
    cerr << "ERROR: Can't deploy component homes!" << endl;
    assert(0);
  }

  /* SET UP / DEPLOYMENT */

  try {
    // find component/mirror homes, instantiate components

    SmartPtr<MathHome> myMathHome ( dynamic_cast<MathHome*>
      ( homeFinder->find_home_by_name ( "MathHome" ).ptr (  ) ) );
    SmartPtr<MathHome_mirror> myMathHomeMirror ( dynamic_cast<MathHome_mirror*>
      ( homeFinder->find_home_by_name ( "MathHome_mirror" ).ptr (  ) ) );

    myMath = myMathHome.ptr (  )->create (  );
    myMathMirror = myMathHomeMirror.ptr (  )->create (  );

    // create facets, connect components

    Math_provides_stack =
      myMath.ptr (  )->provide_facet ( "stack" );

    Math_ck_stack = myMathMirror.ptr (  )->connect
      ( "stack_mirror", Math_provides_stack );

    myMath.ptr (  )->configuration_complete (  );
    myMathMirror.ptr (  )->configuration_complete (  );
  } catch ( LocalComponents::HomeNotFound ) {
    cout << "DEPLOY: can't find a home!" << endl;
    result = -1;
  } catch ( LocalComponents::NotImplemented& e ) {
    cout << "DEPLOY: function not implemented: " << e.what (  ) << endl;
    result = -1;
  } catch ( LocalComponents::InvalidName& e ) {
    cout << "DEPLOY: invalid name during connection: " << e.what (  ) << endl;
    result = -1;
  } catch ( ... )  {
    cout << "DEPLOY: there is something wrong!" << endl;
    result = -1;
  }

  if (result < 0) return result;

  /* TESTING */

  try {
    DEBUGNL("==== Begin Test Case =============================================" );

    cout << endl << "performance test with DbC" << endl << endl;
    try {
        SmartPtr<IntegerStack> theStack = myMath->provide_stack();
        if( !theStack->isEmpty() )
        {
            cout << "ERROR: stack must be empty" << endl;
            result = -1;
        }
        int index;
        for( index=0; index<100; index++ )
        {
            theStack->push(index);
        }
        if( theStack->isEmpty() )
        {
            cout << "ERROR: stack can not be empty" << endl;
            result = -1;
        }
        long dummy;
        for( index=0; index<100; index++ )
        {
            dummy = theStack->pop();
        }
        if( !theStack->isEmpty() )
        {
            cout << "ERROR: stack must be empty" << endl;
            result = -1;
        }
    }
     catch(CCM_OCL::OclException& e)
     {
        cout << e.what();
        result = -1;
     }

    DEBUGNL("==== End Test Case ===============================================" );
  } catch ( LocalComponents::NotImplemented& e ) {
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

    myMathMirror.ptr (  )->disconnect ( "stack_mirror", Math_ck_stack );



    myMath.ptr (  )->remove (  );
    myMathMirror.ptr (  )->remove (  );

  } catch ( LocalComponents::HomeNotFound ) {
    cout << "TEARDOWN: can't find a home!" << endl;
    result = -1;
  } catch ( LocalComponents::NotImplemented& e ) {
    cout << "TEARDOWN: function not implemented: " << e.what (  ) << endl;
    result = -1;
  } catch ( ... )  {
    cout << "TEARDOWN: there is something wrong!" << endl;
    result = -1;
  }

  error =  local_undeploy_MathHome("MathHome");
  error += local_undeploy_MathHome_mirror("MathHome_mirror");
  if(error) {
    cerr << "ERROR: Can't undeploy component homes!" << endl;
    assert(0);
  }

#ifdef CCM_TEST_PYTHON
  Py_Finalize();
#endif

  DEBUGNL ( "exit test_client_Math_component_main (  )" );

  return result;
}
