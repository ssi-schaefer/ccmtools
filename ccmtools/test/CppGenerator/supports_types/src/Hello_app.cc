
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


time_t
CCM_Hello_impl::foo1 ( const time_t& p1, time_t& p2, time_t& p3 )
  
{
#ifdef CCM_TEST_PYTHON
  DEBUGNL ( " CCM_Hello_impl->call_python_foo1 ( p1, p2, p3 )" );
  return call_python_foo1 ( p1, p2, p3 );
#else
  DEBUGNL ( " CCM_Hello_impl->foo1 ( p1, p2, p3 )" );

  p3=p2;
  p2=p1;
  return p3+p1;

#endif
}

Color
CCM_Hello_impl::foo2 ( const Color& p1, Color& p2, Color& p3 )
  
{
#ifdef CCM_TEST_PYTHON
  DEBUGNL ( " CCM_Hello_impl->call_python_foo2 ( p1, p2, p3 )" );
  return call_python_foo2 ( p1, p2, p3 );
#else
  DEBUGNL ( " CCM_Hello_impl->foo2 ( p1, p2, p3 )" );

  p3=p2;
  p2=p1;
  return p1;

#endif
}

Value
CCM_Hello_impl::foo3 ( const Value& p1, Value& p2, Value& p3 )
  
{
#ifdef CCM_TEST_PYTHON
  DEBUGNL ( " CCM_Hello_impl->call_python_foo3 ( p1, p2, p3 )" );
  return call_python_foo3 ( p1, p2, p3 );
#else
  DEBUGNL ( " CCM_Hello_impl->foo3 ( p1, p2, p3 )" );

  cout << p1.s << endl;
  cout << p2.s << endl;
  cout << p3.s << endl;

  Value r;
  r.s = p1.s + p2.s;
  r.dd = p1.dd + p2.dd;
  p3=p2;
  p2=p1;
  return r;

#endif
}

map
CCM_Hello_impl::foo4 ( const map& p1, map& p2, map& p3 )
  
{
#ifdef CCM_TEST_PYTHON
  DEBUGNL ( " CCM_Hello_impl->call_python_foo4 ( p1, p2, p3 )" );
  return call_python_foo4 ( p1, p2, p3 );
#else
  DEBUGNL ( " CCM_Hello_impl->foo4 ( p1, p2, p3 )" );

  map r;
  for(int i=0;i<(int)p1.size();i++) {
    Value v;
    v.s = "test";
    v.dd = (double)i;
    r.push_back(v);
    p3.push_back(p2.at(i));
    p2.at(i) = p1.at(i);
  }
  return r;

#endif
}

doubleArray
CCM_Hello_impl::foo5 ( const doubleArray& p1, doubleArray& p2, doubleArray& p3 )
  
{
#ifdef CCM_TEST_PYTHON
  DEBUGNL ( " CCM_Hello_impl->call_python_foo5 ( p1, p2, p3 )" );
  return call_python_foo5 ( p1, p2, p3 );
#else
  DEBUGNL ( " CCM_Hello_impl->foo5 ( p1, p2, p3 )" );

  doubleArray r(10);
  for(int i=0; i<(int)p1.size(); i++) {
    r.at(i) = p1.at(i);
    p3.at(i) = p2.at(i);
    p2.at(i) = p1.at(i);
  }
  return r;

#endif
}

short
CCM_Hello_impl::println1 ( const short p1, short& p2, short& p3 )
  
{
#ifdef CCM_TEST_PYTHON
  DEBUGNL ( " CCM_Hello_impl->call_python_println1 ( p1, p2, p3 )" );
  return call_python_println1 ( p1, p2, p3 );
#else
  DEBUGNL ( " CCM_Hello_impl->println1 ( p1, p2, p3 )" );

  // TODO : IMPLEMENT ME HERE !
  p3=p2;
  p2=p1;
  return p3+p1;
#endif
}

long
CCM_Hello_impl::println2 ( const long p1, long& p2, long& p3 )
  
{
#ifdef CCM_TEST_PYTHON
  DEBUGNL ( " CCM_Hello_impl->call_python_println2 ( p1, p2, p3 )" );
  return call_python_println2 ( p1, p2, p3 );
#else
  DEBUGNL ( " CCM_Hello_impl->println2 ( p1, p2, p3 )" );

  // TODO : IMPLEMENT ME HERE !
  p3=p2;
  p2=p1;
  return p3+p1;
#endif
}

unsigned short
CCM_Hello_impl::println3 ( const unsigned short p1, unsigned short& p2, unsigned short& p3 )
  
