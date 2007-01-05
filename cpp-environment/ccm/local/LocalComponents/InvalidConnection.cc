#ifndef __INVALID_CONNECTION__CC__
#define __INVALID_CONNECTION__CC__

#include "ccmtools.h"

namespace Components {

using namespace std;
using namespace wamas::platform::utils;

InvalidConnection::InvalidConnection() 
	throw()
  : Exception("Components::InvalidConnection")
{
}

InvalidConnection::~InvalidConnection() 
	throw()
{
}

} // /namespace Components


#endif // __INVALID_CONNECTION__CC__
