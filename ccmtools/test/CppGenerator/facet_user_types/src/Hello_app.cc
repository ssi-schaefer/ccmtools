
//==============================================================================
// Hello - business logic implementation
//==============================================================================

#include <iostream>
#include <CCM_Utils/Debug.h>

#include "Hello_app.h"

using namespace std;
using namespace CCM_Utils;

namespace CCM_Local {
namespace CCM_Session_Hello {


//==============================================================================
// business logic functionality
//==============================================================================

CCM_Console*
CCM_Hello_impl::get_console (  )
{
  DEBUGNL ( " CCM_Hello_impl->get_console (  )" );
  console_impl* facet = new console_impl ( this  );
  return dynamic_cast<CCM_Console*> ( facet );
}



//==============================================================================
// console - facet implementation
//==============================================================================

console_impl::console_impl (  CCM_Hello_impl* c )
  : component(c)
{
  DEBUGNL ( "+console_impl->console_impl (  )" );
}

console_impl::~console_impl (  )
{
  DEBUGNL ( "-console_impl->~console_impl (  )" );
}

time_t
console_impl::foo1 ( const time_t& p1, time_t& p2, time_t& p3 )
  
{
  DEBUGNL ( " console_impl->foo1 ( p1, p2, p3 )" );

  // TODO : IMPLEMENT ME HERE !
  p3=p2;
  p2=p1;
  return p3+p1; 
}

Color
console_impl::foo2 ( const Color& p1, Color& p2, Color& p3 )
  
{
  DEBUGNL ( " console_impl->foo2 ( p1, p2, p3 )" );

  // TODO : IMPLEMENT ME HERE !
  p3=p2;
  p2=p1;
  return p1; 
}

Value
console_impl::foo3 ( const Value& p1, Value& p2, Value& p3 )
{
  DEBUGNL ( " console_impl->foo3 ( p1, p2, p3 )" );

  // TODO : IMPLEMENT ME HERE !
  cout << p1.s << endl;
  cout << p2.s << endl;
  cout << p3.s << endl;

  Value r;
  r.s = p1.s + p2.s;
  r.dd = p1.dd + p2.dd; 
  p3=p2;  
  p2=p1;
  return r; 
}

map
console_impl::foo4 ( const map& p1, map& p2, map& p3 )
  
{
  DEBUGNL ( " console_impl->foo4 ( p1, p2, p3 )" );

  // TODO : IMPLEMENT ME HERE !
  map r;
  for(int i=0;i<p1.size();i++) {
    Value v;
    v.s = "test";
    v.dd = (double)i;
    r.push_back(v);
    p3.push_back(p2.at(i));
    p2.at(i) = p1.at(i);
  }
  return r;
}

doubleArray
console_impl::foo5 ( const doubleArray& p1, doubleArray& p2, doubleArray& p3 )
  
{
  DEBUGNL ( " console_impl->foo5 ( p1, p2, p3 )" );

  // TODO : IMPLEMENT ME HERE !
  doubleArray r(10);
  for(int i; i<p1.size(); i++) {
    r.at(i) = p1.at(i);
    p3.at(i) = p2.at(i);
    p2.at(i) = p1.at(i);
  }
  return r;
}





//==============================================================================
// class implementation
//==============================================================================

CCM_Hello_impl::CCM_Hello_impl (  )
{
  DEBUGNL ( "+CCM_Hello_impl->CCM_Hello_impl (  )" );
}

CCM_Hello_impl::~CCM_Hello_impl (  )
{
  DEBUGNL ( "-CCM_Hello_impl->~CCM_Hello_impl (  )" );
}




void
CCM_Hello_impl::set_session_context ( localComponents::SessionContext* context )
  throw ( localComponents::CCMException )
{
  DEBUGNL ( " CCM_Hello_impl->set_session_context (  )" );
  ctx = (CCM_Hello_Context*) context;
}

void
CCM_Hello_impl::ccm_activate (  )
  throw ( localComponents::CCMException )
{
  DEBUGNL ( " CCM_Hello_impl->ccm_activate (  )" );
}

void
CCM_Hello_impl::ccm_passivate (  )
  throw ( localComponents::CCMException )
{
  DEBUGNL ( " CCM_Hello_impl->ccm_passivate (  )" );
}

void
CCM_Hello_impl::ccm_remove (  )
  throw ( localComponents::CCMException )
{
  DEBUGNL ( " CCM_Hello_impl->ccm_remove (  )" );
}

} // /namespace CCM_Session_Hello
} // /namespace CCM_Local



