
//==============================================================================
// Calculator - business logic implementation
//==============================================================================

#include <iostream>
#include <CCM_Utils/Debug.h>

#include "Calculator_app.h"

using namespace CCM_Utils;

namespace CCM_Local {
namespace CCM_Session_Calculator {
;

//==============================================================================
// business logic functionality
//==============================================================================

CCM_CalculatorFacade*
CCM_Calculator_impl::get_calc (  )
{
  DEBUGNL ( " CCM_Calculator_impl->get_calc (  )" );
  calc_impl* facet = new calc_impl (  );
  facet->set_component ( this );
  return dynamic_cast<CCM_CalculatorFacade*> ( facet );
}



//==============================================================================
// calc - facet implementation
//==============================================================================

calc_impl::calc_impl
 (  )
{
  DEBUGNL ( "+calc_impl->calc_impl (  )" );
}

calc_impl::~calc_impl (  )
{
  DEBUGNL ( "-calc_impl->~calc_impl (  )" );
}

void
calc_impl::set_component ( CCM_Calculator_impl* c )
{
  DEBUGNL ( " calc_impl->set_component (  )" );
  component = c;
}

void
calc_impl::print ( const std::string& s )
  
{
  DEBUGNL ( " calc_impl->print ( s )" );

  // TODO : IMPLEMENT ME HERE !
}

void
calc_impl::write ( const Record& r )
  throw ( WriteException )
{
  DEBUGNL ( " calc_impl->write ( r )" );

  // TODO : IMPLEMENT ME HERE !
}

RecordSet
calc_impl::read ( const Record& r )
  throw ( ReadException )
{
  DEBUGNL ( " calc_impl->read ( r )" );

  // TODO : IMPLEMENT ME HERE !
  return RecordSet();
}

long
calc_impl::sub ( const long a, const long b )
  throw ( MathException )
{
  DEBUGNL ( " calc_impl->sub ( a, b )" );

  // TODO : IMPLEMENT ME HERE !
  return a-b;
}

long
calc_impl::add ( const long a, const long b )
  throw ( MathException )
{
  DEBUGNL ( " calc_impl->add ( a, b )" );

  // TODO : IMPLEMENT ME HERE !
  return a+b;
}





//==============================================================================
// class implementation
//==============================================================================

CCM_Calculator_impl::CCM_Calculator_impl (  )
{
  DEBUGNL ( "+CCM_Calculator_impl->CCM_Calculator_impl (  )" );
}

CCM_Calculator_impl::~CCM_Calculator_impl (  )
{
  DEBUGNL ( "-CCM_Calculator_impl->~CCM_Calculator_impl (  )" );
}

long
CCM_Calculator_impl::SerNr (  )
{
  DEBUGNL ( " CCM_Calculator_impl->SerNr (  )" );
  return SerNr_;
}

void
CCM_Calculator_impl::SerNr ( const long value )
{
  DEBUGNL ( " CCM_Calculator_impl->SerNr (  )" );
  SerNr_ = value;
}

double
CCM_Calculator_impl::ats_to_eur ( const double ats )
  
{
#ifdef CCM_TEST_PYTHON
  DEBUGNL ( " CCM_Calculator_impl->call_python_ats_to_eur ( ats )" );
  return call_python_ats_to_eur ( ats );
#else
  DEBUGNL ( " CCM_Calculator_impl->ats_to_eur ( ats )" );

  // TODO : IMPLEMENT ME HERE !
#endif
}

double
CCM_Calculator_impl::eur_to_ats ( const double eur )
  
{
#ifdef CCM_TEST_PYTHON
  DEBUGNL ( " CCM_Calculator_impl->call_python_eur_to_ats ( eur )" );
  return call_python_eur_to_ats ( eur );
#else
  DEBUGNL ( " CCM_Calculator_impl->eur_to_ats ( eur )" );

  // TODO : IMPLEMENT ME HERE !
#endif
}



void
CCM_Calculator_impl::set_session_context ( localComponents::SessionContext* context )
  throw ( localComponents::CCMException )
{
  DEBUGNL ( " CCM_Calculator_impl->set_session_context (  )" );
  ctx = (CCM_Calculator_Context*) context;
}

void
CCM_Calculator_impl::ccm_activate (  )
  throw ( localComponents::CCMException )
{
  DEBUGNL ( " CCM_Calculator_impl->ccm_activate (  )" );
}

void
CCM_Calculator_impl::ccm_passivate (  )
  throw ( localComponents::CCMException )
{
  DEBUGNL ( " CCM_Calculator_impl->ccm_passivate (  )" );
}

void
CCM_Calculator_impl::ccm_remove (  )
  throw ( localComponents::CCMException )
{
  DEBUGNL ( " CCM_Calculator_impl->ccm_remove (  )" );
}

} // /namespace CCM_Session_Calculator
} // /namespace CCM_Local



