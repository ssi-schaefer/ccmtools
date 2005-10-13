
/***
 * This file was automatically generated by CCM Tools version 0.5.3-pre3
 * <http://ccmtools.sourceforge.net/>
 *
 * Test component business logic implementation.
 * 
 * // TODO: WRITE YOUR DESCRIPTION HERE! 
 *
 * @author
 * @version 
 ***/

#include <cassert>
#include <iostream>
#include <WX/Utils/debug.h>

#include "Test_impl.h"
#include "Test_inPort_impl.h"

namespace ccm {
namespace local {
namespace component {
namespace Test {

using namespace std;
using namespace WX::Utils;

//==============================================================================
// CCM_Test - component implementation
//==============================================================================

CCM_Test_impl::CCM_Test_impl()
{
    // OPTIONAL : IMPLEMENT ME HERE !
}

CCM_Test_impl::~CCM_Test_impl()
{
    // OPTIONAL : IMPLEMENT ME HERE !
}

void
CCM_Test_impl::set_session_context(
    ::ccm::local::Components::SessionContext* context)
    throw(::ccm::local::Components::CCMException)
{
    ctx = dynamic_cast<CCM_Test_Context*>(context);
}

void
CCM_Test_impl::ccm_activate()
    throw(::ccm::local::Components::CCMException)
{
    string s = "Salomon.Automation";
    long len =  ctx->get_connection_outPort()->print(s);
    assert(len == (long)s.length());
  
    try {
        string s = "SimpleError";
        ctx->get_connection_outPort()->print(s);
        assert(0);
    }
    catch(SimpleError& e) {
        cout << "OK: error exception catched! ";
        cout << "(" 
             << e.info[0].code << ", " 
             << e.info[0].message << ")" 
             << endl;
      LDEBUGNL(CCM_LOCAL, ccmDebug(e));
    }
    
    try {
        string s = "SuperError";
        ctx->get_connection_outPort()->print(s);
        assert(0);
    }
    catch(SuperError& e) {
      cout << "OK: super_error exception catched!" << endl;
      LDEBUGNL(CCM_LOCAL, ccmDebug(e));
    }
  
    try {
        string s = "FatalError";
        ctx->get_connection_outPort()->print(s);
        assert(0);
    }
    catch(Components::Exception& e) {
      // catch base class exception 
      cout << e.what() << endl;
    }
    catch(FatalError& e) {
      cout << "OK: fatal_error exception catched!" << endl;
      LDEBUGNL(CCM_LOCAL, ccmDebug(e));
    }

}

void
CCM_Test_impl::ccm_passivate()
    throw(::ccm::local::Components::CCMException)
{
    // OPTIONAL : IMPLEMENT ME HERE !
}

void
CCM_Test_impl::ccm_remove()
    throw(::ccm::local::Components::CCMException)
{
    // OPTIONAL : IMPLEMENT ME HERE !
}

//==============================================================================
// ccm::local::CCM_Console facet implementation
//==============================================================================

ccm::local::CCM_Console*
CCM_Test_impl::get_inPort()
{
    inPort_impl* facet = new inPort_impl(this);
    return dynamic_cast< ccm::local::CCM_Console*>(facet);
}

} // /namespace Test
} // /namespace component
} // /namespace local
} // /namespace ccm

