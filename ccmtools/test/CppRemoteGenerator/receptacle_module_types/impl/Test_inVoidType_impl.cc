
/**
 * This file was automatically generated by CCM Tools
 * <http://ccmtools.sourceforge.net/>
 *
 * CCM_VoidTypeInterface facet class implementation. 
 *
 * // TODO: WRITE YOUR DESCRIPTION HERE! 
 *
 * @author
 * @version 
 **/

#include <cassert>
#include <iostream>

#include "Test_inVoidType_impl.h"

using namespace std;
using namespace wamas::platform::utils;

namespace world {
namespace europe {
namespace austria {

Test_inVoidType_impl::Test_inVoidType_impl(Test_impl* component_impl)
  : component(component_impl)
{
    // OPTIONAL : IMPLEMENT ME HERE !
}

Test_inVoidType_impl::~Test_inVoidType_impl()
{
    // OPTIONAL : IMPLEMENT ME HERE !
}

void
Test_inVoidType_impl::f1(const long p1)
throw(::Components::ccm::local::CCMException)
{
  attr = p1;
}

long
Test_inVoidType_impl::f2()
throw(::Components::ccm::local::CCMException)
{
  return attr;
}

} // /namespace austria
} // /namespace europe
} // /namespace world
