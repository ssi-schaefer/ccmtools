
/**
 * This file was automatically generated by CCM Tools version 0.5.3-pre3
 * <http://ccmtools.sourceforge.net/>
 *
 * world::ccm::local::CCM_Hello facet class implementation. 
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

namespace world {
namespace ccm {
namespace local {
namespace component {
namespace Server {

hello_impl::hello_impl(
    world::ccm::local::component::Server::CCM_Server_impl* component_impl)
  : component(component_impl)
{
    // OPTIONAL : IMPLEMENT ME HERE !
}

hello_impl::~hello_impl()
{
    // OPTIONAL : IMPLEMENT ME HERE !
}

std::string
hello_impl::sayHello()
throw(::ccm::local::Components::CCMException)
{
    return "Hello from Server component!";
}

} // /namespace Server
} // /namespace component
} // /namespace local
} // /namespace ccm
} // /namespace world
