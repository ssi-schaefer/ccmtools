
//==============================================================================
// Hello_mirror - business logic implementation
//==============================================================================

#include <iostream>
#include <CCM_Utils/Debug.h>

#include "Hello_mirror_app.h"

using namespace std;
using namespace CCM_Utils;

namespace CCM_Local {
namespace CCM_Session_Hello_mirror {


//==============================================================================
// business logic functionality
//==============================================================================

CCM_Console*
CCM_Hello_mirror_impl::get_console_mirror (  )
{
  DEBUGNL ( " CCM_Hello_mirror_impl->get_console_mirror (  )" );
  console_mirror_impl* facet = new console_mirror_impl (  );
  facet->set_component ( this );
  return dynamic_cast<CCM_Console*> ( facet );
}



//==============================================================================
// console_mirror - facet implementation
//==============================================================================

console_mirror_impl::console_mirror_impl
 (  )
{
  DEBUGNL ( "+console_mirror_impl->console_mirror_impl (  )" );
}

console_mirror_impl::~console_mirror_impl (  )
{
  DEBUGNL ( "-console_mirror_impl->~console_mirror_impl (  )" );
}

void
console_mirror_impl::set_component ( CCM_Hello_mirror_impl* c )
{
  DEBUGNL ( " console_mirror_impl->set_component (  )" );
  component = c;
}

long
console_mirror_impl::println ( const std::string& s2 )
  
{
  DEBUGNL ( " console_mirror_impl->println ( s2 )" );

  // TODO : IMPLEMENT ME HERE !
  cout << s2 << endl;
  return s2.length();
}





//==============================================================================
// class implementation
//==============================================================================

CCM_Hello_mirror_impl::CCM_Hello_mirror_impl (  )
{
  DEBUGNL ( "+CCM_Hello_mirror_impl->CCM_Hello_mirror_impl (  )" );
}

CCM_Hello_mirror_impl::~CCM_Hello_mirror_impl (  )
{
  DEBUGNL ( "-CCM_Hello_mirror_impl->~CCM_Hello_mirror_impl (  )" );
}




void
CCM_Hello_mirror_impl::set_session_context ( localComponents::SessionContext* context )
  throw ( localComponents::CCMException )
{
  DEBUGNL ( " CCM_Hello_mirror_impl->set_session_context (  )" );
  ctx = (CCM_Hello_mirror_Context*) context;
}

void
CCM_Hello_mirror_impl::ccm_activate (  )
  throw ( localComponents::CCMException )
{
  DEBUGNL ( " CCM_Hello_mirror_impl->ccm_activate (  )" );
}

void
CCM_Hello_mirror_impl::ccm_passivate (  )
  throw ( localComponents::CCMException )
{
  DEBUGNL ( " CCM_Hello_mirror_impl->ccm_passivate (  )" );
}

void
CCM_Hello_mirror_impl::ccm_remove (  )
  throw ( localComponents::CCMException )
{
  DEBUGNL ( " CCM_Hello_mirror_impl->ccm_remove (  )" );
}

} // /namespace CCM_Session_Hello_mirror
} // /namespace CCM_Local



