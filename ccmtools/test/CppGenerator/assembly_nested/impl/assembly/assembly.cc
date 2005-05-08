#include <cassert>
#include <iostream>

#include "assembly.h"

using namespace std;
using namespace WX::Utils;
using namespace CCM_Local;

namespace CCM_Local {
    
Assembly::Assembly()
	: state_ (LocalComponents::INACTIVE)
{
  DEBUGNL("+Assembly::Assembly()");
}


Assembly::~Assembly()
{
  DEBUGNL("-Assembly::Assembly()");
}


void 
Assembly::build()
  throw (LocalComponents::CreateFailure)
{
  DEBUGNL("+Assembly::build()");
  throw LocalComponents::CreateFailure();
}


void 
Assembly::build(WX::Utils::SmartPtr<LocalComponents::CCMObject> facadeComponent)
  throw (LocalComponents::CreateFailure)
{
  DEBUGNL("+Assembly::build(WX::Utils::SmartPtr<LocalComponents::"
	  "CCMObject> facadeComponent)");
  int error = 0;
  LocalComponents::HomeFinder* homeFinder;
  homeFinder = HomeFinder::Instance ();
  
  try {
    // find home ob components
    SmartPtr<BasicTestHome> basicTestHome(dynamic_cast<BasicTestHome*>
       (homeFinder->find_home_by_name("BasicTestHome").ptr()));
    SmartPtr<UserTestHome> userTestHome(dynamic_cast<UserTestHome*>
       (homeFinder->find_home_by_name("UserTestHome").ptr()));

    // create components
    superTest = SmartPtr<SuperTest>(
		 dynamic_cast<SuperTest*>(facadeComponent.ptr()));
    basicTest = basicTestHome->create();
    userTest = userTestHome->create();
    
    //provide facets
    basicType = basicTest->provide_basicType();
    userType = userTest->provide_userType();

    // connect components
    superTest->connect_innerBasicType(basicType);
    superTest->connect_innerUserType(userType);
  }
  catch (LocalComponents::HomeNotFound) {
    cout << "DEPLOYMENT ERROR: can't find a home!" << endl;
    error = -1;
  }
  catch (LocalComponents::NotImplemented& e) {
    cout << "DEPLOYMENT ERROR: function not implemented: "
	 << e.what () << endl;
    error = -1;
  }
  catch (LocalComponents::InvalidName& e) {
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
    throw LocalComponents::CreateFailure();
  } else {
    state_ = LocalComponents::INSERVICE;
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
	  throw (LocalComponents::RemoveFailure)
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
  catch (LocalComponents::HomeNotFound) {
    cout << "TEARDOWN ERROR: can't find a home!" << endl;
    error = -1;
  }
  catch (LocalComponents::NotImplemented& e) {
    cout << "TEARDOWN ERROR: function not implemented: " << e.what()
	 << endl;
    error = -1;
  }
  catch (...) {
    cout << "TEARDOWN ERROR: there is something going wrong!" << endl;
    error = -1;
  }
  
  state_ = LocalComponents::INACTIVE;
}


LocalComponents::AssemblyState 
Assembly::get_state()
{
  return state_;
}

} // /namespace CCM_Local
