
/**
 * This file was automatically generated by CCM Tools version 0.5.4
 * <http://ccmtools.sourceforge.net/>
 *
 * ccm::local::CCM_SubType facet class implementation. 
 *
 * // TODO: WRITE YOUR DESCRIPTION HERE! 
 *
 * @author
 * @version 
 **/

#include <cassert>
#include <iostream>
#include <wx/utils/debug.h>

#include "Test_ifaceIn_impl.h"

using namespace std;
using namespace wx::utils;

namespace ccm {
namespace local {
namespace component {
namespace Test {

ifaceIn_impl::ifaceIn_impl(
    ccm::local::component::Test::CCM_Test_impl* component_impl)
  : component(component_impl)
{
    // OPTIONAL : IMPLEMENT ME HERE !
}

ifaceIn_impl::~ifaceIn_impl()
{
    // OPTIONAL : IMPLEMENT ME HERE !
}

const long
ifaceIn_impl::attr3() const
    throw(::Components::ccm::local::CCMException)
{
    return attr3_;
}

void
ifaceIn_impl::attr3(const long value)
    throw(::Components::ccm::local::CCMException)
{
    attr3_ = value;
}

const long
ifaceIn_impl::attr2() const
    throw(::Components::ccm::local::CCMException)
{
    return attr2_;
}

void
ifaceIn_impl::attr2(const long value)
    throw(::Components::ccm::local::CCMException)
{
    attr2_ = value;
}

const long
ifaceIn_impl::attr1() const
    throw(::Components::ccm::local::CCMException)
{
    return attr1_;
}

void
ifaceIn_impl::attr1(const long value)
    throw(::Components::ccm::local::CCMException)
{
    attr1_ = value;
}

long
ifaceIn_impl::op3(const std::string& str)
throw(::Components::ccm::local::CCMException)
{
    cout << str << endl;
    return str.length();
}

long
ifaceIn_impl::op2(const std::string& str)
throw(::Components::ccm::local::CCMException)
{
    cout << str << endl;
    return str.length();
}

long
ifaceIn_impl::op1(const std::string& str)
throw(::Components::ccm::local::CCMException)
{
    cout << str << endl;
    return str.length();
}

} // /namespace Test
} // /namespace component
} // /namespace local
} // /namespace ccm
