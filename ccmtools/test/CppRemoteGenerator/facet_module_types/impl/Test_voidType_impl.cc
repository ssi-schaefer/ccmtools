
/**
 * This file was automatically generated by CCM Tools version 0.5.3-pre3
 * <http://ccmtools.sourceforge.net/>
 *
 * world::europe::austria::ccm::local::CCM_VoidTypeInterface facet class implementation. 
 *
 * // TODO: WRITE YOUR DESCRIPTION HERE! 
 *
 * @author
 * @version 
 **/

#include <cassert>
#include <iostream>
#include <wx/utils/debug.h>

#include "Test_voidType_impl.h"

using namespace std;
using namespace wx::utils;

namespace world {
namespace europe {
namespace austria {
namespace ccm {
namespace local {

Test_voidType_impl::Test_voidType_impl(world::europe::austria::ccm::local::Test_impl* component_impl)
  : component(component_impl)
{
    // OPTIONAL : IMPLEMENT ME HERE !
}

Test_voidType_impl::~Test_voidType_impl()
{
    // OPTIONAL : IMPLEMENT ME HERE !
}

void
Test_voidType_impl::f1(const long p1)
throw(::Components::ccm::local::CCMException)
{
    attr = p1;
}

long
Test_voidType_impl::f2()
throw(::Components::ccm::local::CCMException)
{
  return attr;
}

} // /namespace local
} // /namespace ccm
} // /namespace austria
} // /namespace europe
} // /namespace world
