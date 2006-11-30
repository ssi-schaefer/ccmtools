
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
#include <wamas/platform/utils/debug.h>

#include "SuperTest_userType_impl.h"

using namespace std;
using namespace wamas::platform::utils;

namespace ccm {
namespace local {

SuperTest_userType_impl::SuperTest_userType_impl(SuperTest_impl* component_impl)
  : component(component_impl)
{
    // OPTIONAL : IMPLEMENT ME HERE !
}

SuperTest_userType_impl::~SuperTest_userType_impl()
{
    // OPTIONAL : IMPLEMENT ME HERE !
}

Color
SuperTest_userType_impl::f1(const Color& p1, Color& p2, Color& p3)
    throw (Components::ccm::local::CCMException)
{
    SmartPtr<CCM_UserTypeInterface> inner = 
      component->ctx->get_connection_innerUserType();
    return inner->f1(p1,p2,p3);
}

Person
SuperTest_userType_impl::f2(const Person& p1, Person& p2, Person& p3)
    throw (Components::ccm::local::CCMException)
{
    SmartPtr<CCM_UserTypeInterface> inner = 
      component->ctx->get_connection_innerUserType();
    return inner->f2(p1,p2,p3);
}

Address
SuperTest_userType_impl::f3(const Address& p1, Address& p2, Address& p3)
    throw (Components::ccm::local::CCMException)
{
    SmartPtr<CCM_UserTypeInterface> inner = 
      component->ctx->get_connection_innerUserType();
    return inner->f3(p1,p2,p3);
}

LongList
SuperTest_userType_impl::f4(const LongList& p1, LongList& p2, LongList& p3)
    throw (Components::ccm::local::CCMException)
{
    SmartPtr<CCM_UserTypeInterface> inner = 
      component->ctx->get_connection_innerUserType();
    return inner->f4(p1,p2,p3);
}

StringList
SuperTest_userType_impl::f5(const StringList& p1, StringList& p2, StringList& p3)
    throw (Components::ccm::local::CCMException)
{
    SmartPtr<CCM_UserTypeInterface> inner = 
      component->ctx->get_connection_innerUserType();
    return inner->f5(p1,p2,p3);
}

PersonList
SuperTest_userType_impl::f6(const PersonList& p1, PersonList& p2, PersonList& p3)
    throw (Components::ccm::local::CCMException)
{
    SmartPtr<CCM_UserTypeInterface> inner = 
      component->ctx->get_connection_innerUserType();
    return inner->f6(p1,p2,p3);
}

time_t
SuperTest_userType_impl::f7(const time_t& t1, time_t& t2, time_t& t3)
    throw (Components::ccm::local::CCMException)
{
    SmartPtr<CCM_UserTypeInterface> inner = 
      component->ctx->get_connection_innerUserType();
    return inner->f7(t1,t2,t3);
}

} // /namespace local
} // /namespace ccm
