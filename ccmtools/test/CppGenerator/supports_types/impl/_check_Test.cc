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
#include <cmath>

#include <Components/CCM.h>

#include <TestHome_gen.h>

#include "MyObject.h"

using namespace std;
using namespace wamas::platform::utils;

int main(int argc, char *argv[])
{
  	cout << ">>>> Start Test Client: " << __FILE__ << endl;

  	int error = 0;
  	error = deploy_TestHome("TestHome");
  	if(error) 
  	{
		cerr << "BOOTSTRAP ERROR: Can't deploy component homes!" << endl;
    		return(error);
  	}

  	try 
  	{
		::Components::HomeFinder* homeFinder = ::Components::HomeFinder::Instance();
  		SmartPtr<Test> myTest;
  
    		SmartPtr<TestHome> myTestHome(dynamic_cast<TestHome*>
      		(homeFinder->find_home_by_name("TestHome").ptr()));

    		myTest = myTestHome->create();
    		myTest->configuration_complete();
    		
	    cout << "== Begin Test Case ====================================" << endl;
    		{
      		// basic types test cases
      		short short_2=3, short_3, short_r;
      		short_r = myTest->op_b1(7,short_2, short_3);
      		assert(short_2 == 7);
      		assert(short_3 == 3);
      		assert(short_r == 3+7);
      
     		long long_2=3, long_3, long_r;
      		long_r = myTest->op_b2(7,long_2, long_3);
      		assert(long_2 == 7);
      		assert(long_3 == 3);
      		assert(long_r == 3+7);
      
     	 	unsigned short ushort_2=3, ushort_3, ushort_r;
      		ushort_r = myTest->op_b3(7,ushort_2, ushort_3);
     		assert(ushort_2 == 7);
      		assert(ushort_3 == 3);
      		assert(ushort_r == 3+7);
      
      		unsigned long ulong_2=3, ulong_3, ulong_r;
      		ulong_r = myTest->op_b4(7,ulong_2, ulong_3);
      		assert(ulong_2 == 7);
      		assert(ulong_3 == 3);
      		assert(ulong_r == 3+7);
      
      		float float_2=3.0, float_3, float_r;
      		float_r = myTest->op_b5(7.0,float_2, float_3);
      		assert(abs(float_2 - 7.0) < 0.001);
      		assert(abs(float_3 - 3.0) < 0.001);
      		assert(abs(float_r - (3.0+7.0)) < 0.001);
      
      		double double_2=3.0, double_3, double_r;
      		double_r = myTest->op_b6(7.0,double_2, double_3);
      		assert(abs(double_2 - 7.0) < 0.000001);
      		assert(abs(double_3 - 3.0) < 0.000001);
      		assert(abs(double_r - (3.0+7.0)) < 0.000001);
      
      		char char_2=3, char_3, char_r;
      		char_r = myTest->op_b7(7,char_2, char_3);
      		assert(char_2 == 7);
      		assert(char_3 == 3);
      		assert(char_r == 3+7);
      
      		string string_2="drei", string_3, string_r;
      		string_r = myTest->op_b8("sieben",string_2, string_3);
      		assert(string_2 == "sieben");
      		assert(string_3 == "drei");
      		assert(string_r == "dreisieben");
      
      		bool bool_2=false, bool_3, bool_r;
      		bool_r = myTest->op_b9(true, bool_2, bool_3);
      		assert(bool_2 == true);
      		assert(bool_3 == false);
      		assert(bool_r == false && true);

      		unsigned char uchar_2=3, uchar_3, uchar_r;
      		uchar_r = myTest->op_b10(7,uchar_2, uchar_3);
      		assert(uchar_2 == 7);
      		assert(uchar_3 == 3);
      		assert(uchar_r == 3+7);
    		}    
    
    		{
      		// test case: typedef long time_t;
      		time_t time_t_2 = 3, time_t_3, time_t_r;
      		time_t_r = myTest->op_u1(7,time_t_2, time_t_3);
      		assert(time_t_2 == 7);
      		assert(time_t_3 == 3);
      		assert(time_t_r == 3+7);
      
     		// Test case: enum Color {red, green, blue, black, orange}; 
      		Color Color_2,Color_3, Color_r;
      		Color_2 = Color(blue);
      		Color_r = myTest->op_u2(Color(red),Color_2, Color_3);
      		assert(Color_2 == Color(red));
      		assert(Color_3 == Color(blue));
      		assert(Color_r == Color(red));

      		// Test case: struct Value { string s; double dd; };
      		Pair Pair_1, Pair_2, Pair_3, Pair_r;
      		Pair_1.key = "a"; Pair_1.value = 1.0;
      		Pair_2.key = "b"; Pair_2.value = 2.0;
      		Pair_r = myTest->op_u3(Pair_1,Pair_2,Pair_3);
      		assert(Pair_3.key == "b");
      		assert(Pair_2.key == "a");
      		assert(Pair_r.key == "ab");
      
      		// Test case: typedef sequence<Value> map;
      		Map map_1, map_2, map_3, map_r;
      		for(int i=0;i<5;i++) 
      		{
        			Pair p1, p2;
        			p1.key = "1";
        			p1.value = (double)i;
        			map_1.push_back(p1);
        			p2.key = "2";
        			p2.value = (double)(i+i);
        			map_2.push_back(p2);
      		}
      		map_r = myTest->op_u4(map_1,map_2,map_3);
      		for(unsigned int i=0;i<map_r.size();i++) 
      		{
        			Pair p = map_r.at(i);
        			assert(p.value == (long)i);
      		}
      		for(unsigned int i=0;i<map_2.size();i++) 
      		{
        			Pair p = map_2.at(i);
        			assert(p.value == (long)i);
      		}
      		for(unsigned int i=0;i<map_3.size();i++) 
      		{
        			Pair p = map_3.at(i);
        			assert(p.value == (long)(i+i));
      		}
    		}
  
    		// Test interface types
    		{
      		MyObject* my_object1 = new MyObject;
      		SmartPtr<Console> console1(my_object1);
      		console1->prompt("prompt1> ");
      		MyObject* my_object2 = new MyObject;
      		SmartPtr<Console> console2(my_object2);
      		console2->prompt("prompt2> ");
      		SmartPtr<Console> console3;
      		SmartPtr<Console> console4;
      		console4 = myTest->op_i1(console1,console2,console3);
      		assert(console2->prompt()=="prompt1> ");
      		assert(console3->prompt()=="prompt2> ");
      		assert(console4->prompt()=="prompt2> prompt1> ");
    		}
    		cout << "== End Test Case =====================================" << endl;

		myTest->remove();
	} 
  	catch ( ::Components::HomeNotFound ) 
  	{
    		cout << "DEPLOYMENT ERROR: can't find a home!" << endl;
    		return -1;
  	} 
  	catch ( ::Components::NotImplemented& e ) 
  	{
    		cout << "DEPLOYMENT ERROR: function not implemented: " << e.what (  ) << endl;
    		return -1;
  	} 
  	catch ( ::Components::InvalidName& e ) 
  	{
    		cout << "DEPLOYMENT ERROR: invalid name during connection: " << e.what (  ) << endl;
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
  
  	cout << ">>>> Stop Test Client: " << __FILE__ << endl;
}
