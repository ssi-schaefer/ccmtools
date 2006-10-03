
/**
 * CCM_TypeTest facet class implementation. 
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

#include "MyObject.h"
#include "TestMirror_type_test_impl.h"


namespace ccm {
namespace local {
namespace component {
namespace TestMirror {

using namespace std;
using namespace wx::utils;

type_test_impl::type_test_impl(CCM_TestMirror_impl* component_impl)
  : component(component_impl)
{
    // OPTIONAL : IMPLEMENT ME HERE !
}

type_test_impl::~type_test_impl()
{
    // OPTIONAL : IMPLEMENT ME HERE !
}

short
type_test_impl::op_b1(const short p1, short& p2, short& p3)
    throw (::Components::ccm::local::CCMException)
{
    p3=p2;
    p2=p1;
    return p3+p1;
}

long
type_test_impl::op_b2(const long p1, long& p2, long& p3)
    throw (::Components::ccm::local::CCMException)
{
    p3=p2;
    p2=p1;
    return p3+p1;
}

unsigned short
type_test_impl::op_b3(const unsigned short p1, unsigned short& p2, unsigned short& p3)
    throw (::Components::ccm::local::CCMException)
{
    p3=p2;
    p2=p1;
    return p3+p1;
}

unsigned long
type_test_impl::op_b4(const unsigned long p1, unsigned long& p2, unsigned long& p3)
    throw (::Components::ccm::local::CCMException)
{
    p3=p2;
    p2=p1;
    return p3+p1;
}

float
type_test_impl::op_b5(const float p1, float& p2, float& p3)
    throw (::Components::ccm::local::CCMException)
{
    p3=p2;
    p2=p1;
    return p3+p1;
}

double
type_test_impl::op_b6(const double p1, double& p2, double& p3)
    throw (::Components::ccm::local::CCMException)
{
    p3=p2;
    p2=p1;
    return p3+p1;
}

char
type_test_impl::op_b7(const char p1, char& p2, char& p3)
    throw (::Components::ccm::local::CCMException)
{
    p3=p2;
    p2=p1;
    return p3+p1;
}

std::string
type_test_impl::op_b8(const std::string& p1, std::string& p2, std::string& p3)
    throw (::Components::ccm::local::CCMException)
{
    p3=p2;
    p2=p1;
    return p3+p1;
}

bool
type_test_impl::op_b9(const bool p1, bool& p2, bool& p3)
    throw (::Components::ccm::local::CCMException)
{
    p3=p2;
    p2=p1;
    return p3 && p1;
}

unsigned char
type_test_impl::op_b10(const unsigned char p1, unsigned char& p2, unsigned char& p3)
    throw (::Components::ccm::local::CCMException)
{
    p3=p2;
    p2=p1;
    return p3+p1;
}

time_t
type_test_impl::op_u1(const time_t& p1, time_t& p2, time_t& p3)
    throw (::Components::ccm::local::CCMException)
{
    p3=p2;
    p2=p1;
    return p3+p1;
}

Color
type_test_impl::op_u2(const Color& p1, Color& p2, Color& p3)
    throw (::Components::ccm::local::CCMException)
{
    p3=p2;
    p2=p1;
    return p1;
}

Pair
type_test_impl::op_u3(const Pair& p1, Pair& p2, Pair& p3)
    throw (::Components::ccm::local::CCMException)
{
    Pair r;
    r.key = p1.key + p2.key;
    r.value = p1.value + p2.value;
    p3=p2;
    p2=p1;
    return r;
}

Map
type_test_impl::op_u4(const Map& p1, Map& p2, Map& p3)
    throw (::Components::ccm::local::CCMException)
{
    Map r;
    for(unsigned int i=0;i<p1.size();i++) {
      Pair p;
      p.key = "test";
      p.value = (double)i;
      r.push_back(p);
      p3.push_back(p2.at(i));
      p2.at(i) = p1.at(i);
    }
    return r;
}

doubleArray
type_test_impl::op_u5(const doubleArray& p1, doubleArray& p2, doubleArray& p3)
    throw (::Components::ccm::local::CCMException)
{
    doubleArray r(10);
    for(unsigned int i=0; i<p1.size(); i++) {
      r.at(i) = p1.at(i);
      p3.at(i) = p2.at(i);
      p2.at(i) = p1.at(i);
    }
    return r;
}

SmartPtr<Console>
type_test_impl::op_i1(const SmartPtr<Console>& p1, SmartPtr<Console>& p2, SmartPtr<Console>& p3)
    throw (::Components::ccm::local::CCMException)
{
    MyObject* my_object3 = new MyObject;
    p3 = SmartPtr<Console>(my_object3);
    p3->prompt(p2->prompt());
    string p1_prompt = "prompt1> ";  // BUG: p1->prompt(); const !!!
    p2->prompt(p1_prompt);
    MyObject* my_object4 = new MyObject;
    SmartPtr<Console> result(my_object4);
    result->prompt(p3->prompt() + p1_prompt);
    return result;
}

} // /namespace TestMirror
} // /namespace component
} // /namespace local
} // /namespace ccm
