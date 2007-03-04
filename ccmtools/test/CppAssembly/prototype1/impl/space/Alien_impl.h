
/***
 * PROTOTYPE 1
 ***/

#ifndef __COMPONENT_Space_Alien_IMPL__H__
#define __COMPONENT_Space_Alien_IMPL__H__

#include <Space/Alien_share.h>

namespace Space {

/**
 * This class implements a component's equivalent and supported interfaces
 * as well as component attributes. Additionally, session component callback
 * methods must be implemented.
 *
 * Alien component class
 *
 * // TODO: WRITE YOUR DESCRIPTION HERE!
 *
 * @author
 * @version
 **/
class Alien_impl
    : virtual public ::Space::CCM_Alien
    , virtual public ::Components::ComponentDelegator
{
  private:

    bool ccm_activate_ok;

  public:
    ::Space::CCM_Alien_Context* ctx;

    Alien_impl();
    virtual ~Alien_impl();

    // ComponentDelegator implementation
    virtual ::Components::Object::SmartPtr provide(const char* facet);
    virtual ::Components::Cookie connect(const char* receptacle, ::Components::Object::SmartPtr facet);
    virtual ::Components::Object::SmartPtr disconnect(const char* receptacle, ::Components::Cookie const& cookie);

private:
    ::World::CCM_Data* Ap1_;
public:
    virtual ::World::CCM_Data* get_Ap1();
private:
    ::World::CCM_Data* Ap10_;
public:
    virtual ::World::CCM_Data* get_Ap10();

    // CCM callback methods

    virtual void set_session_context(Components::SessionContext* ctx)
        throw(Components::CCMException);

    virtual void ccm_activate()
        throw(Components::CCMException);

    virtual void ccm_passivate()
        throw(Components::CCMException);

    virtual void ccm_remove()
        throw(Components::CCMException);
};

} // /namespace Space

#endif

