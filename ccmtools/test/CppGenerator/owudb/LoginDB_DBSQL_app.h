
//==============================================================================
// LoginDB_DBSQL - business logic class definition
//==============================================================================

#ifndef __LoginDB_DBSQL__APP__H__
#define __LoginDB_DBSQL__APP__H__

#include "LoginDB_DBSQL_gen.h"

namespace CCM_Local {
namespace owil {
namespace udb {
namespace CCM_Session_LoginDB_DBSQL {


//==============================================================================
// component implementation object
//==============================================================================

class CCM_LoginDB_DBSQL_impl
  : public CCM_LoginDB_DBSQL
{
 private:
  std::string systemname_;

 public:
  CCM_LoginDB_DBSQL_Context* ctx;

  CCM_LoginDB_DBSQL_impl (  );
  virtual ~CCM_LoginDB_DBSQL_impl (  );

  // Equivalent operations for systemname attribute
  std::string systemname (  );
  void systemname ( std::string value );

  CCM_LoginManager* get_mgr (  );
  CCM_LoginDBBrowser* get_browser (  );



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
// mgr - facet adapter implementation class
//==============================================================================

class mgr_impl
  : public CCM_LoginManager
{
 private:
  CCM_LoginDB_DBSQL_impl* component;

 public:
  mgr_impl (  );
  virtual ~mgr_impl (  );

  void
  set_component
    ( CCM_LoginDB_DBSQL_impl* c );

  owil::udb::LoginResult login ( const std::string& user, const std::string& password, const std::string& login ) throw ( DatabaseError, NoSuchUser, NoDefaultLogin, InvalidPassword, PasswordExpired, NoSuchLogin, TerminalBlocked, UserBlocked );
#ifdef CCM_TEST_PYTHON
  owil::udb::LoginResult call_python_login ( const std::string& user, const std::string& password, const std::string& login ) throw ( DatabaseError, NoSuchUser, NoDefaultLogin, InvalidPassword, PasswordExpired, NoSuchLogin, TerminalBlocked, UserBlocked );
#endif

};

//==============================================================================
// browser - facet adapter implementation class
//==============================================================================

class browser_impl
  : public CCM_LoginDBBrowser
{
 private:
  CCM_LoginDB_DBSQL_impl* component;

 public:
  browser_impl (  );
  virtual ~browser_impl (  );

  void
  set_component
    ( CCM_LoginDB_DBSQL_impl* c );

  owil::udb::LoginDataList get_logins_of_user ( const std::string& user ) throw ( DatabaseError, NoSuchUser );
#ifdef CCM_TEST_PYTHON
  owil::udb::LoginDataList call_python_get_logins_of_user ( const std::string& user ) throw ( DatabaseError, NoSuchUser );
#endif
  owil::udb::LoginDataList get_all_logins (  ) throw ( DatabaseError );
#ifdef CCM_TEST_PYTHON
  owil::udb::LoginDataList call_python_get_all_logins (  ) throw ( DatabaseError );
#endif

};



} // /namespace CCM_Session_LoginDB_DBSQL
} // /namespace udb
} // /namespace owil
} // /namespace CCM_Local


#endif


