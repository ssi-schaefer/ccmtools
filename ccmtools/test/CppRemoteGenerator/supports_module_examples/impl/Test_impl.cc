
/***
 * This file was automatically generated by CCM Tools version 0.5.3-pre3
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

namespace world {
namespace europe {
namespace austria {
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

long
CCM_Test_impl::print(const std::string& msg)
throw(::ccm::local::Components::CCMException,
        world::europe::austria::ccm::local::SuperError,
        world::europe::austria::ccm::local::SimpleError,
        world::europe::austria::ccm::local::FatalError )
{
    cout << ">> " << msg << endl;

    if(msg == "SimpleError") {
        SimpleError error;
        ErrorInfoList error_info_list;
        ErrorInfo error_info;
        error_info.code = 7;
        error_info.message = "A simple error!";
        error_info_list.push_back(error_info);
        error.info = error_info_list;
        throw error;
    }

    if(msg == "SuperError")
        throw SuperError();

    if(msg == "FatalError")
        throw FatalError();

    return msg.length();
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
    // OPTIONAL : IMPLEMENT ME HERE !
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

} // /namespace Test
} // /namespace component
} // /namespace local
} // /namespace ccm
} // /namespace austria
} // /namespace europe
} // /namespace world

