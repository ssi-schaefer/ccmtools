
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

#include "Test_inPort_impl.h"

using namespace std;
using namespace WX::Utils;

namespace CCM_Local {
namespace CCM_Session_Test {

inPort_impl::inPort_impl(CCM_Local::CCM_Session_Test::CCM_Test_impl* component_impl)
  : component(component_impl)
{
    DEBUGNL("+inPort_impl->inPort_impl()");

    // OPTIONAL : IMPLEMENT ME HERE !
}

inPort_impl::~inPort_impl()
{
    DEBUGNL ( "-inPort_impl->~inPort_impl (  )" );

    // OPTIONAL : IMPLEMENT ME HERE !
}

long
inPort_impl::f1(const long p1, long& p2, long& p3)
    throw (LocalComponents::CCMException)
{
    DEBUGNL("inPort_impl->f1(p1, p2, p3)");
    p3=p2;
    p2=p1;
    return p3+p1; 
}

Person
inPort_impl::f2(const Person& p1, Person& p2, Person& p3)
    throw (LocalComponents::CCMException)
{
    DEBUGNL("inPort_impl->f2(p1, p2, p3)");
    Person r;
    r.name = p1.name + p2.name;
    r.id = p1.id + p2.id; 
    p3=p2;  
    p2=p1;
    return r; 
}

} // /namespace CCM_Session_Test
} // /namespace CCM_Local
