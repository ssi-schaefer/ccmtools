
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
#include <WX/Utils/debug.h>

#include "Test_my_facet_impl.h"

using namespace std;
using namespace WX::Utils;

namespace world {
namespace ccm {
namespace local {
namespace component {
namespace Test {

my_facet_impl::my_facet_impl(
    world::ccm::local::component::Test::CCM_Test_impl* component_impl)
  : component(component_impl)
{
    // OPTIONAL : IMPLEMENT ME HERE !
}

my_facet_impl::~my_facet_impl()
{
    // OPTIONAL : IMPLEMENT ME HERE !
}

long
my_facet_impl::op1(const std::string& str)
throw(::ccm::local::Components::CCMException)
{
    cout << ">>> " << str << endl;
    return str.length();
}

} // /namespace Test
} // /namespace component
} // /namespace local
} // /namespace ccm
} // /namespace world
