
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
#include <wx/utils/debug.h>

#include "TestMirror_console1_impl.h"


namespace ccm {
namespace local {
namespace component {
namespace TestMirror {

using namespace std;
using namespace wx::utils;

console1_impl::console1_impl(CCM_TestMirror_impl* component_impl)
  : component(component_impl)
{
    // OPTIONAL : IMPLEMENT ME HERE !
}

console1_impl::~console1_impl()
{
    // OPTIONAL : IMPLEMENT ME HERE !
}

long
console1_impl::println(const std::string& s2)
    throw (::Components::ccm::local::CCMException)
{
    cout << "Console 1 : " << s2 << endl;
    return s2.length();
}

} // /namespace TestMirror
} // /namespace component
} // /namespace local
} // /namespace ccm
