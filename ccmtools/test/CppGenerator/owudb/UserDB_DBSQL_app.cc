
//==============================================================================
// UserDB_DBSQL - business logic implementation
//==============================================================================

#include <iostream>
#include <CCM_Utils/Debug.h>

#include "UserDB_DBSQL_app.h"

using namespace CCM_Utils;

namespace CCM_Local {
namespace owil {
namespace udb {
namespace CCM_Session_UserDB_DBSQL {

//==============================================================================
// business logic functionality
//==============================================================================

CCM_UserPassAuthentication*
CCM_UserDB_DBSQL_impl::get_auth (  )
{
  DEBUGNL ( " CCM_UserDB_DBSQL_impl->get_auth (  )" );
  auth_impl* facet = new auth_impl (  );
  facet->set_component ( this );
  return dynamic_cast<CCM_UserPassAuthentication*> ( facet );
}

CCM_UserDBBrowser*
CCM_UserDB_DBSQL_impl::get_browser (  )
{
  DEBUGNL ( " CCM_UserDB_DBSQL_impl->get_browser (  )" );
  browser_impl* facet = new browser_impl (  );
  facet->set_component ( this );
  return dynamic_cast<CCM_UserDBBrowser*> ( facet );
}



//==============================================================================
// auth - facet implementation
//==============================================================================

auth_impl::auth_impl
 (  )
{
  DEBUGNL ( "+auth_impl->auth_impl (  )" );
}

auth_impl::~auth_impl (  )
{
  DEBUGNL ( "-auth_impl->~auth_impl (  )" );
}

void
auth_impl::set_component ( CCM_UserDB_DBSQL_impl* c )
{
  DEBUGNL ( " auth_impl->set_component (  )" );
  component = c;
}

void
auth_impl::auth_userpass ( const std::string& name, const std::string& password )
  throw ( DatabaseError, TerminalBlocked, InvalidPassword, UserBlocked, PasswordExpired, NoSuchUser )
{
  DEBUGNL ( " auth_impl->auth_userpass ( name, password )" );

  // TODO : IMPLEMENT ME HERE !
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
browser_impl::set_component ( CCM_UserDB_DBSQL_impl* c )
{
  DEBUGNL ( " browser_impl->set_component (  )" );
  component = c;
}

owil::udb::PersData
browser_impl::get_user ( const std::string& name )
  throw ( DatabaseError, NoSuchUser )
{
  DEBUGNL ( " browser_impl->get_user ( name )" );

  return owil::udb::PersData (  );
}





//==============================================================================
// class implementation
//==============================================================================

CCM_UserDB_DBSQL_impl::CCM_UserDB_DBSQL_impl (  )
{
  DEBUGNL ( "+CCM_UserDB_DBSQL_impl->CCM_UserDB_DBSQL_impl (  )" );
}

CCM_UserDB_DBSQL_impl::~CCM_UserDB_DBSQL_impl (  )
{
  DEBUGNL ( "-CCM_UserDB_DBSQL_impl->~CCM_UserDB_DBSQL_impl (  )" );
}

bool
CCM_UserDB_DBSQL_impl::update_stat (  )
{
  DEBUGNL ( " CCM_UserDB_DBSQL_impl->update_stat (  )" );
  return update_stat_;
}

void
CCM_UserDB_DBSQL_impl::update_stat ( const bool value )
{
  DEBUGNL ( " CCM_UserDB_DBSQL_impl->update_stat (  )" );
  update_stat_ = value;
}



void
CCM_UserDB_DBSQL_impl::set_session_context ( localComponents::SessionContext* context )
  throw ( localComponents::CCMException )
{
  DEBUGNL ( " CCM_UserDB_DBSQL_impl->set_session_context (  )" );
  ctx = (CCM_UserDB_DBSQL_Context*) context;
}

void
CCM_UserDB_DBSQL_impl::ccm_activate (  )
  throw ( localComponents::CCMException )
{
  DEBUGNL ( " CCM_UserDB_DBSQL_impl->ccm_activate (  )" );
}

void
CCM_UserDB_DBSQL_impl::ccm_passivate (  )
  throw ( localComponents::CCMException )
{
  DEBUGNL ( " CCM_UserDB_DBSQL_impl->ccm_passivate (  )" );
}

void
CCM_UserDB_DBSQL_impl::ccm_remove (  )
  throw ( localComponents::CCMException )
{
  DEBUGNL ( " CCM_UserDB_DBSQL_impl->ccm_remove (  )" );
}

} // /namespace CCM_Session_UserDB_DBSQL
} // /namespace udb
} // /namespace owil
} // /namespace CCM_Local



