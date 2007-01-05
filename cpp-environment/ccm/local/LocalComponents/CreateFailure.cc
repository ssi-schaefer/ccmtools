#ifndef __CREATE_FAILURE__CC__
#define __CREATE_FAILURE__CC__

#include "ccmtools.h"

namespace Components {

using namespace std;
using namespace wamas::platform::utils;

CreateFailure::CreateFailure() 
	throw()
  : Exception("Components::CreateFailure"), reason_(0)
{
}

CreateFailure::CreateFailure(const FailureReason reason) 
	throw()
  : Exception("Components::CreateFailure"), reason_(reason)
{
}

CreateFailure::~CreateFailure() 
	throw()
{
}
    
} // /namespace Components


#endif // __CREATE_FAILURE__CC__
