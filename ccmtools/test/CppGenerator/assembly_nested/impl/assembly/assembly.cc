#include <cassert>
#include <iostream>

#include "assembly.h"

namespace ccm {
namespace local {

using namespace std;
using namespace wamas::platform::utils;
    
Assembly::Assembly()
	: state_ (Components::ccm::local::INACTIVE)
{
  DEBUGNL("+Assembly::Assembly()");
}


Assembly::~Assembly()
{
  DEBUGNL("-Assembly::Assembly()");
}


void 
Assembly::build()
  throw (Components::ccm::local::CreateFailure)
{
  DEBUGNL("+Assembly::build()");
  throw Components::ccm::local::CreateFailure();
}


void 
Assembly::build(SmartPtr<Components::ccm::local::CCMObject> facadeComponent)
  throw (Components::ccm::local::CreateFailure)
{
  DEBUGNL("+Assembly::build(wamas::platform::utils::SmartPtr<Components::"
	  "CCMObject> facadeComponent)");
  int error = 0;
  Components::ccm::local::HomeFinder* homeFinder;
  homeFinder = HomeFinder::Instance ();
  
  try {
    // find home ob components
    SmartPtr<BasicTestHome> 
      basicTestHome(dynamic_cast<BasicTestHome*>
       (homeFinder->find_home_by_name("BasicTestHome").ptr()));
    SmartPtr<UserTestHome> 
      userTestHome(dynamic_cast<UserTestHome*>
       (homeFinder->find_home_by_name("UserTestHome").ptr()));

    // create components
    superTest = SmartPtr<SuperTest>(dynamic_cast<SuperTest*>(facadeComponent.ptr()));
    basicTest = basicTestHome->create();
    userTest = userTestHome->create();
    
    //provide facets
    basicType = basicTest->provide_basicType();
    userType = userTest->provide_userType();

    // connect components
    superTest->connect_innerBasicType(basicType);
    superTest->connect_innerUserType(userType);
  }
  catch (Components::ccm::local::HomeNotFound) {
    cout << "DEPLOYMENT ERROR: can't find a home!" << endl;
    error = -1;
  }
  catch (Components::ccm::local::NotImplemented& e) {
    cout << "DEPLOYMENT ERROR: function not implemented: "
	 << e.what () << endl;
    error = -1;
  }
  catch (Components::ccm::local::InvalidName& e) {
    cout << "DEPLOYMENT ERROR: invalid name during connection: "
	 << e.what () << endl;
    error = -1;
  }
  catch (std::exception& e) {
    cout << "Standard exception: "
	 << e.what () << endl;
    error = -1;
  }
  catch (...) {
    cout << "DEPLOYMENT ERROR: there is something going wrong!" << endl;
    error = -1;
  }
  if (error < 0) {
    throw Components::ccm::local::CreateFailure();
  } else {
    state_ = Components::ccm::local::INSERVICE;
  }
}


void
Assembly::configuration_complete()
{
  DEBUGNL("+StocktakeAssembly::configuration_complete()");
  basicTest->configuration_complete();
  userTest->configuration_complete();
}


void
Assembly::tear_down()
	  throw (Components::ccm::local::RemoveFailure)
{
  DEBUGNL("+StocktakeAssembly::tear_down()");
  int error = 0;
  try {
    // disconnect components
    superTest->disconnect_innerBasicType();
    superTest->disconnect_innerUserType();

    // remove components
    basicTest->remove();
    userTest->remove();
  }
  catch (Components::ccm::local::HomeNotFound) {
    cout << "TEARDOWN ERROR: can't find a home!" << endl;
    error = -1;
  }
  catch (Components::ccm::local::NotImplemented& e) {
    cout << "TEARDOWN ERROR: function not implemented: " << e.what()
	 << endl;
    error = -1;
  }
  catch (...) {
    cout << "TEARDOWN ERROR: there is something going wrong!" << endl;
    error = -1;
  }
  
  state_ = Components::ccm::local::INACTIVE;
}


Components::ccm::local::AssemblyState 
Assembly::get_state()
{
  return state_;
}

} // /namespace local
} // /namespace ccm
