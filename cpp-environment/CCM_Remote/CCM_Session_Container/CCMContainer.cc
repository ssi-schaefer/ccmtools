/* -*- mode: C++; c-basic-offset: 4 -*-
 *
 * CCM Tools : C++ Code Generator 
 * Egon Teiniker <egon.teiniker@tugraz.at>
 * copyright (c) 2002, 2003 Salomon Automation
 *
 * $Id$
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

#include <WX/Utils/debug.h>

#include "CCMContainer.h"

using namespace std;
using namespace WX::Utils;

//============================================================================
// Convert basic types from C++ to CORBA 
//============================================================================

void 
CCM::convertToCorba(const bool& in, CORBA::Boolean& out)
{
    LDEBUGNL(CCM_REMOTE, "convertToCorba(CORBA::Boolean)");
    out = (CORBA::Boolean)in;
}

void 
CCM::convertToCorba(const char& in, CORBA::Char& out)
{
    LDEBUGNL(CCM_REMOTE, "convertToCorba(CORBA::Char)");
    out = (CORBA::Char)in;
}

void 
CCM::convertToCorba(const double& in, CORBA::Double& out)
{
    LDEBUGNL(CCM_REMOTE, "convertToCorba(CORBA::Double)");
    out = (CORBA::Double)in;
}

void 
CCM::convertToCorba(const float& in, CORBA::Float& out)
{
    LDEBUGNL(CCM_REMOTE, "convertToCorba(CORBA::Float)");
    out = (CORBA::Float)in;
}

void 
CCM::convertToCorba(const long& in, CORBA::Long& out)
{
    LDEBUGNL(CCM_REMOTE, "convertToCorba(CORBA::Long)");
    out = (CORBA::Long)in;
}

void 
CCM::convertToCorba(const unsigned char& in, CORBA::Octet& out)
{
    LDEBUGNL(CCM_REMOTE, "convertToCorba(CORBA::Octet)");
    out = (CORBA::Octet)in;
}

void 
CCM::convertToCorba(const short& in, CORBA::Short& out)
{
    LDEBUGNL(CCM_REMOTE, "convertToCorba(CORBA::Short)");
    out = (CORBA::Short)in;
}

void 
CCM::convertToCorba(const std::string& in, char*& out)
{
    LDEBUGNL(CCM_REMOTE, "convertToCorba(char*)");
    const char* s = in.c_str();
    out = CORBA::string_dup(s);
}

void 
CCM::convertToCorba(const unsigned long& in, CORBA::ULong& out)
{
    LDEBUGNL(CCM_REMOTE, "convertToCorba(CORBA::ULong)");
    out = (CORBA::ULong)in;
}

void 
CCM::convertToCorba(const unsigned short& in, CORBA::UShort& out)
{
    LDEBUGNL(CCM_REMOTE, "convertToCorba(CORBA::UShort)");
    out = (CORBA::UShort)in;
}



//============================================================================
// Convert CORBA to C++ types
//============================================================================

void 
CCM::convertFromCorba(const CORBA::Boolean& in, bool& out)
{
    LDEBUGNL(CCM_REMOTE, "convertFromCorba(CORBA::Boolean)");
    out = (bool)in;
}

void 
CCM::convertFromCorba(const CORBA::Char& in, char& out)
{
    LDEBUGNL(CCM_REMOTE, "convertFromCorba(CORBA::Char)");
    out = (char)in;
}

void 
CCM::convertFromCorba(const CORBA::Double& in, double& out)
{
    LDEBUGNL(CCM_REMOTE, "convertFromCorba(CORBA::Double)");
    out = (double)in;
}

void 
CCM::convertFromCorba(const CORBA::Float& in, float& out)
{
    LDEBUGNL(CCM_REMOTE, "convertFromCorba(CORBA::Float)");
    out = (float)in;
}

void 
CCM::convertFromCorba(const CORBA::Long& in, long& out)
{
    LDEBUGNL(CCM_REMOTE, "convertFromCorba(CORBA::Long)");
    out = (long)in;
}

void 
CCM::convertFromCorba(const CORBA::Octet& in, unsigned char& out)
{
    LDEBUGNL(CCM_REMOTE, "convertFromCorba(CORBA::Octet)");
    out = (unsigned char)in;
}

void 
CCM::convertFromCorba(const CORBA::Short& in, short& out)
{
    LDEBUGNL(CCM_REMOTE, "convertFromCorba(CORBA::Short)");
    out = (short)in;
}

void 
CCM::convertFromCorba(const char*& in, std::string& out)
{
    LDEBUGNL(CCM_REMOTE, "convertFromCorba()");
    out = (std::string)in;
}

void 
CCM::convertFromCorba(char*& in, std::string& out)
{
    LDEBUGNL(CCM_REMOTE, "convertFromCorba()");
    out = (std::string)in;
}

void 
CCM::convertFromCorba(const CORBA::ULong& in, unsigned long& out)
{
    LDEBUGNL(CCM_REMOTE, "convertFromCorba(CORBA::ULong)");
    out = (unsigned long)in;
}  

void 
CCM::convertFromCorba(const CORBA::UShort& in, unsigned short& out)
{
    LDEBUGNL(CCM_REMOTE, "convertFromCorba( CORBA::UShort)");
    out = (unsigned short)in;
}



//============================================================================
// CCM Containers
//============================================================================

/*
 * There is one container per home. Multiple containers may be active in
 * a single process. Each container creates a new POA, where it activates
 * the home, all components and all facets.
 */

