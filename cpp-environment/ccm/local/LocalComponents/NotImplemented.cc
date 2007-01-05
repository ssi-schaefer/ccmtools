#ifndef __NOT_IMPLEMENTED__CC__
#define __NOT_IMPLEMENTED__CC__

#include "ccmtools.h"

namespace Components {

using namespace std;
using namespace wamas::platform::utils;

NotImplemented::NotImplemented() 
	throw()
  : Exception("Components::NotImplemented")
{
}

NotImplemented::NotImplemented(const std::string& message) 
	throw()
  : Exception(message)
{
}

NotImplemented::~NotImplemented() 
	throw()
{
}
 
} // /namespace Components


#endif // __NOT_IMPLEMENTED__CC__
