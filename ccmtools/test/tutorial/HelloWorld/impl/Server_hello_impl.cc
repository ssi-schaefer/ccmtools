
/**
 * This file was automatically generated by 
 * <http://ccmtools.sourceforge.net/>
 * DO NOT EDIT !
 *
 * CCM_Hello facet class implementation. 
 *
 * // TODO: WRITE YOUR DESCRIPTION HERE! 
 *
 * @author
 * @version 
 **/

#include <cassert>
#include <iostream>
#include <WX/Utils/debug.h>

#include "Server_hello_impl.h"

using namespace std;
using namespace WX::Utils;

namespace CCM_Local {
namespace world {
namespace CCM_Session_Server {

hello_impl::hello_impl(CCM_Local::world::CCM_Session_Server::CCM_Server_impl* component_impl)
  : component(component_impl)
{
    DEBUGNL("+hello_impl->hello_impl()");

    // OPTIONAL : IMPLEMENT ME HERE !
}

hello_impl::~hello_impl()
{
    DEBUGNL ( "-hello_impl->~hello_impl (  )" );

    // OPTIONAL : IMPLEMENT ME HERE !
}

std::string
hello_impl::sayHello()
    throw (LocalComponents::CCMException)
{
    DEBUGNL("hello_impl->sayHello()");

    // TODO : IMPLEMENT ME HERE !
    return "Hello from Server component!";
}

} // /namespace CCM_Session_Server
} // /namespace world
} // /namespace CCM_Local
