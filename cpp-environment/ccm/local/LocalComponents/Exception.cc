#ifndef __EXCEPTION__CC__
#define __EXCEPTION__CC__

#include "ccmtools.h"

namespace Components {

using namespace std;
using namespace wamas::platform::utils;

Exception::Exception() 
	throw()
  : message_("Components::Exception")
{
}

Exception::Exception(const std::string& message) 
	throw()
  : message_(message)
{
}
    
Exception::~Exception()
	throw()
{
}

const char* 
Exception::what() const 
	throw()
{
    return message_.c_str(); 
}

} // /namespace Components


#endif // EXCEPTION__CC__
