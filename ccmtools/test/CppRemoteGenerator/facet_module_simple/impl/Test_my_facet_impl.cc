/**
 * This file was automatically generated by CCM Tools
 * <http://ccmtools.sourceforge.net/>
 *
 * CCM_I2 facet class implementation. 
 *
 * // TODO: WRITE YOUR DESCRIPTION HERE! 
 *
 * @author
 * @version 
 **/

#include <cassert>
#include <iostream>

#include "Test_my_facet_impl.h"

using namespace std;
using namespace wamas::platform::utils;

namespace world {

Test_my_facet_impl::Test_my_facet_impl(Test_impl* component_impl)
  : component(component_impl)
{
    // OPTIONAL : IMPLEMENT ME HERE !
}

Test_my_facet_impl::~Test_my_facet_impl()
{
    // OPTIONAL : IMPLEMENT ME HERE !
}

long
Test_my_facet_impl::op1(const std::string& str)
	throw(Components::CCMException)
{
    cout << ">>> " << str << endl;
    return str.length();
}

} // /namespace world
