
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
    if(facet=="Ap10")
    {
        return ::Components::Object::SmartPtr();
    }
    throw ::Components::InvalidName();
}


::Components::Cookie Alien_impl::connect(const ::Components::FeatureName& receptacle, ::Components::Object::SmartPtr facet)
{
    if(receptacle=="Ar3")
    {
        return inner_->connect("r3", facet);
    }
    if(receptacle=="Ar4")
    {
        return inner_->connect("r4", facet);
    }
    if(receptacle=="Ar10")
    {
        Alien_Ap10_impl* impl = dynamic_cast<Alien_Ap10_impl*>(get_Ap10());
        impl->target = facet;
    }
    if(receptacle=="Ar6a")
    {
        Ar6a_ = inner_->connect("r6", facet);
        return Ar6a_;
    }
    if(receptacle=="Ar6b")
    {
        Ar6b_ = inner_->connect("r6", facet);
        return Ar6a_;
    }
    if(receptacle=="Ar7a")
    {
        return inner_->connect("r7", facet);
    }
    if(receptacle=="Ar7b")
    {
        return inner_->connect("r7", facet);
    }
    throw ::Components::InvalidName();
}


void Alien_impl::disconnect(const ::Components::FeatureName& receptacle, ::Components::Cookie const& cookie)
{
    if(receptacle=="Ar3")
    {
        inner_->disconnect("r3", cookie);
        return;
    }
    if(receptacle=="Ar4")
    {
        inner_->disconnect("r4", cookie);
        return;
    }
    if(receptacle=="Ar10")
    {
        Alien_Ap10_impl* impl = dynamic_cast<Alien_Ap10_impl*>(get_Ap10());
        impl->target.forget();
    }
    if(receptacle=="Ar6a")
    {
        inner_->disconnect("r6", Ar6a_);
        return;
    }
    if(receptacle=="Ar6b")
    {
        inner_->disconnect("r6", Ar6b_);
        return;
    }
    if(receptacle=="Ar7a")
    {
        inner_->disconnect("r7", cookie);
        return;
    }
    if(receptacle=="Ar7b")
    {
        inner_->disconnect("r7", cookie);
        return;
    }
    throw ::Components::InvalidName();
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

        inner_->connect("r2", inner_->provide_facet("p2"));

        inner_->connect("r5", inner_->provide_facet("p5a"));
        inner_->connect("r5", inner_->provide_facet("p5b"));

        inner_->configuration_complete();

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

        inner_->remove();

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
    if(!Ap1_)
    {
        Alien_Ap1_impl* facet =
            new Alien_Ap1_impl(this);

        Ap1_ = dynamic_cast< ::World::CCM_Data* >(facet);
    }
    return Ap1_;
}

//==============================================================================
// ::World::CCM_Data facet implementation
//==============================================================================

::World::CCM_Data*
Alien_impl::get_Ap10()
{
    if(!Ap10_)
    {
        Alien_Ap10_impl* facet =
            new Alien_Ap10_impl(this);

        Ap10_ = dynamic_cast< ::World::CCM_Data* >(facet);
    }
    return Ap10_;
}

} // /namespace Space

