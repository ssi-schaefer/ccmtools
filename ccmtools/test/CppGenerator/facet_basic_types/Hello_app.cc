
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
  console_impl* facet = new console_impl ( this );
  return dynamic_cast<CCM_Console*> ( facet );
}



//==============================================================================
// console - facet implementation
//==============================================================================

console_impl::console_impl( CCM_Hello_impl* c  )
  : component(c)
{
  DEBUGNL ( "+console_impl->console_impl (  )" );
}

console_impl::~console_impl (  )
{
  DEBUGNL ( "-console_impl->~console_impl (  )" );
}

short
console_impl::println1 ( const short p1, short& p2, short& p3 )
  
{
  DEBUGNL ( " console_impl->println1 ( p1, p2, p3 )" );

  // TODO : IMPLEMENT ME HERE !
  p3=p2;
  p2=p1;
  return p3+p1;
}

long
console_impl::println2 ( const long p1, long& p2, long& p3 )
  
{
  DEBUGNL ( " console_impl->println2 ( p1, p2, p3 )" );

  // TODO : IMPLEMENT ME HERE !
  p3=p2;
  p2=p1;
  return p3+p1; 
}

unsigned short
console_impl::println3 ( const unsigned short p1, unsigned short& p2, unsigned short& p3 )
  
{
  DEBUGNL ( " console_impl->println3 ( p1, p2, p3 )" );

  // TODO : IMPLEMENT ME HERE !
  p3=p2;
  p2=p1;
  return p3+p1; 
}

unsigned long
console_impl::println4 ( const unsigned long p1, unsigned long& p2, unsigned long& p3 )
  
{
  DEBUGNL ( " console_impl->println4 ( p1, p2, p3 )" );

  // TODO : IMPLEMENT ME HERE !
  p3=p2;
  p2=p1;
  return p3+p1; 
}

float
console_impl::println5 ( const float p1, float& p2, float& p3 )
  
{
  DEBUGNL ( " console_impl->println5 ( p1, p2, p3 )" );

  // TODO : IMPLEMENT ME HERE !
  p3=p2;
  p2=p1;
  return p3+p1; 
}

double
console_impl::println6 ( const double p1, double& p2, double& p3 )
  
{
  DEBUGNL ( " console_impl->println6 ( p1, p2, p3 )" );

  // TODO : IMPLEMENT ME HERE !
  p3=p2;
  p2=p1;
  return p3+p1; 
}

char
console_impl::println7 ( const char p1, char& p2, char& p3 )
  
{
  DEBUGNL ( " console_impl->println7 ( p1, p2, p3 )" );

  // TODO : IMPLEMENT ME HERE !
  p3=p2;
  p2=p1;
  return p3+p1; 
}

std::string
console_impl::println8 ( const std::string& p1, std::string& p2, std::string& p3 )
  
{
  DEBUGNL ( " console_impl->println8 ( p1, p2, p3 )" );

  // TODO : IMPLEMENT ME HERE !
  cout << p1 << endl;
  cout << p2 << endl;
  cout << p3 << endl;

  p3=p2;
  p2=p1;
  return p3+p1;
}

bool
console_impl::println9 ( const bool p1, bool& p2, bool& p3 )
  
{
  DEBUGNL ( " console_impl->println9 ( p1, p2, p3 )" );

  // TODO : IMPLEMENT ME HERE !
  p3=p2;
  p2=p1;
  return p3 && p1;
}

unsigned char
console_impl::println10 ( const unsigned char p1, unsigned char& p2, unsigned char& p3 )
  
{
  DEBUGNL ( " console_impl->println10 ( p1, p2, p3 )" );

  // TODO : IMPLEMENT ME HERE !
  p3=p2;
  p2=p1;
  return p3+p1; 
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



