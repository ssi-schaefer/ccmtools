
/**
 * This file was automatically generated by 
 * <http://ccmtools.sourceforge.net/>
 * DO NOT EDIT !
 *
 * CCM_UserTypeInterface facet class implementation. 
 *
 * // TODO: WRITE YOUR DESCRIPTION HERE! 
 *
 * @author
 * @version 
 **/

#include <cassert>
#include <iostream>

#include "UserTest_userType_impl.h"

using namespace std;
using namespace wamas::platform::utils;

UserTest_userType_impl::UserTest_userType_impl(UserTest_impl* component_impl)
  : component(component_impl)
{
    // OPTIONAL : IMPLEMENT ME HERE !
}

UserTest_userType_impl::~UserTest_userType_impl()
{
    // OPTIONAL : IMPLEMENT ME HERE !
}

Color
UserTest_userType_impl::f1(const Color& p1, Color& p2, Color& p3)
    throw (::Components::CCMException)
{
    p3=p2;
    p2=p1;
    return p1;
}

Person
UserTest_userType_impl::f2(const Person& p1, Person& p2, Person& p3)
    throw (::Components::CCMException)
{
    Person r;
    r.name = p1.name + p2.name;
    r.id = p1.id + p2.id; 
    p3=p2;  
    p2=p1;
    return r; 
}

Address
UserTest_userType_impl::f3(const Address& p1, Address& p2, Address& p3)
    throw (::Components::CCMException)
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

LongList
UserTest_userType_impl::f4(const LongList& p1, LongList& p2, LongList& p3)
    throw (::Components::CCMException)
{
    LongList r;
    for(unsigned long i=0;i<p1.size();i++) {
      r.push_back(i);
      p3.push_back(p2.at(i));
      p2.at(i) = p1.at(i);
    }
    return r;
}

StringList
UserTest_userType_impl::f5(const StringList& p1, StringList& p2, StringList& p3)
    throw (::Components::CCMException)
{
    StringList r;
    for(unsigned long i=0;i<p1.size();i++) {
      r.push_back("Test");
      p3.push_back(p2.at(i));
      p2.at(i) = p1.at(i);
    }
    return r;
}

PersonList
UserTest_userType_impl::f6(const PersonList& p1, PersonList& p2, PersonList& p3)
    throw (::Components::CCMException)
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

time_t
UserTest_userType_impl::f7(const time_t& t1, time_t& t2, time_t& t3)
    throw (::Components::CCMException)
{
    t3=t2;
    t2=t1;
    return t3+t1; 
}

