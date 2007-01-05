#ifndef __EXCEEDED_CONNECTION_LIMIT__CC__
#define __EXCEEDED_CONNECTION_LIMIT__CC__

#include "ccmtools.h"

namespace Components {

using namespace std;
using namespace wamas::platform::utils;

ExceededConnectionLimit::ExceededConnectionLimit() 
	throw()
  : Exception("Components::ExceededConnectionLimit")
{
}

ExceededConnectionLimit::~ExceededConnectionLimit() 
	throw()
{
}


} // /namespace Components


#endif // __EXCEEDED_CONNECTION_LIMIT__CC__
