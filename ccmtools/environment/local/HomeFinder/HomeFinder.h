//==============================================================================
// home finder
//==============================================================================

#ifndef __CCM__LOCAL__HOMEFINDER__H__
#define __CCM__LOCAL__HOMEFINDER__H__

#include <map>
#include <CCM_Utils/SmartPointer.h>
#include <localComponents/CCM.h>

namespace CCM_Local {

typedef std::map<std::string, CCM_Utils::SmartPtr<localComponents::CCMHome> > HomePoolMap;

/***
 * This is a local version of a HomeFinder which returns local (C++) references
 * of component homes. We use a singleton pattern to implement the local
 * HomeFinder. Instance (  ) provides a pointer to the single instance.
 *
 * Note: this is a simple hard coded version - there will be a dynamic map...
 ***/
class HomeFinder
  : public localComponents::HomeFinder
{
 private:
  static localComponents::HomeFinder* instance;
  HomePoolMap HomePool;

 protected:
  HomeFinder (  );
  virtual ~HomeFinder (  );

 public:
  static localComponents::HomeFinder* Instance (  );

  // methods from HomeFinder
  CCM_Utils::SmartPtr<localComponents::CCMHome>
  find_home_by_name ( const std::string& name )
    throw ( localComponents::HomeNotFound );

  CCM_Utils::SmartPtr<localComponents::CCMHome>
    find_home_by_type ( const std::string& home_repid )
    throw ( localComponents::HomeNotFound );

  CCM_Utils::SmartPtr<localComponents::CCMHome>
    find_home_by_component_type ( const std::string& comp_repid )
    throw ( localComponents::HomeNotFound );

  // methods from HomeRegistration
  void register_home ( CCM_Utils::SmartPtr<localComponents::CCMHome> home_ref, const std::string& home_name );
  void unregister_home ( CCM_Utils::SmartPtr<localComponents::CCMHome> home_ref );
  void unregister_home ( const std::string& home_name );
};

} // /namespace CCM_Local

#endif // __CCM__LOCAL__HOMEFINDER__H__


