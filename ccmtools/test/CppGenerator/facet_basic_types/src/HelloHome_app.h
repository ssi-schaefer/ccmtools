
//==============================================================================
// HelloHome - home class business logic
//==============================================================================

#ifndef __CCM_Local_CCM_Session_Hello_HelloHome_APP__H__
#define __CCM_Local_CCM_Session_Hello_HelloHome_APP__H__

#include <CCM_Local/CCM_Session_Hello/HelloHome_share.h>
#include "HelloHome_entry.h"

namespace CCM_Local {
namespace CCM_Session_Hello {


class CCM_HelloHome;

class CCM_HelloHome_impl
  : public CCM_HelloHome
{
 public:
  CCM_HelloHome_impl (  );
  virtual ~CCM_HelloHome_impl (  );

  virtual localComponents::EnterpriseComponent* create (  )
    throw ( localComponents::CCMException );


};

} // /namespace CCM_Session_Hello
} // /namespace CCM_Local


#endif


