
//==============================================================================
// Hello - business logic implementation
//==============================================================================

#include <cassert>
#include <iostream>
#include <WX/Utils/debug.h>

#include "Hello_app.h"

using namespace std;
using namespace WX::Utils;

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




void
CCM_Hello_impl::set_session_context ( LocalComponents::SessionContext* context )
  throw ( LocalComponents::CCMException )
{
  DEBUGNL ( " CCM_Hello_impl->set_session_context (  )" );
  ctx = dynamic_cast<CCM_Hello_Context*>(context);
}

void
CCM_Hello_impl::ccm_activate (  )
  throw ( LocalComponents::CCMException )
{
  DEBUGNL ( " CCM_Hello_impl->ccm_activate (  )" );

  cout << "=== Begin test case =======================================" << endl;
  consoleConnections multiCon = ctx->get_connections_console();
  consoleConnections::const_iterator it;
  long size;
  for(it=multiCon.begin();it != multiCon.end(); ++it) {
    cout << "       ";
    size = it->second.ptr()->println("String from component");
    cout << "Number of printed characters = " << size << endl;
  }
  cout << "=== End test case =========================================" << endl;

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



