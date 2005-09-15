
/***
 * Test_mirror component business logic implementation.
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
#include <WX/Utils/debug.h>

#include "Test_mirror_impl.h"


namespace ccm {
namespace local {
namespace component {
namespace Test_mirror {

using namespace std;
using namespace WX::Utils;

//==============================================================================
// CCM_Test_mirror - component implementation
//==============================================================================

CCM_Test_mirror_impl::CCM_Test_mirror_impl()
{
    DEBUGNL("+CCM_Test_mirror_impl->CCM_Test_mirror_impl()");
}

CCM_Test_mirror_impl::~CCM_Test_mirror_impl()
{
    DEBUGNL("-CCM_Test_mirror_impl->~CCM_Test_mirror_impl()");
}

void
CCM_Test_mirror_impl::set_session_context(
    Components::SessionContext* context)
    throw(Components::CCMException)
{
    DEBUGNL(" CCM_Test_mirror_impl->set_session_context()");
    ctx = dynamic_cast<CCM_Test_mirror_Context*>(context);
}

void
CCM_Test_mirror_impl::ccm_activate()
    throw(Components::CCMException)
{
    DEBUGNL(" CCM_Test_mirror_impl->ccm_activate()");

    WX::Utils::SmartPtr<CCM_InterfaceType> receptacle = 
        ctx->get_connection_a_facet_mirror();
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
CCM_Test_mirror_impl::ccm_passivate()
    throw(Components::CCMException)
{
    DEBUGNL(" CCM_Test_mirror_impl->ccm_passivate()");

    // OPTIONAL : IMPLEMENT ME HERE !
}

void
CCM_Test_mirror_impl::ccm_remove()
    throw(Components::CCMException)
{
    DEBUGNL(" CCM_Test_mirror_impl->ccm_remove()");

    // OPTIONAL : IMPLEMENT ME HERE !
}

} // /namespace Test_mirror
} // /namespace component
} // /namespace local
} // /namespace ccm

