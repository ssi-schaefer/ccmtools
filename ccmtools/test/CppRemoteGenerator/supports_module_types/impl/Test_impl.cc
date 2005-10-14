
/***
 * This file was automatically generated by CCM Tools version 0.5.3-pre3
 * <http://ccmtools.sourceforge.net/>
 *
 * Test component business logic implementation.
 * 
 * // TODO: WRITE YOUR DESCRIPTION HERE! 
 *
 * @author
 * @version 
 ***/

#include <cassert>
#include <iostream>
#include <WX/Utils/debug.h>

#include "Test_impl.h"

namespace world {
namespace europe {
namespace austria {
namespace ccm {
namespace local {
namespace component {
namespace Test {

using namespace std;
using namespace WX::Utils;

//==============================================================================
// CCM_Test - component implementation
//==============================================================================

CCM_Test_impl::CCM_Test_impl()
{
    // OPTIONAL : IMPLEMENT ME HERE !
}

CCM_Test_impl::~CCM_Test_impl()
{
    // OPTIONAL : IMPLEMENT ME HERE !
}

short
CCM_Test_impl::fb1(
        const short p1,
        short& p2,
        short& p3)
throw(::ccm::local::Components::CCMException)
{
    p3=p2;
    p2=p1;
    return p3+p1;
}

long
CCM_Test_impl::fb2(
        const long p1,
        long& p2,
        long& p3)
throw(::ccm::local::Components::CCMException)
{
    p3=p2;
    p2=p1;
    return p3+p1;
}

unsigned short
CCM_Test_impl::fb3(
        const unsigned short p1,
        unsigned short& p2,
        unsigned short& p3)
throw(::ccm::local::Components::CCMException)
{
    p3=p2;
    p2=p1;
    return p3+p1;
}

unsigned long
CCM_Test_impl::fb4(
        const unsigned long p1,
        unsigned long& p2,
        unsigned long& p3)
throw(::ccm::local::Components::CCMException)
{
    p3=p2;
    p2=p1;
    return p3+p1;
}

float
CCM_Test_impl::fb5(
        const float p1,
        float& p2,
        float& p3)
throw(::ccm::local::Components::CCMException)
{
    p3=p2;
    p2=p1;
    return p3+p1;
}

double
CCM_Test_impl::fb6(
        const double p1,
        double& p2,
        double& p3)
throw(::ccm::local::Components::CCMException)
{
    p3=p2;
    p2=p1;
    return p3+p1;
}

char
CCM_Test_impl::fb7(
        const char p1,
        char& p2,
        char& p3)
throw(::ccm::local::Components::CCMException)
{
    p3=p2;
    p2=p1;
    return p3+p1;
}

std::string
CCM_Test_impl::fb8(
        const std::string& p1,
        std::string& p2,
        std::string& p3)
throw(::ccm::local::Components::CCMException)
{
    p3=p2;
    p2=p1;
    return p3+p1;
}

bool
CCM_Test_impl::fb9(
        const bool p1,
        bool& p2,
        bool& p3)
throw(::ccm::local::Components::CCMException)
{
    p3=p2;
    p2=p1;
    return p3 && p1;
}

unsigned char
CCM_Test_impl::fb10(
        const unsigned char p1,
        unsigned char& p2,
        unsigned char& p3)
throw(::ccm::local::Components::CCMException)
{
    p3=p2;
    p2=p1;
    return p3+p1;
}


world::europe::austria::ccm::local::Color
CCM_Test_impl::fu1(
        const world::europe::austria::ccm::local::Color& p1,
        world::europe::austria::ccm::local::Color& p2,
        world::europe::austria::ccm::local::Color& p3)
throw(::ccm::local::Components::CCMException)
{
    p3=p2;
    p2=p1;
    return ccm::local::orange;
}

world::europe::austria::ccm::local::Person
CCM_Test_impl::fu2(
        const world::europe::austria::ccm::local::Person& p1,
        world::europe::austria::ccm::local::Person& p2,
        world::europe::austria::ccm::local::Person& p3)
throw(::ccm::local::Components::CCMException)
{
    Person r;
    r.name = p1.name + p2.name;
    r.id = p1.id + p2.id; 
    p3=p2;  
    p2=p1;
    return r;
}

world::europe::austria::ccm::local::Address
CCM_Test_impl::fu3(
        const world::europe::austria::ccm::local::Address& p1,
        world::europe::austria::ccm::local::Address& p2,
        world::europe::austria::ccm::local::Address& p3)
throw(::ccm::local::Components::CCMException)
{
    Address r;
    r.street = p1.street + p2.street;
    r.number = p1.number + p2.number;
    r.resident.id   = p1.resident.id + p2.resident.id;
    r.resident.name = p1.resident.name + p2.resident.name;
    p3 = p2;
    p2 = p1;
    return r;
}

world::europe::austria::ccm::local::LongList
CCM_Test_impl::fu4(
        const world::europe::austria::ccm::local::LongList& p1,
        world::europe::austria::ccm::local::LongList& p2,
        world::europe::austria::ccm::local::LongList& p3)
throw(::ccm::local::Components::CCMException)
{
    LongList r;
    for(unsigned long i=0;i<p1.size();i++) {
      r.push_back(i);
      p3.push_back(p2.at(i));
      p2.at(i) = p1.at(i);
    }
    return r;
}

world::europe::austria::ccm::local::StringList
CCM_Test_impl::fu5(
        const world::europe::austria::ccm::local::StringList& p1,
        world::europe::austria::ccm::local::StringList& p2,
        world::europe::austria::ccm::local::StringList& p3)
throw(::ccm::local::Components::CCMException)
{
    StringList r;
    for(unsigned long i=0;i<p1.size();i++) {
      r.push_back("Test");
      p3.push_back(p2.at(i));
      p2.at(i) = p1.at(i);
    }
    return r;
}

world::europe::austria::ccm::local::PersonList
CCM_Test_impl::fu6(
        const world::europe::austria::ccm::local::PersonList& p1,
        world::europe::austria::ccm::local::PersonList& p2,
        world::europe::austria::ccm::local::PersonList& p3)
throw(::ccm::local::Components::CCMException)
{
  PersonList r;
  for(unsigned long i=0; i < p1.size(); i++) {
    Person v;
    v.name = "Test";
    v.id = i;
    r.push_back(v);
    p3.push_back(p2.at(i));
    p2.at(i) = p1.at(i);
  }
  return r;
}

world::europe::austria::ccm::local::time_t
CCM_Test_impl::fu7(
        const world::europe::austria::ccm::local::time_t& t1,
        world::europe::austria::ccm::local::time_t& t2,
        world::europe::austria::ccm::local::time_t& t3)
throw(::ccm::local::Components::CCMException)
{
    t3=t2;
    t2=t1;
    return t3+t1;
}


void
CCM_Test_impl::fv1(const long p1)
throw(::ccm::local::Components::CCMException)
{
    attr_ = p1;
}

long
CCM_Test_impl::fv2()
throw(::ccm::local::Components::CCMException)
{
  return attr_;
}


void
CCM_Test_impl::set_session_context(
    ::ccm::local::Components::SessionContext* context)
    throw(::ccm::local::Components::CCMException)
{
    ctx = dynamic_cast<CCM_Test_Context*>(context);
}

void
CCM_Test_impl::ccm_activate()
    throw(::ccm::local::Components::CCMException)
{
    // OPTIONAL : IMPLEMENT ME HERE !
}

void
CCM_Test_impl::ccm_passivate()
    throw(::ccm::local::Components::CCMException)
{
    // OPTIONAL : IMPLEMENT ME HERE !
}

void
CCM_Test_impl::ccm_remove()
    throw(::ccm::local::Components::CCMException)
{
    // OPTIONAL : IMPLEMENT ME HERE !
}

} // /namespace Test
} // /namespace component
} // /namespace local
} // /namespace ccm
} // /namespace austria
} // /namespace europe
} // /namespace world

