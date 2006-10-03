
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
#include <wx/utils/debug.h>

#include "SuperTest_userType_impl.h"

using namespace std;
using namespace wx::utils;

namespace ccm {
namespace local {
namespace component {
namespace SuperTest {

userType_impl::userType_impl(CCM_SuperTest_impl* component_impl)
  : component(component_impl)
{
    DEBUGNL("+userType_impl->userType_impl()");

    // OPTIONAL : IMPLEMENT ME HERE !
}

userType_impl::~userType_impl()
{
    DEBUGNL ( "-userType_impl->~userType_impl (  )" );

    // OPTIONAL : IMPLEMENT ME HERE !
}

Color
userType_impl::f1(const Color& p1, Color& p2, Color& p3)
    throw (Components::ccm::local::CCMException)
{
    DEBUGNL("userType_impl->f1(p1, p2, p3)");
    SmartPtr<CCM_UserTypeInterface> inner = 
      component->ctx->get_connection_innerUserType();
    return inner->f1(p1,p2,p3);
}

Person
userType_impl::f2(const Person& p1, Person& p2, Person& p3)
    throw (Components::ccm::local::CCMException)
{
    DEBUGNL("userType_impl->f2(p1, p2, p3)");
    SmartPtr<CCM_UserTypeInterface> inner = 
      component->ctx->get_connection_innerUserType();
    return inner->f2(p1,p2,p3);
}

Address
userType_impl::f3(const Address& p1, Address& p2, Address& p3)
    throw (Components::ccm::local::CCMException)
{
    DEBUGNL("userType_impl->f3(p1, p2, p3)");
    SmartPtr<CCM_UserTypeInterface> inner = 
      component->ctx->get_connection_innerUserType();
    return inner->f3(p1,p2,p3);
}

LongList
userType_impl::f4(const LongList& p1, LongList& p2, LongList& p3)
    throw (Components::ccm::local::CCMException)
{
    DEBUGNL("userType_impl->f4(p1, p2, p3)");
    SmartPtr<CCM_UserTypeInterface> inner = 
      component->ctx->get_connection_innerUserType();
    return inner->f4(p1,p2,p3);
}

StringList
userType_impl::f5(const StringList& p1, StringList& p2, StringList& p3)
    throw (Components::ccm::local::CCMException)
{
    DEBUGNL("userType_impl->f5(p1, p2, p3)");
    SmartPtr<CCM_UserTypeInterface> inner = 
      component->ctx->get_connection_innerUserType();
    return inner->f5(p1,p2,p3);
}

PersonList
userType_impl::f6(const PersonList& p1, PersonList& p2, PersonList& p3)
    throw (Components::ccm::local::CCMException)
{
    DEBUGNL("userType_impl->f6(p1, p2, p3)");
    SmartPtr<CCM_UserTypeInterface> inner = 
      component->ctx->get_connection_innerUserType();
    return inner->f6(p1,p2,p3);
}

time_t
userType_impl::f7(const time_t& t1, time_t& t2, time_t& t3)
    throw (Components::ccm::local::CCMException)
{
    DEBUGNL("userType_impl->f7(t1, t2, t3)");
    SmartPtr<CCM_UserTypeInterface> inner = 
      component->ctx->get_connection_innerUserType();
    return inner->f7(t1,t2,t3);
}

} // /namespace SuperTest
} // /namespace component
} // /namespace local
} // /namespace ccm
