
/**
 * This file was automatically generated by CCM Tools version 0.5.3-pre2
 * <http://ccmtools.sourceforge.net/>
 *
 * CCM_InterfaceType facet class implementation. 
 *
 * // TODO: WRITE YOUR DESCRIPTION HERE! 
 *
 * @author
 * @version 
 **/

#include <cassert>
#include <iostream>
#include <wamas/platform/utils/debug.h>

#include "TestMirror_a_receptacle_impl.h"


namespace ccm {
namespace local {

using namespace std;
using namespace wamas::platform::utils;


TestMirror_a_receptacle_impl::TestMirror_a_receptacle_impl(TestMirror_impl* component_impl)
  : component(component_impl)
{
    // OPTIONAL : IMPLEMENT ME HERE !
}

TestMirror_a_receptacle_impl::~TestMirror_a_receptacle_impl()
{
    // OPTIONAL : IMPLEMENT ME HERE !
}


const long
TestMirror_a_receptacle_impl::attr3() const
    throw(::Components::ccm::local::CCMException)
{
    return attr3_;
}

void
TestMirror_a_receptacle_impl::attr3(const long value)
    throw(::Components::ccm::local::CCMException)
{
    attr3_ = value;
}

const long
TestMirror_a_receptacle_impl::attr2() const
    throw(::Components::ccm::local::CCMException)
{
    return attr2_;
}

void
TestMirror_a_receptacle_impl::attr2(const long value)
    throw(::Components::ccm::local::CCMException)
{
    attr2_ = value;
}

const long
TestMirror_a_receptacle_impl::attr1() const
    throw(::Components::ccm::local::CCMException)
{
    return attr1_;
}

void
TestMirror_a_receptacle_impl::attr1(const long value)
    throw(::Components::ccm::local::CCMException)
{
    attr1_ = value;
}


long
TestMirror_a_receptacle_impl::op3(const std::string& str)
    throw (::Components::ccm::local::CCMException)
{
  cout << str << endl;
  return str.length();
}

long
TestMirror_a_receptacle_impl::op2(const std::string& str)
    throw (::Components::ccm::local::CCMException)
{
  cout << str << endl;
  return str.length();
}

long
TestMirror_a_receptacle_impl::op1(const std::string& str)
    throw (::Components::ccm::local::CCMException)
{
  cout << str << endl;
  return str.length();
}

} // /namespace local
} // /namespace ccm
