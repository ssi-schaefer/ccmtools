
//==============================================================================
// HelloHome - home business logic implementation
//==============================================================================

#include <iostream>
#include <CCM_Utils/Debug.h>

#include "Hello_app.h"
#include "HelloHome_app.h"

using namespace std;
using namespace CCM_Utils;

namespace CCM_Local {
namespace CCM_Session_Hello {


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
  return dynamic_cast<localComponents::EnterpriseComponent*>(new CCM_Hello_impl (  ));
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

