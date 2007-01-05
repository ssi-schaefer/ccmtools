#ifndef __REMOVE_FAILURE__CC__
#define __REMOVE_FAILURE__CC__

#include "ccmtools.h"

namespace Components {

using namespace std;
using namespace wamas::platform::utils;

RemoveFailure::RemoveFailure() 
	throw()
  : Exception("Components::RemoveFailure"), reason_(0)
{
}

RemoveFailure::RemoveFailure(const FailureReason reason) 
	throw()
  : Exception("Components::RemoveFailure"), reason_(reason)
{
}

RemoveFailure::~RemoveFailure()
	throw()
{
}

} // /namespace Components


#endif // __REMOVE_FAILURE__CC__
