
/***
 * This file was automatically generated by CCM Tools version 0.5.3-pre2
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
    // OPTIONAL : IMPLEMENT ME HERE !
}

Test_impl::~Test_impl()
{
    // OPTIONAL : IMPLEMENT ME HERE !
}

void
Test_impl::set_session_context(
    Components::ccm::local::SessionContext* context)
    throw(::Components::ccm::local::CCMException)
{
    ctx = dynamic_cast<CCM_Test_Context*>(context);
}

void
Test_impl::ccm_activate()
    throw(::Components::ccm::local::CCMException)
{
  try {
    string s = "1234567890";
    ctx->get_connection_console()->println(s);
    assert(false);
  }
  catch(::Components::ccm::local::NoConnection& e) {
    cerr << "!! NoConnection exception: Receptacle console is not connected!" << endl;
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
    throw(::Components::ccm::local::CCMException)
{
    // OPTIONAL : IMPLEMENT ME HERE !
}

} // /namespace local
} // /namespace ccm

