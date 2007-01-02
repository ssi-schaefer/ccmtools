
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

#include "TestMirror_impl.h"

using namespace std;
using namespace wamas::platform::utils;

//==============================================================================
// CCM_Test_mirror - component implementation
//==============================================================================

TestMirror_impl::TestMirror_impl()
{
}

TestMirror_impl::~TestMirror_impl()
{
}

void
TestMirror_impl::set_session_context(
    ::Components::SessionContext* context)
    throw(::Components::CCMException)
{
    ctx = dynamic_cast<ccm::local::CCM_TestMirror_Context*>(context);
}

void
TestMirror_impl::ccm_activate()
    throw(::Components::CCMException)
{
    {
      const long maxSize = 10;
      ctx->get_connection_console()->max_size(maxSize);
      assert(ctx->get_connection_console()->max_size() == maxSize);
    }

    {
      const string msg = "12345";
      long result = ctx->get_connection_console()->print(msg);
      cout << msg.length() << ", " << result << endl;
      assert(result == msg.length());
    }

    {
      const string msg = "1234567890123";
      long result = ctx->get_connection_console()->print(msg);
      cout << msg.length() << ", " << result << endl;
      assert(result == ctx->get_connection_console()->max_size());
    }
}

void
TestMirror_impl::ccm_passivate()
    throw(::Components::CCMException)
{
    // OPTIONAL : IMPLEMENT ME HERE !
}

void
TestMirror_impl::ccm_remove()
    throw(::Components::CCMException)
{
    // OPTIONAL : IMPLEMENT ME HERE !
}

