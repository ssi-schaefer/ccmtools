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

#include <ccm/utils/Timer.h>
#include <ccm/utils/TimerEvaluation.h>

#include <ccm/local/Components/CCM.h>
#include <ccm/local/HomeFinder.h>

#include <benchmark/ccm/local/component/Test/Test_bm_impl.h>
#include <benchmark/ccm/local/component/Test/Test_gen.h>
#include <benchmark/ccm/local/component/Test/TestHome_gen.h>

using namespace std;
using namespace WX::Utils;
using namespace benchmark::ccm::local;


int main(int argc, char *argv[])
{
    ccm::utils::Timer globalTimer;
    ccm::utils::TimerEvaluation eval;
    globalTimer.start();

    cout << ">>>> Start Test Client: " << __FILE__ << endl;

    SmartPtr<component::Test::Test> myTest;
    SmartPtr<BenchmarkInterface> bm;

    // Component bootstrap:
    // We get an instance of the local HomeFinder and register the deployed
    // component- and mirror component home.
    // Here we can also decide to use a Design by Contract component.  	
    int error = 0;
    ccm::local::Components::HomeFinder* homeFinder;
    homeFinder = ccm::local::HomeFinder::Instance();
    error  = deploy_benchmark_ccm_local_component_Test_TestHome("TestHome");
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
        SmartPtr<component::Test::TestHome> myTestHome(
	     dynamic_cast<component::Test::TestHome*>
            (homeFinder->find_home_by_name("TestHome").ptr()));

        myTest = myTestHome->create();
        bm = myTest->provide_bm();
        myTest->configuration_complete();
    } 
    catch ( ccm::local::Components::HomeNotFound ) {
        cout << "DEPLOYMENT ERROR: can't find a home!" << endl;
        error = -1;
    } 
    catch (  ccm::local::Components::NotImplemented& e ) {
        cout << "DEPLOYMENT ERROR: function not implemented: " 
	     << e.what (  ) << endl;
        error = -1;
    }  
    catch (  ccm::local::Components::InvalidName& e ) {
        cout << "DEPLOYMENT ERROR: invalid name during connection: " 
             << e.what (  ) << endl;
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
      ccm::utils::Timer timer;

      //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
      // Here you can change some benchmark settings:
      const long MAX_LOOP_COUNT = 10000;
      const long SEQUENCE_SIZE_MAX = 1000;
      const long SEQUENCE_SIZE_STEP = 100;
      //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

      //----------------------------------------------------------
      // ping test case
      //----------------------------------------------------------

      {
	// ping
	cout << endl;
	cout << "Local CCM Test: void f0() "; 

	timer.start();
	for(long counter=0; counter<MAX_LOOP_COUNT; counter++ ) {
	  bm->f0();
	}
	timer.stop();
	cout << eval.getTimerResult(timer,MAX_LOOP_COUNT,1);
      }


      //----------------------------------------------------------
      // in parameter test cases 
      //----------------------------------------------------------

      {
	// in long parameter
	cout << endl;
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
	cout << endl;
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
	cout << endl;
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


      //----------------------------------------------------------
      // inout parameter test cases 
      //----------------------------------------------------------

      {
	// in long parameter
	cout << endl;
	cout << "Local CCM Test: void f_inout1(inout long l1) "; 

	long value = 7;
	long result;
        bm->long_attr(value);

	timer.start();
	for(long counter=0; counter<MAX_LOOP_COUNT; counter++ ) {
	  bm->f_inout1(value);
	}
	timer.stop();
	cout << eval.getTimerResult(timer,MAX_LOOP_COUNT,1);
      }

      {
	const long MAX_LOOP_COUNT = 10000000;
	// in string parameter with increasing size
	cout << endl;
	for(long size=0; size<=SEQUENCE_SIZE_MAX; size+=SEQUENCE_SIZE_STEP) {
	  cout << "Local CCM Test: void f_inout2(inout string s1) "; 

	  string value;
	  string result;
	  for(int i=0; i<size; i++)
	    value += "X";
	  bm->string_attr(value);

	  timer.start();
	  for(long counter=0; counter<MAX_LOOP_COUNT; counter++ ) {
	    bm->f_inout2(value);
	  }
	  timer.stop();
	  cout << eval.getTimerResult(timer,MAX_LOOP_COUNT,size);
	}
      }

      {
	const long MAX_LOOP_COUNT = 1000000;
	// in sequence of long parameter with increasing size
	cout << endl;
	for(long size=0; size<=SEQUENCE_SIZE_MAX; size+=SEQUENCE_SIZE_STEP) {
	  cout << "Local CCM Test: void f_inout3(inout LongList ll1) "; 

	  LongList value;
	  LongList result;
	  for(long i=0; i<size; i++)
	    value.push_back(i);
	  bm->LongList_attr(value);

	  timer.start();
	  for(long counter=0; counter<MAX_LOOP_COUNT; counter++ ) {
	    bm->f_inout3(value);
	  }
	  timer.stop();
	  cout << eval.getTimerResult(timer,MAX_LOOP_COUNT,size);
	}
      }	


      //----------------------------------------------------------
      // out parameter test cases 
      //----------------------------------------------------------

      {
	// in long parameter
	cout << endl;
	cout << "Local CCM Test: void f_out1(out long l1) "; 

	long value = 7;
	long result;
        bm->long_attr(value);

	timer.start();
	for(long counter=0; counter<MAX_LOOP_COUNT; counter++ ) {
	  bm->f_out1(value);
	}
	timer.stop();
	cout << eval.getTimerResult(timer,MAX_LOOP_COUNT,1);
      }

      {
	const long MAX_LOOP_COUNT = 10000000;
	// in string parameter with increasing size
	cout << endl;
	for(long size=0; size<=SEQUENCE_SIZE_MAX; size+=SEQUENCE_SIZE_STEP) {
	  cout << "Local CCM Test: void f_out2(out string s1) "; 

	  string value;
	  string result;
	  for(int i=0; i<size; i++)
	    value += "X";
	  bm->string_attr(value);

	  timer.start();
	  for(long counter=0; counter<MAX_LOOP_COUNT; counter++ ) {
	    bm->f_out2(value);
	  }
	  timer.stop();
	  cout << eval.getTimerResult(timer,MAX_LOOP_COUNT,size);
	}
      }

      {
	const long MAX_LOOP_COUNT = 1000000;
	// in sequence of long parameter with increasing size
	cout << endl;
	for(long size=0; size<=SEQUENCE_SIZE_MAX; size+=SEQUENCE_SIZE_STEP) {
	  cout << "Local CCM Test: void f_out3(out LongList ll1) "; 

	  LongList value;
	  LongList result;
	  for(long i=0; i<size; i++)
	    value.push_back(i);
	  bm->LongList_attr(value);

	  timer.start();
	  for(long counter=0; counter<MAX_LOOP_COUNT; counter++ ) {
	    bm->f_out3(value);
	  }
	  timer.stop();
	  cout << eval.getTimerResult(timer,MAX_LOOP_COUNT,size);
	}
      }	


      //----------------------------------------------------------
      // return value test cases
      //----------------------------------------------------------

      {
	// return long 
	cout << endl;
	cout << "Local CCM Test: long f_ret1() "; 

	long value = 7;
	long result;
	bm->long_attr(value);

	timer.start();
	for(long counter=0; counter<MAX_LOOP_COUNT; counter++ ) {
	  result = bm->f_ret1();
	}
	timer.stop();
	cout << eval.getTimerResult(timer,MAX_LOOP_COUNT,1);
      }

      {
	const long MAX_LOOP_COUNT = 10000000;
	// in string parameter with increasing size
	cout << endl;
	for(long size=0; size<=SEQUENCE_SIZE_MAX; size+=SEQUENCE_SIZE_STEP) {
	  cout << "Local CCM Test: string f_ret2() "; 

	  string value;
	  string result;
	  for(int i=0; i<size; i++)
	    value += "X";
	  bm->string_attr(value);

	  timer.start();
	  for(long counter=0; counter<MAX_LOOP_COUNT; counter++ ) {
	    result = bm->f_ret2();
	  }
	  timer.stop();
	  cout << eval.getTimerResult(timer,MAX_LOOP_COUNT,size);
	}
      }

      {
	const long MAX_LOOP_COUNT = 1000000;
	// in sequence of long parameter with increasing size
	cout << endl;
	for(long size=0; size<=SEQUENCE_SIZE_MAX; size+=SEQUENCE_SIZE_STEP) {
	  cout << "Local CCM Test: void f_in3(in LongList ll1) "; 

	  LongList value;
	  LongList result;
	  for(long i=0; i<size; i++)
	    value.push_back(i);
	  bm->LongList_attr(value); 

	  timer.start();
	  for(long counter=0; counter<MAX_LOOP_COUNT; counter++ ) {
	    result = bm->f_ret3();
	  }
	  timer.stop();
	  cout << eval.getTimerResult(timer,MAX_LOOP_COUNT,size);
	}
      }	

      cout << "--- Stop Test Case ------------------------------------" << endl;
    } 
    catch (  ccm::local::Components::NotImplemented& e ) {
        cout << "TEST: function not implemented: " << e.what (  ) << endl;
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
    catch ( ccm::local::Components::HomeNotFound ) {
        cout << "TEARDOWN ERROR: can't find a home!" << endl;
        error = -1;
    } 
    catch ( ccm::local::Components::NotImplemented& e ) {
        cout << "TEARDOWN ERROR: function not implemented: " 
	     << e.what (  ) << endl;
        error = -1;
    } 
    catch(...) {
        cout << "TEARDOWN ERROR: there is something wrong!" << endl;
        error = -1;
    }

    error += undeploy_benchmark_ccm_local_component_Test_TestHome("TestHome");
    if(error) {
        cerr << "TEARDOWN ERROR: Can't undeploy component homes!" << endl;
        return error;
    }
    cout << ">>>> Stop Test Client: " << __FILE__ << endl;

    globalTimer.stop();
    cout << eval.getTimerResult(globalTimer,1,1);
}
