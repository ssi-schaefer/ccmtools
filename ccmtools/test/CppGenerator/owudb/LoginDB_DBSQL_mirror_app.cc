
//==============================================================================
// LoginDB_DBSQL_mirror - business logic implementation
//==============================================================================

#include <iostream>
#include <CCM_Utils/Debug.h>

#include "LoginDB_DBSQL_mirror_app.h"

using namespace CCM_Utils;
using namespace std;

namespace CCM_Local {
namespace owil {
namespace udb {
namespace CCM_Session_LoginDB_DBSQL_mirror {


//==============================================================================
// business logic functionality
//==============================================================================

CCM_UserPassAuthentication*
CCM_LoginDB_DBSQL_mirror_impl::get_auth_mirror (  )
{
  DEBUGNL ( " CCM_LoginDB_DBSQL_mirror_impl->get_auth_mirror (  )" );
  auth_mirror_impl* facet = new auth_mirror_impl (  );
  facet->set_component ( this );
  return dynamic_cast<CCM_UserPassAuthentication*> ( facet );
}

CCM_UserDBBrowser*
CCM_LoginDB_DBSQL_mirror_impl::get_userbrowser_mirror (  )
{
  DEBUGNL ( " CCM_LoginDB_DBSQL_mirror_impl->get_userbrowser_mirror (  )" );
  userbrowser_mirror_impl* facet = new userbrowser_mirror_impl (  );
  facet->set_component ( this );
  return dynamic_cast<CCM_UserDBBrowser*> ( facet );
}



//==============================================================================
// auth_mirror - facet implementation
//==============================================================================

auth_mirror_impl::auth_mirror_impl
 (  )
{
  DEBUGNL ( "+auth_mirror_impl->auth_mirror_impl (  )" );
}

auth_mirror_impl::~auth_mirror_impl (  )
{
  DEBUGNL ( "-auth_mirror_impl->~auth_mirror_impl (  )" );
}

void
auth_mirror_impl::set_component ( CCM_LoginDB_DBSQL_mirror_impl* c )
{
  DEBUGNL ( " auth_mirror_impl->set_component (  )" );
  component = c;
}

void
auth_mirror_impl::auth_userpass ( const std::string& name, const std::string& password )
  throw ( InvalidPassword, DatabaseError, UserBlocked, TerminalBlocked, PasswordExpired, NoSuchUser )
{
  DEBUGNL ( " auth_mirror_impl->auth_userpass ( name, password )" );

  // TODO : IMPLEMENT ME HERE !
  return;
}



//==============================================================================
// userbrowser_mirror - facet implementation
//==============================================================================

userbrowser_mirror_impl::userbrowser_mirror_impl
 (  )
{
  DEBUGNL ( "+userbrowser_mirror_impl->userbrowser_mirror_impl (  )" );
}

userbrowser_mirror_impl::~userbrowser_mirror_impl (  )
{
  DEBUGNL ( "-userbrowser_mirror_impl->~userbrowser_mirror_impl (  )" );
}

void
userbrowser_mirror_impl::set_component ( CCM_LoginDB_DBSQL_mirror_impl* c )
{
  DEBUGNL ( " userbrowser_mirror_impl->set_component (  )" );
  component = c;
}

owil::udb::PersData
userbrowser_mirror_impl::get_user ( const std::string& name )
  throw ( DatabaseError, NoSuchUser )
{
  DEBUGNL ( " userbrowser_mirror_impl->get_user ( name )" );

  // TODO : IMPLEMENT ME HERE !

  owil::udb::PersData result;
  return result;
}





//==============================================================================
// class implementation
//==============================================================================

CCM_LoginDB_DBSQL_mirror_impl::CCM_LoginDB_DBSQL_mirror_impl (  )
{
  DEBUGNL ( "+CCM_LoginDB_DBSQL_mirror_impl->CCM_LoginDB_DBSQL_mirror_impl (  )" );
}

CCM_LoginDB_DBSQL_mirror_impl::~CCM_LoginDB_DBSQL_mirror_impl (  )
{
  DEBUGNL ( "-CCM_LoginDB_DBSQL_mirror_impl->~CCM_LoginDB_DBSQL_mirror_impl (  )" );
}

std::string
CCM_LoginDB_DBSQL_mirror_impl::systemname (  )
{
  DEBUGNL ( " CCM_LoginDB_DBSQL_mirror_impl->systemname (  )" );
  return systemname_;
}

void
CCM_LoginDB_DBSQL_mirror_impl::systemname ( const std::string value )
{
  DEBUGNL ( " CCM_LoginDB_DBSQL_mirror_impl->systemname (  )" );
  systemname_ = value;
}



void
CCM_LoginDB_DBSQL_mirror_impl::set_session_context ( localComponents::SessionContext* context )
  throw ( localComponents::CCMException )
{
  DEBUGNL ( " CCM_LoginDB_DBSQL_mirror_impl->set_session_context (  )" );
  ctx = (CCM_LoginDB_DBSQL_mirror_Context*) context;
}

void
CCM_LoginDB_DBSQL_mirror_impl::ccm_activate (  )
  throw ( localComponents::CCMException )
{
  DEBUGNL ( " CCM_LoginDB_DBSQL_mirror_impl->ccm_activate (  )" );

  cout << "----- Begin of test case -----------------------------" << endl;

  // check mgr facet
  string user = "Egon";
  string passwd = "***";
  string login = "user";
  CCM_Utils::SmartPtr<CCM_LoginManager> mgr = ctx->get_connection_mgr_mirror();
  mgr.ptr()->login(user, passwd,login);

  // check browser facet
  CCM_Utils::SmartPtr<CCM_LoginDBBrowser> browser = ctx->get_connection_browser_mirror();
  browser.ptr()->get_logins_of_user(user);
  browser.ptr()->get_all_logins();

  cout << "----- End of test case -------------------------------" << endl;
}

void
CCM_LoginDB_DBSQL_mirror_impl::ccm_passivate (  )
  throw ( localComponents::CCMException )
{
  DEBUGNL ( " CCM_LoginDB_DBSQL_mirror_impl->ccm_passivate (  )" );
}

void
CCM_LoginDB_DBSQL_mirror_impl::ccm_remove (  )
  throw ( localComponents::CCMException )
{
  DEBUGNL ( " CCM_LoginDB_DBSQL_mirror_impl->ccm_remove (  )" );
}

} // /namespace CCM_Session_LoginDB_DBSQL_mirror
} // /namespace udb
} // /namespace owil
} // /namespace CCM_Local



