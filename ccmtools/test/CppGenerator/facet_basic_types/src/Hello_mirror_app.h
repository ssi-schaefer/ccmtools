
//==============================================================================
// Hello_mirror - business logic class definition
//==============================================================================

#ifndef __CCM_Local_CCM_Session_Hello_mirror_Hello_mirror_APP__H__
#define __CCM_Local_CCM_Session_Hello_mirror_Hello_mirror_APP__H__

#include <CCM_Local/CCM_Session_Hello_mirror/Hello_mirror_share.h>

namespace CCM_Local {
namespace CCM_Session_Hello_mirror {


//==============================================================================
// component implementation object
//==============================================================================

class CCM_Hello_mirror_impl
  : public CCM_Hello_mirror
{
 private:


 public:
  CCM_Hello_mirror_Context* ctx;

  CCM_Hello_mirror_impl (  );
  virtual ~CCM_Hello_mirror_impl (  );





  // Callback methods

  virtual void set_session_context ( localComponents::SessionContext* ctx )
    throw ( localComponents::CCMException );
  virtual void ccm_activate (  )
    throw ( localComponents::CCMException );
  virtual void ccm_passivate (  )
    throw ( localComponents::CCMException );
  virtual void ccm_remove (  )
    throw ( localComponents::CCMException );
};



} // /namespace CCM_Session_Hello_mirror
} // /namespace CCM_Local


#endif


