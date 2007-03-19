#ifndef __HOME_NOT_FOUND__CC__
#define __HOME_NOT_FOUND__CC__

#include "ccmtools.h"

namespace Components {

using namespace std;
using namespace wamas::platform::utils;

HomeNotFound::HomeNotFound() 
	throw()
  : Exception("Components::HomeNotFound")
{
}

HomeNotFound::HomeNotFound(const std::string& message)
    throw()
  : Exception(message)
{
}

HomeNotFound::~HomeNotFound() 
	throw()
{
}

} // /namespace Components


#endif // __HOME_NOT_FOUND__CC__
