
//==============================================================================
// Hello_mirror - business logic implementation
//==============================================================================

#include <cassert>
#include <iostream>
#include <WX/Utils/debug.h>

#include "Hello_mirror_impl.h"

using namespace std;
using namespace WX::Utils;

namespace CCM_Local {
namespace CCM_Session_Hello_mirror {


//==============================================================================
// business logic functionality
//==============================================================================

CCM_Console*
CCM_Hello_mirror_impl::get_console_mirror (  )
{
  DEBUGNL ( " CCM_Hello_mirror_impl->get_console_mirror (  )" );
  console_mirror_impl* facet = new console_mirror_impl(this);
  return dynamic_cast<CCM_Console*> ( facet );
}



//==============================================================================
// console_mirror - facet implementation
//==============================================================================

console_mirror_impl::console_mirror_impl(CCM_Hello_mirror_impl* component_impl)
  : component(component_impl)
{
  DEBUGNL ( "+console_mirror_impl->console_mirror_impl (  )" );
}

console_mirror_impl::~console_mirror_impl (  )
{
  DEBUGNL ( "-console_mirror_impl->~console_mirror_impl (  )" );
}

long
console_mirror_impl::println ( const std::string& s2 )
  throw (LocalComponents::CCMException)  
{
  DEBUGNL ( " console_mirror_impl->println ( s2 )" );

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
CCM_Hello_mirror_impl::set_session_context ( LocalComponents::SessionContext* context )
  throw ( LocalComponents::CCMException )
{
  DEBUGNL ( " CCM_Hello_mirror_impl->set_session_context (  )" );
  ctx = dynamic_cast<CCM_Hello_mirror_Context*>(context);
}

void
CCM_Hello_mirror_impl::ccm_activate (  )
  throw ( LocalComponents::CCMException )
{
  DEBUGNL ( " CCM_Hello_mirror_impl->ccm_activate (  )" );
}

void
CCM_Hello_mirror_impl::ccm_passivate (  )
  throw ( LocalComponents::CCMException )
{
  DEBUGNL ( " CCM_Hello_mirror_impl->ccm_passivate (  )" );
}

void
CCM_Hello_mirror_impl::ccm_remove (  )
  throw ( LocalComponents::CCMException )
{
  DEBUGNL ( " CCM_Hello_mirror_impl->ccm_remove (  )" );
}

} // /namespace CCM_Session_Hello_mirror
} // /namespace CCM_Local



