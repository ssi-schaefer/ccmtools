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
#include <string>
#include <iostream>

#include <WX/Utils/debug.h>
#include <WX/Utils/smartptr.h>

#include <LocalComponents/CCM.h>
#include <CCM_Local/HomeFinder.h>

#include <CCM_Local/CCM_Session_Test_mirror/Test_mirror_gen.h>

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

int main(int argc, char *argv[])
{
  cout << ">>>> Start Test Client: " << __FILE__ << endl;

  SmartPtr<Test> myTest;

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

    myTest = myTestHome->create();

    myTest->configuration_complete();
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

    {
      short short_value = -7;
      myTest->short_value(short_value);
      short short_result = myTest->short_value();
      assert(short_result == short_value);
      
      long long_value = -7777;
      myTest->long_value(long_value);
      long long_result = myTest->long_value();
      assert(long_result == long_value);
      
      unsigned short ushort_value = 7;
      myTest->ushort_value(ushort_value);
      unsigned short ushort_result = myTest->ushort_value();
      assert(ushort_result == ushort_value);
      
      unsigned long ulong_value = 7777;
      myTest->ulong_value(ulong_value);
      unsigned long ulong_result = myTest->ulong_value();
      assert(ulong_result == ulong_value);
      
      float float_value = -77.77;
      myTest->float_value(float_value);
      float float_result = myTest->float_value();
      assert(float_result == float_value);
      
      double double_value = -77.7777;
      myTest->double_value(double_value);
      double double_result = myTest->double_value();
      assert(double_result == double_value);
      
      char char_value = 'x';
      myTest->char_value(char_value);
      char char_result = myTest->char_value();
      assert(char_result == char_value);
      
      string string_value = "0123456789";
      myTest->string_value(string_value);
      string string_result = myTest->string_value();
      assert(string_result == string_value);
      
      bool boolean_value = true;
      myTest->boolean_value(boolean_value);
      bool boolean_result = myTest->boolean_value();
      assert(boolean_result == boolean_value);

      unsigned char octet_value = 0xff;
      myTest->octet_value(octet_value);
      unsigned char octet_result = myTest->octet_value();
      assert(octet_result == octet_value);

      wchar_t wchar_value = 'x';
      myTest->wchar_value(wchar_value);
      wchar_t wchar_result = myTest->char_value();
      assert(wchar_result == wchar_value);
      
      wstring wstring_value = L"0123456789";
      myTest->wstring_value(wstring_value);
      wstring wstring_result = myTest->wstring_value();
      assert(wstring_result == wstring_value);
    }

    {
      //Test Case for: typedef long time_t;
      CCM_Local::time_t time_value = 3;
      myTest->typedef_value(time_value);
      CCM_Local::time_t time_result = myTest->typedef_value();
      assert(time_result == time_value);
     

      // Test Case for: enum Color {red, green, blue, black, orange};
      Color Color_value;
      Color_value = Color(blue);
      myTest->enum_value(Color_value);
      Color Color_result = myTest->enum_value();
      assert(Color_result == Color_value);
      

      // Test Case for: struct Pair { string key; double value; };
      Pair struct_value;
      struct_value.key = "a"; 
      struct_value.value = 1.0;
      myTest->struct_value(struct_value);
      Pair struct_result = myTest->struct_value();
      assert(struct_result.key == struct_value.key);
      assert(struct_result.value == struct_value.value);

      // Test Case for: typedef sequence<Value> map;
      Map map_value;
      for(int i=0;i<5;i++) {
	Pair p1;
	p1.key = "1";
	p1.value = (double)i;
	map_value.push_back(p1);
      }
      myTest->sequence_value(map_value);
      Map map_result = myTest->sequence_value();
      for(int i=0;i<(int)map_result.size();i++) {
	Pair p = map_result.at(i);
	assert((int)p.value == i);
      }

      // Test Case for: typedef double doubleArray[10];
      CCM_Local::doubleArray array_value(10);
      for(int i=0;i<10;i++) {
	array_value.at(i) = i;
      } 
      myTest->array_value(array_value);
      CCM_Local::doubleArray array_result = myTest->array_value();
      for(int i=0;i<10;i++) {
	assert(array_result.at(i) == i);
      }

    }

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
  if(error) {
    cerr << "TEARDOWN ERROR: Can't undeploy component homes!" << endl;
    return error;
  }
  cout << ">>>> Stop Test Client: " << __FILE__ << endl;
}
