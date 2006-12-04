/***
 * TestMirror component business logic implementation.
 * 
 * // TODO: WRITE YOUR DESCRIPTION HERE! 
 *
 * @author
 * @version 
 *
 * This file structure was automatically generated by CCM Tools
 * <http://ccmtools.sourceforge.net/> and contains a component's
 * implementation classes. 
 ***/

#include <cassert>
#include <iostream>

#include "TestMirror_impl.h"


namespace ccm {
namespace local {

using namespace std;
using namespace wamas::platform::utils;

//==============================================================================
// TestMirror - component implementation
//==============================================================================

TestMirror_impl::TestMirror_impl()
{
}

TestMirror_impl::~TestMirror_impl()
{
}

void
TestMirror_impl::set_session_context(
    Components::ccm::local::SessionContext* context)
    throw(::Components::ccm::local::CCMException)
{
    ctx = dynamic_cast<CCM_TestMirror_Context*>(context);
}

void
TestMirror_impl::ccm_activate()
    throw(Components::ccm::local::CCMException)
{
    SmartPtr<CCM_InterfaceType> receptacle = ctx->get_connection_a_facet();
    string str1 = "Hallo to first op()";
    long size1 = receptacle->op1(str1);
    assert(size1 == str1.length());

    string str2 = "Hallo to second op()";
    long size2 = receptacle->op2(str2);
    assert(size2 == str2.length());
    
    string str3 = "Hallo to third op()";
    long size3 = receptacle->op3(str3);
    assert(size3 == str3.length());

    long attr1 = 1;
    receptacle->attr1(attr1);
    assert(attr1 == receptacle->attr1());

    long attr2 = 2;
    receptacle->attr2(attr2);
    assert(attr2 == receptacle->attr2());

    long attr3 = 3;
    receptacle->attr3(attr3);
    assert(attr3 == receptacle->attr3());
}

void
TestMirror_impl::ccm_passivate()
    throw(::Components::ccm::local::CCMException)
{
    // OPTIONAL : IMPLEMENT ME HERE !
}

void
TestMirror_impl::ccm_remove()
    throw(::Components::ccm::local::CCMException)
{
    // OPTIONAL : IMPLEMENT ME HERE !
}

} // /namespace local
} // /namespace ccm

