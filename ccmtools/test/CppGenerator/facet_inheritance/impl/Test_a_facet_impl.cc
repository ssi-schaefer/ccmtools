
/**
 * CCM_InterfaceType facet class implementation. 
 *
 * // TODO: WRITE YOUR DESCRIPTION HERE! 
 *
 * @author
 * @version 
 *
 * This class implements a facet's methods and attributes.
 **/

#include <cassert>
#include <iostream>
#include <wamas/platform/utils/debug.h>

#include "Test_a_facet_impl.h"


namespace ccm {
namespace local {

using namespace std;
using namespace wamas::platform::utils;


Test_a_facet_impl::Test_a_facet_impl(Test_impl* component_impl)
  : component(component_impl)
{
    // OPTIONAL : IMPLEMENT ME HERE !
}

Test_a_facet_impl::~Test_a_facet_impl()
{
    // OPTIONAL : IMPLEMENT ME HERE !
}

const long
Test_a_facet_impl::attr3() const
    throw(::Components::ccm::local::CCMException)
{
    return attr3_;
}

void
Test_a_facet_impl::attr3(const long value)
    throw(::Components::ccm::local::CCMException)
{
    attr3_ = value;
}

const long
Test_a_facet_impl::attr2() const
    throw(::Components::ccm::local::CCMException)
{
    return attr2_;
}

void
Test_a_facet_impl::attr2(const long value)
    throw(::Components::ccm::local::CCMException)
{
    attr2_ = value;
}

const long
Test_a_facet_impl::attr1() const
    throw(::Components::ccm::local::CCMException)
{
    return attr1_;
}

void
Test_a_facet_impl::attr1(const long value)
    throw(::Components::ccm::local::CCMException)
{
    attr1_ = value;
}


long
Test_a_facet_impl::op3(const std::string& str)
    throw (::Components::ccm::local::CCMException)
{
    cout << str << endl;
    return str.length();
}

long
Test_a_facet_impl::op2(const std::string& str)
    throw (::Components::ccm::local::CCMException)
{
    cout << str << endl;
    return str.length();
}

long
Test_a_facet_impl::op1(const std::string& str)
    throw (::Components::ccm::local::CCMException)
{
    cout << str << endl;
    return str.length();
}

} // /namespace local
} // /namespace ccm
