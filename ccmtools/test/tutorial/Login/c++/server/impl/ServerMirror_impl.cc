
/***
 * This file was automatically generated by CCM Tools 
 * <http://ccmtools.sourceforge.net/>
 *
 * ServerMirror component business logic implementation.
 * 
 * // TODO: WRITE YOUR DESCRIPTION HERE! 
 *
 * @author
 * @version 
 ***/

#include <cassert>
#include <iostream>

#include "ServerMirror_impl.h"

namespace application {

using namespace std;

//==============================================================================
// CCM_ServerMirror - component implementation
//==============================================================================

ServerMirror_impl::ServerMirror_impl()
{
    // OPTIONAL : IMPLEMENT ME HERE !
}

ServerMirror_impl::~ServerMirror_impl()
{
    // OPTIONAL : IMPLEMENT ME HERE !
}

void
ServerMirror_impl::set_session_context(Components::SessionContext* context)
    throw(Components::CCMException)
{
    ctx = dynamic_cast<CCM_ServerMirror_Context*>(context);
}

void
ServerMirror_impl::ccm_activate()
    throw(Components::CCMException)
{
      try
      {
          Login::SmartPtr login =  ctx->get_connection_login();

	  try 
	  {
	    PersonData person;
	    person.id = 277;
	    person.name = "eteinik";
	    person.password = "eteinik";
	    person.group = USER;

	    bool result = login->isValidUser(person);
	    if(result) 
	    {
	      cout << "Welcome " << person.name << endl;
	    }
	    else 
	    {
	      cout << "We don't know you !!!" << endl;
	    }
	  }
	  catch(InvalidPersonData& e) 
	  {
	    cout << "Error: InvalidPersonData!!" << endl;
	  }

	  try 
	  {
	       PersonData person;
	       person.id = 0;
	       person.name = "";
	       person.password = "";
	       person.group = USER;
	       
	       login->isValidUser(person);
	       assert(false);
	  }
	  catch(InvalidPersonData& e) 
	  {
	    cout << "OK, caught InvalidPersonData exception!" << endl;
	  }
      }
      catch(Components::Exception& e)
      {
	cerr << "ERROR: " << e.what() << endl;
      }
}

void
ServerMirror_impl::ccm_passivate()
    throw(Components::CCMException)
{
    // OPTIONAL : IMPLEMENT ME HERE !
}

void
ServerMirror_impl::ccm_remove()
    throw(Components::CCMException)
{
    // OPTIONAL : IMPLEMENT ME HERE !
}

} // /namespace application

