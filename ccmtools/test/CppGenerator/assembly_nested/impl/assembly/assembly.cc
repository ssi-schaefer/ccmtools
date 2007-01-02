#include <cassert>
#include <iostream>

#include "assembly.h"

using namespace std;
namespace wx = wamas::platform::utils;
    
Assembly::Assembly()
	: state_ (::Components::INACTIVE)
{
}


Assembly::~Assembly()
{
}


void 
Assembly::build()
  throw(::Components::CreateFailure)
{
  throw ::Components::CreateFailure();
}


void 
Assembly::build(wx::SmartPtr< ::Components::CCMObject> facadeComponent)
  throw (::Components::CreateFailure)
{
  int error = 0;
  ::Components::HomeFinder* homeFinder = ::Components::HomeFinder::Instance ();
  
  try 
  {
    // find home ob components
    wx::SmartPtr<BasicTestHome> basicTestHome(dynamic_cast<BasicTestHome*>
       (homeFinder->find_home_by_name("BasicTestHome").ptr()));
    wx::SmartPtr<UserTestHome> userTestHome(dynamic_cast<UserTestHome*>
       (homeFinder->find_home_by_name("UserTestHome").ptr()));

    // create components
    superTest = wx::SmartPtr<SuperTest>(dynamic_cast<SuperTest*>(facadeComponent.ptr()));
    basicTest = basicTestHome->create();
    userTest = userTestHome->create();
    
    //provide facets
    basicType = basicTest->provide_basicType();
    userType = userTest->provide_userType();

    // connect components
    superTest->connect_innerBasicType(basicType);
    superTest->connect_innerUserType(userType);
    
    
  }
  catch (::Components::HomeNotFound) 
  {
    cout << "DEPLOYMENT ERROR: can't find a home!" << endl;
    error = -1;
  }
  catch (::Components::NotImplemented& e) 
  {
    cout << "DEPLOYMENT ERROR: function not implemented: "
	 << e.what () << endl;
    error = -1;
  }
  catch (::Components::InvalidName& e) 
  {
    cout << "DEPLOYMENT ERROR: invalid name during connection: "
	 << e.what () << endl;
    error = -1;
  }
  catch (std::exception& e) 
  {
    cout << "Standard exception: "
	 << e.what () << endl;
    error = -1;
  }
  catch (...) {
    cout << "DEPLOYMENT ERROR: there is something going wrong!" << endl;
    error = -1;
  }
  
  if (error < 0) 
  {
    throw ::Components::CreateFailure();
  } 
  else 
  {
    state_ = ::Components::INSERVICE;
  }
}


void
Assembly::configuration_complete()
{
  basicTest->configuration_complete();
  userTest->configuration_complete();
}


void
Assembly::tear_down()
	  throw (::Components::RemoveFailure)
{
  int error = 0;
  try 
  {
    // disconnect components
    superTest->disconnect_innerBasicType();
    superTest->disconnect_innerUserType();

    // remove components
    basicTest->remove();
    userTest->remove();
  }
  catch (::Components::HomeNotFound) 
  {
    cerr << "TEARDOWN ERROR: can't find a home!" << endl;
    error = -1;
  }
  catch (::Components::NotImplemented& e) 
  {
    cerr << "TEARDOWN ERROR: function not implemented: " << e.what()
	 << endl;
    error = -1;
  }
  catch (...) 
  {
    cerr << "TEARDOWN ERROR: there is something going wrong!" << endl;
    error = -1;
  }
  
  state_ = ::Components::INACTIVE;
}


::Components::AssemblyState 
Assembly::get_state()
{
  return state_;
}
