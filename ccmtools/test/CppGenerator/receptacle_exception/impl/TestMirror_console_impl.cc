
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

#include "TestMirror_console_impl.h"

namespace ccm {
namespace local {
namespace component {
namespace TestMirror {

using namespace std;
using namespace WX::Utils;

console_impl::console_impl(CCM_TestMirror_impl* component_impl)
  : component(component_impl)
{
    // OPTIONAL : IMPLEMENT ME HERE !
}

console_impl::~console_impl()
{
    // OPTIONAL : IMPLEMENT ME HERE !
}

long
console_impl::print(const std::string& msg)
    throw (Components::CCMException, SuperError, Error, FatalError )
{
    cout << ">> " << msg << endl;

    if(msg == "Error") {
        Error error;
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

} // /namespace Test_mirror
} // /namespace component
} // /namespace local
} // /namespace ccm
