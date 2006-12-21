#ifndef __COMPONENTS__CCM__CC__
#define __COMPONENTS__CCM__CC__

#include "CCM.h"
#include "HomeFinderImpl.h"

namespace Components {

using namespace std;
using namespace wamas::platform::utils;

HomeFinder* HomeFinder::instance_ = NULL;

HomeFinder*
HomeFinder::Instance()
{
	if(instance_ == NULL)
	{
		instance_ = new ::ccm::local::HomeFinderImpl();
	}
	return instance_;
}

void 
HomeFinder::destroy()
{
	delete instance_;
}


} // /namespace Components


#endif // COMPONENTS__CCM__CC__


