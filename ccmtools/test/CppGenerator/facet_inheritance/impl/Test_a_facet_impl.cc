
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
#include <wx/utils/debug.h>

#include "Test_a_facet_impl.h"


namespace ccm {
namespace local {
namespace component {
namespace Test {

using namespace std;
using namespace wx::utils;


a_facet_impl::a_facet_impl(CCM_Test_impl* component_impl)
  : component(component_impl)
{
    DEBUGNL("+a_facet_impl->a_facet_impl()");

    // OPTIONAL : IMPLEMENT ME HERE !
}

a_facet_impl::~a_facet_impl()
{
    DEBUGNL ( "-a_facet_impl->~a_facet_impl (  )" );

    // OPTIONAL : IMPLEMENT ME HERE !
}

const long
a_facet_impl::attr3() const
    throw(::Components::ccm::local::CCMException)
{
    return attr3_;
}

void
a_facet_impl::attr3(const long value)
    throw(::Components::ccm::local::CCMException)
{
    attr3_ = value;
}

const long
a_facet_impl::attr2() const
    throw(::Components::ccm::local::CCMException)
{
    return attr2_;
}

void
a_facet_impl::attr2(const long value)
    throw(::Components::ccm::local::CCMException)
{
    attr2_ = value;
}

const long
a_facet_impl::attr1() const
    throw(::Components::ccm::local::CCMException)
{
    return attr1_;
}

void
a_facet_impl::attr1(const long value)
    throw(::Components::ccm::local::CCMException)
{
    attr1_ = value;
}



long
a_facet_impl::op3(const std::string& str)
    throw (::Components::ccm::local::CCMException)
{
    DEBUGNL("a_facet_impl->op3(str)");
    cout << str << endl;
    return str.length();
}

long
a_facet_impl::op2(const std::string& str)
    throw (::Components::ccm::local::CCMException)
{
    DEBUGNL("a_facet_impl->op2(str)");
    cout << str << endl;
    return str.length();
}

long
a_facet_impl::op1(const std::string& str)
    throw (::Components::ccm::local::CCMException)
{
    DEBUGNL("a_facet_impl->op1(str)");
    cout << str << endl;
    return str.length();
}

} // /namespace Test
} // /namespace component
} // /namespace local
} // /namespace ccm
