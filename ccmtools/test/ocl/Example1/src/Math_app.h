/*
    $Id$
*/

//==============================================================================
// Math - business logic class definition
//==============================================================================

#ifndef __COMPONENT_CCM_Local_CCM_Session_Math_Math_APP__H__
#define __COMPONENT_CCM_Local_CCM_Session_Math_Math_APP__H__

#include <CCM_Local/CCM_Session_Math/Math_share.h>

namespace CCM_Local {
namespace CCM_Session_Math {


//==============================================================================
// component implementation object
//==============================================================================

class CCM_Math_impl
  : public CCM_Math
{
 private:


 public:
  CCM_Math_Context* ctx;

  CCM_Math_impl (  );
  virtual ~CCM_Math_impl (  );


  virtual CCM_IntegerStack* get_stack (  );



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
// stack - facet adapter implementation class
//==============================================================================

class stack_impl
  : public CCM_IntegerStack
{
 private:
  CCM_Math_impl* component;
  IntegerVector field_;
  long maxSize_;

 public:
  stack_impl ( CCM_Math_impl* component_impl );
  virtual ~stack_impl (  );

  virtual IntegerVector field (  );
  virtual void field ( const IntegerVector value );
  virtual long maxSize (  );
  virtual void maxSize ( const long value );

  virtual bool isEmpty (  ) ;
#ifdef CCM_TEST_PYTHON
  bool call_python_isEmpty (  ) ;
#endif
  virtual bool isFull (  ) ;
#ifdef CCM_TEST_PYTHON
  bool call_python_isFull (  ) ;
#endif
  virtual void push ( const long value ) ;
#ifdef CCM_TEST_PYTHON
  void call_python_push ( const long value ) ;
#endif
  virtual long pop (  ) ;
#ifdef CCM_TEST_PYTHON
  long call_python_pop (  ) ;
#endif
  virtual long top (  ) ;
#ifdef CCM_TEST_PYTHON
  long call_python_top (  ) ;
#endif

};



} // /namespace CCM_Session_Math
} // /namespace CCM_Local


#endif


