
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

  cout << "==== Begin of Test Case =========================" << endl;
  CCM_Utils::SmartPtr<CCM_Console> console = ctx->get_connection_console_mirror();
  
  string s = "Salomon.Automation";
  long len = console.ptr()->println(s);
  assert(len == s.length());
  
  try {
    string s = "error";
    console.ptr()->println(s);
    assert(0);
  }
  catch(CCM_Local::error& e) {
    cout << "OK: error exception catched!" << endl;
  }
  
  try {
    string s = "super_error";
    console.ptr()->println(s);
    assert(0);
  }
  catch(CCM_Local::super_error& e) {
    cout << "OK: super_error exception catched!" << endl;
  }
  
  try {
    string s = "fatal_error";
    console.ptr()->println(s);
    assert(0);
  }
  catch(CCM_Local::fatal_error& e) {
    cout << "OK: fatal_error exception catched!" << endl;
  }
  
  cout << "==== End of Test Case ===========================" << endl;
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



