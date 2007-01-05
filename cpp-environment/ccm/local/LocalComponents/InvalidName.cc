#ifndef __INVALID_NAME__CC__
#define __INVALID_NAME__CC__

#include "ccmtools.h"

namespace Components {

using namespace std;
using namespace wamas::platform::utils;


InvalidName::InvalidName() 
	throw()
  : Exception("Components::InvalidName")
{
}

InvalidName::~InvalidName() 
	throw()
{
}

} // /namespace Components


#endif // __INVALID_NAME__CC__
