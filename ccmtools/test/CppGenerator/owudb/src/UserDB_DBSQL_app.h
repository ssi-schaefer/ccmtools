
//==============================================================================
// UserDB_DBSQL - business logic class definition
//==============================================================================

#ifndef __UserDB_DBSQL__APP__H__
#define __UserDB_DBSQL__APP__H__

#include "UserDB_DBSQL_gen.h"

namespace CCM_Local {
namespace owil {
namespace udb {
namespace CCM_Session_UserDB_DBSQL {


//==============================================================================
// component implementation object
//==============================================================================

class CCM_UserDB_DBSQL_impl
  : public CCM_UserDB_DBSQL
{
 private:
  bool update_stat_;

 public:
  CCM_UserDB_DBSQL_Context* ctx;

  CCM_UserDB_DBSQL_impl (  );
  virtual ~CCM_UserDB_DBSQL_impl (  );

  // Equivalent operations for update_stat attribute
  bool update_stat (  );
  void update_stat ( bool value );

  CCM_UserPassAuthentication* get_auth (  );
  CCM_UserDBBrowser* get_browser (  );



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
// auth - facet adapter implementation class
//==============================================================================

class auth_impl
  : public CCM_UserPassAuthentication
{
 private:
  CCM_UserDB_DBSQL_impl* component;

 public:
  auth_impl (  );
  virtual ~auth_impl (  );

  void
  set_component
    ( CCM_UserDB_DBSQL_impl* c );

  void auth_userpass ( const std::string& name, const std::string& password ) throw ( InvalidPassword, PasswordExpired, UserBlocked, TerminalBlocked, DatabaseError, NoSuchUser );
#ifdef CCM_TEST_PYTHON
  void call_python_auth_userpass ( const std::string& name, const std::string& password ) throw ( InvalidPassword, PasswordExpired, UserBlocked, TerminalBlocked, DatabaseError, NoSuchUser );
#endif

};

//==============================================================================
// browser - facet adapter implementation class
//==============================================================================

class browser_impl
  : public CCM_UserDBBrowser
{
 private:
  CCM_UserDB_DBSQL_impl* component;

 public:
  browser_impl (  );
  virtual ~browser_impl (  );

  void
  set_component
    ( CCM_UserDB_DBSQL_impl* c );

  owil::udb::PersData get_user ( const std::string& name ) throw ( DatabaseError, NoSuchUser );
#ifdef CCM_TEST_PYTHON
  owil::udb::PersData call_python_get_user ( const std::string& name ) throw ( DatabaseError, NoSuchUser );
#endif

};



} // /namespace CCM_Session_UserDB_DBSQL
} // /namespace udb
} // /namespace owil
} // /namespace CCM_Local


#endif


