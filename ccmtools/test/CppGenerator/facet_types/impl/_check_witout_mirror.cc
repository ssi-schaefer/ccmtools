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
 ***/

#include <cassert>
#include <iostream>

#include <Components/ccmtools.h>

#include <TestHome_gen.h>

using namespace std;

int main(int argc, char *argv[])
{
    cout << ">>>> Start Test Client: " << __FILE__ << endl;

    int error = 0;
    Components::HomeFinder* homeFinder = Components::HomeFinder::Instance();

    error = deploy_TestHome("TestHome");
    if(error) {
        cerr << "BOOTSTRAP ERROR: Can't deploy component homes!" << endl;
        return error;
    }

    try 
    {
        TestHome::SmartPtr myTestHome(dynamic_cast<TestHome*>(
        		homeFinder->find_home_by_name("TestHome").ptr()));
    		
    		Test::SmartPtr myTest;
        myTest = myTestHome->create();
        
        myTest->configuration_complete();

		{	        
			TypeTest::SmartPtr type_test;
			type_test = myTest->provide_type_test();
			long long_2=3, long_3, long_r;
			long_r = type_test->op_b2(7,long_2, long_3);
			assert(long_2 == 7);
			assert(long_3 == 3);
			assert(long_r == 3+7);
      	}
      	{	
			TypeTest::SmartPtr type_test;
			type_test = myTest->provide_type_test();
			long long_2=3, long_3, long_r;
			long_r = type_test->op_b2(7,long_2, long_3);
			assert(long_2 == 7);
			assert(long_3 == 3);
			assert(long_r == 3+7);
      	}
      	{	
			TypeTest::SmartPtr type_test;
			type_test = myTest->provide_type_test();
			long long_2=3, long_3, long_r;
			long_r = type_test->op_b2(7,long_2, long_3);
			assert(long_2 == 7);
			assert(long_3 == 3);
			assert(long_r == 3+7);
      	}

        myTest->remove();
    } 
    catch ( Components::HomeNotFound ) 
    {
        cout << "DEPLOYMENT ERROR: can't find a home!" << endl;
        return -1;
    } 
    catch ( Components::NotImplemented& e ) 
    {
        cout << "DEPLOYMENT ERROR: function not implemented: " 
	     << e.what (  ) << endl;
        return -1;
    }  
    catch ( Components::InvalidName& e ) 
    {
        cout << "DEPLOYMENT ERROR: invalid name during connection: " 
             << e.what (  ) << endl;
        return -1;
    }
    catch ( ... )  
    {
        cout << "DEPLOYMENT ERROR: there is something wrong!" << endl;
        return -1;
    }

    error = undeploy_TestHome("TestHome");
    if(error) 
    {
        cerr << "TEARDOWN ERROR: Can't undeploy component homes!" << endl;
        return error;
    }

    Components::HomeFinder::destroy(); // Clean up HomeFinder singleton

    cout << ">>>> Stop Test Client: " << __FILE__ << endl;
}
