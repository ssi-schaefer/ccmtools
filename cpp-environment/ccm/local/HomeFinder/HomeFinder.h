//==============================================================================
// home finder
//==============================================================================

#ifndef __CCM__LOCAL__HOMEFINDER__H__
#define __CCM__LOCAL__HOMEFINDER__H__

#include <map>
#include <wx/utils/smartptr.h>
#include <Components/ccm/local/CCM.h>

namespace ccm {
namespace local {

typedef std::map<std::string, 
  wx::utils::SmartPtr<Components::ccm::local::CCMHome> > HomePoolMap;

/***
 * This is a local version of a HomeFinder which returns local (C++) references
 * of component homes. We use a singleton pattern to implement the local
 * HomeFinder. Instance (  ) provides a pointer to the single instance.
 *
 * Note: this is a simple hard coded version - there will be a dynamic map...
 ***/
class HomeFinder
  : public Components::ccm::local::HomeFinder
{
 private:
  static Components::ccm::local::HomeFinder* instance_;
  HomePoolMap HomePool;

 protected:
  HomeFinder (  );
  virtual ~HomeFinder (  );

 public:
  static Components::ccm::local::HomeFinder* Instance();
  static void destroy();

  // methods from HomeFinder
  wx::utils::SmartPtr<Components::ccm::local::CCMHome>
  find_home_by_name(const std::string& name)
    throw(Components::ccm::local::HomeNotFound);

  wx::utils::SmartPtr<Components::ccm::local::CCMHome>
  find_home_by_type(const std::string& home_repid)
    throw (Components::ccm::local::HomeNotFound);

  wx::utils::SmartPtr<Components::ccm::local::CCMHome>
  find_home_by_component_type ( const std::string& comp_repid )
    throw (Components::ccm::local::HomeNotFound);

  // methods from HomeRegistration
  void register_home(wx::utils::SmartPtr<Components::ccm::local::CCMHome> home_ref, 
		     const std::string& home_name);
  void unregister_home(wx::utils::SmartPtr<Components::ccm::local::CCMHome> home_ref);
  void unregister_home(const std::string& home_name);
};

} // /namespace local
} // /namespace ccm

#endif // __CCM__LOCAL__HOMEFINDER__H__


