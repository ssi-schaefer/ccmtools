
/***
 * This file was automatically generated by CCM Tools 
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
#include <wamas/platform/utils/debug.h>

#include "Test_impl.h"

namespace world {
namespace europe {
namespace austria {
namespace ccm {
namespace local {

using namespace std;
using namespace wamas::platform::utils;

//==============================================================================
// CCM_Test - component implementation
//==============================================================================

Test_impl::Test_impl()
{
    // OPTIONAL : IMPLEMENT ME HERE !
}

Test_impl::~Test_impl()
{
    // OPTIONAL : IMPLEMENT ME HERE !
}

short
Test_impl::fb1(
        const short p1,
        short& p2,
        short& p3)
throw(::Components::ccm::local::CCMException)
{
    p3=p2;
    p2=p1;
    return p3+p1;
}

long
Test_impl::fb2(
        const long p1,
        long& p2,
        long& p3)
throw(::Components::ccm::local::CCMException)
{
    p3=p2;
    p2=p1;
    return p3+p1;
}

unsigned short
Test_impl::fb3(
        const unsigned short p1,
        unsigned short& p2,
        unsigned short& p3)
throw(::Components::ccm::local::CCMException)
{
    p3=p2;
    p2=p1;
    return p3+p1;
}

unsigned long
Test_impl::fb4(
        const unsigned long p1,
        unsigned long& p2,
        unsigned long& p3)
throw(::Components::ccm::local::CCMException)
{
    p3=p2;
    p2=p1;
    return p3+p1;
}

float
Test_impl::fb5(
        const float p1,
        float& p2,
        float& p3)
throw(::Components::ccm::local::CCMException)
{
    p3=p2;
    p2=p1;
    return p3+p1;
}

double
Test_impl::fb6(
        const double p1,
        double& p2,
        double& p3)
throw(::Components::ccm::local::CCMException)
{
    p3=p2;
    p2=p1;
    return p3+p1;
}

char
Test_impl::fb7(
        const char p1,
        char& p2,
        char& p3)
throw(::Components::ccm::local::CCMException)
{
    p3=p2;
    p2=p1;
    return p3+p1;
}

std::string
Test_impl::fb8(
        const std::string& p1,
        std::string& p2,
        std::string& p3)
throw(::Components::ccm::local::CCMException)
{
    p3=p2;
    p2=p1;
    return p3+p1;
}

bool
Test_impl::fb9(
        const bool p1,
        bool& p2,
        bool& p3)
throw(::Components::ccm::local::CCMException)
{
    p3=p2;
    p2=p1;
    return p3 && p1;
}

unsigned char
Test_impl::fb10(
        const unsigned char p1,
        unsigned char& p2,
        unsigned char& p3)
throw(::Components::ccm::local::CCMException)
{
    p3=p2;
    p2=p1;
    return p3+p1;
}


world::europe::austria::ccm::local::Color
Test_impl::fu1(
        const world::europe::austria::ccm::local::Color& p1,
        world::europe::austria::ccm::local::Color& p2,
        world::europe::austria::ccm::local::Color& p3)
throw(::Components::ccm::local::CCMException)
{
    p3=p2;
    p2=p1;
    return ccm::local::orange;
}

world::europe::austria::ccm::local::Person
Test_impl::fu2(
        const world::europe::austria::ccm::local::Person& p1,
        world::europe::austria::ccm::local::Person& p2,
        world::europe::austria::ccm::local::Person& p3)
throw(::Components::ccm::local::CCMException)
{
    Person r;
    r.name = p1.name + p2.name;
    r.id = p1.id + p2.id; 
    p3=p2;  
    p2=p1;
    return r;
}

world::europe::austria::ccm::local::Address
Test_impl::fu3(
        const world::europe::austria::ccm::local::Address& p1,
        world::europe::austria::ccm::local::Address& p2,
        world::europe::austria::ccm::local::Address& p3)
throw(::Components::ccm::local::CCMException)
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
Test_impl::fu4(
        const world::europe::austria::ccm::local::LongList& p1,
        world::europe::austria::ccm::local::LongList& p2,
        world::europe::austria::ccm::local::LongList& p3)
throw(::Components::ccm::local::CCMException)
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
Test_impl::fu5(
        const world::europe::austria::ccm::local::StringList& p1,
        world::europe::austria::ccm::local::StringList& p2,
        world::europe::austria::ccm::local::StringList& p3)
throw(::Components::ccm::local::CCMException)
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
Test_impl::fu6(
        const world::europe::austria::ccm::local::PersonList& p1,
        world::europe::austria::ccm::local::PersonList& p2,
        world::europe::austria::ccm::local::PersonList& p3)
throw(::Components::ccm::local::CCMException)
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
Test_impl::fu7(
        const world::europe::austria::ccm::local::time_t& t1,
        world::europe::austria::ccm::local::time_t& t2,
        world::europe::austria::ccm::local::time_t& t3)
throw(::Components::ccm::local::CCMException)
{
    t3=t2;
    t2=t1;
    return t3+t1;
}


void
Test_impl::fv1(const long p1)
throw(::Components::ccm::local::CCMException)
{
    attr_ = p1;
}

long
Test_impl::fv2()
throw(::Components::ccm::local::CCMException)
{
  return attr_;
}


void
Test_impl::set_session_context(
    ::Components::ccm::local::SessionContext* context)
    throw(::Components::ccm::local::CCMException)
{
    ctx = dynamic_cast<CCM_Test_Context*>(context);
}

void
Test_impl::ccm_activate()
    throw(::Components::ccm::local::CCMException)
{
    // OPTIONAL : IMPLEMENT ME HERE !
}

void
Test_impl::ccm_passivate()
    throw(::Components::ccm::local::CCMException)
{
    // OPTIONAL : IMPLEMENT ME HERE !
}

void
Test_impl::ccm_remove()
    throw(::Components::ccm::local::CCMException)
{
    // OPTIONAL : IMPLEMENT ME HERE !
}

} // /namespace local
} // /namespace ccm
} // /namespace austria
} // /namespace europe
} // /namespace world

