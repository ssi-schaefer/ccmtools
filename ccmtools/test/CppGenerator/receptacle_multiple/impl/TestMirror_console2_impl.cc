
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

#include "TestMirror_console2_impl.h"


namespace ccm {
namespace local {
namespace component {
namespace TestMirror {

using namespace std;
using namespace WX::Utils;

console2_impl::console2_impl(CCM_TestMirror_impl* component_impl)
  : component(component_impl)
{
    // OPTIONAL : IMPLEMENT ME HERE !
}

console2_impl::~console2_impl()
{
    // OPTIONAL : IMPLEMENT ME HERE !
}

long
console2_impl::println(const std::string& s2)
    throw (Components::CCMException)
{
    cout << "Console 2 : " << s2 << endl;
    return s2.length();
}

} // /namespace TestMirror
} // /namespace component
} // /namespace local
} // /namespace ccm
