/* CCM Tools : C++ Code Generator 
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

#include "CCMContainer.h"

using namespace std;
using namespace CCM_Utils;

//============================================================================
// Convert C++ to CORBA types
//============================================================================

CORBA::Boolean 
CCM::PK_BOOLEAN_to_CORBAPK_BOOLEAN(const bool value)
{
   DEBUGNL(" PK_BOOLEAN_to_CORBAPK_BOOLEAN()");
   return (CORBA::Boolean)value;
}

CORBA::Char 
CCM::PK_CHAR_to_CORBAPK_CHAR(const char value)
{
   DEBUGNL(" PK_CHAR_to_CORBAPK_CHAR()");
   return (CORBA::Char)value;
}

CORBA::Double  
CCM::PK_DOUBLE_to_CORBAPK_DOUBLE(const double value)
{
   DEBUGNL(" PK_DOUBLE_to_CORBAPK_DOUBLE()");
   return (CORBA::Double)value;
}


CORBA::Float   
CCM::PK_FLOAT_to_CORBAPK_FLOAT(const float value)
{
   DEBUGNL(" PK_FLOAT_to_CORBAPK_FLOAT()");
   return (CORBA::Float)value;
}

CORBA::Long    
CCM::PK_LONG_to_CORBAPK_LONG(const long value)
{
   DEBUGNL(" PK_LONG_to_CORBAPK_LONG()");
   return (CORBA::Long)value;
}

CORBA::Octet   
CCM::PK_OCTET_to_CORBAPK_OCTET(const unsigned char value)
{
   DEBUGNL(" PK_OCTET_to_CORBAPK_OCTET()");
   return (CORBA::Octet)value;
}

CORBA::Short   
CCM::PK_SHORT_to_CORBAPK_SHORT(const short value)
{
   DEBUGNL(" PK_SHORT_to_CORBAPK_SHORT()");
   return (CORBA::Short)value;
}

char*          
CCM::PK_STRING_to_CORBAPK_STRING(const std::string value)
{
   DEBUGNL(" PK_STRING_to_CORBAPK_STRING()");
   return CORBA::string_dup(value.c_str());
}

CORBA::ULong   
CCM::PK_ULONG_to_CORBAPK_ULONG(const unsigned long value)
{
   DEBUGNL(" PK_ULONG_to_CORBAPK_ULONG()");
   return (CORBA::ULong)value;
}

CORBA::UShort  
CCM::PK_USHORT_to_CORBAPK_USHORT(const unsigned short value)
{
   DEBUGNL(" PK_USHORT_to_CORBAPK_USHORT()");
   return (CORBA::UShort)value;
}



//============================================================================
// Convert CORBA to C++ types
//============================================================================

bool           
CCM::CORBAPK_BOOLEAN_to_PK_BOOLEAN(const CORBA::Boolean value)
{
  DEBUGNL(" CORBAPK_BOOLEAN_to_PK_BOOLEAN()");	
  return (bool)value;	
}

char           
CCM::CORBAPK_CHAR_to_PK_CHAR(const CORBA::Char value)
{
  DEBUGNL(" CCM::CORBAPK_CHAR_to_PK_CHAR()");
  return (char)value;
}

double         
CCM::CORBAPK_DOUBLE_to_PK_DOUBLE(const CORBA::Double value)
{
  DEBUGNL(" CCM::CORBAPK_DOUBLE_to_PK_DOUBLE()");
  return (double)value;
}

float          
CCM::CORBAPK_FLOAT_to_PK_FLOAT(const CORBA::Float value)
{
  DEBUGNL(" CCM::CORBAPK_FLOAT_to_PK_FLOAT()");
  return (float)value;
}

long           
CCM::CORBAPK_LONG_to_PK_LONG(const CORBA::Long value)
{
  DEBUGNL(" CCM::CORBAPK_LONG_to_PK_LONG()");
  return (long)value;
}

unsigned char  
CCM::CORBAPK_OCTET_to_PK_OCTET(const CORBA::Octet value)
{
  DEBUGNL(" CCM::CORBAPK_OCTET_to_PK_OCTET()");
  return (unsigned char)value;
}

short          
CCM::CORBAPK_SHORT_to_PK_SHORT(const CORBA::Short value)
{
  DEBUGNL(" CCM::CORBAPK_SHORT_to_PK_SHORT()");
  return (short)value;
}

std::string    
CCM::CORBAPK_STRING_to_PK_STRING(const char* value)
{
  DEBUGNL(" CCM::CORBAPK_STRING_to_PK_STRING()");
  return std::string(value);
}

unsigned long  
CCM::CORBAPK_ULONG_to_PK_ULONG(const CORBA::ULong value)
{
  DEBUGNL(" CCM::CORBAPK_ULONG_to_PK_ULONG()");
  return (long)value;
}

unsigned short 
CCM::CORBAPK_USHORT_to_PK_USHORT(const CORBA::UShort value)
{
  DEBUGNL(" CCM::CORBAPK_USHORT_to_PK_USHORT()");
  return (unsigned short)value;
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
CCM::SessionContainer::activate_component (localComponents::EnterpriseComponent* inst,
						 PortableServer::Servant skel)
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
CCM::SessionContainer::get_reference_for_instance(localComponents::EnterpriseComponent* o)
{
  DEBUGNL(" CCM::SessionContainer::get_reference_for_instance()");

  // TODO
  return Components::CCMObject::_nil();
}


localComponents::EnterpriseComponent*
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


CORBA::Object_ptr CCM::SessionContainer::get_CCM_object (localComponents::EnterpriseComponent* o)
{
  DEBUGNL(" CCM::SessionContainer::get_CCM_object()");

  return get_reference_for_instance(o);
}





//============================================================================
// Valuetype implementations
//============================================================================

CCM::PortDescription_impl::PortDescription_impl ()
{
  DEBUGNL(" CCM::PortDescription_impl::PortDescription_impl()");

  name ((const char *) "");
  type_id ((const char *) "");
}

CCM::Cookie_impl::Cookie_impl ()
{
  DEBUGNL(" CCM::Cookie_impl::Cookie_impl()");
}

CCM::FacetDescription_impl::FacetDescription_impl ()
{
  //ref (CORBA::Object::_nil ());
}

CCM::ConnectionDescription_impl::ConnectionDescription_impl ()
{
  ck (0);
  objref (CORBA::Object::_nil ());
}

CCM::ReceptacleDescription_impl::ReceptacleDescription_impl ()
{
}

CCM::ConsumerDescription_impl::ConsumerDescription_impl ()
{
  consumer (Components::EventConsumerBase::_nil ());
}

CCM::EmitterDescription_impl::EmitterDescription_impl ()
{
  consumer (Components::EventConsumerBase::_nil ());
}

CCM::SubscriberDescription_impl::SubscriberDescription_impl ()
{
  ck (0);
  consumer (Components::EventConsumerBase::_nil ());
}

CCM::PublisherDescription_impl::PublisherDescription_impl ()
{
}

CCM::ComponentPortDescription_impl::ComponentPortDescription_impl ()
{
}

/*
 * ----------------------------------------------------------------------
 * Valuetype factories
 * ----------------------------------------------------------------------
 */