CORBA::ULong CCM::ContainerBase::_container_number = 0;

CCM::ContainerBase::ContainerBase()
{
  DEBUGNL("+CCM::ContainerBase::ContainerBase()");		
  _my_number = ++_container_number;
}

CCM::ContainerBase::~ContainerBase ()
{
  DEBUGNL("-CCM::ContainerBase::~ContainerBase ()");		
}



//============================================================================
// Implementation for the Session Container
//============================================================================

CCM::SessionContainer::SessionContainer (CORBA::ORB_ptr orb)
{
  DEBUGNL("+CCM::SessionContainer->SessionContainer()");

  CORBA::Object_var obj;
  char name[256];

  _have_info = 0;
  _orb = CORBA::ORB::_duplicate (orb);

  /*
   * Create a private POA
   */

  sprintf (name, "SessionContainer-%lu", _my_number);
  obj = _orb->resolve_initial_references ("RootPOA");
  PortableServer::POA_var poa = PortableServer::POA::_narrow (obj);

  CORBA::PolicyList pl;
  _my_poa = poa->create_POA(name, poa->the_POAManager(), pl);
}


CCM::SessionContainer::~SessionContainer ()
{
  DEBUGNL("-CCM::SessionContainer->SessionContainer()");
}


void CCM::SessionContainer::load(const ComponentInfo& info)
{
   DEBUGNL(" CCM::SessionContainer->load()");

   assert (!_have_info);
   _info = info;
   _have_info = 1;

  //Activate home singleton
  PortableServer::ObjectId_var oid =
    _my_poa->activate_object(_info.home_glue.in());
  _home_ref = _my_poa->id_to_reference(oid.in());
}


void CCM::SessionContainer::activate ()
{
  DEBUGNL(" CCM::SessionContainer->activate()");

  // TODO
  // Set instances to active, if their configuration is complete
  // iterate through an InstanceMap and call ccm_activate()

  // Activate our POA
  PortableServer::POAManager_var mgr;
  mgr = _my_poa->the_POAManager ();
  mgr->activate ();
}


void CCM::SessionContainer::passivate ()
{
  DEBUGNL(" CCM::SessionContainer::passivate()");

  // TODO
  // Set instances to inactive
  // iterate through an InstanceMap and call instance->ccm_passivate()

  // Deactivate our POA
  PortableServer::POAManager_var mgr = _my_poa->the_POAManager ();
  mgr->hold_requests (1);
}


void CCM::SessionContainer::remove ()
{
  DEBUGNL(" CCM::SessionContainer::remove()");

  passivate ();

  // TODO
  // Prepare instances for destruction
  // iterate through an InstanceMap and call instance->ccm_remove()

  // Destroy our POA
  _my_poa->destroy(1, 1);
}


CORBA::Boolean CCM::SessionContainer::compare(Components::CCMHome_ptr ohome)
{
  DEBUGNL(" CCM::SessionContainer::compare()");

  PortableServer::ObjectId_var myid = _my_poa->reference_to_id(_home_ref);
  PortableServer::ObjectId_var oid;

  try {
    oid = _my_poa->reference_to_id (ohome);
  }
  catch (PortableServer::POA::WrongAdapter &) {
    return 0;
  }

// HACK !!!!!!!!!!!!
//    return ((myid->length() == oid->length()) &&
//  	  memcmp (myid->get_buffer(), oid->get_buffer(), myid->length()) == 0);
  return false;
}


Components::CCMHome_ptr CCM::SessionContainer::get_reference_for_home ()
{
  DEBUGNL(" CCM::SessionContainer->get_reference_for_home()");
  return Components::CCMHome::_narrow (_home_ref.in());
}


