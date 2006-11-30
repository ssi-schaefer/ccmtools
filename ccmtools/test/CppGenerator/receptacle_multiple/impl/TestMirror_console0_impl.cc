
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
#include <wamas/platform/utils/debug.h>

#include "TestMirror_console0_impl.h"


namespace ccm {
namespace local {

using namespace std;
using namespace wamas::platform::utils;

TestMirror_console0_impl::TestMirror_console0_impl(TestMirror_impl* component_impl)
  : component(component_impl)
{
    // OPTIONAL : IMPLEMENT ME HERE !
}

TestMirror_console0_impl::~TestMirror_console0_impl()
{
    // OPTIONAL : IMPLEMENT ME HERE !
}

long
TestMirror_console0_impl::println(const std::string& s2)
    throw (::Components::ccm::local::CCMException)
{
    cout << "Console 0 : " << s2 << endl;
    return s2.length();
}

} // /namespace local
} // /namespace ccm
