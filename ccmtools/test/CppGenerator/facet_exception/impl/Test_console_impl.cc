
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

#include "Test_console_impl.h"

using namespace std;
using namespace wamas::platform::utils;

Test_console_impl::Test_console_impl(Test_impl* component_impl)
  : component(component_impl)
{
    // OPTIONAL : IMPLEMENT ME HERE !
}

Test_console_impl::~Test_console_impl()
{
    // OPTIONAL : IMPLEMENT ME HERE !
}

long
Test_console_impl::println(const std::string& msg)
    throw (::Components::ccm::local::CCMException, Error, SuperError, FatalError )
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
