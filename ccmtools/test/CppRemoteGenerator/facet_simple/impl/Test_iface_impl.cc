
/**
 * This file was automatically generated by CCM Tools version 0.5.3-pre3
 * <http://ccmtools.sourceforge.net/>
 *
 * ccm::local::CCM_I2 facet class implementation. 
 *
 * // TODO: WRITE YOUR DESCRIPTION HERE! 
 *
 * @author
 * @version 
 **/

#include <cassert>
#include <iostream>
#include <wx/utils/debug.h>

#include "Test_iface_impl.h"

using namespace std;
using namespace wx::utils;

namespace ccm {
namespace local {
namespace component {
namespace Test {

iface_impl::iface_impl(
    ccm::local::component::Test::CCM_Test_impl* component_impl)
  : component(component_impl)
{
    // OPTIONAL : IMPLEMENT ME HERE !
}

iface_impl::~iface_impl()
{
    // OPTIONAL : IMPLEMENT ME HERE !
}

long
iface_impl::op1(const std::string& str)
throw(::Components::ccm::local::CCMException)
{
    cout << ">>> " << str << endl;
    return str.length();
}

} // /namespace Test
} // /namespace component
} // /namespace local
} // /namespace ccm
