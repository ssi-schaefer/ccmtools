#ifndef __HOME_FINDER__CC__
#define __HOME_FINDER__CC__

#include "CCM.h"

namespace Components {

using namespace std;
using namespace wamas::platform::utils;

HomeFinder* HomeFinder::instance_ = NULL;

HomeFinder*
HomeFinder::Instance()
{
	if(instance_ == NULL)
	{
		instance_ = new HomeFinder();
	}
	return instance_;
}


void 
HomeFinder::destroy()
{
	delete instance_;
}


wamas::platform::utils::SmartPtr<CCMHome> 
HomeFinder::find_home_by_name(const std::string& name)
	throw(HomeNotFound) 
{
	HomePoolMap::iterator it;
	it = HomePool.find(name);
	if(it == HomePool.end())
	{
		throw ::Components::HomeNotFound();
	}
	else
	{
		return HomePool[name];
	}		
}


wamas::platform::utils::SmartPtr<CCMHome> 
HomeFinder::find_home_by_component_type(const std::string& comp_repid)
	throw(HomeNotFound)
{
	throw NotImplemented("HomeFinder::find_home_by_component_type()");
}


wamas::platform::utils::SmartPtr<CCMHome> 
HomeFinder::find_home_by_type(const std::string& home_repid)
	throw(HomeNotFound)
{
	throw NotImplemented("HomeFinder::find_home_by_type()");
}


void
HomeFinder::register_home(SmartPtr< ::Components::CCMHome> home_ref, const string& home_name)
{
	if(home_ref == SmartPtr< ::Components::CCMHome>())
	{
		throw ::Components::HomeNotFound();
	}
	HomePool.insert(make_pair(home_name,home_ref));
}

void
HomeFinder::unregister_home(SmartPtr< ::Components::CCMHome> home_ref)
{
	HomePoolMap::iterator it;
	for(it = HomePool.begin(); it != HomePool.end();) 
	{
		if(it->second ==  home_ref) 
		{
			HomePool.erase(it++);
		}
 		else 
		{
			++it;
		}
	}
}

void
HomeFinder::unregister_home(const string& home_name)
{
	HomePoolMap::iterator pos;
	pos = HomePool.find(home_name);
	if(pos == HomePool.end())
	{
		throw ::Components::HomeNotFound();
	}
	else
	{
		HomePool.erase(pos);
	}
}

} // /namespace Components


#endif // HOME_FINDER__CC__


