/*
 * PROTOTYPE 1
 */

//==============================================================================
// Alien - component logic (header)
//==============================================================================

#ifndef __COMPONENT_Space_Alien_GEN__H__
#define __COMPONENT_Space_Alien_GEN__H__

#include <string>
#include <map>

#include <Components/ccmtools.h>

#include <Space/Alien_share.h>
#include <Space/AlienHome_share.h>

#include <World/Data.h>




namespace Space {

class AlienHome;

//==============================================================================
// Alien local component adapter
//==============================================================================

class Alien
    : virtual public ::Components::CCMObject


{
  public:
    typedef wamas::platform::utils::SmartPtr<Alien> SmartPtr;

    Alien(AlienHome* h,
        ::Space::CCM_Alien* lc, ::Components::Assembly::SmartPtr a);
    virtual ~Alien();

    // Equivalent operations for facet Ap1

    ::World::Data::SmartPtr provide_Ap1();

    // Equivalent operations for facet Ap10

    ::World::Data::SmartPtr provide_Ap10();

    // Equivalent operations for single receptacle Ar3

    void connect_Ar3(::World::Data::SmartPtr i)
        throw(::Components::AlreadyConnected, ::Components::InvalidConnection);

    ::World::Data::SmartPtr disconnect_Ar3()
        throw(::Components::NoConnection, ::Components::InvalidConnection);

    // Equivalent operations for multiple receptacle Ar4
    ::Components::Cookie
    connect_Ar4(::World::Data::SmartPtr connection)
        throw(::Components::ExceededConnectionLimit, ::Components::InvalidConnection);

    ::World::Data::SmartPtr
    disconnect_Ar4(::Components::Cookie ck)
        throw(::Components::InvalidConnection);

    // Equivalent operations for single receptacle Ar10

    void connect_Ar10(::World::Data::SmartPtr i)
        throw(::Components::AlreadyConnected, ::Components::InvalidConnection);

    ::World::Data::SmartPtr disconnect_Ar10()
        throw(::Components::NoConnection, ::Components::InvalidConnection);

    // Equivalent operations for single receptacle Ar6a

    void connect_Ar6a(::World::Data::SmartPtr i)
        throw(::Components::AlreadyConnected, ::Components::InvalidConnection);

    ::World::Data::SmartPtr disconnect_Ar6a()
        throw(::Components::NoConnection, ::Components::InvalidConnection);

    // Equivalent operations for single receptacle Ar6b

    void connect_Ar6b(::World::Data::SmartPtr i)
        throw(::Components::AlreadyConnected, ::Components::InvalidConnection);

    ::World::Data::SmartPtr disconnect_Ar6b()
        throw(::Components::NoConnection, ::Components::InvalidConnection);

    // Equivalent operations for multiple receptacle Ar7a
    ::Components::Cookie
    connect_Ar7a(::World::Data::SmartPtr connection)
        throw(::Components::ExceededConnectionLimit, ::Components::InvalidConnection);

    ::World::Data::SmartPtr
    disconnect_Ar7a(::Components::Cookie ck)
        throw(::Components::InvalidConnection);

    // Equivalent operations for multiple receptacle Ar7b
    ::Components::Cookie
    connect_Ar7b(::World::Data::SmartPtr connection)
        throw(::Components::ExceededConnectionLimit, ::Components::InvalidConnection);

    ::World::Data::SmartPtr
    disconnect_Ar7b(::Components::Cookie ck)
        throw(::Components::InvalidConnection);

    ::World::Data::SmartPtr get_connection_Ar3()
  		throw(::Components::NoConnection, ::Components::InvalidConnection);

    ::Space::Alien_Ar4_Connections& get_connections_Ar4();
    ::World::Data::SmartPtr get_connection_Ar10()
  		throw(::Components::NoConnection, ::Components::InvalidConnection);

    ::World::Data::SmartPtr get_connection_Ar6a()
  		throw(::Components::NoConnection, ::Components::InvalidConnection);

    ::World::Data::SmartPtr get_connection_Ar6b()
  		throw(::Components::NoConnection, ::Components::InvalidConnection);

    ::Space::Alien_Ar7a_Connections& get_connections_Ar7a();
    ::Space::Alien_Ar7b_Connections& get_connections_Ar7b();

    // Navigation functions
    ::Components::Object::SmartPtr
    provide_facet(const std::string& name)
        throw(::Components::InvalidName);

    // Receptacle functions
    ::Components::Cookie
    connect(const ::Components::FeatureName& name,
        ::Components::Object::SmartPtr connection)
        throw(::Components::InvalidName,
              ::Components::InvalidConnection,
              ::Components::AlreadyConnected,
              ::Components::ExceededConnectionLimit);

    void
    disconnect(const ::Components::FeatureName& name,
        const ::Components::Cookie& ck)
        throw(::Components::InvalidName,
              ::Components::InvalidConnection,
              ::Components::CookieRequired,
              ::Components::NoConnection );

    // CCMObject functions
    ::Components::HomeExecutorBase* get_ccm_home();

    void configuration_complete()
        throw(::Components::InvalidConfiguration);

    void configuration_complete(::Space::CCM_Alien_Context* ctx)
        throw(::Components::InvalidConfiguration);

    void remove()
        throw(::Components::RemoveFailure);

   protected:
    AlienHome* home_local_adapter;
    ::Space::CCM_Alien* local_component;
    ::Space::CCM_Alien_Context* context;
    ::Components::Assembly::SmartPtr assembly;
    bool ValidConnection;
    ::Components::ComponentDelegator* delegator;

    ::World::Data::SmartPtr Ap1_facet;
    ::World::CCM_Data* Ap1_facet_impl;


    ::World::Data::SmartPtr Ap10_facet;
    ::World::CCM_Data* Ap10_facet_impl;


    ::World::Data::SmartPtr Ar3_receptacle;

    long Ar4_receptacle_counter;
    ::Space::Alien_Ar4_Connections Ar4_receptacles;
    ::World::Data::SmartPtr Ar10_receptacle;

    ::World::Data::SmartPtr Ar6a_receptacle;

    ::World::Data::SmartPtr Ar6b_receptacle;

    long Ar7a_receptacle_counter;
    ::Space::Alien_Ar7a_Connections Ar7a_receptacles;
    long Ar7b_receptacle_counter;
    ::Space::Alien_Ar7b_Connections Ar7b_receptacles;


  private:
    Alien(const Alien&);
    void operator=(const Alien&);
};

//==============================================================================
// context adapter class
//==============================================================================

class CCM_Alien_Context_impl
    : public ::Space::CCM_Alien_Context
{
  public:
    CCM_Alien_Context_impl(Alien* c);
    virtual ~CCM_Alien_Context_impl();

    ::World::Data::SmartPtr get_connection_Ar3()
        throw(::Components::NoConnection);

            ::Space::Alien_Ar4_Connections& get_connections_Ar4();
    ::World::Data::SmartPtr get_connection_Ar10()
        throw(::Components::NoConnection);

            ::World::Data::SmartPtr get_connection_Ar6a()
        throw(::Components::NoConnection);

            ::World::Data::SmartPtr get_connection_Ar6b()
        throw(::Components::NoConnection);

            ::Space::Alien_Ar7a_Connections& get_connections_Ar7a();
    ::Space::Alien_Ar7b_Connections& get_connections_Ar7b();

    // CCMContext function
    ::Components::HomeExecutorBase* get_CCM_home();

    // SessionContext function
    ::Components::Object* get_CCM_object()
        throw(::Components::IllegalState);

  protected:
    Alien* component_local_adapter;

  private:
    CCM_Alien_Context_impl(const CCM_Alien_Context_impl&);
    void operator=(const CCM_Alien_Context_impl&);
};

} // /namespace Space

#endif

