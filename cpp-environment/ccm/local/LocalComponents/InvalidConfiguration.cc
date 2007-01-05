#ifndef __INVALID_CONFIGURATION__CC__
#define __INVALID_CONFIGURATION__CC__

#include "ccmtools.h"

namespace Components {

using namespace std;
using namespace wamas::platform::utils;
    
InvalidConfiguration::InvalidConfiguration() 
	throw()
  : Exception("Components::InvalidConfiguration")
{
}

InvalidConfiguration::~InvalidConfiguration() 
	throw()
{
}

} // /namespace Components


#endif // __INVALID_CONFIGURATION__CC__
