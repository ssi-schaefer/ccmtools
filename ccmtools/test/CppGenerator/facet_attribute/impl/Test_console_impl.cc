
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

Test_console_impl::Test_console_impl(Test_impl* component_impl)
  : component(component_impl)
{
    // OPTIONAL : IMPLEMENT ME HERE !
}

Test_console_impl::~Test_console_impl()
{
    // OPTIONAL : IMPLEMENT ME HERE !
}

const long
Test_console_impl::max_size() const
    throw(Components::CCMException)
{
    return max_size_;
}

void
Test_console_impl::max_size(const long value)
    throw(Components::CCMException)
{
    max_size_ = value;
}

long
Test_console_impl::print(const std::string& msg)
    throw (Components::CCMException)
{
    cout << ">> " << msg << endl;
    if(msg.length() < max_size())
      return msg.length();
    else
      return max_size();
}

