
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

  virtual short println1 ( const short p1, short& p2, short& p3 ) ;
#ifdef CCM_TEST_PYTHON
  short call_python_println1 ( const short p1, short& p2, short& p3 ) ;
#endif
  virtual long println2 ( const long p1, long& p2, long& p3 ) ;
#ifdef CCM_TEST_PYTHON
  long call_python_println2 ( const long p1, long& p2, long& p3 ) ;
#endif
  virtual unsigned short println3 ( const unsigned short p1, unsigned short& p2, unsigned short& p3 ) ;
#ifdef CCM_TEST_PYTHON
  unsigned short call_python_println3 ( const unsigned short p1, unsigned short& p2, unsigned short& p3 ) ;
#endif
  virtual unsigned long println4 ( const unsigned long p1, unsigned long& p2, unsigned long& p3 ) ;
#ifdef CCM_TEST_PYTHON
  unsigned long call_python_println4 ( const unsigned long p1, unsigned long& p2, unsigned long& p3 ) ;
#endif
  virtual float println5 ( const float p1, float& p2, float& p3 ) ;
#ifdef CCM_TEST_PYTHON
  float call_python_println5 ( const float p1, float& p2, float& p3 ) ;
#endif
  virtual double println6 ( const double p1, double& p2, double& p3 ) ;
#ifdef CCM_TEST_PYTHON
  double call_python_println6 ( const double p1, double& p2, double& p3 ) ;
#endif
  virtual char println7 ( const char p1, char& p2, char& p3 ) ;
#ifdef CCM_TEST_PYTHON
  char call_python_println7 ( const char p1, char& p2, char& p3 ) ;
#endif
  virtual std::string println8 ( const std::string& p1, std::string& p2, std::string& p3 ) ;
#ifdef CCM_TEST_PYTHON
  std::string call_python_println8 ( const std::string& p1, std::string& p2, std::string& p3 ) ;
#endif
  virtual bool println9 ( const bool p1, bool& p2, bool& p3 ) ;
#ifdef CCM_TEST_PYTHON
  bool call_python_println9 ( const bool p1, bool& p2, bool& p3 ) ;
#endif
  virtual unsigned char println10 ( const unsigned char p1, unsigned char& p2, unsigned char& p3 ) ;
#ifdef CCM_TEST_PYTHON
  unsigned char call_python_println10 ( const unsigned char p1, unsigned char& p2, unsigned char& p3 ) ;
#endif

};



} // /namespace CCM_Session_Hello
} // /namespace CCM_Local


#endif


