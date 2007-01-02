
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

#include "Test_impl.h"

using namespace std;
using namespace wamas::platform::utils;

//==============================================================================
// Test - component implementation
//==============================================================================

Test_impl::Test_impl()
{
}

Test_impl::~Test_impl()
{
}

void
Test_impl::set_session_context(
    ::Components::SessionContext* context)
    throw(::Components::CCMException)
{
  ctx = dynamic_cast<ccm::local::CCM_Test_Context*>(context);
}

void
Test_impl::ccm_activate()
    throw(::Components::CCMException)
{
  const long maxSize = 10;
  ctx->get_connection_console()->max_size(maxSize);
  assert(ctx->get_connection_console()->max_size() == maxSize);
}

void
Test_impl::ccm_passivate()
    throw(::Components::CCMException)
{
    // OPTIONAL : IMPLEMENT ME HERE !
}

void
Test_impl::ccm_remove()
    throw(::Components::CCMException)
{
    // OPTIONAL : IMPLEMENT ME HERE !
}

