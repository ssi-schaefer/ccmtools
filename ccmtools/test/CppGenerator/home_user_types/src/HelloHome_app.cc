
//==============================================================================
// HelloHome - home business logic implementation
//==============================================================================

#include <iostream>
#include <CCM_Utils/Debug.h>

#include "HelloHome_app.h"
#include "Hello_app.h"

using namespace std;
using namespace CCM_Utils;
using namespace CCM_Local;

namespace CCM_Local {
namespace CCM_Session_Hello {
;

//==============================================================================
// implementation of component home
//==============================================================================

CCM_HelloHome_impl::CCM_HelloHome_impl (  )
{
  DEBUGNL ( "+CCM_HelloHome_impl->CCM_HelloHome_impl (  )" );
}

CCM_HelloHome_impl::~CCM_HelloHome_impl (  )
{
  DEBUGNL ( "-CCM_HelloHome_impl->~CCM_HelloHome_impl (  )" );
}

localComponents::EnterpriseComponent*
CCM_HelloHome_impl::create (  )
  throw ( localComponents::CCMException )
{
  DEBUGNL ( " CCM_HelloHome_impl->create (  )" );
  return ( localComponents::EnterpriseComponent* ) new CCM_Hello_impl (  );
}

localComponents::EnterpriseComponent*
CCM_HelloHome_impl::create_with_typedef ( const time_t& p )
  throw ( localComponents::CreateFailure )
{
  DEBUGNL ( " CCM_HelloHome_impl->create_with_typedef (  )" );
  CCM_Hello_impl* lc = new CCM_Hello_impl();

  // TODO : IMPLEMENT FACTORY DETAILS HERE !
  lc->typedef_value(p);
  return dynamic_cast<localComponents::EnterpriseComponent*> ( lc );
}
localComponents::EnterpriseComponent*
CCM_HelloHome_impl::create_with_enum ( const Color& p )
  throw ( localComponents::CreateFailure )
{
  DEBUGNL ( " CCM_HelloHome_impl->create_with_enum (  )" );
  CCM_Hello_impl* lc = new CCM_Hello_impl();

  // TODO : IMPLEMENT FACTORY DETAILS HERE !
  lc->enum_value(p);
  return dynamic_cast<localComponents::EnterpriseComponent*> ( lc );
}
localComponents::EnterpriseComponent*
CCM_HelloHome_impl::create_with_struct ( const Value& p )
  throw ( localComponents::CreateFailure )
{
  DEBUGNL ( " CCM_HelloHome_impl->create_with_struct (  )" );
  CCM_Hello_impl* lc = new CCM_Hello_impl();

  // TODO : IMPLEMENT FACTORY DETAILS HERE !
  lc->struct_value(p);
  return dynamic_cast<localComponents::EnterpriseComponent*> ( lc );
}
localComponents::EnterpriseComponent*
CCM_HelloHome_impl::create_with_sequence ( const map& p )
  throw ( localComponents::CreateFailure )
{
  DEBUGNL ( " CCM_HelloHome_impl->create_with_sequence (  )" );
  CCM_Hello_impl* lc = new CCM_Hello_impl();

  // TODO : IMPLEMENT FACTORY DETAILS HERE !
  lc->sequence_value(p);
  return dynamic_cast<localComponents::EnterpriseComponent*> ( lc );
}
localComponents::EnterpriseComponent*
CCM_HelloHome_impl::create_with_array ( const doubleArray& p )
  throw ( localComponents::CreateFailure )
{
  DEBUGNL ( " CCM_HelloHome_impl->create_with_array (  )" );
  CCM_Hello_impl* lc = new CCM_Hello_impl();

  // TODO : IMPLEMENT FACTORY DETAILS HERE !
  lc->array_value(p);
  return dynamic_cast<localComponents::EnterpriseComponent*> ( lc );
}


} // /namespace CCM_Session_Hello
} // /namespace CCM_Local


//==============================================================================
// entry point
//==============================================================================

extern "C" {
  localComponents::HomeExecutorBase*
  create_HelloHome (  )
  {
    DEBUGNL ( " create_HelloHome (  )" );
    return new CCM_Local::CCM_Session_Hello::CCM_HelloHome_impl (  );
  }
}

