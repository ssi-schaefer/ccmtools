/***
 * Test component business logic implementation.
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
#include <wamas/platform/utils/debug.h>

#include "Test_impl.h"

namespace ccm {
namespace local {

using namespace std;
using namespace wamas::platform::utils;

//==============================================================================
// CCM_Test - component implementation
//==============================================================================

Test_impl::Test_impl()
{
}

Test_impl::~Test_impl()
{
}

void
Test_impl::set_session_context(
    Components::ccm::local::SessionContext* context)
    throw(Components::ccm::local::CCMException)
{
    ctx = dynamic_cast<CCM_Test_Context*>(context);
}

void
Test_impl::ccm_activate()
    throw(Components::ccm::local::CCMException)
{
    string s = "Salomon.Automation";
    long len =  ctx->get_connection_console()->print(s);
    assert(len == s.length());
  
    try {
        string s = "Error";
        ctx->get_connection_console()->print(s);
        assert(0);
    }
    catch(Error& e) {
        cout << "OK: error exception catched! ";
        cout << "(" 
             << e.info[0].code << ", " 
             << e.info[0].message << ")" 
             << endl;
    }
    
    try {
        string s = "SuperError";
        ctx->get_connection_console()->print(s);
        assert(0);
    }
    catch(SuperError& e) {
        cout << "OK: super_error exception catched!" << endl;
    }
  
    try {
        string s = "FatalError";
        ctx->get_connection_console()->print(s);
        assert(0);
    }
    catch(FatalError& e) {
        cout << "OK: fatal_error exception catched!" << endl;
    }
}

void
Test_impl::ccm_passivate()
    throw(Components::ccm::local::CCMException)
{
    // OPTIONAL : IMPLEMENT ME HERE !
}

void
Test_impl::ccm_remove()
    throw(Components::ccm::local::CCMException)
{
    // OPTIONAL : IMPLEMENT ME HERE !
}

} // /namespace local
} // /namespace ccm

