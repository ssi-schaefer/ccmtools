/***
 * CCM Tools Test Client 
 *
 * This file was automatically generated by the CCM Tools.
 *         <http://ccmtools.sourceforge.net/>
 *
 * This test client is part of the mirror component test concept. For each
 * component a corresponding mirror component will be instantiated. 
 * All component ports will be connected to the mirror component's ports. 
 * Additionally, developers can add some testing code to validate supported
 * interfaces as well as component attribute access.
 *
 * To enable debug output use -DWXDEBUG compiler flag
 * To enable DbC adapter use -DCCM_USE_DBC compiler flag
 ***/

#include <cassert>
#include <iostream>

#include <WX/Utils/debug.h>
#include <WX/Utils/smartptr.h>

#include <LocalComponents/CCM.h>
#include <CCM_Local/HomeFinder.h>

#include <CCM_Local/CCM_Session_Test_mirror/Test_mirror_gen.h>
#include <CCM_Local/CCM_Session_Test_mirror/TestHome_mirror_gen.h>

#ifdef CCM_USE_DBC
#include <CCM_Local/CCM_Session_Test/Test_dbc.h>
#include <CCM_Local/CCM_Session_Test/TestHome_dbc.h>
#else
#include <CCM_Local/CCM_Session_Test/Test_gen.h>
#include <CCM_Local/CCM_Session_Test/TestHome_gen.h>
#endif

using namespace std;
using namespace WX::Utils;
using namespace CCM_Local;
using namespace CCM_Session_Test;
using namespace CCM_Session_Test_mirror;

int main(int argc, char *argv[])
{
  cout << ">>>> Start Test Client: " << __FILE__ << endl;

  SmartPtr<Test> myTest;
  SmartPtr<Test_mirror> myTestMirror;

  // Debug tools:
  // We use debug tools defined in the WX::Utils package.
  Debug::instance().set_global(true);

  // Component bootstrap:
  // We get an instance of the local HomeFinder and register the deployed
  // component- and mirror component home.
  // Here we can also decide to use a Design by Contract component.  	
  int error = 0;
  LocalComponents::HomeFinder* homeFinder;
  homeFinder = HomeFinder::Instance (  );
#ifdef CCM_USE_DBC
  error  = DbC_deploy_TestHome("TestHome", false);
#else
  error  = local_deploy_TestHome("TestHome");
#endif
  error += local_deploy_TestHome_mirror("TestHome_mirror");	
  if(error) {
    cerr << "BOOTSTRAP ERROR: Can't deploy component homes!" << endl;
    return(error);
  }

  // Component deployment:
  // We use the HomeFinder method find_home_by_name() to get a smart pointer 
  // to a component home. From a component home, we get a smart pointer to a 
  // component instance using the create() method.
  // Component and mirror component are connected via provide_facet() and 
  // connect() methods.
  // The last step of deployment is to call configuration_complete() that 
  // forces components to run the ccm_set_session_context() and ccm_activate() 
  // callback methods.
  try {
    SmartPtr<TestHome> myTestHome(dynamic_cast<TestHome*>
      (homeFinder->find_home_by_name("TestHome").ptr()));
    SmartPtr<TestHome_mirror> 
      myTestHomeMirror(dynamic_cast<TestHome_mirror*>
        (homeFinder->find_home_by_name("TestHome_mirror").ptr()));
    myTest = myTestHome->create();
    myTestMirror = myTestHomeMirror->create();

    myTest->configuration_complete();
    myTestMirror->configuration_complete();
  } 
  catch ( LocalComponents::HomeNotFound ) {
    cout << "DEPLOYMENT ERROR: can't find a home!" << endl;
    error = -1;
  } 
  catch ( LocalComponents::NotImplemented& e ) {
    cout << "DEPLOYMENT ERROR: function not implemented: " << e.what (  ) << endl;
    error = -1;
  } 
  catch ( LocalComponents::InvalidName& e ) {
    cout << "DEPLOYMENT ERROR: invalid name during connection: " << e.what (  ) << endl;
    error = -1;
  }
#ifdef CCM_USE_DBC
  catch(CCM_OCL::OclException& e)
  {
    cout << "DEPLOYMENT ERROR: 'design by contract' error:" << endl << e.what();
    error = -1;
  }
#endif
  catch ( ... )  {
    cout << "DEPLOYMENT ERROR: there is something wrong!" << endl;
    error = -1;
  }
  if (error < 0) {
    return error;
  }

  // Component test:
  // After component deployment, we can access components and their facets.
  // Usually, the test cases for facets and receptacles are implemened in the
  // mirror component. But for supported interfaces and component attributes, 
  // we can realize test cases in the following section.
  try {
    cout << "== Begin Test Case =============================================" << endl;
    
    string str1 = "Hallo to first op()";
    long size1 = myTest->op1(str1);
    assert(size1 == str1.length());
    
    string str2 = "Hallo to second op()";
    long size2 = myTest->op2(str2);
    assert(size2 == str2.length());
    
    string str3 = "Hallo to third op()";
    long size3 = myTest->op3(str3);
    assert(size3 == str3.length());

    cout << "== End Test Case ===============================================" << endl;
  } 
  catch ( LocalComponents::NotImplemented& e ) {
    cout << "TEST: function not implemented: " << e.what (  ) << endl;
    error = -1;
  }
#ifdef CCM_USE_DBC
  catch(CCM_OCL::OclException& e)
  {
    cout << "TEST: 'design by contract' error:" << endl << e.what();
    error = -1;
  }
#endif
  catch ( ... )  {
    cout << "TEST: there is something wrong!" << endl;
    error = -1;
  }
  if (error < 0) {
	return error;
  }
  

  // Component tear down:
  // Finally, the component and mirror component instances are disconnected 
  // and removed. Thus component homes can be undeployed.
  try {

    myTest->remove();
    myTestMirror->remove();
  } 
  catch ( LocalComponents::HomeNotFound ) {
    cout << "TEARDOWN ERROR: can't find a home!" << endl;
    error = -1;
  } 
  catch ( LocalComponents::NotImplemented& e ) {
    cout << "TEARDOWN ERROR: function not implemented: " << e.what (  ) << endl;
    error = -1;
  } 
  catch ( ... )  {
    cout << "TEARDOWN ERROR: there is something wrong!" << endl;
    error = -1;
  }
  error += local_undeploy_TestHome("TestHome");
  error += local_undeploy_TestHome_mirror("TestHome_mirror");
  if(error) {
    cerr << "TEARDOWN ERROR: Can't undeploy component homes!" << endl;
    return error;
  }
  cout << ">>>> Stop Test Client: " << __FILE__ << endl;
}