Components::CCMObject_ptr
CCM::SessionContainer::activate_component(PortableServer::Servant skel)
{
  DEBUGNL(" CCM::SessionContainer->activate_component()");

  PortableServer::ObjectId_var oid = _my_poa->activate_object(skel);
  CORBA::Object_var ref = _my_poa->id_to_reference (oid.in());

  return Components::CCMObject::_narrow (ref);
}


Components::CCMObject_ptr
CCM::SessionContainer::get_reference_for_component (PortableServer::Servant s)
{
  DEBUGNL(" CCM::SessionContainer::get_reference_for_component()");

  CORBA::Object_var o = _my_poa->servant_to_reference (s);
  return Components::CCMObject::_narrow (o);
}


PortableServer::Servant
CCM::SessionContainer::get_skeleton_for_reference (CORBA::Object_ptr o)
{
  DEBUGNL(" CCM::SessionContainer::get_skeleton_for_reference()");
  
  return _my_poa->reference_to_servant (o);
}


Components::CCMObject_ptr
CCM::SessionContainer::get_reference_for_instance(LocalComponents::EnterpriseComponent* o)
{
  DEBUGNL(" CCM::SessionContainer::get_reference_for_instance()");

  // TODO
  return Components::CCMObject::_nil();
}


LocalComponents::EnterpriseComponent*
CCM::SessionContainer::get_instance_for_component (PortableServer::Servant skel)
{
  DEBUGNL(" CCM::SessionContainer::get_instance_for_component()");

  // TODO
  return NULL;
}


void CCM::SessionContainer::deactivate_component (CORBA::Object_ptr o)
{
  DEBUGNL(" CCM::SessionContainer::deactivate_component()");

  //PortableServer::ServantBase_var skel = get_skeleton_for_reference (o);
  PortableServer::Servant skel = get_skeleton_for_reference (o);
  deactivate_component (skel);
}


void CCM::SessionContainer::deactivate_component (PortableServer::Servant skel)
{
  DEBUGNL(" CCM::SessionContainer::deactivate_component()");

  PortableServer::ObjectId_var oid = _my_poa->servant_to_id (skel);
  /*
    PerComponentData & data = active_components[oid.in()];
    map<string, PortableServer::ServantBase_var, less<string> >::iterator it;
    
    for (it=data.facet_glue.begin(); it!=data.facet_glue.end(); it++) {
    PortableServer::ObjectId_var fid =
    _my_poa->servant_to_id ((*it).second);
    _my_poa->deactivate_object (fid.in());
    }
  */
  _my_poa->deactivate_object (oid);
  //active_components.erase (oid.in());
}


CORBA::Object_ptr
CCM::SessionContainer::activate_facet (PortableServer::Servant comp_glue,
					     const char * name,
					     void* facet_instance,
					     PortableServer::Servant facet_glue)
{
  DEBUGNL(" CCM::SessionContainer->activate_facet()");
  
  PortableServer::ObjectId_var oid = _my_poa->servant_to_id (comp_glue);
  PortableServer::ObjectId_var fid = _my_poa->activate_object (facet_glue);
  CORBA::Object_var fref = _my_poa->id_to_reference (fid.in());

  return fref._retn ();
}

/*
 * Facet management
 */

void CCM::SessionContainer::configuration_complete (PortableServer::Servant comp_glue)
{
  DEBUGNL(" CCM::SessionContainer::configuration_complete()");

  PortableServer::ObjectId_var oid = _my_poa->servant_to_id (comp_glue);
  //PerComponentData & data = active_components[oid.in()];
  //data.configuration_complete = 1;
}

/*
 * for Service Context
 */

Components::CCMHome_ptr CCM::SessionContainer::get_CCM_home ()
{
  DEBUGNL(" CCM::SessionContainer->get_CCM_home()");

  return get_reference_for_home ();
}


CORBA::Object_ptr CCM::SessionContainer::get_CCM_object (LocalComponents::EnterpriseComponent* o)
{
  DEBUGNL(" CCM::SessionContainer::get_CCM_object()");

  return get_reference_for_instance(o);
}





//============================================================================
// Valuetype implementations
//============================================================================

CCM::Cookie_impl::Cookie_impl ()
{
  DEBUGNL(" CCM::Cookie_impl::Cookie_impl()");
}


/*
 * ----------------------------------------------------------------------
 * Valuetype factories
 * ----------------------------------------------------------------------
 */

CORBA::ValueBase *
CCM::Cookie_Factory::create_for_unmarshal ()
{
  return new Cookie_impl;
}


void
CCM::register_all_factories (CORBA::ORB_ptr orb)
{
  CORBA::ValueFactoryBase_var vf;

  vf = new Cookie_Factory;
  orb->register_value_factory ("IDL:omg.org/Components/Cookie:1.0", vf);
}
