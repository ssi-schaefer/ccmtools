
/**
 * CCM_Console facet class implementation. 
 *
 * // TODO: WRITE YOUR DESCRIPTION HERE! 
 *
 * @author
 * @version 
 *
 * This class implements a facet's methods and attributes.
 **/

#include <cassert>
#include <iostream>
#include <WX/Utils/debug.h>

#include "Test_console_impl.h"

namespace ccm {
namespace local {
namespace component {
namespace Test {

using namespace std;
using namespace WX::Utils;

console_impl::console_impl(CCM_Test_impl* component_impl)
  : component(component_impl)
{
    DEBUGNL("+console_impl->console_impl()");

    // OPTIONAL : IMPLEMENT ME HERE !
}

console_impl::~console_impl()
{
    DEBUGNL ( "-console_impl->~console_impl (  )" );

    // OPTIONAL : IMPLEMENT ME HERE !
}

const long
console_impl::max_size() const
    throw(Components::CCMException)
{
    DEBUGNL(" console_impl->max_size()");
    return max_size_;
}

void
console_impl::max_size(const long value)
    throw(Components::CCMException)
{
    DEBUGNL(" console_impl->max_size(value)");
    max_size_ = value;
}

long
console_impl::print(const std::string& msg)
    throw (Components::CCMException)
{
    DEBUGNL("console_impl->print(msg)");
    
    cout << ">> " << msg << endl;
    if(msg.length() < max_size())
      return msg.length();
    else
      return max_size();
}

} // /namespace Test
} // /namespace component
} // /namespace local
} // /namespace ccm
