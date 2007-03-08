
/*
 * PROTOTYPE 1
 */

//==============================================================================
// Alien - component logic (implementation)
//==============================================================================

#include <cassert>
#include <iostream>
#include <sstream>

#ifdef HAVE_CONFIG_H
#include <config.h>
#endif

#include <ccmtools/local/World/DataAdapter.h>


#include "Alien_gen.h"

namespace Space {

using namespace std;

//==============================================================================
// local component adapter implementation
//==============================================================================

Alien::Alien(AlienHome* h,
    ::Space::CCM_Alien* lc, ::Components::Assembly::SmartPtr a)
    :
    home_local_adapter(h), local_component(lc), assembly(a)
{
    context = NULL;
    ValidConnection = local_component!=NULL;
    delegator = dynamic_cast< ::Components::ComponentDelegator*>(local_component);

    Ap1_facet_impl = NULL;
    Ap10_facet_impl = NULL;

    Ar4_receptacle_counter = 0;
    Ar7a_receptacle_counter = 0;
    Ar7b_receptacle_counter = 0;

}

Alien::~Alien()
{
    delete context;
    delete local_component;
    delete Ap1_facet_impl;
    delete Ap10_facet_impl;

}

::World::Data::SmartPtr
Alien::provide_Ap1()
{
    if(!ValidConnection)
    {
        throw ::Components::InvalidConnection();
    }
    if(!Ap1_facet)
    {
        if(delegator)
        {
            ::Components::Object::SmartPtr o = delegator->provide("Ap1");
            if(o)
            {
                Ap1_facet.eat(dynamic_cast< ::World::Data*>(o.ptr()));
                return Ap1_facet;
            }
        }
        Ap1_facet_impl = local_component->get_Ap1();
        Ap1_facet = ::World::Data::SmartPtr(
            new ::ccmtools::local::World::DataAdapter(Ap1_facet_impl));
    }
    return Ap1_facet;
}

::World::Data::SmartPtr
Alien::provide_Ap10()
{
    if(!ValidConnection)
    {
        throw ::Components::InvalidConnection();
    }
    if(!Ap10_facet)
    {
        if(delegator)
        {
            ::Components::Object::SmartPtr o = delegator->provide("Ap10");
            if(o)
            {
                Ap10_facet.eat(dynamic_cast< ::World::Data*>(o.ptr()));
                return Ap10_facet;
            }
        }
        Ap10_facet_impl = local_component->get_Ap10();
        Ap10_facet = ::World::Data::SmartPtr(
            new ::ccmtools::local::World::DataAdapter(Ap10_facet_impl));
    }
    return Ap10_facet;
}

template<typename T>
static void
generic_single_connect(T& receptacle, const std::string& name, const T& f,
    ::Components::ComponentDelegator* delegator)
{
    if(delegator)
    {
        delegator->connect(name, f);
    }
    else
    {
        if(receptacle)
        {
            throw ::Components::AlreadyConnected();
        }
    }
    receptacle = f;
}

void
Alien::connect_Ar3(::World::Data::SmartPtr f)
    throw(::Components::AlreadyConnected, ::Components::InvalidConnection)
{
    if(!ValidConnection)
    {
        throw ::Components::InvalidConnection();
    }
    generic_single_connect(Ar3_receptacle, "Ar3", f, delegator);
}

template<typename T>
static T
generic_single_disconnect(T& receptacle, const std::string& name,
    ::Components::ComponentDelegator* delegator)
{
    if(delegator)
    {
        delegator->disconnect(name);
    }
    else
    {
        if(!receptacle)
        {
            throw ::Components::NoConnection();
        }
    }
    T f = receptacle;
    receptacle.forget();
    return f;
}

::World::Data::SmartPtr
Alien::disconnect_Ar3()
    throw(::Components::NoConnection, ::Components::InvalidConnection)
{
    if(!ValidConnection)
    {
        throw ::Components::InvalidConnection();
    }
    return generic_single_disconnect(Ar3_receptacle, "Ar3", delegator);
}

::World::Data::SmartPtr
Alien::get_connection_Ar3()
	throw(::Components::NoConnection, ::Components::InvalidConnection)
{
  	if(!ValidConnection)
  	{
  		throw ::Components::InvalidConnection();
  	}
    if(!Ar3_receptacle)
        throw ::Components::NoConnection();
    return Ar3_receptacle;
}

template<typename MAP, typename T>
static ::Components::Cookie
generic_multiple_connect(MAP& receptacles, long& receptacle_counter,
    const std::string& name, const T& f,
    ::Components::ComponentDelegator* delegator)
{
    if(delegator)
    {
        ::Components::Cookie ck = delegator->connect(name, f);
        receptacles.insert(make_pair(ck, f));
        return ck;
    }
    else
    {
        stringstream s;
        s << "CCM_" << name << ":" << receptacle_counter++;
        ::Components::Cookie ck(s.str());
        receptacles.insert(make_pair(ck, f));
        return ck;
    }
}

::Components::Cookie
Alien::connect_Ar4(::World::Data::SmartPtr f)
    throw(::Components::ExceededConnectionLimit, ::Components::InvalidConnection)
{
    if(!ValidConnection)
    {
        throw ::Components::InvalidConnection();
    }
    return generic_multiple_connect(Ar4_receptacles, Ar4_receptacle_counter, "Ar4", f, delegator);
}

template<typename MAP, typename T>
static T
generic_multiple_disconnect(MAP& receptacles, const std::string& name,
    const ::Components::Cookie& ck,
    ::Components::ComponentDelegator* delegator)
{
    if(delegator)
    {
        delegator->disconnect(name, ck);
        T f = receptacles[ck];
        receptacles.erase(ck);
        return f;
    }
    else
    {
        if(receptacles.find(ck) != receptacles.end())
        {
            T f = receptacles[ck];
            receptacles.erase(ck);
            return f;
        }
        else
        {
            throw ::Components::InvalidConnection();
        }
    }
}

::World::Data::SmartPtr
Alien::disconnect_Ar4(::Components::Cookie ck)
    throw(::Components::InvalidConnection)
{
    if(!ValidConnection)
    {
        throw ::Components::InvalidConnection();
    }
    return generic_multiple_disconnect< ::Space::Alien_Ar4_Connections, ::World::Data::SmartPtr>(Ar4_receptacles, "Ar4", ck, delegator);
}

::Space::Alien_Ar4_Connections&
Alien::get_connections_Ar4 (  )
{
    if(!ValidConnection)
    {
        throw ::Components::InvalidConnection();
    }
    return Ar4_receptacles;
}

void
Alien::connect_Ar10(::World::Data::SmartPtr f)
  	throw(::Components::AlreadyConnected, ::Components::InvalidConnection)
{
  	if(!ValidConnection)
  	{
  		throw ::Components::InvalidConnection();
    }
    generic_single_connect(Ar10_receptacle, "Ar10", f, delegator);
}

::World::Data::SmartPtr
Alien::disconnect_Ar10()
  	throw(::Components::NoConnection, ::Components::InvalidConnection)
{
  	if(!ValidConnection)
  	{
  	    throw ::Components::InvalidConnection();
  	}
    return generic_single_disconnect(Ar10_receptacle, "Ar10", delegator);
}

::World::Data::SmartPtr
Alien::get_connection_Ar10()
	throw(::Components::NoConnection, ::Components::InvalidConnection)
{
  	if(!ValidConnection)
  	{
  		throw ::Components::InvalidConnection();
  	}
  	if(!Ar10_receptacle)
    	throw ::Components::NoConnection();
  	return Ar10_receptacle;
}

void
Alien::connect_Ar6a(::World::Data::SmartPtr f)
  	throw(::Components::AlreadyConnected, ::Components::InvalidConnection)
{
  	if(!ValidConnection)
  	{
  		throw ::Components::InvalidConnection();
    }
    generic_single_connect(Ar6a_receptacle, "Ar6a", f, delegator);
}

::World::Data::SmartPtr
Alien::disconnect_Ar6a()
  	throw(::Components::NoConnection, ::Components::InvalidConnection)
{
  	if(!ValidConnection)
  	{
  	    throw ::Components::InvalidConnection();
  	}
    return generic_single_disconnect(Ar6a_receptacle, "Ar6a", delegator);
}

::World::Data::SmartPtr
Alien::get_connection_Ar6a()
	throw(::Components::NoConnection, ::Components::InvalidConnection)
{
  	if(!ValidConnection)
  	{
  		throw ::Components::InvalidConnection();
  	}
  	if(!Ar6a_receptacle)
    	throw ::Components::NoConnection();
  	return Ar6a_receptacle;
}

void
Alien::connect_Ar6b(::World::Data::SmartPtr f)
  	throw(::Components::AlreadyConnected, ::Components::InvalidConnection)
{
  	if(!ValidConnection)
  	{
  		throw ::Components::InvalidConnection();
    }
    generic_single_connect(Ar6b_receptacle, "Ar6b", f, delegator);
}

::World::Data::SmartPtr
Alien::disconnect_Ar6b()
  	throw(::Components::NoConnection, ::Components::InvalidConnection)
{
  	if(!ValidConnection)
  	{
  	    throw ::Components::InvalidConnection();
  	}
    return generic_single_disconnect(Ar6b_receptacle, "Ar6b", delegator);
}

::World::Data::SmartPtr
Alien::get_connection_Ar6b()
	throw(::Components::NoConnection, ::Components::InvalidConnection)
{
  	if(!ValidConnection)
  	{
  		throw ::Components::InvalidConnection();
  	}
  	if(!Ar6b_receptacle)
    	throw ::Components::NoConnection();
  	return Ar6b_receptacle;
}

::Components::Cookie
Alien::connect_Ar7a(::World::Data::SmartPtr f)
    throw(::Components::ExceededConnectionLimit, ::Components::InvalidConnection)
{
    if(!ValidConnection)
    {
        throw ::Components::InvalidConnection();
    }
    return generic_multiple_connect(Ar7a_receptacles, Ar7a_receptacle_counter, "Ar7a", f, delegator);
}

::World::Data::SmartPtr
Alien::disconnect_Ar7a(::Components::Cookie ck)
    throw(::Components::InvalidConnection)
{
    if(!ValidConnection)
    {
        throw ::Components::InvalidConnection();
    }
    return generic_multiple_disconnect< ::Space::Alien_Ar7a_Connections, ::World::Data::SmartPtr>(Ar7a_receptacles, "Ar7a", ck, delegator);
}

::Space::Alien_Ar7a_Connections&
Alien::get_connections_Ar7a (  )
{
    if(!ValidConnection)
    {
        throw ::Components::InvalidConnection();
    }
    return Ar7a_receptacles;
}

::Components::Cookie
Alien::connect_Ar7b(::World::Data::SmartPtr f)
    throw(::Components::ExceededConnectionLimit, ::Components::InvalidConnection)
{
    if(!ValidConnection)
    {
        throw ::Components::InvalidConnection();
    }
    return generic_multiple_connect(Ar7b_receptacles, Ar7b_receptacle_counter, "Ar7b", f, delegator);
}

::World::Data::SmartPtr
Alien::disconnect_Ar7b(::Components::Cookie ck)
    throw(::Components::InvalidConnection)
{
    if(!ValidConnection)
    {
        throw ::Components::InvalidConnection();
    }
    return generic_multiple_disconnect< ::Space::Alien_Ar7b_Connections, ::World::Data::SmartPtr>(Ar7b_receptacles, "Ar7b", ck, delegator);
}

::Space::Alien_Ar7b_Connections&
Alien::get_connections_Ar7b (  )
{
    if(!ValidConnection)
    {
        throw ::Components::InvalidConnection();
    }
    return Ar7b_receptacles;
}

// Navigation functions

::Components::Object::SmartPtr
Alien::provide_facet(const std::string& name)
    throw(::Components::InvalidName)
{
    if(name == "Ap1")
        return ::Components::Object::SmartPtr
            (dynamic_cast< ::Components::Object*> (provide_Ap1().ptr()));

    if(name == "Ap10")
        return ::Components::Object::SmartPtr
            (dynamic_cast< ::Components::Object*> (provide_Ap10().ptr()));

    throw ::Components::InvalidName();
}

// Receptacle functions

::Components::Cookie
Alien::connect(const ::Components::FeatureName& name,
                        ::Components::Object::SmartPtr connection)
    throw(::Components::InvalidName,
          ::Components::InvalidConnection,
          ::Components::AlreadyConnected,
          ::Components::ExceededConnectionLimit)
{
    if(name == "Ar3")
    {
        connect_Ar3(::World::Data::SmartPtr(
            dynamic_cast< ::World::Data*> (connection.ptr())));
        ::Components::Cookie ck;
        return ck;
    }
    if(name == "Ar4")
    {
        return connect_Ar4(::World::Data::SmartPtr
            (dynamic_cast< ::World::Data*> (connection.ptr())));
    }
    if(name == "Ar10")
    {
        connect_Ar10(::World::Data::SmartPtr(
            dynamic_cast< ::World::Data*> (connection.ptr())));
        ::Components::Cookie ck;
        return ck;
    }
    if(name == "Ar6a")
    {
        connect_Ar6a(::World::Data::SmartPtr(
            dynamic_cast< ::World::Data*> (connection.ptr())));
        ::Components::Cookie ck;
        return ck;
    }
    if(name == "Ar6b")
    {
        connect_Ar6b(::World::Data::SmartPtr(
            dynamic_cast< ::World::Data*> (connection.ptr())));
        ::Components::Cookie ck;
        return ck;
    }
    if(name == "Ar7a")
    {
        return connect_Ar7a(::World::Data::SmartPtr
            (dynamic_cast< ::World::Data*> (connection.ptr())));
    }
    if(name == "Ar7b")
    {
        return connect_Ar7b(::World::Data::SmartPtr
            (dynamic_cast< ::World::Data*> (connection.ptr())));
    }

    throw ::Components::InvalidName();
}

void
Alien::disconnect(const ::Components::FeatureName& name,
                           const ::Components::Cookie& ck)
    throw(::Components::InvalidName,
          ::Components::InvalidConnection,
          ::Components::CookieRequired,
          ::Components::NoConnection )
{
    if(!ValidConnection)
        throw ::Components::InvalidConnection();

    if(name == "Ar3")
    {
        disconnect_Ar3();
        return;
    }
    if(name == "Ar4")
    {
        disconnect_Ar4(ck);
        return;
    }
    if(name == "Ar10")
    {
        disconnect_Ar10();
        return;
    }
    if(name == "Ar6a")
    {
        disconnect_Ar6a();
        return;
    }
    if(name == "Ar6b")
    {
        disconnect_Ar6b();
        return;
    }
    if(name == "Ar7a")
    {
        disconnect_Ar7a(ck);
        return;
    }
    if(name == "Ar7b")
    {
        disconnect_Ar7b(ck);
        return;
    }

    throw ::Components::InvalidName();
}

// CCMObject functions

::Components::HomeExecutorBase*
Alien::get_ccm_home()
{
    throw ::Components::NotImplemented();
}

void
Alien::configuration_complete()
    throw(::Components::InvalidConfiguration)
{
    configuration_complete(new CCM_Alien_Context_impl(this));
}

void
Alien::configuration_complete(::Space::CCM_Alien_Context* ctx)
    throw(::Components::InvalidConfiguration)
{
    if(!ValidConnection)
        throw ::Components::InvalidConnection();
    if(!context)
      context = ctx;
    if(assembly != ::Components::Assembly::SmartPtr())
    {
        assembly->configuration_complete();
    }
    local_component->set_session_context(context);
    local_component->ccm_activate();
}

void
Alien::remove()
    throw(::Components::RemoveFailure)
{
    if(!ValidConnection)
        throw ::Components::InvalidConnection();
    local_component->ccm_remove();

    (dynamic_cast< ::ccmtools::local::World::DataAdapter* >(
        Ap1_facet.ptr()))->validConnection(false);

    (dynamic_cast< ::ccmtools::local::World::DataAdapter* >(
        Ap10_facet.ptr()))->validConnection(false);

    if(assembly != ::Components::Assembly::SmartPtr())
    {
        assembly->tear_down();
        // To resolve a cyclic smart pointer reference between a component
        // and an assembly object, the assembly smart pointer must be cleaned.
        assembly = ::Components::Assembly::SmartPtr();
    }
    ValidConnection = false;
}

//==============================================================================
// context adapter class implementation
//==============================================================================

CCM_Alien_Context_impl::CCM_Alien_Context_impl(Alien* c)
    : component_local_adapter(c)
{
}

CCM_Alien_Context_impl::~CCM_Alien_Context_impl()
{
}

::World::Data::SmartPtr
CCM_Alien_Context_impl::get_connection_Ar3()
    throw(::Components::NoConnection)
{
    return component_local_adapter->get_connection_Ar3();
}

::Space::Alien_Ar4_Connections&
CCM_Alien_Context_impl::get_connections_Ar4()
{
    return component_local_adapter->get_connections_Ar4();
}

::World::Data::SmartPtr
CCM_Alien_Context_impl::get_connection_Ar10()
    throw(::Components::NoConnection)
{
    return component_local_adapter->get_connection_Ar10();
}

::World::Data::SmartPtr
CCM_Alien_Context_impl::get_connection_Ar6a()
    throw(::Components::NoConnection)
{
    return component_local_adapter->get_connection_Ar6a();
}

::World::Data::SmartPtr
CCM_Alien_Context_impl::get_connection_Ar6b()
    throw(::Components::NoConnection)
{
    return component_local_adapter->get_connection_Ar6b();
}

::Space::Alien_Ar7a_Connections&
CCM_Alien_Context_impl::get_connections_Ar7a()
{
    return component_local_adapter->get_connections_Ar7a();
}

::Space::Alien_Ar7b_Connections&
CCM_Alien_Context_impl::get_connections_Ar7b()
{
    return component_local_adapter->get_connections_Ar7b();
}

::Components::HomeExecutorBase*
CCM_Alien_Context_impl::get_CCM_home()
{
    throw ::Components::NotImplemented();
}

::Components::Object*
CCM_Alien_Context_impl::get_CCM_object()
    throw(::Components::IllegalState)
{
    throw ::Components::NotImplemented();
}

} // /namespace Space

