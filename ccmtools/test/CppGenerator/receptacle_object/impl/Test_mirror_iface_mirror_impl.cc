
/**
 * CCM_IFace facet class implementation. 
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

#include "Test_mirror_iface_mirror_impl.h"


namespace ccm {
namespace local {
namespace component {
namespace Test_mirror {

using namespace std;
using namespace WX::Utils;

iface_mirror_impl::iface_mirror_impl(CCM_Test_mirror_impl* component_impl)
  : component(component_impl)
{
    DEBUGNL("+iface_mirror_impl->iface_mirror_impl()");

    // OPTIONAL : IMPLEMENT ME HERE !
}

iface_mirror_impl::~iface_mirror_impl()
{
    DEBUGNL ( "-iface_mirror_impl->~iface_mirror_impl (  )" );

    // OPTIONAL : IMPLEMENT ME HERE !
}

long
iface_mirror_impl::op_b1(const long p1, long& p2, long& p3)
    throw (Components::CCMException)
{
    DEBUGNL("iface_mirror_impl->op_b1(p1, p2, p3)");
    cout << "iface_mirror_impl->op_b1(p1, p2, p3)" << endl;
    p3=p2;
    p2=p1;
    return p3+p1;
}

std::string
iface_mirror_impl::op_b2(const std::string& p1, std::string& p2, std::string& p3)
    throw (Components::CCMException)
{
    DEBUGNL("iface_mirror_impl->op_b2(p1, p2, p3)");
    cout << "iface_mirror_impl->op_b2(p1, p2, p3)" << endl;
    p3=p2;
    p2=p1;
    return p3+p1;
}

} // /namespace Test_mirror
} // /namespace component
} // /namespace local
} // /namespace ccm
