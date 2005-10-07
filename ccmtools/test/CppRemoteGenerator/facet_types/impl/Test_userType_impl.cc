
/**
 * This file was automatically generated by CCM Tools version 0.5.3-pre3
 * <http://ccmtools.sourceforge.net/>
 *
 * ccm::local::CCM_UserTypeInterface facet class implementation. 
 *
 * // TODO: WRITE YOUR DESCRIPTION HERE! 
 *
 * @author
 * @version 
 **/

#include <cassert>
#include <iostream>
#include <WX/Utils/debug.h>

#include "Test_userType_impl.h"

using namespace std;
using namespace WX::Utils;

namespace ccm {
namespace local {
namespace component {
namespace Test {

userType_impl::userType_impl(
    ccm::local::component::Test::CCM_Test_impl* component_impl)
  : component(component_impl)
{
    // OPTIONAL : IMPLEMENT ME HERE !
}

userType_impl::~userType_impl()
{
    // OPTIONAL : IMPLEMENT ME HERE !
}

ccm::local::Color
userType_impl::f1(
        const ccm::local::Color& p1,
        ccm::local::Color& p2,
        ccm::local::Color& p3)
throw(::ccm::local::Components::CCMException)
{
    p3=p2;
    p2=p1;
    return ccm::local::orange; 
}

ccm::local::Person
userType_impl::f2(
        const ccm::local::Person& p1,
        ccm::local::Person& p2,
        ccm::local::Person& p3)
throw(::ccm::local::Components::CCMException)
{
    Person r;
    r.name = p1.name + p2.name;
    r.id = p1.id + p2.id; 
    p3=p2;  
    p2=p1;
    return r; 
}

ccm::local::Address
userType_impl::f3(
        const ccm::local::Address& p1,
        ccm::local::Address& p2,
        ccm::local::Address& p3)
throw(::ccm::local::Components::CCMException)
{
    Address r;
    r.street = p1.street + p2.street;
    r.number = p1.number + p2.number;
    r.resident.id   = p1.resident.id + p2.resident.id;
    r.resident.name = p1.resident.name + p2.resident.name;
    p3 = p2;
    p2 = p1;
    return r;
}

ccm::local::LongList
userType_impl::f4(
        const ccm::local::LongList& p1,
        ccm::local::LongList& p2,
        ccm::local::LongList& p3)
throw(::ccm::local::Components::CCMException)
{
    LongList r;
    for(unsigned long i=0;i<p1.size();i++) {
      r.push_back(i);
      p3.push_back(p2.at(i));
      p2.at(i) = p1.at(i);
    }
    return r;
}

ccm::local::StringList
userType_impl::f5(
        const ccm::local::StringList& p1,
        ccm::local::StringList& p2,
        ccm::local::StringList& p3)
throw(::ccm::local::Components::CCMException)
{
    StringList r;
    for(unsigned long i=0;i<p1.size();i++) {
      r.push_back("Test");
      p3.push_back(p2.at(i));
      p2.at(i) = p1.at(i);
    }
    return r;
}

ccm::local::PersonList
userType_impl::f6(
        const ccm::local::PersonList& p1,
        ccm::local::PersonList& p2,
        ccm::local::PersonList& p3)
throw(::ccm::local::Components::CCMException)
{
    PersonList r;
    for(unsigned long i=0; i < p1.size(); i++) {
      Person v;
      v.name = "Test";
      v.id = i;
      r.push_back(v);
      p3.push_back(p2.at(i));
      p2.at(i) = p1.at(i);
    }
    return r;
}

ccm::local::time_t
userType_impl::f7(
        const ccm::local::time_t& t1,
        ccm::local::time_t& t2,
        ccm::local::time_t& t3)
throw(::ccm::local::Components::CCMException)
{
    t3=t2;
    t2=t1;
    return t3+t1; 
}

} // /namespace Test
} // /namespace component
} // /namespace local
} // /namespace ccm
