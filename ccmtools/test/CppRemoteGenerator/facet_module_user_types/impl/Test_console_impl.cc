
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
#include <WX/Utils/debug.h>

#include "Test_console_impl.h"

using namespace std;
using namespace WX::Utils;

namespace CCM_Local {
namespace world {
namespace europe {
namespace austria {
namespace CCM_Session_Test {

console_impl::console_impl(CCM_Local::world::europe::austria::CCM_Session_Test::CCM_Test_impl* component_impl)
  : component(component_impl)
{
    DEBUGNL("+console_impl->console_impl()");

    // OPTIONAL : IMPLEMENT ME HERE !
}

console_impl::~console_impl()
{
    DEBUGNL ( "-console_impl->~console_impl (  )" );

    // OPTIONAL : IMPLEMENT ME HERE !
}

world::europe::austria::Person
console_impl::f2(const world::europe::austria::Person& p1, world::europe::austria::Person& p2, world::europe::austria::Person& p3)
    throw (LocalComponents::CCMException)
{
    DEBUGNL("console_impl->f2(p1, p2, p3)");
    Person r;
    r.name = p1.name + p2.name;
    r.id = p1.id + p2.id; 
    p3=p2;  
    p2=p1;
    return r; 
}

world::europe::austria::Address
console_impl::f3(const world::europe::austria::Address& p1, world::europe::austria::Address& p2, world::europe::austria::Address& p3)
    throw (LocalComponents::CCMException)
{
    DEBUGNL("console_impl->f3(p1, p2, p3)");

    Address r;
    r.street = p1.street + p2.street;
    r.number = p1.number + p2.number;
    r.resident.id   = p1.resident.id + p2.resident.id;
    r.resident.name = p1.resident.name + p2.resident.name;

    p3 = p2;
    p2 = p1;
    return r;
}

world::europe::austria::LongList
console_impl::f4(const world::europe::austria::LongList& p1, world::europe::austria::LongList& p2, world::europe::austria::LongList& p3)
    throw (LocalComponents::CCMException)
{
    DEBUGNL("console_impl->f4(p1, p2, p3)");

    LongList r;
    for(unsigned long i=0;i<p1.size();i++) {
      r.push_back(i);
      p3.push_back(p2.at(i));
      p2.at(i) = p1.at(i);
    }
    return r;
}

world::europe::austria::StringList
console_impl::f5(const world::europe::austria::StringList& p1, world::europe::austria::StringList& p2, world::europe::austria::StringList& p3)
    throw (LocalComponents::CCMException)
{
    DEBUGNL("console_impl->f5(p1, p2, p3)");

    StringList r;
    for(unsigned long i=0;i<p1.size();i++) {
      r.push_back("Test");
      p3.push_back(p2.at(i));
      p2.at(i) = p1.at(i);
    }
    return r;
}

world::europe::austria::PersonList
console_impl::f6(const world::europe::austria::PersonList& p1, world::europe::austria::PersonList& p2, world::europe::austria::PersonList& p3)
    throw (LocalComponents::CCMException)
{
    DEBUGNL("console_impl->f6(p1, p2, p3)");

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

world::europe::austria::time_t
console_impl::f7(const world::europe::austria::time_t& t1, world::europe::austria::time_t& t2, world::europe::austria::time_t& t3)
    throw (LocalComponents::CCMException)
{
    DEBUGNL("console_impl->f7(t1, t2, t3)");

    t3=t2;
    t2=t1;
    return t3+t1; 
}

} // /namespace CCM_Session_Test
} // /namespace austria
} // /namespace europe
} // /namespace world
} // /namespace CCM_Local
