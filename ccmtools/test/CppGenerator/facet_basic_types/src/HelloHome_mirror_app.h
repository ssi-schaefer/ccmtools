
//==============================================================================
// HelloHome_mirror - home class business logic
//==============================================================================

#ifndef __CCM_Local_CCM_Session_Hello_mirror_HelloHome_mirror_APP__H__
#define __CCM_Local_CCM_Session_Hello_mirror_HelloHome_mirror_APP__H__

#include <CCM_Local/CCM_Session_Hello_mirror/HelloHome_mirror_share.h>
#include "HelloHome_mirror_entry.h"

namespace CCM_Local {
namespace CCM_Session_Hello_mirror {


class CCM_HelloHome_mirror;

class CCM_HelloHome_mirror_impl
  : public CCM_HelloHome_mirror
{
 public:
  CCM_HelloHome_mirror_impl (  );
  virtual ~CCM_HelloHome_mirror_impl (  );

  virtual localComponents::EnterpriseComponent* create (  )
    throw ( localComponents::CCMException );


};

} // /namespace CCM_Session_Hello_mirror
} // /namespace CCM_Local


#endif


