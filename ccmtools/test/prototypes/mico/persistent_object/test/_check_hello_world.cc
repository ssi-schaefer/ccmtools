/* -*- mode: C++; c-basic-offset: 4 -*- */

/***
 *  This Mico test case shows the implementation of a POA with 
 *  PERSISTENT and USER_ID policies, and the usage of corbaloc
 *  and the Interoperable NamingService.
 ***/

#include <cstdlib> 
#include <iostream>
#include <fstream>
#include <string>

#include <CORBA.h>
#include <coss/CosNaming.h>

#include "HelloWorld.h"
#include "HelloWorldImpl.h"

using namespace std;

//==============================================================================
//  Server main function() 
//==============================================================================
int main (int argc, char** argv)
{
    // Collect commandline parameters for ORB init
    int argc_ = 6;
    char* argv_[] = { 
      "", 
      //"-ORBInitRef","NameService=corbaloc:iiop:1.2@localhost:5050/NameService",
      "-ORBIIOPAddr","inet:localhost:7777",
      "-POAImplName", "Hello",
      "-ORBIIOPVersion 1.2"
    }; 

    CORBA::ORB_var orb = CORBA::ORB_init(argc_, argv_);

    /*
     * Server Part
     */
    try { 
	
	// Obtain a reference to the RootPOA and its Manager
	CORBA::Object_var poaobj = orb->resolve_initial_references("RootPOA");
	PortableServer::POA_var root_poa = PortableServer::POA::_narrow(poaobj);
	PortableServer::POAManager_var mgr = root_poa->the_POAManager();

	// Create a new POA with PERSISTENT and USER_ID policies
	CORBA::PolicyList pl;
	pl.length(2);
	pl[0] = root_poa->create_lifespan_policy (PortableServer::PERSISTENT);
	pl[1] = root_poa->create_id_assignment_policy (PortableServer::USER_ID);
	PortableServer::POA_var child_poa = 
	    root_poa->create_POA ("HelloPOA", mgr, pl);
	
	// Create the servant object
	HelloWorldImpl* servant = new HelloWorldImpl;

	// Activate the Servant
	PortableServer::ObjectId_var oid =
	    PortableServer::string_to_ObjectId("HelloWorld");
	child_poa->activate_object_with_id (*oid, servant);	
	CORBA::Object_var ref = child_poa->id_to_reference(oid.in());
	assert(!CORBA::is_nil(ref));

	string ior;
	ior = orb->object_to_string(ref);
	cout << ior << endl;

	// Activate the POA and start serving requests
	mgr->activate ();
	cout << "Server is running..." << endl;
	// When using collocation, we don't call orb->run();
	//orb->run();
    }
    catch(const CORBA::Exception &e) {
	cerr << "!!!: Server-side CORBA exception: " << e << endl;
	return 1;
    }


    /*
     * Client Part (Collocated)
     */
    try { 
	// Obtain the CORBA object reference via Interoperable Naming Service
	// using corbaloc addressing.
	// Note that a NameService must be running (see argv arguments) 
	CORBA::Object_var obj =
	    orb->string_to_object("corbaloc::127.0.0.1:7777/Hello/HelloPOA/HelloWorld");
	assert(!CORBA::is_nil(obj));

	HelloWorld_var hello = HelloWorld::_narrow(obj);
	
	hello->hello("Test!");
    }
    catch(const CORBA::Exception &e) {
	cerr << "!!!: Client-side CORBA exception:" << e << endl;
	return 1;
    }

    return 0;
}
