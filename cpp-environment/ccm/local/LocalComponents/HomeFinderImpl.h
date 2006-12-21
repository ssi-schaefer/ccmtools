#ifndef __COMPONENTS__HOMEFINDER__H__
#define __COMPONENTS__HOMEFINDER__H__

#include <map>
#include <wamas/platform/utils/smartptr.h>
#include "CCM.h"

namespace ccm {
namespace local {
	
typedef std::map<std::string, wamas::platform::utils::SmartPtr< ::Components::CCMHome> > HomePoolMap;

class HomeFinderImpl
  : public ::Components::HomeFinder
{
	public:
	HomeFinderImpl() {}
	virtual ~HomeFinderImpl() {}

	// Methods defined in Components::HomeFinder

	wamas::platform::utils::SmartPtr< ::Components::CCMHome> find_home_by_name(const std::string& name)
		throw(::Components::HomeNotFound);

	wamas::platform::utils::SmartPtr< ::Components::CCMHome> find_home_by_type(const std::string& home_repid)
		throw(::Components::HomeNotFound);

	wamas::platform::utils::SmartPtr< ::Components::CCMHome> find_home_by_component_type(const std::string& comp_repid)
		throw(::Components::HomeNotFound);


  	// Methods defined in Components::HomeRegistration
	
	void register_home(wamas::platform::utils::SmartPtr< ::Components::CCMHome> home_ref, const std::string& home_name);
  
	void unregister_home(wamas::platform::utils::SmartPtr< ::Components::CCMHome> home_ref);

	void unregister_home(const std::string& home_name);

	protected:
	HomePoolMap HomePool;
};

} // /namespace local
} // /namespace local

#endif // __COMPONENTS__HOMEFINDER__H__