{
#ifdef CCM_TEST_PYTHON
  DEBUGNL ( " CCM_Hello_impl->call_python_println3 ( p1, p2, p3 )" );
  return call_python_println3 ( p1, p2, p3 );
#else
  DEBUGNL ( " CCM_Hello_impl->println3 ( p1, p2, p3 )" );

  // TODO : IMPLEMENT ME HERE !
  p3=p2;
  p2=p1;
  return p3+p1;
#endif
}

unsigned long
CCM_Hello_impl::println4 ( const unsigned long p1, unsigned long& p2, unsigned long& p3 )
  
{
#ifdef CCM_TEST_PYTHON
  DEBUGNL ( " CCM_Hello_impl->call_python_println4 ( p1, p2, p3 )" );
  return call_python_println4 ( p1, p2, p3 );
#else
  DEBUGNL ( " CCM_Hello_impl->println4 ( p1, p2, p3 )" );

  // TODO : IMPLEMENT ME HERE !
  p3=p2;
  p2=p1;
  return p3+p1;
#endif
}

float
CCM_Hello_impl::println5 ( const float p1, float& p2, float& p3 )
  
{
#ifdef CCM_TEST_PYTHON
  DEBUGNL ( " CCM_Hello_impl->call_python_println5 ( p1, p2, p3 )" );
  return call_python_println5 ( p1, p2, p3 );
#else
  DEBUGNL ( " CCM_Hello_impl->println5 ( p1, p2, p3 )" );

  // TODO : IMPLEMENT ME HERE !
  p3=p2;
  p2=p1;
  return p3+p1;
#endif
}

double
CCM_Hello_impl::println6 ( const double p1, double& p2, double& p3 )
  
{
#ifdef CCM_TEST_PYTHON
  DEBUGNL ( " CCM_Hello_impl->call_python_println6 ( p1, p2, p3 )" );
  return call_python_println6 ( p1, p2, p3 );
#else
  DEBUGNL ( " CCM_Hello_impl->println6 ( p1, p2, p3 )" );

  // TODO : IMPLEMENT ME HERE !
  p3=p2;
  p2=p1;
  return p3+p1;
#endif
}

char
CCM_Hello_impl::println7 ( const char p1, char& p2, char& p3 )
  
{
#ifdef CCM_TEST_PYTHON
  DEBUGNL ( " CCM_Hello_impl->call_python_println7 ( p1, p2, p3 )" );
  return call_python_println7 ( p1, p2, p3 );
#else
  DEBUGNL ( " CCM_Hello_impl->println7 ( p1, p2, p3 )" );

  // TODO : IMPLEMENT ME HERE !
  p3=p2;
  p2=p1;
  return p3+p1;
#endif
}

std::string
CCM_Hello_impl::println8 ( const std::string& p1, std::string& p2, std::string& p3 )
  
{
#ifdef CCM_TEST_PYTHON
  DEBUGNL ( " CCM_Hello_impl->call_python_println8 ( p1, p2, p3 )" );
  return call_python_println8 ( p1, p2, p3 );
#else
  DEBUGNL ( " CCM_Hello_impl->println8 ( p1, p2, p3 )" );

  // TODO : IMPLEMENT ME HERE !
  cout << p1 << endl;
  cout << p2 << endl;
  cout << p3 << endl;

  p3=p2;
  p2=p1;
  return p3+p1;
#endif
}

bool
CCM_Hello_impl::println9 ( const bool p1, bool& p2, bool& p3 )
  
{
#ifdef CCM_TEST_PYTHON
  DEBUGNL ( " CCM_Hello_impl->call_python_println9 ( p1, p2, p3 )" );
  return call_python_println9 ( p1, p2, p3 );
#else
  DEBUGNL ( " CCM_Hello_impl->println9 ( p1, p2, p3 )" );

  // TODO : IMPLEMENT ME HERE !
  p3=p2;
  p2=p1;
  return p3 && p1;
#endif
}

unsigned char
CCM_Hello_impl::println10 ( const unsigned char p1, unsigned char& p2, unsigned char& p3 )
  
{
#ifdef CCM_TEST_PYTHON
  DEBUGNL ( " CCM_Hello_impl->call_python_println10 ( p1, p2, p3 )" );
  return call_python_println10 ( p1, p2, p3 );
#else
  DEBUGNL ( " CCM_Hello_impl->println10 ( p1, p2, p3 )" );

  // TODO : IMPLEMENT ME HERE !
  p3=p2;
  p2=p1;
  return p3+p1;
#endif
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



