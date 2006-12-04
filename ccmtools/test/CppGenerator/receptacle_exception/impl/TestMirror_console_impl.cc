
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

#include "TestMirror_console_impl.h"

namespace ccm {
namespace local {

using namespace std;
using namespace wamas::platform::utils;

TestMirror_console_impl::TestMirror_console_impl(TestMirror_impl* component_impl)
  : component(component_impl)
{
    // OPTIONAL : IMPLEMENT ME HERE !
}

TestMirror_console_impl::~TestMirror_console_impl()
{
    // OPTIONAL : IMPLEMENT ME HERE !
}

long
TestMirror_console_impl::print(const std::string& msg)
    throw (Components::ccm::local::CCMException, SuperError, Error, FatalError )
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

} // /namespace local
} // /namespace ccm
