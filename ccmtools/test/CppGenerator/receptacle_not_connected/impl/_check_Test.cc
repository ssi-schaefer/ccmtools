/***
 * CCM Tools Test Client 
 *
 * This file was automatically generated by CCM Tools 
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

#include <Components/CCM.h>

#include <TestHomeMirror_gen.h>
#include <TestHome_gen.h>

using namespace std;
using namespace wamas::platform::utils;

int main(int argc, char *argv[])
{
    cout << ">>>> Start Test Client: " << __FILE__ << endl;

    SmartPtr<Test> myTest;
    SmartPtr<TestMirror> myTestMirror;

    SmartPtr< ::Components::Object> Test_uses_console;

    ::Components::Cookie Test_ck_console;

    int error = 0;
    ::Components::HomeFinder* homeFinder = ::Components::HomeFinder::Instance();
    error  = deploy_TestHome("TestHome");
    error += deploy_TestHomeMirror("TestHomeMirror");	
    if(error) 
    {
        cerr << "BOOTSTRAP ERROR: Can't deploy component homes!" << endl;
        return error;
    }

    try 
    {
        SmartPtr<TestHome> myTestHome(dynamic_cast<TestHome*>
            (homeFinder->find_home_by_name("TestHome").ptr()));

        SmartPtr<TestHomeMirror>myTestHomeMirror(dynamic_cast<TestHomeMirror*>
            (homeFinder->find_home_by_name("TestHomeMirror").ptr()));

        myTest = myTestHome->create();
        myTestMirror = myTestHomeMirror->create();
        Test_uses_console = myTestMirror->provide_facet("console");

		/*
	 	 * Don't connect the receptacle to activate a Components::NoConnection 
	 	 * exception.
	 	 * Test_ck_console = myTest->connect("console", Test_uses_console);
	 	 */
        myTest->configuration_complete();
        myTestMirror->configuration_complete();


      	/*
       	 * Don't disconnect a unconnected receptacle
       	 * myTest->disconnect("console", Test_ck_console);
       	 */
        myTest->remove();
        myTestMirror->remove();

    } 
    catch(::Components::HomeNotFound ) {
        cout << "DEPLOYMENT ERROR: can't find a home!" << endl;
        return -1;
    } 
    catch(::Components::NotImplemented& e ) {
        cout << "DEPLOYMENT ERROR: function not implemented: " 
	     << e.what (  ) << endl;
        return -1;
    }  
    catch(::Components::InvalidName& e ) 
    {
        cout << "DEPLOYMENT ERROR: invalid name during connection: " 
             << e.what (  ) << endl;
        return -1;
    }
    catch ( ... )  
    {
        cout << "DEPLOYMENT ERROR: there is something wrong!" << endl;
        error = -1;
    }
    if (error < 0) 
    {
        return error;
    }
  
    error += undeploy_TestHome("TestHome");
    error += undeploy_TestHomeMirror("TestHomeMirror");
    if(error)
    {
        cerr << "TEARDOWN ERROR: Can't undeploy component homes!" << endl;
        return error;
    }
    cout << ">>>> Stop Test Client: " << __FILE__ << endl;
}
