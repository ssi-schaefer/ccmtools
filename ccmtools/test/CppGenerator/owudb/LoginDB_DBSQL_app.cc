
//==============================================================================
// LoginDB_DBSQL - business logic implementation
//==============================================================================

#include <iostream>
#include <CCM_Utils/Debug.h>

#include "LoginDB_DBSQL_app.h"

using namespace CCM_Utils;

namespace CCM_Local {
namespace owil {
namespace udb {
namespace CCM_Session_LoginDB_DBSQL {

//==============================================================================
// business logic functionality
//==============================================================================

CCM_LoginManager*
CCM_LoginDB_DBSQL_impl::get_mgr (  )
{
  DEBUGNL ( " CCM_LoginDB_DBSQL_impl->get_mgr (  )" );
  mgr_impl* facet = new mgr_impl (  );
  facet->set_component ( this );
  return dynamic_cast<CCM_LoginManager*> ( facet );
}

CCM_LoginDBBrowser*
CCM_LoginDB_DBSQL_impl::get_browser (  )
{
  DEBUGNL ( " CCM_LoginDB_DBSQL_impl->get_browser (  )" );
  browser_impl* facet = new browser_impl (  );
  facet->set_component ( this );
  return dynamic_cast<CCM_LoginDBBrowser*> ( facet );
}



//==============================================================================
// mgr - facet implementation
//==============================================================================

mgr_impl::mgr_impl
 (  )
{
  DEBUGNL ( "+mgr_impl->mgr_impl (  )" );
}

mgr_impl::~mgr_impl (  )
{
  DEBUGNL ( "-mgr_impl->~mgr_impl (  )" );
}

void
mgr_impl::set_component ( CCM_LoginDB_DBSQL_impl* c )
{
  DEBUGNL ( " mgr_impl->set_component (  )" );
  component = c;
}

owil::udb::LoginResult
mgr_impl::login ( const std::string& user, const std::string& password, const std::string& login )
  throw ( NoSuchLogin, DatabaseError, NoDefaultLogin, TerminalBlocked, InvalidPassword, UserBlocked, PasswordExpired, NoSuchUser )
{
  DEBUGNL ( " mgr_impl->login ( user, password, login )" );

  return owil::udb::LoginResult (  );
}



//==============================================================================
// browser - facet implementation
//==============================================================================

browser_impl::browser_impl
 (  )
{
  DEBUGNL ( "+browser_impl->browser_impl (  )" );
}

browser_impl::~browser_impl (  )
{
  DEBUGNL ( "-browser_impl->~browser_impl (  )" );
}

void
browser_impl::set_component ( CCM_LoginDB_DBSQL_impl* c )
{
  DEBUGNL ( " browser_impl->set_component (  )" );
  component = c;
}

owil::udb::LoginDataList
browser_impl::get_logins_of_user ( const std::string& user )
  throw ( DatabaseError, NoSuchUser )
{
  DEBUGNL ( " browser_impl->get_logins_of_user ( user )" );

  return owil::udb::LoginDataList (  );
}

owil::udb::LoginDataList
browser_impl::get_all_logins (  )
  throw ( DatabaseError )
{
  DEBUGNL ( " browser_impl->get_all_logins (  )" );

  return owil::udb::LoginDataList (  );
}





//==============================================================================
// class implementation
//==============================================================================

CCM_LoginDB_DBSQL_impl::CCM_LoginDB_DBSQL_impl (  )
{
  DEBUGNL ( "+CCM_LoginDB_DBSQL_impl->CCM_LoginDB_DBSQL_impl (  )" );
}

CCM_LoginDB_DBSQL_impl::~CCM_LoginDB_DBSQL_impl (  )
{
  DEBUGNL ( "-CCM_LoginDB_DBSQL_impl->~CCM_LoginDB_DBSQL_impl (  )" );
}

std::string
CCM_LoginDB_DBSQL_impl::systemname (  )
{
  DEBUGNL ( " CCM_LoginDB_DBSQL_impl->systemname (  )" );
  return systemname_;
}

void
CCM_LoginDB_DBSQL_impl::systemname ( const std::string value )
{
  DEBUGNL ( " CCM_LoginDB_DBSQL_impl->systemname (  )" );
  systemname_ = value;
}



void
CCM_LoginDB_DBSQL_impl::set_session_context ( localComponents::SessionContext* context )
  throw ( localComponents::CCMException )
{
  DEBUGNL ( " CCM_LoginDB_DBSQL_impl->set_session_context (  )" );
  ctx = (CCM_LoginDB_DBSQL_Context*) context;
}

void
CCM_LoginDB_DBSQL_impl::ccm_activate (  )
  throw ( localComponents::CCMException )
{
  DEBUGNL ( " CCM_LoginDB_DBSQL_impl->ccm_activate (  )" );
}

void
CCM_LoginDB_DBSQL_impl::ccm_passivate (  )
  throw ( localComponents::CCMException )
{
  DEBUGNL ( " CCM_LoginDB_DBSQL_impl->ccm_passivate (  )" );
}

void
CCM_LoginDB_DBSQL_impl::ccm_remove (  )
  throw ( localComponents::CCMException )
{
  DEBUGNL ( " CCM_LoginDB_DBSQL_impl->ccm_remove (  )" );
}

} // /namespace CCM_Session_LoginDB_DBSQL
} // /namespace udb
} // /namespace owil
} // /namespace CCM_Local



