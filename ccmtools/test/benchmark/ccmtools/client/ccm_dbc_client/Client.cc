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

#include <WX/Utils/Timer.h>
#include <WX/Utils/TimerEvaluation.h>

#include <LocalComponents/CCM.h>
#include <CCM_Local/HomeFinder.h>

#include <CCM_Local/CCM_Session_Test/Test_dbc.h>
#include <CCM_Local/CCM_Session_Test/TestHome_dbc.h>

using namespace std;
using namespace WX::Utils;
using namespace CCM_Local;
using namespace CCM_Session_Test;

int main(int argc, char *argv[])
{
    WX::Utils::Timer globalTimer;
    WX::Utils::TimerEvaluation eval;
    globalTimer.start();

    cout << ">>>> Start Test Client: " << __FILE__ << endl;

    SmartPtr<Test> myTest;
    SmartPtr<Benchmark> bm;

    // Component bootstrap:
    // We get an instance of the local HomeFinder and register the deployed
    // component- and mirror component home.
    // Here we can also decide to use a Design by Contract component.  	
    int error = 0;
    LocalComponents::HomeFinder* homeFinder;
    homeFinder = HomeFinder::Instance();
    error  = deploy_dbc_CCM_Local_TestHome("TestHome", false);

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

        bm = myTest->provide_bm();

        myTest->configuration_complete();
    } 
    catch ( LocalComponents::HomeNotFound ) {
        cout << "DEPLOYMENT ERROR: can't find a home!" << endl;
        error = -1;
    } 
    catch ( LocalComponents::NotImplemented& e ) {
        cout << "DEPLOYMENT ERROR: function not implemented: " 
	     << e.what (  ) << endl;
        error = -1;
    }  
    catch ( LocalComponents::InvalidName& e ) {
        cout << "DEPLOYMENT ERROR: invalid name during connection: " 
             << e.what (  ) << endl;
        error = -1;
    }
    catch(CCM_OCL::OclException& e)
    {
        cout << "DEPLOYMENT ERROR: 'design by contract' error:" 
             << endl << e.what();
        error = -1;
    }
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

      cout << "--- Start Test Case -----------------------------------" << endl;

      // Test configuration
      WX::Utils::Timer timer;
      
      const long MAX_LOOP_COUNT = 100000000;

      const long SEQUENCE_SIZE_MAX = 1000;
      const long SEQUENCE_SIZE_STEP = 100;

      {
        // ping
        cout << "Local CCM Test: void f0() "; 

        timer.start();
        for(long counter=0; counter<MAX_LOOP_COUNT; counter++ ) {
          bm->f0();
        }
        timer.stop();
        cout << eval.getTimerResult(timer,MAX_LOOP_COUNT,1);
      }


      {
        // in long parameter
        cout << "Local CCM Test: void f_in1(in long l1) "; 

        long value = 7;

        timer.start();
        for(long counter=0; counter<MAX_LOOP_COUNT; counter++ ) {
          bm->f_in1(value);
        }
        timer.stop();
        cout << eval.getTimerResult(timer,MAX_LOOP_COUNT,1);
      }


      {
        // in string parameter with increasing size
        for(long size=0; size<=SEQUENCE_SIZE_MAX; size+=SEQUENCE_SIZE_STEP) {
          cout << "Local CCM Test: void f_in2(in string s1) "; 

          string value;
          for(int i=0; i<size; i++)
            value += "X";

          timer.start();
          for(long counter=0; counter<MAX_LOOP_COUNT; counter++ ) {
            bm->f_in2(value);
          }
          timer.stop();
          cout << eval.getTimerResult(timer,MAX_LOOP_COUNT,size);
        }
      }


      {
        // in sequence of long parameter with increasing size
        for(long size=0; size<=SEQUENCE_SIZE_MAX; size+=SEQUENCE_SIZE_STEP) {
          cout << "Local CCM Test: void f_in3(in LongList ll1) "; 

          LongList value;
          for(long i=0; i<size; i++)
            value.push_back(i);

          timer.start();
          for(long counter=0; counter<MAX_LOOP_COUNT; counter++ ) {
            bm->f_in3(value);
          }
          timer.stop();
          cout << eval.getTimerResult(timer,MAX_LOOP_COUNT,size);
        }
      } 

      cout << "--- Stop Test Case ------------------------------------" << endl;
    } 
    catch ( LocalComponents::NotImplemented& e ) {
        cout << "TEST: function not implemented: " << e.what (  ) << endl;
        error = -1;
    }
    catch(CCM_OCL::OclException& e)
    {
        cout << "TEST: 'design by contract' error:" << endl << e.what();
        error = -1;
    }
    catch(...) {
        cout << "TEST: there is something wrong!" << endl;
        error = -1;
    }
    if(error < 0) {
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
        cout << "TEARDOWN ERROR: function not implemented: " 
	     << e.what (  ) << endl;
        error = -1;
    } 
    catch(...) {
        cout << "TEARDOWN ERROR: there is something wrong!" << endl;
        error = -1;
    }
    error += undeploy_CCM_Local_TestHome("TestHome");
    if(error) {
        cerr << "TEARDOWN ERROR: Can't undeploy component homes!" << endl;
        return error;
    }
    cout << ">>>> Stop Test Client: " << __FILE__ << endl;
    globalTimer.stop();
    cout << eval.getTimerResult(globalTimer,1,1);
}
