#include <iostream>
#include <string>

#include "HomeFinderImpl.h"

namespace ccm {
namespace local {	

using namespace std;
using namespace wamas::platform::utils;

SmartPtr< ::Components::CCMHome>
HomeFinderImpl::find_home_by_name(const string& name)
  throw(::Components::HomeNotFound)
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

SmartPtr< ::Components::CCMHome>
HomeFinderImpl::find_home_by_type(const string& home_repid)
  throw(::Components::HomeNotFound)
{
	throw ::Components::NotImplemented("HomeFinder::find_home_by_type()");
}

SmartPtr< ::Components::CCMHome>
HomeFinderImpl::find_home_by_component_type(const string& comp_repid)
  throw(::Components::HomeNotFound)
{
	throw ::Components::NotImplemented("HomeFinder::find_home_by_component_type()");
}

void
HomeFinderImpl::register_home(SmartPtr< ::Components::CCMHome> home_ref, const string& home_name)
{
	if(home_ref == SmartPtr< ::Components::CCMHome>())
	{
		throw ::Components::HomeNotFound();
	}
	HomePool.insert(make_pair(home_name,home_ref));
}

void
HomeFinderImpl::unregister_home(SmartPtr< ::Components::CCMHome> home_ref)
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
HomeFinderImpl::unregister_home(const string& home_name)
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

} // /namespace local
} // /namespace ccm

