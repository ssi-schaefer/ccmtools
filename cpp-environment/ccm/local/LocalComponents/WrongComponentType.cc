#ifndef __WRONG_COMPONENT_TYPE__CC__
#define __WRONG_COMPONENT_TYPE__CC__

#include "ccmtools.h"

namespace Components {

using namespace std;
using namespace wamas::platform::utils;

WrongComponentType::WrongComponentType() 
	throw()
  : Exception("Components::WrongComponentType" )
{
}

WrongComponentType::~WrongComponentType() 
	throw()
{
}
      
} // /namespace Components


#endif // __WRONG_COMPONENT_TYPE__CC__
