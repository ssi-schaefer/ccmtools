#include <iostream>
#include <string>

#include <wamas/platform/utils/debug.h>

#include "HomeFinder.h"

using namespace std;
using namespace wamas::platform::utils;

namespace ccm {
namespace local {

Components::ccm::local::HomeFinder* HomeFinder::instance_ = NULL;

HomeFinder::HomeFinder (  )
{
  LDEBUGNL(CCM_CONTAINER,"+HomeFinder->Homefinder (  )" );
}

HomeFinder::~HomeFinder (  )
{
  LDEBUGNL(CCM_CONTAINER,"-HomeFinder->~Homefinder (  )" );
}

Components::ccm::local::HomeFinder*
HomeFinder::Instance (  )
{
  LDEBUGNL(CCM_CONTAINER," HomeFinder::Instance (  )" );
  if(instance_ == NULL)
     instance_ = new HomeFinder();
  return instance_;
}

void 
HomeFinder::destroy()
{
  LDEBUGNL(CCM_CONTAINER," HomeFinder::destroy()");
  delete instance_;
}


SmartPtr<Components::ccm::local::CCMHome>
HomeFinder::find_home_by_name ( const std::string& name )
  throw (Components::ccm::local::HomeNotFound )
{
  LDEBUGNL(CCM_CONTAINER," HomeFinder->find_home_by_name( '" << name << "' )");
  HomePoolMap::iterator it;
  it = HomePool.find(name);
  if(it == HomePool.end())
    throw Components::ccm::local::HomeNotFound();
  else
    return HomePool[name];
}

SmartPtr<Components::ccm::local::CCMHome>
HomeFinder::find_home_by_type ( const std::string& home_repid )
  throw (Components::ccm::local::HomeNotFound )
{
  LDEBUGNL(CCM_CONTAINER," HomeFinder->find_home_by_type (  )" );
  // TODO
  throw Components::ccm::local::NotImplemented("HomeFinder::find_home_by_type()");
}

SmartPtr<Components::ccm::local::CCMHome>
HomeFinder::find_home_by_component_type ( const std::string& comp_repid )
  throw (Components::ccm::local::HomeNotFound )
{
  LDEBUGNL(CCM_CONTAINER," HomeFinder->find_home_by_component_type (  )" );
  // TODO
  throw Components::ccm::local::NotImplemented("HomeFinder::find_home_by_component_type()");
}

void
HomeFinder::register_home ( SmartPtr<Components::ccm::local::CCMHome> home_ref, 
                            const std::string& home_name )
{
  LDEBUGNL(CCM_CONTAINER," HomeFinder->register_home (  )" );
  if ( home_ref == SmartPtr<Components::ccm::local::CCMHome> (  ) )
    throw Components::ccm::local::HomeNotFound (  );

  HomePool.insert ( make_pair ( home_name, home_ref ) );
}

void
HomeFinder::unregister_home ( SmartPtr<Components::ccm::local::CCMHome> home_ref )
{
  LDEBUGNL(CCM_CONTAINER," HomeFinder->unregister_home ( home_ref )" );
  // the correct way to remove elements ;-) (Josuttis p205)
  HomePoolMap::iterator it;
  for ( it = HomePool.begin (  ); it != HomePool.end (  ); ) {
    if ( it->second ==  home_ref ) {
      HomePool.erase ( it++ );
    }
    else {
      ++it;
    }
  }
}

void
HomeFinder::unregister_home(const std::string& home_name)
{
  LDEBUGNL(CCM_CONTAINER," HomeFinder->unregister_home(home_name)");
  HomePoolMap::iterator pos;
  pos = HomePool.find(home_name);
  if(pos == HomePool.end())
  {
      throw Components::ccm::local::HomeNotFound();
  }
  else
  {
   	  HomePool.erase(pos);
  }
}

} // /namespace local
} // /namespace ccm


