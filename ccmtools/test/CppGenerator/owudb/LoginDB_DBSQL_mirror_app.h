
//==============================================================================
// LoginDB_DBSQL_mirror - business logic class definition
//==============================================================================

#ifndef __LoginDB_DBSQL_mirror__APP__H__
#define __LoginDB_DBSQL_mirror__APP__H__

#include "LoginDB_DBSQL_mirror_gen.h"

namespace CCM_Local {
namespace owil {
namespace udb {
namespace CCM_Session_LoginDB_DBSQL_mirror {


//==============================================================================
// component implementation object
//==============================================================================

class CCM_LoginDB_DBSQL_mirror_impl
  : public CCM_LoginDB_DBSQL_mirror
{
 private:
  std::string systemname_;

 public:
  CCM_LoginDB_DBSQL_mirror_Context* ctx;

  CCM_LoginDB_DBSQL_mirror_impl (  );
  virtual ~CCM_LoginDB_DBSQL_mirror_impl (  );

  // Equivalent operations for systemname attribute
  std::string systemname (  );
  void systemname ( std::string value );

  CCM_UserPassAuthentication* get_auth_mirror (  );
  CCM_UserDBBrowser* get_userbrowser_mirror (  );



  // Callback methods

  void set_session_context ( localComponents::SessionContext* ctx )
    throw ( localComponents::CCMException );
  void ccm_activate (  )
    throw ( localComponents::CCMException );
  void ccm_passivate (  )
    throw ( localComponents::CCMException );
  void ccm_remove (  )
    throw ( localComponents::CCMException );
};

//==============================================================================
// auth_mirror - facet adapter implementation class
//==============================================================================

class auth_mirror_impl
  : public CCM_UserPassAuthentication
{
 private:
  CCM_LoginDB_DBSQL_mirror_impl* component;

 public:
  auth_mirror_impl (  );
  virtual ~auth_mirror_impl (  );

  void
  set_component
    ( CCM_LoginDB_DBSQL_mirror_impl* c );

  void auth_userpass ( const std::string& name, const std::string& password ) throw ( InvalidPassword, DatabaseError, UserBlocked, TerminalBlocked, PasswordExpired, NoSuchUser );
#ifdef CCM_TEST_PYTHON
  void call_python_auth_userpass ( const std::string& name, const std::string& password ) throw ( InvalidPassword, DatabaseError, UserBlocked, TerminalBlocked, PasswordExpired, NoSuchUser );
#endif

};

//==============================================================================
// userbrowser_mirror - facet adapter implementation class
//==============================================================================

class userbrowser_mirror_impl
  : public CCM_UserDBBrowser
{
 private:
  CCM_LoginDB_DBSQL_mirror_impl* component;

 public:
  userbrowser_mirror_impl (  );
  virtual ~userbrowser_mirror_impl (  );

  void
  set_component
    ( CCM_LoginDB_DBSQL_mirror_impl* c );

  owil::udb::PersData get_user ( const std::string& name ) throw ( DatabaseError, NoSuchUser );
#ifdef CCM_TEST_PYTHON
  owil::udb::PersData call_python_get_user ( const std::string& name ) throw ( DatabaseError, NoSuchUser );
#endif

};



} // /namespace CCM_Session_LoginDB_DBSQL_mirror
} // /namespace udb
} // /namespace owil
} // /namespace CCM_Local


#endif


