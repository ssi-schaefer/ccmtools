
//==============================================================================
// HelloHome_mirror - home business logic implementation
//==============================================================================

#include <iostream>
#include <CCM_Utils/Debug.h>

#include "Hello_mirror_app.h"
#include "HelloHome_mirror_app.h"

using namespace std;
using namespace CCM_Utils;

namespace CCM_Local {
namespace CCM_Session_Hello_mirror {


//==============================================================================
// implementation of component home
//==============================================================================

CCM_HelloHome_mirror_impl::CCM_HelloHome_mirror_impl (  )
{
  DEBUGNL ( "+CCM_HelloHome_mirror_impl->CCM_HelloHome_mirror_impl (  )" );
}

CCM_HelloHome_mirror_impl::~CCM_HelloHome_mirror_impl (  )
{
  DEBUGNL ( "-CCM_HelloHome_mirror_impl->~CCM_HelloHome_mirror_impl (  )" );
}

localComponents::EnterpriseComponent*
CCM_HelloHome_mirror_impl::create (  )
  throw ( localComponents::CCMException )
{
  DEBUGNL ( " CCM_HelloHome_mirror_impl->create (  )" );
  return dynamic_cast<localComponents::EnterpriseComponent*>(new CCM_Hello_mirror_impl (  ));
}



} // /namespace CCM_Session_Hello_mirror
} // /namespace CCM_Local


//==============================================================================
// entry point
//==============================================================================

extern "C" {
  localComponents::HomeExecutorBase*
  create_HelloHome_mirror (  )
  {
    DEBUGNL ( " create_HelloHome_mirror (  )" );
    return new CCM_Local::CCM_Session_Hello_mirror::CCM_HelloHome_mirror_impl (  );
  }
}

