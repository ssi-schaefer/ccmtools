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
#include <wx/utils/debug.h>

#include "TestMirror_impl.h"

namespace ccm {
namespace local {
namespace component {
namespace TestMirror {

using namespace std;
using namespace wx::utils;

//==============================================================================
// CCM_TestMirror - component implementation
//==============================================================================

CCM_TestMirror_impl::CCM_TestMirror_impl()
{
}

CCM_TestMirror_impl::~CCM_TestMirror_impl()
{
}

void
CCM_TestMirror_impl::set_session_context(
    Components::ccm::local::SessionContext* context)
    throw(Components::ccm::local::CCMException)
{
    ctx = dynamic_cast<CCM_TestMirror_Context*>(context);
}

void
CCM_TestMirror_impl::ccm_activate()
    throw(Components::ccm::local::CCMException)
{
    string s = "Salomon.Automation";
    long len =  ctx->get_connection_console()->println(s);
    assert(len == (long)s.length());
  
    try {
        string s = "Error";
        ctx->get_connection_console()->println(s);
        assert(0);
    }
    catch(Error& e) {
        cout << "OK: error exception catched! ";
        cout << "(" 
	     << e.info[0].code << ", " 
	     << e.info[0].message << ")" 
	     << endl;
      LDEBUGNL(CCM_LOCAL, ccmDebug(e));
    }
    
    try {
        string s = "SuperError";
        ctx->get_connection_console()->println(s);
        assert(0);
    }
    catch(SuperError& e) {
      cout << "OK: super_error exception catched!" << endl;
      LDEBUGNL(CCM_LOCAL, ccmDebug(e));
    }
  
    try {
        string s = "FatalError";
        ctx->get_connection_console()->println(s);
        assert(0);
    }
    catch(Components::ccm::local::Exception& e) {
      // catch base class exception 
      cout << e.what() << endl;
    }
    catch(FatalError& e) {
      cout << "OK: fatal_error exception catched!" << endl;
      LDEBUGNL(CCM_LOCAL, ccmDebug(e));
    }
}

void
CCM_TestMirror_impl::ccm_passivate()
    throw(Components::ccm::local::CCMException)
{
    // OPTIONAL : IMPLEMENT ME HERE !
}

void
CCM_TestMirror_impl::ccm_remove()
    throw(Components::ccm::local::CCMException)
{
    // OPTIONAL : IMPLEMENT ME HERE !
}

} // /namespace TestMirror
} // /namespace component
} // /namespace local
} // /namespace ccm

