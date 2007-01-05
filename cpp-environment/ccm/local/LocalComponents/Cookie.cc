#ifndef __COOKIE__CC__
#define __COOKIE__CC__

#include "ccmtools.h"

namespace Components {

using namespace std;
using namespace wamas::platform::utils;

Cookie::Cookie() 
{ 
	cookieValue_ = ""; 
}

Cookie::Cookie(const std::string& value) 
{ 
	cookieValue_ = value; 
}

Cookie::~Cookie() 
{
}


bool 
Cookie::operator< (const Cookie& ck) const 
{ 
	return cookieValue_ < ck.cookieValue_; 
}


OctetSeq 
Cookie::cookieValue() const 
{ 
	return cookieValue_; 
}


void 
Cookie::cookieValue(const OctetSeq& cookieValue) 
{ 
	cookieValue_ = cookieValue; 
}

} // /namespace Components


#endif // __COOKIE__CC__
