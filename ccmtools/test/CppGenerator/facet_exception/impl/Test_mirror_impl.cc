
/***
 * Test_mirror component business logic implementation.
 * 
 * // TODO: WRITE YOUR DESCRIPTION HERE! 
 *
 * @author
 * @version 
 *
 * This file structure was automatically generated by CCM Tools
 * <http://ccmtools.sourceforge.net/> and contains a component's
 * implementation classes. 
 ***/

#include <cassert>
#include <iostream>
#include <WX/Utils/debug.h>

#include "Test_mirror_impl.h"

using namespace std;
using namespace WX::Utils;
using namespace CCM_Local;

namespace CCM_Local {
namespace CCM_Session_Test_mirror {

//==============================================================================
// CCM_Test_mirror - component implementation
//==============================================================================

CCM_Test_mirror_impl::CCM_Test_mirror_impl()
{
    DEBUGNL("+CCM_Test_mirror_impl->CCM_Test_mirror_impl()");
}

CCM_Test_mirror_impl::~CCM_Test_mirror_impl()
{
    DEBUGNL("-CCM_Test_mirror_impl->~CCM_Test_mirror_impl()");
}

void
CCM_Test_mirror_impl::set_session_context(
    LocalComponents::SessionContext* context)
    throw(LocalComponents::CCMException)
{
    DEBUGNL(" CCM_Test_mirror_impl->set_session_context()");
    ctx = dynamic_cast<CCM_Test_mirror_Context*>(context);
}

void
CCM_Test_mirror_impl::ccm_activate()
    throw(LocalComponents::CCMException)
{
    DEBUGNL(" CCM_Test_mirror_impl->ccm_activate()");

    string s = "Salomon.Automation";
    long len =  ctx->get_connection_console_mirror()->println(s);
    assert(len == s.length());
  
    try {
        string s = "Error";
        ctx->get_connection_console_mirror()->println(s);
        assert(0);
    }
    catch(CCM_Local::Error& e) {
        cout << "OK: error exception catched! ";
        cout << "(" 
	     << e.info[0].code << ", " 
	     << e.info[0].message << ")" 
	     << endl;
      LDEBUGNL(CCM_LOCAL, ccmDebug(e));
    }
    
    try {
        string s = "SuperError";
        ctx->get_connection_console_mirror()->println(s);
        assert(0);
    }
    catch(CCM_Local::SuperError& e) {
      cout << "OK: super_error exception catched!" << endl;
      LDEBUGNL(CCM_LOCAL, ccmDebug(e));
    }
  
    try {
        string s = "FatalError";
        ctx->get_connection_console_mirror()->println(s);
        assert(0);
    }
    catch(CCM_Local::FatalError& e) {
      cout << "OK: fatal_error exception catched!" << endl;
      LDEBUGNL(CCM_LOCAL, ccmDebug(e));
    }
}

void
CCM_Test_mirror_impl::ccm_passivate()
    throw(LocalComponents::CCMException)
{
    DEBUGNL(" CCM_Test_mirror_impl->ccm_passivate()");

    // OPTIONAL : IMPLEMENT ME HERE !
}

void
CCM_Test_mirror_impl::ccm_remove()
    throw(LocalComponents::CCMException)
{
    DEBUGNL(" CCM_Test_mirror_impl->ccm_remove()");

    // OPTIONAL : IMPLEMENT ME HERE !
}

} // /namespace CCM_Session_Test_mirror
} // /namespace CCM_Local

