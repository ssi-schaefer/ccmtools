
//==============================================================================
// Hello_mirror - business logic implementation
//==============================================================================

#include <iostream>
#include <CCM_Utils/Debug.h>

#include "Hello_mirror_app.h"

using namespace std;
using namespace CCM_Utils;

namespace CCM_Local {
namespace CCM_Session_Hello_mirror {


//==============================================================================
// business logic functionality
//==============================================================================

CCM_Console*
CCM_Hello_mirror_impl::get_console_mirror (  )
{
  DEBUGNL ( " CCM_Hello_mirror_impl->get_console_mirror (  )" );
  console_mirror_impl* facet = new console_mirror_impl ( this );
  return dynamic_cast<CCM_Console*> ( facet );
}



//==============================================================================
// console_mirror - facet implementation
//==============================================================================

console_mirror_impl::console_mirror_impl( CCM_Hello_mirror_impl* c  )
  : component(c)
{
  DEBUGNL ( "+console_mirror_impl->console_mirror_impl (  )" );
}

console_mirror_impl::~console_mirror_impl (  )
{
  DEBUGNL ( "-console_mirror_impl->~console_mirror_impl (  )" );
}

short
console_mirror_impl::println1 ( const short p1, short& p2, short& p3 )
  
{
  DEBUGNL ( " console_mirror_impl->println1 ( p1, p2, p3 )" );

  // TODO : IMPLEMENT ME HERE !
  p3=p2;
  p2=p1;
  return p3+p1;
}

long
console_mirror_impl::println2 ( const long p1, long& p2, long& p3 )
  
{
  DEBUGNL ( " console_mirror_impl->println2 ( p1, p2, p3 )" );

  // TODO : IMPLEMENT ME HERE !
  p3=p2;
  p2=p1;
  return p3+p1; 
}

unsigned short
console_mirror_impl::println3 ( const unsigned short p1, unsigned short& p2, unsigned short& p3 )
  
{
  DEBUGNL ( " console_mirror_impl->println3 ( p1, p2, p3 )" );

  // TODO : IMPLEMENT ME HERE !
  p3=p2;
  p2=p1;
  return p3+p1; 
}

unsigned long
console_mirror_impl::println4 ( const unsigned long p1, unsigned long& p2, unsigned long& p3 )
  
{
  DEBUGNL ( " console_mirror_impl->println4 ( p1, p2, p3 )" );

  // TODO : IMPLEMENT ME HERE !
  p3=p2;
  p2=p1;
  return p3+p1; 
}

float
console_mirror_impl::println5 ( const float p1, float& p2, float& p3 )
  
{
  DEBUGNL ( " console_mirror_impl->println5 ( p1, p2, p3 )" );

  // TODO : IMPLEMENT ME HERE !
  p3=p2;
  p2=p1;
  return p3+p1; 
}

double
console_mirror_impl::println6 ( const double p1, double& p2, double& p3 )
  
{
  DEBUGNL ( " console_mirror_impl->println6 ( p1, p2, p3 )" );

  // TODO : IMPLEMENT ME HERE !
  p3=p2;
  p2=p1;
  return p3+p1; 
}

char
console_mirror_impl::println7 ( const char p1, char& p2, char& p3 )
  
{
  DEBUGNL ( " console_mirror_impl->println7 ( p1, p2, p3 )" );

  // TODO : IMPLEMENT ME HERE !
  p3=p2;
  p2=p1;
  return p3+p1; 
}

std::string
console_mirror_impl::println8 ( const std::string& p1, std::string& p2, std::string& p3 )
  
{
  DEBUGNL ( " console_mirror_impl->println8 ( p1, p2, p3 )" );

  // TODO : IMPLEMENT ME HERE !
  cout << p1 << endl;
  cout << p2 << endl;
  cout << p3 << endl;

  p3=p2;
  p2=p1;
  return p3+p1;
}

bool
console_mirror_impl::println9 ( const bool p1, bool& p2, bool& p3 )
  
{
  DEBUGNL ( " console_mirror_impl->println9 ( p1, p2, p3 )" );

  // TODO : IMPLEMENT ME HERE !
  p3=p2;
  p2=p1;
  return p3 && p1;
}

unsigned char
console_mirror_impl::println10 ( const unsigned char p1, unsigned char& p2, unsigned char& p3 )
  
{
  DEBUGNL ( " console_mirror_impl->println10 ( p1, p2, p3 )" );

  // TODO : IMPLEMENT ME HERE !
  p3=p2;
  p2=p1;
  return p3+p1; 
}





//==============================================================================
// class implementation
//==============================================================================

CCM_Hello_mirror_impl::CCM_Hello_mirror_impl (  )
{
  DEBUGNL ( "+CCM_Hello_mirror_impl->CCM_Hello_mirror_impl (  )" );
}

CCM_Hello_mirror_impl::~CCM_Hello_mirror_impl (  )
{
  DEBUGNL ( "-CCM_Hello_mirror_impl->~CCM_Hello_mirror_impl (  )" );
}




void
CCM_Hello_mirror_impl::set_session_context ( localComponents::SessionContext* context )
  throw ( localComponents::CCMException )
{
  DEBUGNL ( " CCM_Hello_mirror_impl->set_session_context (  )" );
  ctx = (CCM_Hello_mirror_Context*) context;
}

void
CCM_Hello_mirror_impl::ccm_activate (  )
  throw ( localComponents::CCMException )
{
  DEBUGNL ( " CCM_Hello_mirror_impl->ccm_activate (  )" );
}

void
CCM_Hello_mirror_impl::ccm_passivate (  )
  throw ( localComponents::CCMException )
{
  DEBUGNL ( " CCM_Hello_mirror_impl->ccm_passivate (  )" );
}

void
CCM_Hello_mirror_impl::ccm_remove (  )
  throw ( localComponents::CCMException )
{
  DEBUGNL ( " CCM_Hello_mirror_impl->ccm_remove (  )" );
}

} // /namespace CCM_Session_Hello_mirror
} // /namespace CCM_Local



