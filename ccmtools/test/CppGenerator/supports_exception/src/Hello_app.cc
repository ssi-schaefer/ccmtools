
//==============================================================================
// Hello - business logic implementation
//==============================================================================

#include <iostream>
#include <CCM_Utils/Debug.h>

#include "Hello_app.h"

using namespace std;
using namespace CCM_Utils;
using namespace CCM_Local;

namespace CCM_Local {
namespace CCM_Session_Hello {


//==============================================================================
// business logic functionality
//==============================================================================





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



long
CCM_Hello_impl::println ( const std::string& s2 )
  throw ( fatal_error, error, super_error )
{
  DEBUGNL ( " CCM_Hello_impl->println ( s2 )" );

  cout << ">> " << s2 << endl;

  if(s2 == "error")
    throw error();
  if(s2 == "super_error")
    throw super_error();
  if(s2 == "fatal_error")
    throw fatal_error();

  return s2.length();
}



void
CCM_Hello_impl::set_session_context ( LocalComponents::SessionContext* context )
  throw ( LocalComponents::CCMException )
{
  DEBUGNL ( " CCM_Hello_impl->set_session_context (  )" );
  ctx = dynamic_cast<CCM_Hello_Context*> ( context );
}

void
CCM_Hello_impl::ccm_activate (  )
  throw ( LocalComponents::CCMException )
{
  DEBUGNL ( " CCM_Hello_impl->ccm_activate (  )" );
}

void
CCM_Hello_impl::ccm_passivate (  )
  throw ( LocalComponents::CCMException )
{
  DEBUGNL ( " CCM_Hello_impl->ccm_passivate (  )" );
}

void
CCM_Hello_impl::ccm_remove (  )
  throw ( LocalComponents::CCMException )
{
  DEBUGNL ( " CCM_Hello_impl->ccm_remove (  )" );
}

} // /namespace CCM_Session_Hello
} // /namespace CCM_Local



