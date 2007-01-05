#ifndef __ILLEGAL_STATE__CC__
#define __ILLEGAL_STATE__CC__

#include "ccmtools.h"

namespace Components {

using namespace std;
using namespace wamas::platform::utils;

IllegalState::IllegalState() 
	throw()
  : Exception("Components::IllegalState")
{
}

IllegalState::~IllegalState() 
	throw()
{
}

} // /namespace Components


#endif // __ILLEGAL_STATE__CC__
