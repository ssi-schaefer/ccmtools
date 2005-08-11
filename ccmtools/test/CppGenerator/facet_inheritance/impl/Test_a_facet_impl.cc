
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
#include <WX/Utils/debug.h>

#include "Test_a_facet_impl.h"

using namespace std;
using namespace WX::Utils;

namespace ccm {
namespace local {
namespace component {

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

long
a_facet_impl::op3(const std::string& str)
    throw (Components::CCMException)
{
    DEBUGNL("a_facet_impl->op3(str)");
    cout << str << endl;
    return str.length();
}

long
a_facet_impl::op2(const std::string& str)
    throw (Components::CCMException)
{
    DEBUGNL("a_facet_impl->op2(str)");
    cout << str << endl;
    return str.length();
}

long
a_facet_impl::op1(const std::string& str)
    throw (Components::CCMException)
{
    DEBUGNL("a_facet_impl->op1(str)");
    cout << str << endl;
    return str.length();
}

} // /namespace component
} // /namespace local
} // /namespace ccm
