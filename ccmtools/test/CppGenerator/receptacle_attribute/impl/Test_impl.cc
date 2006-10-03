
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
namespace component {
namespace Test {

using namespace std;
using namespace wx::utils;

//==============================================================================
// CCM_Test - component implementation
//==============================================================================

CCM_Test_impl::CCM_Test_impl()
{
}

CCM_Test_impl::~CCM_Test_impl()
{
}

void
CCM_Test_impl::set_session_context(
    Components::ccm::local::SessionContext* context)
    throw(Components::ccm::local::CCMException)
{
  ctx = dynamic_cast<CCM_Test_Context*>(context);
}

void
CCM_Test_impl::ccm_activate()
    throw(Components::ccm::local::CCMException)
{
  const long maxSize = 10;
  ctx->get_connection_console()->max_size(maxSize);
  assert(ctx->get_connection_console()->max_size() == maxSize);
}

void
CCM_Test_impl::ccm_passivate()
    throw(Components::ccm::local::CCMException)
{
    // OPTIONAL : IMPLEMENT ME HERE !
}

void
CCM_Test_impl::ccm_remove()
    throw(Components::ccm::local::CCMException)
{
    // OPTIONAL : IMPLEMENT ME HERE !
}

} // /namespace Test
} // /namespace component
} // /namespace local
} // /namespace ccm

