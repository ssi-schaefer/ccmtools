
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
#include <wx/utils/debug.h>

#include "Test_impl.h"

namespace ccm {
namespace local {

using namespace std;
using namespace wx::utils;


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
    // basic types test cases
    long long_2=3, long_3, long_r;
    long_r = ctx->get_connection_iface()->op_b1(7,long_2, long_3);
    assert(long_2 == 7);
    assert(long_3 == 3);
    assert(long_r == 3+7);
    
    string string_2="drei", string_3, string_r;
    string_r = ctx->get_connection_iface()->op_b2("sieben",string_2, string_3);
    assert(string_2 == "sieben");
    assert(string_3 == "drei");
    assert(string_r == "dreisieben");
}

void
Test_impl::ccm_passivate()
    throw(::Components::ccm::local::CCMException)
{
    // OPTIONAL : IMPLEMENT ME HERE !
}

void
Test_impl::ccm_remove()
    throw(::Components::ccm::local::CCMException)
{
    // OPTIONAL : IMPLEMENT ME HERE !
}

} // /namespace local
} // /namespace ccm

