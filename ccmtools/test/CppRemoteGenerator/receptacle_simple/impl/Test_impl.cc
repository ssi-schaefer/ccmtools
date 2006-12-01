
/***
 * This file was automatically generated by CCM Tools
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
#include <wamas/platform/utils/debug.h>

#include "Test_impl.h"
#include "Test_in_port_impl.h"

namespace ccm {
namespace local {

using namespace std;
using namespace wamas::platform::utils;

//==============================================================================
// Test - component implementation
//==============================================================================

Test_impl::Test_impl()
{
    // OPTIONAL : IMPLEMENT ME HERE !
}

Test_impl::~Test_impl()
{
    // OPTIONAL : IMPLEMENT ME HERE !
}

void
Test_impl::set_session_context(
    ::Components::ccm::local::SessionContext* context)
    throw(::Components::ccm::local::CCMException)
{
    ctx = dynamic_cast<CCM_Test_Context*>(context);
}

void
Test_impl::ccm_activate()
    throw(::Components::ccm::local::CCMException)
{
    string s = "0123456789";
    long l;
    l = ctx->get_connection_out_port()->op1(s);
    assert(l == s.length());
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

//==============================================================================
// ccm::local::CCM_I2 facet implementation
//==============================================================================

ccm::local::CCM_I2*
Test_impl::get_in_port()
{
    Test_in_port_impl* facet = new Test_in_port_impl(this);
    return dynamic_cast< ccm::local::CCM_I2*>(facet);
}

} // /namespace local
} // /namespace ccm

