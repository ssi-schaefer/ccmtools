#ifndef __ALREADY_CONNECTED__CC__
#define __ALREADY_CONNECTED__CC__

#include "ccmtools.h"

namespace Components {

using namespace std;
using namespace wamas::platform::utils;

AlreadyConnected::AlreadyConnected() 
	throw()
  : Exception("Components::AlreadyConnected")
{
}

AlreadyConnected::~AlreadyConnected() 
	throw()
{
}


} // /namespace Components


#endif // __ALREADY_CONNECTED__CC__
