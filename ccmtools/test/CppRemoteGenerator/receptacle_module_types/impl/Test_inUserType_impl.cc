
/**
 * This file was automatically generated by CCM Tools version 0.5.3-pre3
 * <http://ccmtools.sourceforge.net/>
 *
 * world::europe::austria::ccm::local::CCM_UserTypeInterface facet class implementation. 
 *
 * // TODO: WRITE YOUR DESCRIPTION HERE! 
 *
 * @author
 * @version 
 **/

#include <cassert>
#include <iostream>
#include <wx/utils/debug.h>

#include "Test_inUserType_impl.h"

using namespace std;
using namespace wx::utils;

namespace world {
namespace europe {
namespace austria {
namespace ccm {
namespace local {

Test_inUserType_impl::Test_inUserType_impl(world::europe::austria::ccm::local::Test_impl* component_impl)
  : component(component_impl)
{
    // OPTIONAL : IMPLEMENT ME HERE !
}

Test_inUserType_impl::~Test_inUserType_impl()
{
    // OPTIONAL : IMPLEMENT ME HERE !
}

world::europe::austria::ccm::local::Color
Test_inUserType_impl::f1(
        const world::europe::austria::ccm::local::Color& p1,
        world::europe::austria::ccm::local::Color& p2,
        world::europe::austria::ccm::local::Color& p3)
throw(::Components::ccm::local::CCMException)
{
    p3=p2;
    p2=p1;
    return orange;
}

world::europe::austria::ccm::local::Person
Test_inUserType_impl::f2(
        const world::europe::austria::ccm::local::Person& p1,
        world::europe::austria::ccm::local::Person& p2,
        world::europe::austria::ccm::local::Person& p3)
throw(::Components::ccm::local::CCMException)
{
    Person r;
    r.name = p1.name + p2.name;
    r.id = p1.id + p2.id; 
    p3=p2;  
    p2=p1;
    return r; 
}

world::europe::austria::ccm::local::Address
Test_inUserType_impl::f3(
        const world::europe::austria::ccm::local::Address& p1,
        world::europe::austria::ccm::local::Address& p2,
        world::europe::austria::ccm::local::Address& p3)
throw(::Components::ccm::local::CCMException)
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

world::europe::austria::ccm::local::LongList
Test_inUserType_impl::f4(
        const world::europe::austria::ccm::local::LongList& p1,
        world::europe::austria::ccm::local::LongList& p2,
        world::europe::austria::ccm::local::LongList& p3)
throw(::Components::ccm::local::CCMException)
{
    LongList r;
    for(unsigned long i=0;i<p1.size();i++) {
      r.push_back(i);
      p3.push_back(p2.at(i));
      p2.at(i) = p1.at(i);
    }
    return r;
}

world::europe::austria::ccm::local::StringList
Test_inUserType_impl::f5(
        const world::europe::austria::ccm::local::StringList& p1,
        world::europe::austria::ccm::local::StringList& p2,
        world::europe::austria::ccm::local::StringList& p3)
throw(::Components::ccm::local::CCMException)
{
    StringList r;
    for(unsigned long i=0;i<p1.size();i++) {
      r.push_back("Test");
      p3.push_back(p2.at(i));
      p2.at(i) = p1.at(i);
    }
    return r;
}

world::europe::austria::ccm::local::PersonList
Test_inUserType_impl::f6(
        const world::europe::austria::ccm::local::PersonList& p1,
        world::europe::austria::ccm::local::PersonList& p2,
        world::europe::austria::ccm::local::PersonList& p3)
throw(::Components::ccm::local::CCMException)
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

world::europe::austria::ccm::local::time_t
Test_inUserType_impl::f7(
        const world::europe::austria::ccm::local::time_t& t1,
        world::europe::austria::ccm::local::time_t& t2,
        world::europe::austria::ccm::local::time_t& t3)
throw(::Components::ccm::local::CCMException)
{
    t3=t2;
    t2=t1;
    return t3+t1;
}

} // /namespace local
} // /namespace ccm
} // /namespace austria
} // /namespace europe
} // /namespace world
