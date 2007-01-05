#ifndef __COOKIE_REQUIRED__CC__
#define __COOKIE_REQUIRED__CC__

#include "ccmtools.h"

namespace Components {

using namespace std;
using namespace wamas::platform::utils;

CookieRequired::CookieRequired() 
	throw()
  : Exception("Components::CookieRequired")
{
}

CookieRequired::~CookieRequired() 
	throw()
{
}
    
} // /namespace Components


#endif // __COOKIE_REQUIRED__CC__
