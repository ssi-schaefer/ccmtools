
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
namespace CCM_Session_Test {

console_impl::console_impl(CCM_Local::CCM_Session_Test::CCM_Test_impl* 
			   component_impl)
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

long
console_impl::f0(const long p1, long& p2, long& p3)
    throw (LocalComponents::CCMException)
{
    DEBUGNL("console_impl->f0(p1, p2, p3)");


}

Person
console_impl::f2(const Person& p1, Person& p2, Person& p3)
    throw (LocalComponents::CCMException)
{
    DEBUGNL("console_impl->f2(p1, p2, p3)");

    cout << "{ " << p1.id << ", " << p1.name << " }"  << endl;
    cout << "{ " << p2.id << ", " << p2.name << " }"  << endl;

    Person r;
    r.name = p1.name + p2.name;
    r.id = p1.id + p2.id; 
    p3=p2;  
    p2=p1;
    return r; 
}

Address
console_impl::f3(const Address& p1, Address& p2, Address& p3)
    throw (LocalComponents::CCMException)
{
    DEBUGNL("console_impl->f3(p1, p2, p3)");

    cout << "{ " << p1.street << ", " << p1.number 
	 << "{ " << p1.resident.id << ", " << p1.resident.name << " }"
	 << " }"   << endl;
    cout << "{ " << p2.street << ", " << p2.number 
	 << "{ " << p2.resident.id << ", " << p2.resident.name << " }"
	 << " }"  << endl;
    
    Address r;
    r.street = p1.street + p2.street;
    r.number = p1.number + p2.number;
    r.resident.id   = p1.resident.id + p2.resident.id;
    r.resident.name = p1.resident.name + p2.resident.name;

    p3 = p2;
    p2 = p1;
    return r;
}

} // /namespace CCM_Session_Test
} // /namespace CCM_Local
