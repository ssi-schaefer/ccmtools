/* -*- mode: C++; c-basic-offset: 4 -*-
 *
 *  server.cc
 *
 *  The simplest CORBA program using the Naming Service
 ***/

#include <cstdlib>
#include <iostream>
#include <string>

#include <WX/Utils/Timer.h>
#include <WX/Utils/TimerEvaluation.h>

#include <CORBA.h>
#include <coss/CosNaming.h>

#include "Benchmark.h"
#include "BenchmarkImpl.h"

using namespace std;

//==============================================================================
//  Server main function()
//==============================================================================
int main (int argc, char** argv)
{
    WX::Utils::Timer globalTimer;
    WX::Utils::TimerEvaluation eval;
    globalTimer.start();

    CORBA::ORB_var orb;
    CosNaming::NamingContextExt_var naming;
    
    // Initialize ORB
    int argc_ = 3;
    char* argv_[] = {
        "",
        "-ORBInitRef",
        "NameService=corbaloc:iiop:1.2@localhost:5050/NameService"
    };
    orb = CORBA::ORB_init(argc_, argv_);
    
    // Get reference to initial naming context
    CORBA::Object_var nameobj = orb->resolve_initial_references("NameService");
    naming = CosNaming::NamingContextExt::_narrow(nameobj);
    
    try { // Server part
	
        // Obtain a reference to the RootPOA and its Manager
        CORBA::Object_var poaobj = orb->resolve_initial_references("RootPOA");
        PortableServer::POA_var poa = PortableServer::POA::_narrow(poaobj);
	
        // Activate POA manager
        PortableServer::POAManager_var mgr = poa->the_POAManager();
        mgr->activate ();

        // Create the servant object
        BenchmarkImpl* servant = new BenchmarkImpl();

        // Activate the Servant
        CORBA::Object_var obj = poa->servant_to_reference(servant);

        // Register object reference with the Nameing Service
        CosNaming::Name name;
        name.length(1);
        name[0].id = CORBA::string_dup("Benchmark");
        naming->rebind(name,obj);

        // Activate the POA and start serving requests
        cout << "Server is running..." << endl;
        // When using collocation, we don't call orb->run();
    }
    catch(const CORBA::Exception &) {
        cerr << "Aut'sch: CORBA exception!" << endl;
        return 1;
    }


    try { // Collocated client

	cout << "Run client..." << endl;
        // Get object reference from naming service
        CORBA::Object_var obj = naming->resolve_str("Benchmark");
        Benchmark_var bm = Benchmark::_narrow(obj);

        // Execute methods on the remote object via proxy object
        
	cout << "--- Start Test Case --------------------------------" << endl;
	
	//----------------------------------------------------------
	// check CORBA object's functionality
	//----------------------------------------------------------

	{
	    // long setter and getter
	    CORBA::Long value = 7;
	    bm->long_attr(value);

	    CORBA::Long result;
	    result = bm->long_attr();
	    
	    assert(result == value);
	}

	{
	    // string setter and getter
	    int size = 100;
	    string s;
	    for(int i=0; i<size; i++)
		s += "X";
	    CORBA::String_var value = CORBA::string_dup(s.c_str());
	    bm->string_attr(value.in());

	    CORBA::String_var result =  bm->string_attr();
	    
	    assert(strcmp(result, value) == 0);
	}

	{
	    // LongList setter and getter
	    int size = 100;
	    LongList_var value = new LongList;
	    value->length(size);
	    for(int i=0; i<size; i++)
		(*value)[i] = i;
	    bm->LongList_attr(value.in());  

	    LongList_var result =  bm->LongList_attr();
	    
	    for(int i=0; i<size; i++) {
		assert((*result)[i] == (*value)[i]);
	    }
	}


	{
	    // inout long parameter
	    CORBA::Long value = 7;
	    bm->long_attr(value);

	    value = 0;
	    bm->f_inout1(value);

	    assert(value == 7);
	}
	
	{
	    // inout string parameter
	    
	    CORBA::String_var value = CORBA::string_dup("0123456789");
	    bm->string_attr(value.in());
	    
	    value = CORBA::string_dup("ABCDEFGHIJK");
	    bm->f_inout2(value.inout());
	    
	    assert(strcmp(value, "0123456789") == 0);
	}
	
	{
	    // inout sequence of long parameter 
	    int size = 100;
	    LongList_var attr = new LongList;
	    attr->length(size);
	    for(int i=0; i<size; i++) {
		(*attr)[i] = i;
	    }
	    bm->LongList_attr(attr.in());  
	    
	    LongList_var param = new LongList;
	    param->length(size);
	    for(int i=0; i<size; i++) {
		(*param)[i] = i*i;
	    }
	    
	    bm->f_inout3(param.inout());

	    for(int i=0; i<size; i++) {
		assert((*param)[i] == (*attr)[i]);
	    }
	}


	{
	    // out long parameter
	    CORBA::Long attr = 7;
	    bm->long_attr(attr);

	    CORBA::Long param;
	    bm->f_out1(param);

	    assert(param == attr);
	}
	/*
	{
	    // out string parameter
	    CORBA::String_var attr = CORBA::string_dup("0123456789");
	    bm->string_attr(attr.in());
	    
	    CORBA::String_var param;
	    bm->f_out2(param.inout());
	    
	    assert(strcmp(param, attr) == 0);
	}
	*/



	/*	
	// Test configuration
	WX::Utils::Timer timer;
	
	const long MAX_LOOP_COUNT = 1000000;
	const long SEQUENCE_SIZE_MAX = 1000;
	const long SEQUENCE_SIZE_STEP = 100;

	{
	    // Ping
	    cout << "Collocated CORBA Test: void f0() "; 
	    
	    timer.start();
	    for(long counter=0; counter<MAX_LOOP_COUNT; counter++ ) {
		bm->f0();
	    }
	    timer.stop();
	    cout << eval.getTimerResult(timer,MAX_LOOP_COUNT,1);
	}
	
	
	{
	    // in long parameter
	    cout << "Collocated CORBA Test: void f_in1(in long l1) "; 
	    
	    CORBA::Long value = 7;
	    
	    timer.start();
	    for(long counter=0; counter<MAX_LOOP_COUNT; counter++ ) {
		bm->f_in1(value);
	    }
	    timer.stop();
	    cout << eval.getTimerResult(timer,MAX_LOOP_COUNT,1);
	}
	
	{
	    // in string parameter with increasing size
	    for(long size=0; size<=SEQUENCE_SIZE_MAX;size+=SEQUENCE_SIZE_STEP) {
		cout << "Collocated CORBA Test: void f_in2(in string s1) "; 
		
		string value;
		for(int i=0; i<size; i++)
		    value += "X";
		char* c_value = CORBA::string_dup(value.c_str());
		timer.start();
		for(long counter=0; counter<MAX_LOOP_COUNT; counter++ ) {
		    bm->f_in2(c_value);
		}
		timer.stop();
		cout << eval.getTimerResult(timer,MAX_LOOP_COUNT,size);
	    }
	}
	
	
	{
	    // in sequence of long parameter with increasing size
	    for(long size=0; size<=SEQUENCE_SIZE_MAX;size+=SEQUENCE_SIZE_STEP) {
		cout << "Collocated CORBA Test: void f_in3(in LongList ll1) "; 
		
		::LongList_var value = new ::LongList;
		value->length(size);
		for(long i=0; i<size; i++)
		    (*value)[i] = i;
		
		timer.start();
		for(long counter=0; counter<MAX_LOOP_COUNT; counter++ ) {
		    bm->f_in3(value);
		}
		timer.stop();
		cout << eval.getTimerResult(timer,MAX_LOOP_COUNT,size);
	    }
	} 

	*/

	
	cout << "--- Stop Test Case ---------------------------------" << endl;
    }
    catch(const CORBA::Exception &) {
        cerr << "Aut'sch: CORBA exception!" << endl;
        return 1;
    }
    catch(...) {
      cout << "TEST: there is something wrong!" << endl;
      return 1;
    }

    globalTimer.stop();
    cout << eval.getTimerResult(globalTimer,1,1);
    return 0;
}
