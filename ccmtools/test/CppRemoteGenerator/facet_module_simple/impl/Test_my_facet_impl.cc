
/**
 * This file was automatically generated by CCM Tools version 0.5.3-pre3
 * <http://ccmtools.sourceforge.net/>
 *
 * world::ccm::local::CCM_I2 facet class implementation. 
 *
 * // TODO: WRITE YOUR DESCRIPTION HERE! 
 *
 * @author
 * @version 
 **/

#include <cassert>
#include <iostream>

#include "Test_my_facet_impl.h"

using namespace std;
using namespace wamas::platform::utils;

namespace world {
namespace ccm {
namespace local {

Test_my_facet_impl::Test_my_facet_impl(world::ccm::local::Test_impl* component_impl)
  : component(component_impl)
{
    // OPTIONAL : IMPLEMENT ME HERE !
}

Test_my_facet_impl::~Test_my_facet_impl()
{
    // OPTIONAL : IMPLEMENT ME HERE !
}

long
Test_my_facet_impl::op1(const std::string& str)
throw(::Components::ccm::local::CCMException)
{
    cout << ">>> " << str << endl;
    return str.length();
}

} // /namespace local
} // /namespace ccm
} // /namespace world
