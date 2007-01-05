#ifndef __CCM_EXCEPTION__CC__
#define __CCM_EXCEPTION__CC__

#include "ccmtools.h"

namespace Components {

using namespace std;
using namespace wamas::platform::utils;

CCMException::CCMException() 
	throw()
  : Exception("Components::CCMException"), reason_(SYSTEM_ERROR)
{
}

CCMException::CCMException(const CCMExceptionReason reason) 
	throw()
  : Exception("Components::CCMException"), reason_(reason)
{
}

CCMException::~CCMException()
	throw()
{
}

} // /namespace Components


#endif // __CCM_EXCEPTION__CC__