CORBA::ValueBase *
CCM::PortDescription_Factory::create_for_unmarshal ()
{
  return new PortDescription_impl;
}

CORBA::ValueBase *
CCM::Cookie_Factory::create_for_unmarshal ()
{
  return new Cookie_impl;
}

CORBA::ValueBase *
CCM::FacetDescription_Factory::create_for_unmarshal ()
{
  return new FacetDescription_impl;
}

CORBA::ValueBase *
CCM::ConnectionDescription_Factory::create_for_unmarshal ()
{
  return new ConnectionDescription_impl;
}

CORBA::ValueBase *
CCM::ReceptacleDescription_Factory::create_for_unmarshal ()
{
  return new ReceptacleDescription_impl;
}

CORBA::ValueBase *
CCM::ConsumerDescription_Factory::create_for_unmarshal ()
{
  return new ConsumerDescription_impl;
}

CORBA::ValueBase *
CCM::EmitterDescription_Factory::create_for_unmarshal ()
{
  return new EmitterDescription_impl;
}

CORBA::ValueBase *
CCM::SubscriberDescription_Factory::create_for_unmarshal ()
{
  return new SubscriberDescription_impl;
}

CORBA::ValueBase *
CCM::PublisherDescription_Factory::create_for_unmarshal ()
{
  return new PublisherDescription_impl;
}

CORBA::ValueBase *
CCM::ComponentPortDescription_Factory::create_for_unmarshal ()
{
  return new ComponentPortDescription_impl;
}

void
CCM::register_all_factories (CORBA::ORB_ptr orb)
{
  CORBA::ValueFactoryBase_var vf;

  vf = new PortDescription_Factory;
  orb->register_value_factory ("IDL:omg.org/Components/PortDescription:1.0", vf);

  vf = new Cookie_Factory;
  orb->register_value_factory ("IDL:omg.org/Components/Cookie:1.0", vf);

  vf = new ConnectionDescription_Factory;
  orb->register_value_factory ("IDL:omg.org/Components/ConnectionDescription:1.0", vf);

  vf = new ReceptacleDescription_Factory;
  orb->register_value_factory ("IDL:omg.org/Components/ReceptacleDescription:1.0", vf);

  vf = new ConsumerDescription_Factory;
  orb->register_value_factory ("IDL:omg.org/Components/ConsumerDescription:1.0", vf);

  vf = new EmitterDescription_Factory;
  orb->register_value_factory ("IDL:omg.org/Components/EmitterDescription:1.0", vf);

  vf = new SubscriberDescription_Factory;
  orb->register_value_factory ("IDL:omg.org/Components/SubscriberDescription:1.0", vf);

  vf = new PublisherDescription_Factory;
  orb->register_value_factory ("IDL:omg.org/Components/PublisherDescription:1.0", vf);

  vf = new ComponentPortDescription_Factory;
  orb->register_value_factory ("IDL:omg.org/Components/ComponentPortDescription:1.0",
			       vf);
}