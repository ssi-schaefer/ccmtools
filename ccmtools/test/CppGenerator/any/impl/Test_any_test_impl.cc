
/**
 * This file was automatically generated by CCM Tools version 0.6.4
 * <http://ccmtools.sourceforge.net/>
 *
 * ccm::local::CCM_AnyTest facet class implementation. 
 *
 * // TODO: WRITE YOUR DESCRIPTION HERE! 
 *
 * @author
 * @version 
 **/

#include <cassert>
#include <iostream>
#include <wx/utils/debug.h>
#include <wx/utils/value_simple.h>

#include "Test_any_test_impl.h"

using namespace std;
using namespace wx::utils;

namespace ccm {
namespace local {
namespace component {
namespace Test {

any_test_impl::any_test_impl(
    ccm::local::component::Test::CCM_Test_impl* component_impl)
  : component(component_impl)
{
    // OPTIONAL : IMPLEMENT ME HERE !
}

any_test_impl::~any_test_impl()
{
    // OPTIONAL : IMPLEMENT ME HERE !
}

SmartPtr< wx::utils::Value > 
any_test_impl::op1(
        const SmartPtr< wx::utils::Value > & p1,
        SmartPtr< wx::utils::Value > & p2,
        SmartPtr< wx::utils::Value > & p3)
throw(::Components::ccm::local::CCMException)
{
    // TODO : IMPLEMENT ME HERE !
}

ccm::local::anyList
any_test_impl::op2(
        const ccm::local::anyList& p1,
        ccm::local::anyList& p2,
        ccm::local::anyList& p3)
throw(::Components::ccm::local::CCMException)
{
    // TODO : IMPLEMENT ME HERE !
}

ccm::local::pair
any_test_impl::op3(
        const ccm::local::pair& p1,
        ccm::local::pair& p2,
        ccm::local::pair& p3)
throw(::Components::ccm::local::CCMException)
{ 
  p3=p2;
  p2=p1;

  ccm::local::pair result;
//  result.name = "keyresult";
//  SmartPtr<Value> vr(new IntValue(3));
 // result.value = vr; !!!!!

  return result;
}

} // /namespace Test
} // /namespace component
} // /namespace local
} // /namespace ccm
