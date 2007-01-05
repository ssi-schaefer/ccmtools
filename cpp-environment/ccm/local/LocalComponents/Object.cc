#ifndef __OBJECT__CC__
#define __OBJECT__CC__

#include "ccmtools.h"

namespace Components {

using namespace std;
using namespace wamas::platform::utils;

Object::~Object() 
{
}

Object::SmartPtr 
Object::get_component()
{
	// TODO
	return SmartPtr();
};

} // /namespace Components


#endif // __OBJECT__CC__
