
//==============================================================================
// Hello - business logic implementation
//==============================================================================

#include <iostream>
#include <CCM_Utils/Debug.h>

#include "Hello_app.h"

using namespace std;
using namespace CCM_Utils;

namespace CCM_Local {
namespace CCM_Session_Hello {


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

long
console_impl::println ( const std::string& s2 )
  throw ( error )
{
  DEBUGNL ( " console_impl->println ( s2 )" );

  // TODO : IMPLEMENT ME HERE !
  cout << ">> " << s2 << endl;
  throw error();
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
} // /namespace CCM_Local



