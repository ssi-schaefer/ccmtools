
/***
 * PROTOTYPE 1
 ***/

#include <cassert>
#include <iostream>

#include "Alien_impl.h"
#include "Alien_Ap1_impl.h"
#include "Alien_Ap10_impl.h"

namespace Space {

using namespace std;

//==============================================================================
// Alien - component implementation
//==============================================================================

Alien_impl::Alien_impl()
: ccm_activate_ok(false)
{
    Ap1_ = NULL;    Ap10_ = NULL;

}

Alien_impl::~Alien_impl()
{
}

::Components::Object::SmartPtr Alien_impl::provide(const char* facet)
{
}

::Components::Cookie Alien_impl::connect(const char* receptacle, ::Components::Object::SmartPtr facet)
{
}

::Components::Object::SmartPtr Alien_impl::disconnect(const char* receptacle, ::Components::Cookie const& cookie)
{
}

void
Alien_impl::set_session_context(Components::SessionContext* context)
    throw(Components::CCMException)
{
    ctx = dynamic_cast< ::Space::CCM_Alien_Context* >(context);
}

void
Alien_impl::ccm_activate()
    throw(Components::CCMException)
{
    try {

    } catch(...) {
        throw Components::CCMException(Components::CREATE_ERROR);
    }
    ccm_activate_ok = true;
}

void
Alien_impl::ccm_passivate()
    throw(Components::CCMException)
{
    // Who calls this method?
}

void
Alien_impl::ccm_remove()
    throw(Components::CCMException)
{
    try {

    } catch(...) {
        throw Components::CCMException(Components::REMOVE_ERROR);
    }
}

//==============================================================================
// ::World::CCM_Data facet implementation
//==============================================================================

::World::CCM_Data*
Alien_impl::get_Ap1()
{
    Alien_Ap1_impl* facet =
        new Alien_Ap1_impl(this);

    Ap1_ = dynamic_cast< ::World::CCM_Data* >(facet);
    return Ap1_;
}

//==============================================================================
// ::World::CCM_Data facet implementation
//==============================================================================

::World::CCM_Data*
Alien_impl::get_Ap10()
{
    Alien_Ap10_impl* facet =
        new Alien_Ap10_impl(this);

    Ap10_ = dynamic_cast< ::World::CCM_Data* >(facet);
    return Ap10_;
}

} // /namespace Space

