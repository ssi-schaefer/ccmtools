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

#ifndef __CCM_CONTAINER_H__
#define __CCM_CONTAINER_H__

#include <CORBA.h>
#include <map>
#include <string>

#include <CCM_Utils/SmartPointer.h>
#include <CCM_Utils/Debug.h> 

#include <Components/CCM.h>
#include <LocalComponents/CCM.h>

namespace CCM {
  
  //============================================================================
  // Convert C++ to CORBA types
  //============================================================================
/*  
  CORBA::Long   long_to_CORBAlong(long i);
  CORBA::Double double_to_CORBAdouble(double d);
  
  char*         string_to_CORBAcharptr(std::string s);
*/

  CORBA::Boolean PK_BOOLEAN_to_CORBAPK_BOOLEAN(const bool);
  CORBA::Char    PK_CHAR_to_CORBAPK_CHAR(const char);
  CORBA::Double  PK_DOUBLE_to_CORBAPK_DOUBLE(const double);
  CORBA::Float   PK_FLOAT_to_CORBAPK_FLOAT(const float);
  CORBA::Long    PK_LONG_to_CORBAPK_LONG(const long);
  CORBA::Octet   PK_OCTET_to_CORBAPK_OCTET(const unsigned char);
  CORBA::Short   PK_SHORT_to_CORBAPK_SHORT(const short);
  char*          PK_STRING_to_CORBAPK_STRING(const std::string);
  CORBA::ULong   PK_ULONG_to_CORBAPK_ULONG(const unsigned long);
  CORBA::UShort  PK_USHORT_to_CORBAPK_USHORT(const unsigned short);


  //============================================================================
  // Convert CORBA to C++ types
  //============================================================================
/*  
  long        CORBAlong_to_long(const CORBA::Long i);
  double      CORBAdouble_to_double(const CORBA::Double d);
  std::string CORBAcharptr_to_string(const char* s);
*/

  bool           CORBAPK_BOOLEAN_to_PK_BOOLEAN(const CORBA::Boolean);
  char           CORBAPK_CHAR_to_PK_CHAR(const CORBA::Char);
  double         CORBAPK_DOUBLE_to_PK_DOUBLE(const CORBA::Double);
  float          CORBAPK_FLOAT_to_PK_FLOAT(const CORBA::Float);
  long           CORBAPK_LONG_to_PK_LONG(const CORBA::Long);
  unsigned char  CORBAPK_OCTET_to_PK_OCTET(const CORBA::Octet);
  short          CORBAPK_SHORT_to_PK_SHORT(const CORBA::Short);
  std::string    CORBAPK_STRING_to_PK_STRING(const char*);
  unsigned long  CORBAPK_ULONG_to_PK_ULONG(const CORBA::ULong);
  unsigned short CORBAPK_USHORT_to_PK_USHORT(const CORBA::UShort);


  //============================================================================
  // Base for all Containers
  //============================================================================
  
    class ContainerBase {
    protected:
      static CORBA::ULong _container_number;
      CORBA::ULong _my_number;

    public:
      ContainerBase ();
      virtual ~ContainerBase ();
      virtual void activate () = 0;
      virtual void passivate () = 0;
      virtual void remove () = 0;

      virtual CORBA::Boolean
      compare (Components::CCMHome_ptr) = 0;

      /*
       * for SessionContext
       */

      virtual Components::CCMHome_ptr
      get_CCM_home () = 0;

      virtual CORBA::Object_ptr
      get_CCM_object (LocalComponents::EnterpriseComponent*) = 0;
    };



    //============================================================================
    // Session Container
    //============================================================================

    class SessionContainer : virtual public ContainerBase {
    private:
      CORBA::ORB_var _orb;
      PortableServer::POA_var _my_poa;

    public:
      struct ComponentInfo {
	std::string home_short_name;
	std::string home_absolute_name;
	std::string home_id;
	std::string component_short_name;
	std::string component_absolute_name;
	std::string component_id;
	LocalComponents::HomeExecutorBase* home_instance;
	PortableServer::ServantBase_var home_glue;
      };

    private:
      ComponentInfo _info;
      bool _have_info;

      struct PerComponentData {
	CORBA::Boolean configuration_complete;
	PortableServer::ServantBase_var glue;
	LocalComponents::EnterpriseComponent* instance;
	CORBA::Object_var reference;
	std::map<std::string, PortableServer::ServantBase_var> facet_glue;
	std::map<std::string, void*> facet_instance;
	std::map<std::string, CORBA::Object_var> facet_reference;
      };

      CORBA::Object_var _home_ref;
      typedef std::map<std::string, PortableServer::ObjectId> InstanceMap;
      InstanceMap active_components;

    public:
      SessionContainer (CORBA::ORB_ptr orb);
      ~SessionContainer ();

      void load (const ComponentInfo & info);
      void activate ();
      void passivate ();
      void remove ();

      virtual CORBA::Boolean
      compare (Components::CCMHome_ptr);

      /*
       * Session Container API
       */

      Components::CCMHome_ptr
      get_reference_for_home ();

      Components::CCMObject_ptr
      activate_component (LocalComponents::EnterpriseComponent* instance,
			  PortableServer::Servant skel);

