
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
// CCM_Test - component implementation
//==============================================================================

Test_impl::Test_impl()
{
}

Test_impl::~Test_impl()
{
}

long
Test_impl::op3(const std::string& str)
    throw (::Components::ccm::local::CCMException)
{
    cout << str << endl;
    return str.length();
}

long
Test_impl::op2(const std::string& str)
    throw (::Components::ccm::local::CCMException)
{
    cout << str << endl;
    return str.length();
}

long
Test_impl::op1(const std::string& str)
    throw (::Components::ccm::local::CCMException)
{
    cout << str << endl;
    return str.length();
}

void
Test_impl::set_session_context(
    Components::ccm::local::SessionContext* context)
    throw(::Components::ccm::local::CCMException)
{
    ctx = dynamic_cast<ccm::local::CCM_Test_Context*>(context);
}

void
Test_impl::ccm_activate()
    throw(::Components::ccm::local::CCMException)
{
    // OPTIONAL : IMPLEMENT ME HERE !
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

