
//==============================================================================
// Hello - business logic implementation
//==============================================================================

#include <iostream>
#include <CCM_Utils/Debug.h>

#include "Hello_app.h"

using namespace CCM_Utils;

namespace CCM_Local {
namespace WORLD {
namespace CCM_Session_Hello {
;

//==============================================================================
// business logic functionality
//==============================================================================

CCM_Console*
CCM_Hello_impl::get_console (  )
{
  DEBUGNL ( " CCM_Hello_impl->get_console (  )" );
  console_impl* facet = new console_impl (  );
  facet->set_component ( this );
  return dynamic_cast<CCM_Console*> ( facet );
}



//==============================================================================
// console - facet implementation
//==============================================================================

console_impl::console_impl
 (  )
{
  DEBUGNL ( "+console_impl->console_impl (  )" );
}

console_impl::~console_impl (  )
{
  DEBUGNL ( "-console_impl->~console_impl (  )" );
}

void
console_impl::set_component ( CCM_Hello_impl* c )
{
  DEBUGNL ( " console_impl->set_component (  )" );
  component = c;
}

std::string
console_impl::read_string ( const std::string& prompt )
  
{
  DEBUGNL ( " console_impl->read_string ( prompt )" );

  return component->prompt (  ) + " Hello component world!";
}





//==============================================================================
// class implementation
//==============================================================================

CCM_Hello_impl::CCM_Hello_impl (  )
{
  DEBUGNL ( "+CCM_Hello_impl->CCM_Hello_impl (  )" );
}

CCM_Hello_impl::~CCM_Hello_impl (  )
{
  DEBUGNL ( "-CCM_Hello_impl->~CCM_Hello_impl (  )" );
}

std::string
CCM_Hello_impl::prompt (  )
{
  DEBUGNL ( " CCM_Hello_impl->prompt (  )" );
  return prompt_;
}

void
CCM_Hello_impl::prompt ( const std::string value )
{
  DEBUGNL ( " CCM_Hello_impl->prompt (  )" );
  prompt_ = value;
}



void
CCM_Hello_impl::set_session_context ( localComponents::SessionContext* context )
  throw ( localComponents::CCMException )
{
  DEBUGNL ( " CCM_Hello_impl->set_session_context (  )" );
  ctx = (CCM_Hello_Context*) context;
}

void
CCM_Hello_impl::ccm_activate (  )
  throw ( localComponents::CCMException )
{
  DEBUGNL ( " CCM_Hello_impl->ccm_activate (  )" );
}

void
CCM_Hello_impl::ccm_passivate (  )
  throw ( localComponents::CCMException )
{
  DEBUGNL ( " CCM_Hello_impl->ccm_passivate (  )" );
}

void
CCM_Hello_impl::ccm_remove (  )
  throw ( localComponents::CCMException )
{
  DEBUGNL ( " CCM_Hello_impl->ccm_remove (  )" );
}

} // /namespace CCM_Session_Hello
} // /namespace WORLD
} // /namespace CCM_Local



