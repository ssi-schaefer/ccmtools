//==============================================================================
// CarRental - business logic implementation
//==============================================================================

#include <cassert>
#include <iostream>
#include <WX/Utils/debug.h>

#include "CarRental_app.h"

using namespace std;
using namespace WX::Utils;
using namespace CCM_Local;

namespace CCM_Local {
namespace BigBusiness {
namespace CCM_Session_CarRental {


//==============================================================================
// business logic functionality
//==============================================================================

CCM_CustomerMaintenance*
CCM_CarRental_impl::get_maintenance (  )
{
  DEBUGNL ( " CCM_CarRental_impl->get_maintenance (  )" );
  maintenance_impl* facet = new maintenance_impl(this);
  return dynamic_cast<CCM_CustomerMaintenance*> ( facet );
}

CCM_CustomerBusiness*
CCM_CarRental_impl::get_business (  )
{
  DEBUGNL ( " CCM_CarRental_impl->get_business (  )" );
  business_impl* facet = new business_impl(this);
  return dynamic_cast<CCM_CustomerBusiness*> ( facet );
}



//==============================================================================
// maintenance - facet implementation
//==============================================================================

maintenance_impl::maintenance_impl ( CCM_CarRental_impl* component_impl )
  : component ( component_impl )
{
  DEBUGNL ( "+maintenance_impl->maintenance_impl (  )" );
}

maintenance_impl::~maintenance_impl (  )
{
  DEBUGNL ( "-maintenance_impl->~maintenance_impl (  )" );
}


void
maintenance_impl::createCustomer ( const Customer& person )
  throw (LocalComponents::CCMException, CreateCustomerException )
{
  DEBUGNL ( " maintenance_impl->createCustomer ( person )" );

  component->CustomerDB.push_back(person);
}


Customer
maintenance_impl::retrieveCustomer ( const long id )
  throw (LocalComponents::CCMException, NoCustomerException )
{
  DEBUGNL ( " maintenance_impl->retrieveCustomer ( id )" );
  std::vector<CCM_Local::BigBusiness::Customer>::iterator pos;
  for(pos = component->CustomerDB.begin(); pos != component->CustomerDB.end(); ++pos) {
    if(pos->id == id) {
      return *pos;
    }
  }
  throw NoCustomerException();
}

CustomerList
maintenance_impl::retrieveAllCustomers (  )
  throw (LocalComponents::CCMException, NoCustomerException )
{
  DEBUGNL ( " maintenance_impl->retrieveAllCustomers (  )" );

  if(component->CustomerDB.size() == 0)
      throw NoCustomerException();

  CCM_Local::BigBusiness::CustomerList customer_list;
  std::vector<CCM_Local::BigBusiness::Customer>::iterator pos;
  for(pos = component->CustomerDB.begin(); pos != component->CustomerDB.end(); ++pos) {
    customer_list.push_back(*pos);
  }
  return customer_list;
}

void
maintenance_impl::updateCustomer ( const Customer& person )
  throw (LocalComponents::CCMException, NoCustomerException )
{
  DEBUGNL ( " maintenance_impl->updateCustomer (person )" );
  
  std::vector<CCM_Local::BigBusiness::Customer>::iterator pos;
  for(pos = component->CustomerDB.begin(); pos != component->CustomerDB.end(); ++pos) {
    if(pos->id == person.id) {
      *pos = person;
      return;
    }
  }
  throw NoCustomerException();  
}

void
maintenance_impl::deleteCustomer ( const long id )
  throw (LocalComponents::CCMException, CreateCustomerException )
{
  DEBUGNL ( " maintenance_impl->removeCustomer ( id )" );
  
  std::vector<CCM_Local::BigBusiness::Customer>::iterator pos;
  for(pos = component->CustomerDB.begin(); pos != component->CustomerDB.end(); ++pos) {
    if(pos->id == id) {
      component->CustomerDB.erase(pos);
      return;
    }
  }
  throw NoCustomerException();  
}



//==============================================================================
// business - facet implementation
//==============================================================================

business_impl::business_impl ( CCM_CarRental_impl* component_impl )
  : component ( component_impl ), _dollars_per_mile(0.0)
{
  DEBUGNL ( "+business_impl->business_impl (  )" );
}

business_impl::~business_impl (  )
{
  DEBUGNL ( "-business_impl->~business_impl (  )" );
}

double
business_impl::dollars_per_mile (  )
  throw ( LocalComponents::CCMException)
{
  DEBUGNL ( " business_impl->dollars_per_mile (  )" );
  return _dollars_per_mile;
}

void
business_impl::dollars_per_mile ( const double value )
  throw ( LocalComponents::CCMException)
{
  DEBUGNL ( " business_impl->dollars_per_mile ( value )" );
  _dollars_per_mile = value;
}


void
business_impl::addCustomerMiles ( const long id, const double miles )
  throw (LocalComponents::CCMException, NoCustomerException )
{
  DEBUGNL ( " business_impl->addCustomerMiles ( id, miles )" );
  std::vector<CCM_Local::BigBusiness::Customer>::iterator pos;
  for(pos = component->CustomerDB.begin(); pos != component->CustomerDB.end(); ++pos) {
    if(pos->id == id) {
      pos->mileage += miles;
      return;
    }
  }
  throw NoCustomerException();    
}

void
business_impl::resetCustomerMiles ( const long id )
  throw (LocalComponents::CCMException, NoCustomerException )
{
  DEBUGNL ( " business_impl->resetCustomerMiles ( id )" );
  std::vector<CCM_Local::BigBusiness::Customer>::iterator pos;
  for(pos = component->CustomerDB.begin(); pos != component->CustomerDB.end(); ++pos) {
    if(pos->id == id) {
      pos->mileage = 0.0;
      return;
    }
  }
  throw NoCustomerException(); 
}

double
business_impl::getCustomerMiles ( const long id )
  throw (LocalComponents::CCMException, NoCustomerException )
{
  DEBUGNL ( " business_impl->getCustomerMilesValue ( id )" );
  std::vector<CCM_Local::BigBusiness::Customer>::iterator pos;
  for(pos = component->CustomerDB.begin(); pos != component->CustomerDB.end(); ++pos) {
    if(pos->id == id) {
      return pos->mileage;
    }
  }
  throw NoCustomerException(); 
}

double
business_impl::getCustomerDollars ( const long id )
  throw (LocalComponents::CCMException, NoCustomerException )
{
  DEBUGNL ( " business_impl->getCustomerDollarValue ( id )" );
  return getCustomerMiles(id) * _dollars_per_mile;
}





//==============================================================================
// component implementation
//==============================================================================

CCM_CarRental_impl::CCM_CarRental_impl (  )
{
  DEBUGNL ( "+CCM_CarRental_impl->CCM_CarRental_impl (  )" );

}

CCM_CarRental_impl::~CCM_CarRental_impl (  )
{
  DEBUGNL ( "-CCM_CarRental_impl->~CCM_CarRental_impl (  )" );
}




void
CCM_CarRental_impl::set_session_context ( LocalComponents::SessionContext* context )
  throw ( LocalComponents::CCMException )
{
  DEBUGNL ( " CCM_CarRental_impl->set_session_context (  )" );
  ctx = dynamic_cast<CCM_CarRental_Context*> ( context );
}

void
CCM_CarRental_impl::ccm_activate (  )
  throw ( LocalComponents::CCMException )
{
  DEBUGNL ( " CCM_CarRental_impl->ccm_activate (  )" );
}

void
CCM_CarRental_impl::ccm_passivate (  )
  throw ( LocalComponents::CCMException )
{
  DEBUGNL ( " CCM_CarRental_impl->ccm_passivate (  )" );
}

void
CCM_CarRental_impl::ccm_remove (  )
  throw ( LocalComponents::CCMException )
{
  DEBUGNL ( " CCM_CarRental_impl->ccm_remove (  )" );
}

} // /namespace CCM_Session_CarRental
} // /namespace BigBusiness
} // /namespace CCM_Local



