
/***
 * This file was automatically generated by CCM Tools version 0.5.4
 * <http://ccmtools.sourceforge.net/>
 *
 * Test component business logic implementation.
 * 
 * // TODO: WRITE YOUR DESCRIPTION HERE! 
 *
 * @author
 * @version 
 ***/

#include <cassert>
#include <iostream>
#include <WX/Utils/debug.h>

#include "Test_impl.h"
#include "Test_ifaceIn_impl.h"

namespace ccm {
namespace local {
namespace component {
namespace Test {

using namespace std;
using namespace WX::Utils;

//==============================================================================
// CCM_Test - component implementation
//==============================================================================

CCM_Test_impl::CCM_Test_impl()
{
    // OPTIONAL : IMPLEMENT ME HERE !
}

CCM_Test_impl::~CCM_Test_impl()
{
    // OPTIONAL : IMPLEMENT ME HERE !
}

void
CCM_Test_impl::set_session_context(
    ::ccm::local::Components::SessionContext* context)
    throw(::ccm::local::Components::CCMException)
{
    ctx = dynamic_cast<CCM_Test_Context*>(context);
}

void
CCM_Test_impl::ccm_activate()
    throw(::ccm::local::Components::CCMException)
{
    WX::Utils::SmartPtr<CCM_SubType> receptacle = 
        ctx->get_connection_ifaceOut();

    {
      string str1 = "Hallo to first op()";
      long size1 = receptacle->op1(str1);
      assert(size1 == str1.length());
    }

    {
      string str2 = "Hallo to second op()";
      long size2 = receptacle->op2(str2);
      assert(size2 == str2.length());
    }

    {
      string str3 = "Hallo to third op()";
      long size3 = receptacle->op3(str3);
      assert(size3 == str3.length());
    }

    {
      long attr1 = 1;
      receptacle->attr1(attr1);
      assert(attr1 == receptacle->attr1());
    }

    {
      long attr2 = 2;
      receptacle->attr2(attr2);
      assert(attr2 == receptacle->attr2());
    }

    {
      long attr3 = 3;
      receptacle->attr3(attr3);
      assert(attr3 == receptacle->attr3());
    }
}

void
CCM_Test_impl::ccm_passivate()
    throw(::ccm::local::Components::CCMException)
{
    // OPTIONAL : IMPLEMENT ME HERE !
}

void
CCM_Test_impl::ccm_remove()
    throw(::ccm::local::Components::CCMException)
{
    // OPTIONAL : IMPLEMENT ME HERE !
}

//==============================================================================
// ccm::local::CCM_SubType facet implementation
//==============================================================================

ccm::local::CCM_SubType*
CCM_Test_impl::get_ifaceIn()
{
    ifaceIn_impl* facet = new ifaceIn_impl(this);
    return dynamic_cast< ccm::local::CCM_SubType*>(facet);
}

} // /namespace Test
} // /namespace component
} // /namespace local
} // /namespace ccm

