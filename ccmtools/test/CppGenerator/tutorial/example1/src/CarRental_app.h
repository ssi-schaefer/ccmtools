
//==============================================================================
// CarRental - business logic class definition
//==============================================================================

#ifndef __COMPONENT_CCM_Local_CCM_Session_CarRental_CarRental_APP__H__
#define __COMPONENT_CCM_Local_CCM_Session_CarRental_CarRental_APP__H__

#include <CCM_Local/BigBusiness/CCM_Session_CarRental/CarRental_share.h>

namespace CCM_Local {
namespace BigBusiness {
namespace CCM_Session_CarRental {


//==============================================================================
// component implementation object
//==============================================================================

class CCM_CarRental_impl
  : public CCM_CarRental
{
 private:


 public:
  CCM_CarRental_Context* ctx;
  std::vector<CCM_Local::BigBusiness::Customer> CustomerDB;

  CCM_CarRental_impl (  );
  virtual ~CCM_CarRental_impl (  );


  virtual CCM_CustomerMaintenance* get_maintenance (  );
  virtual CCM_CustomerBusiness* get_business (  );



  // Callback methods

  virtual void set_session_context ( LocalComponents::SessionContext* ctx )
    throw ( LocalComponents::CCMException );
  virtual void ccm_activate (  )
    throw ( LocalComponents::CCMException );
  virtual void ccm_passivate (  )
    throw ( LocalComponents::CCMException );
  virtual void ccm_remove (  )
    throw ( LocalComponents::CCMException );
};

//==============================================================================
// maintenance - facet adapter implementation class
//==============================================================================

class maintenance_impl
  : public CCM_CustomerMaintenance
{
 private:
  CCM_CarRental_impl* component;

 public:
  maintenance_impl ( CCM_CarRental_impl* component_impl );
  virtual ~maintenance_impl (  );


  virtual void createCustomer ( const Customer& person ) 
    throw (LocalComponents::CCMException, CreateCustomerException );
  virtual Customer retrieveCustomer ( const long id ) 
    throw (LocalComponents::CCMException, NoCustomerException );
  virtual CustomerList retrieveAllCustomers (  ) 
    throw (LocalComponents::CCMException, NoCustomerException );
  virtual void updateCustomer ( const Customer& person ) 
    throw (LocalComponents::CCMException, NoCustomerException );
  virtual void deleteCustomer ( const long id ) 
    throw (LocalComponents::CCMException, RemoveCustomerException );
};

//==============================================================================
// business - facet adapter implementation class
//==============================================================================

class business_impl
  : public CCM_CustomerBusiness
{
 private:
  CCM_CarRental_impl* component;
  double _dollars_per_mile; 

 public:
  business_impl ( CCM_CarRental_impl* component_impl );
  virtual ~business_impl (  );

  virtual double dollars_per_mile (  ) throw ( LocalComponents::CCMException);
  virtual void dollars_per_mile ( const double value ) throw ( LocalComponents::CCMException);

  virtual void addCustomerMiles ( const long id, const double miles ) throw (LocalComponents::CCMException, NoCustomerException );
#ifdef CCM_TEST_PYTHON
  void call_python_addCustomerMiles ( const long id, const double miles ) throw (LocalComponents::CCMException, NoCustomerException );
#endif
  virtual void resetCustomerMiles ( const long id ) throw (LocalComponents::CCMException, NoCustomerException );
#ifdef CCM_TEST_PYTHON
  void call_python_resetCustomerMiles ( const long id ) throw (LocalComponents::CCMException, NoCustomerException );
#endif
  virtual double getCustomerMiles ( const long id ) throw (LocalComponents::CCMException, NoCustomerException );
#ifdef CCM_TEST_PYTHON
  double call_python_getCustomerMiles ( const long id ) throw (LocalComponents::CCMException, NoCustomerException );
#endif
  virtual double getCustomerDollars ( const long id ) throw (LocalComponents::CCMException, NoCustomerException );
#ifdef CCM_TEST_PYTHON
  double call_python_getCustomerDollars ( const long id ) throw (LocalComponents::CCMException, NoCustomerException );
#endif

};



} // /namespace CCM_Session_CarRental
} // /namespace BigBusiness
} // /namespace CCM_Local


#endif


