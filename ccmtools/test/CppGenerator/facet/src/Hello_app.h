
//==============================================================================
// Hello - business logic class definition
//==============================================================================

#ifndef __CCM_Local_CCM_Session_Hello_Hello_APP__H__
#define __CCM_Local_CCM_Session_Hello_Hello_APP__H__

#include <CCM_Local/CCM_Session_Hello/Hello_share.h>

namespace CCM_Local {
namespace CCM_Session_Hello {


//==============================================================================
// component implementation object
//==============================================================================

class CCM_Hello_impl
  : public CCM_Hello
{
 private:


 public:
  CCM_Hello_Context* ctx;

  CCM_Hello_impl (  );
  virtual ~CCM_Hello_impl (  );


  virtual CCM_Console* get_console (  );



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

//==============================================================================
// console - facet adapter implementation class
//==============================================================================

class console_impl
  : public CCM_Console
{
 private:
  CCM_Hello_impl* component;

 public:
  console_impl(CCM_Hello_impl* component_impl);
  virtual ~console_impl (  );

  virtual long println ( const std::string& s2 ) ;
#ifdef CCM_TEST_PYTHON
  long call_python_println ( const std::string& s2 ) ;
#endif

};



} // /namespace CCM_Session_Hello
} // /namespace CCM_Local


#endif