      Components::CCMObject_ptr
      get_reference_for_component(PortableServer::Servant skel);

      PortableServer::Servant
      get_skeleton_for_reference(CORBA::Object_ptr ref);

      Components::CCMObject_ptr
      get_reference_for_instance(LocalComponents::EnterpriseComponent*);

      LocalComponents::EnterpriseComponent*
      get_instance_for_component (PortableServer::Servant skel);

      void
      deactivate_component (CORBA::Object_ptr ref);

      void
      deactivate_component (PortableServer::Servant skel);

      /*
       * for SessionContext
       */

      Components::CCMHome_ptr
      get_CCM_home ();

      CORBA::Object_ptr
      get_CCM_object (LocalComponents::EnterpriseComponent*);

      /*
       * Facet management
       */

      CORBA::Object_ptr
      activate_facet (PortableServer::Servant comp_glue,
		      const char * name,
		      void* facet_instance,
		      PortableServer::Servant facet_glue);

      /*
       * notify container of configuration_complete
       */

      void configuration_complete (PortableServer::Servant comp_glue);
    };




    //============================================================================
    // Valuetype implementations
    //============================================================================

    class PortDescription_impl :
      virtual public OBV_Components::PortDescription,
      virtual public CORBA::DefaultValueRefCountBase
    {
    public:
      PortDescription_impl ();
    };


    class Cookie_impl :
      virtual public OBV_Components::Cookie,
      virtual public CORBA::DefaultValueRefCountBase
    {
    public:
      Cookie_impl ();
    };


    class FacetDescription_impl :
      virtual public OBV_Components::FacetDescription,
      virtual public PortDescription_impl,
      virtual public CORBA::DefaultValueRefCountBase
    {
    public:
      FacetDescription_impl ();
    };


    class ConnectionDescription_impl :
      virtual public OBV_Components::ConnectionDescription,
      virtual public CORBA::DefaultValueRefCountBase
    {
    public:
      ConnectionDescription_impl ();
    };


    class ReceptacleDescription_impl :
      virtual public OBV_Components::ReceptacleDescription,
      virtual public PortDescription_impl,
      virtual public CORBA::DefaultValueRefCountBase
    {
    public:
      ReceptacleDescription_impl ();
    };


    class ConsumerDescription_impl :
      virtual public OBV_Components::ConsumerDescription,
      virtual public PortDescription_impl,
      virtual public CORBA::DefaultValueRefCountBase
    {
    public:
      ConsumerDescription_impl ();
    };


    class EmitterDescription_impl :
      virtual public OBV_Components::EmitterDescription,
      virtual public PortDescription_impl,
      virtual public CORBA::DefaultValueRefCountBase
    {
    public:
      EmitterDescription_impl ();
    };


    class SubscriberDescription_impl :
      virtual public OBV_Components::SubscriberDescription,
      virtual public CORBA::DefaultValueRefCountBase
    {
    public:
      SubscriberDescription_impl ();
    };


    class PublisherDescription_impl :
      virtual public OBV_Components::PublisherDescription,
      virtual public PortDescription_impl,
      virtual public CORBA::DefaultValueRefCountBase
    {
    public:
      PublisherDescription_impl ();
    };


    class ComponentPortDescription_impl :
      virtual public OBV_Components::ComponentPortDescription,
      virtual public CORBA::DefaultValueRefCountBase
    {
    public:
      ComponentPortDescription_impl ();
    };



    /*
     * Valuetype factories
     */

    class PortDescription_Factory :
      virtual public CORBA::ValueFactoryBase
    {
    public:
      CORBA::ValueBase * create_for_unmarshal ();
    };

    class Cookie_Factory :
      virtual public CORBA::ValueFactoryBase
    {
    public:
      CORBA::ValueBase * create_for_unmarshal ();
    };

    class FacetDescription_Factory :
      virtual public CORBA::ValueFactoryBase
    {
    public:
      CORBA::ValueBase * create_for_unmarshal ();
    };

    class ConnectionDescription_Factory :
      virtual public CORBA::ValueFactoryBase
    {
    public:
      CORBA::ValueBase * create_for_unmarshal ();
    };

    class ReceptacleDescription_Factory :
      virtual public CORBA::ValueFactoryBase
    {
    public:
      CORBA::ValueBase * create_for_unmarshal ();
    };

    class ConsumerDescription_Factory :
      virtual public CORBA::ValueFactoryBase
    {
    public:
      CORBA::ValueBase * create_for_unmarshal ();
    };

    class EmitterDescription_Factory :
      virtual public CORBA::ValueFactoryBase
    {
    public:
      CORBA::ValueBase * create_for_unmarshal ();
    };

    class SubscriberDescription_Factory :
      virtual public CORBA::ValueFactoryBase
    {
    public:
      CORBA::ValueBase * create_for_unmarshal ();
    };

    class PublisherDescription_Factory :
      virtual public CORBA::ValueFactoryBase
    {
    public:
      CORBA::ValueBase * create_for_unmarshal ();
    };

    class ComponentPortDescription_Factory :
      virtual public CORBA::ValueFactoryBase
    {
    public:
      CORBA::ValueBase * create_for_unmarshal ();
    };

    void register_all_factories (CORBA::ORB_ptr);
} // /namespace CCM

#endif




