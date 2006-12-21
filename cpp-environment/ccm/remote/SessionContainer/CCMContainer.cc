/* -*- mode: C++; c-basic-offset: 4 -*-
 *
 * CCM Tools : C++ Code Generator 
 * Egon Teiniker <egon.teiniker@tugraz.at>
 * copyright (c) 2002, 2003 Salomon Automation
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

/* 
 * This code is based on the MicoCCM implementation of a CCM session container.
 */

#ifdef HAVE_CONFIG_H
#  include <config.h>
#endif 

#ifdef HAVE_MICO  

#include <sstream>

#include "CCMContainer.h"


namespace ccm {
namespace remote {
	
using namespace std;
using namespace wamas::platform::utils;

//============================================================================
// CCM Containers
//============================================================================

/*
 * There is one container per home. Multiple containers may be active in
 * a single process. Each container creates a new POA, where it activates
 * the home, all components and all facets.
 */

CORBA::ULong ContainerBase::globalContainerNumber_ = 0;

ContainerBase::ContainerBase()
{
    containerNumber_ = ++globalContainerNumber_;
}



//============================================================================
// Session Container Implementation 
//============================================================================

SessionContainer::SessionContainer(CORBA::ORB_ptr orb)
{
    CORBA::Object_var obj;
    _have_info = 0;
    orb_ = CORBA::ORB::_duplicate(orb);
    ostringstream poaName;
    poaName << "CCMSessionPOA" << containerNumber_; 
    obj = orb_->resolve_initial_references ("RootPOA");
    PortableServer::POA_var root_poa = PortableServer::POA::_narrow(obj);
    PortableServer::POAManager_var mgr = root_poa->the_POAManager();
    CORBA::PolicyList pl;

#ifdef CCM_PERSISTENT_POA
    // This POA settings are used for debugging to use the same IOR
    // during server restarts.
    pl.length(2);
    pl[0] = root_poa->create_lifespan_policy (PortableServer::PERSISTENT);
    pl[1] = root_poa->create_id_assignment_policy (PortableServer::USER_ID);
    poa_  = root_poa->create_POA(poaName.str().c_str(), mgr, pl);
    cout << " This CCMContainer version has been compiled with "
	"-DCCM_PERSISTENT_POA (for debugging only)!!!!" << endl;
#else
    poa_ = root_poa->create_POA(poaName.str().c_str(), mgr, pl);
#endif

}

void 
SessionContainer::load(const ComponentInfo& info)
{
    _info = info;
    _have_info = 1;

#ifdef CCM_PERSISTENT_POA
    // Handle POA with USER_ID policy
    PortableServer::ObjectId_var oid =
	PortableServer::string_to_ObjectId(_info.home_short_name.c_str());
    poa_->activate_object_with_id (*oid, _info.home_glue.in());	
    _home_ref = poa_->id_to_reference(oid.in());
#else
    PortableServer::ObjectId_var oid =
	poa_->activate_object(_info.home_glue.in());
    _home_ref = poa_->id_to_reference(oid.in());
#endif
    assert(!CORBA::is_nil(_home_ref));
}


void 
SessionContainer::activate()
{
    // TODO:
    // Set instances to active, if their configuration is complete
    // iterate through an InstanceMap and call ccm_activate()
    
    // Activate our POA
    PortableServer::POAManager_var mgr;
    mgr = poa_->the_POAManager ();
    mgr->activate ();
}


void 
SessionContainer::passivate()
{
    // TODO:
    // Set instances to inactive
    // iterate through an InstanceMap and call instance->ccm_passivate()
}


void 
SessionContainer::remove()
{
    // TODO:
    // Prepare instances for destruction
    // iterate through an InstanceMap and call instance->ccm_remove()
}


CORBA::Boolean 
SessionContainer::compare(::ccm::corba::Components::CCMHome_ptr ohome)
{
	// TODO
    return false;
}


::ccm::corba::Components::CCMHome_ptr 
SessionContainer::get_reference_for_home()
{
    return ::ccm::corba::Components::CCMHome::_narrow(_home_ref.in());
}


::ccm::corba::Components::CCMObject_ptr
SessionContainer::activate_component(PortableServer::Servant skel)
{
#ifdef CCM_PERSISTENT_POA
    PortableServer::ObjectId_var oid =
	PortableServer::string_to_ObjectId(_info.component_short_name.c_str());
    poa_->activate_object_with_id (*oid, skel);	
#else
    PortableServer::ObjectId_var oid = poa_->activate_object(skel);
#endif
    CORBA::Object_var ref = poa_->id_to_reference(oid.in());
    return ::ccm::corba::Components::CCMObject::_narrow(ref);
}


::ccm::corba::Components::CCMObject_ptr
SessionContainer::get_reference_for_component(PortableServer::Servant s)
{
    CORBA::Object_var o = poa_->servant_to_reference(s);
    return ::ccm::corba::Components::CCMObject::_narrow(o);
}


PortableServer::Servant
SessionContainer::get_skeleton_for_reference(CORBA::Object_ptr o)
{
    return poa_->reference_to_servant(o);
}


void 
SessionContainer::deactivate_component(CORBA::Object_ptr o)
{
    PortableServer::Servant skel = get_skeleton_for_reference(o);
    deactivate_component(skel);
}


void 
SessionContainer::deactivate_component(PortableServer::Servant skel)
{
    PortableServer::ObjectId_var oid = poa_->servant_to_id (skel);
	// TODO
    poa_->deactivate_object (oid);
}


CORBA::Object_ptr
SessionContainer::activate_facet(PortableServer::Servant comp_glue, const char * name,
				      void* facet_instance, PortableServer::Servant facet_glue)
{
#ifdef CCM_PERSISTENT_POA
    PortableServer::ObjectId_var fid =
	PortableServer::string_to_ObjectId(name);
    poa_->activate_object_with_id (*fid, facet_glue);	
#else
    PortableServer::ObjectId_var fid = poa_->activate_object (facet_glue);
#endif
    CORBA::Object_var fref = poa_->id_to_reference(fid.in());
    return fref._retn ();
}


/*
 * Facet management
 */

void 
SessionContainer::configuration_complete(PortableServer::Servant comp_glue)
{
    PortableServer::ObjectId_var oid = poa_->servant_to_id (comp_glue);
	// TODO
}


/*
 * for Service Context
 */

::ccm::corba::Components::CCMHome_ptr 
SessionContainer::get_CCM_home ()
{
    return get_reference_for_home ();
}


CORBA::Object_ptr 
SessionContainer::get_CCM_object(::Components::EnterpriseComponent* o)
{
    // TODO
    return ::ccm::corba::Components::CCMObject::_nil();
}



//============================================================================
// Valuetype implementations
//============================================================================

long Cookie_impl::globalId_ = 0;

Cookie_impl::Cookie_impl()
{
    id_ = ++globalId_;
}


/*
 * ----------------------------------------------------------------------
 * Valuetype factories
 * ----------------------------------------------------------------------
 */

CORBA::ValueBase*
Cookie_Factory::create_for_unmarshal()
{
    return new Cookie_impl;
}


void
register_all_factories(CORBA::ORB_ptr orb)
{
    CORBA::ValueFactoryBase_var vf;
    vf = new Cookie_Factory;
    orb->register_value_factory ("IDL:omg.org/Components/Cookie:1.0", vf);
}

} // /namespace remote
} // /namespace ccm

#endif // HAVE_MICO
