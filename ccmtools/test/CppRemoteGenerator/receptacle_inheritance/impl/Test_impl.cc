
/***
 * This file was automatically generated by CCM Tools
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

#include "Test_impl.h"
#include "Test_ifaceIn_impl.h"

using namespace std;
using namespace wamas::platform::utils;

//==============================================================================
// CCM_Test - component implementation
//==============================================================================

Test_impl::Test_impl()
{
    // OPTIONAL : IMPLEMENT ME HERE !
}

Test_impl::~Test_impl()
{
    // OPTIONAL : IMPLEMENT ME HERE !
}

void
Test_impl::set_session_context(Components::SessionContext* context)
    throw(Components::CCMException)
{
    ctx = dynamic_cast<CCM_Test_Context*>(context);
}

void
Test_impl::ccm_activate()
    throw(Components::CCMException)
{
    SmartPtr<CCM_SubType> receptacle = 
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
Test_impl::ccm_passivate()
    throw(Components::CCMException)
{
    // OPTIONAL : IMPLEMENT ME HERE !
}

void
Test_impl::ccm_remove()
    throw(Components::CCMException)
{
    // OPTIONAL : IMPLEMENT ME HERE !
}

//==============================================================================
// CCM_SubType facet implementation
//==============================================================================

CCM_SubType*
Test_impl::get_ifaceIn()
{
    Test_ifaceIn_impl* facet = new Test_ifaceIn_impl(this);
    return dynamic_cast< CCM_SubType*>(facet);
}

