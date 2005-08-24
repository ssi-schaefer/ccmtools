//==============================================================================
// Implementation of HomeFinder
//==============================================================================

#include <iostream>
#include <string>
#include <WX/Utils/debug.h>

#include "HomeFinder.h"

using namespace std;
using namespace WX::Utils;

namespace ccm {
namespace local {

Components::HomeFinder* HomeFinder::instance = NULL;

HomeFinder::HomeFinder (  )
{
  DEBUGNL ( "+HomeFinder->Homefinder (  )" );
}

HomeFinder::~HomeFinder (  )
{
  DEBUGNL ( "-HomeFinder->~Homefinder (  )" );
}

Components::HomeFinder*
HomeFinder::Instance (  )
{
  DEBUGNL ( " HomeFinder::Instance (  )" );
  if ( instance == NULL )
     instance = new HomeFinder (  );
  return instance;
}

SmartPtr<Components::CCMHome>
HomeFinder::find_home_by_name ( const std::string& name )
  throw (Components::HomeNotFound )
{
  DEBUGNL ( " HomeFinder->find_home_by_name ( '" << name << "' )" );

  HomePoolMap::iterator it;
  it = HomePool.find ( name );
  if ( it == HomePool.end (  ) )
    throw Components::HomeNotFound (  );
  else
    return HomePool[name];
}

SmartPtr<Components::CCMHome>
HomeFinder::find_home_by_type ( const std::string& home_repid )
  throw (Components::HomeNotFound )
{
  DEBUGNL ( " HomeFinder->find_home_by_type (  )" );
  // TODO
  throw Components::NotImplemented("HomeFinder::find_home_by_type()");
}

SmartPtr<Components::CCMHome>
HomeFinder::find_home_by_component_type ( const std::string& comp_repid )
  throw (Components::HomeNotFound )
{
  DEBUGNL ( " HomeFinder->find_home_by_component_type (  )" );
  // TODO
  throw Components::NotImplemented("HomeFinder::find_home_by_component_type()");
}

void
HomeFinder::register_home ( SmartPtr<Components::CCMHome> home_ref, 
                            const std::string& home_name )
{
  DEBUGNL ( " HomeFinder->register_home (  )" );
  if ( home_ref == SmartPtr<Components::CCMHome> (  ) )
    throw Components::HomeNotFound (  );

  HomePool.insert ( make_pair ( home_name, home_ref ) );
}

void
HomeFinder::unregister_home ( SmartPtr<Components::CCMHome> home_ref )
{
  DEBUGNL ( " HomeFinder->unregister_home ( home_ref )" );
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
HomeFinder::unregister_home ( const std::string& home_name )
{
  DEBUGNL ( " HomeFinder->unregister_home ( home_name )" );
  HomePoolMap::iterator pos;
  pos = HomePool.find(home_name);
  if ( pos == HomePool.end (  ) )
    throw Components::HomeNotFound (  );
  else
    return HomePool.erase ( pos );
}

} // /namespace local
} // /namespace ccm


