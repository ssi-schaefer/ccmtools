#ifndef __NO_CONNECTION__CC__
#define __NO_CONNECTION__CC__

#include "ccmtools.h"

namespace Components {

using namespace std;
using namespace wamas::platform::utils;

NoConnection::NoConnection() 
	throw()
  : Exception("Components::NoConnection")
{
}

NoConnection::~NoConnection() 
	throw()
{
}

} // /namespace Components


#endif // __NO_CONNECTION__CC__
