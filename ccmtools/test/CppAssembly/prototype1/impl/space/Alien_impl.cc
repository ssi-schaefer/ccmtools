
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

    ::Components::HomeFinder* finder = ::Components::HomeFinder::Instance();
    ::Components::CCMHome::SmartPtr hp = finder->find_home_by_name("Worker");
    ::Components::KeylessCCMHome* home = dynamic_cast< ::Components::KeylessCCMHome*>(hp.ptr());
    inner_ = home->create_component();
}

Alien_impl::~Alien_impl()
{
}


::Components::Object::SmartPtr Alien_impl::provide(const std::string& facet)
{
    if(facet=="Ap1")
    {
        return inner_->provide_facet("p1");
    }
    // TODO
}


::Components::Cookie Alien_impl::connect(const ::Components::FeatureName& receptacle, ::Components::Object::SmartPtr facet)
{
    if(receptacle=="Ar3")
    {
        return inner_->connect("r3", facet);
    }
    // TODO
}


void Alien_impl::disconnect(const ::Components::FeatureName& receptacle, ::Components::Cookie const& cookie)
{
    if(receptacle=="Ar3")
    {
        inner_->disconnect("r3", cookie);
        return;
    }
    // TODO
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

