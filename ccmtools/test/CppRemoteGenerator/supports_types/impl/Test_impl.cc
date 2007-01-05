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

#include "Test_impl.h"

using namespace std;

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
throw(Components::CCMException)
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
throw(Components::CCMException)
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
throw(Components::CCMException)
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
throw(Components::CCMException)
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
throw(Components::CCMException)
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
throw(Components::CCMException)
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
throw(Components::CCMException)
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
throw(Components::CCMException)
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
throw(Components::CCMException)
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
throw(Components::CCMException)
{
    p3=p2;
    p2=p1;
    return p3+p1;
}


Color
Test_impl::fu1(
        const Color& p1,
        Color& p2,
        Color& p3)
throw(Components::CCMException)
{
    p3=p2;
    p2=p1;
    return orange;
}

Person
Test_impl::fu2(
        const Person& p1,
        Person& p2,
        Person& p3)
throw(Components::CCMException)
{
    Person r;
    r.name = p1.name + p2.name;
    r.id = p1.id + p2.id; 
    p3=p2;  
    p2=p1;
    return r;
}

Address
Test_impl::fu3(
        const Address& p1,
        Address& p2,
        Address& p3)
throw(Components::CCMException)
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

LongList
Test_impl::fu4(
        const LongList& p1,
        LongList& p2,
        LongList& p3)
throw(Components::CCMException)
{
    LongList r;
    for(unsigned long i=0;i<p1.size();i++) 
    {
      r.push_back(i);
      p3.push_back(p2.at(i));
      p2.at(i) = p1.at(i);
    }
    return r;
}

StringList
Test_impl::fu5(
        const StringList& p1,
        StringList& p2,
        StringList& p3)
throw(Components::CCMException)
{
    StringList r;
    for(unsigned long i=0;i<p1.size();i++) 
    {
      r.push_back("Test");
      p3.push_back(p2.at(i));
      p2.at(i) = p1.at(i);
    }
    return r;
}

PersonList
Test_impl::fu6(
        const PersonList& p1,
        PersonList& p2,
        PersonList& p3)
throw(Components::CCMException)
{
      PersonList r;
    	  for(unsigned long i=0; i < p1.size(); i++) 
    	  {
      Person v;
      v.name = "Test";
      v.id = i;
      r.push_back(v);
      p3.push_back(p2.at(i));
      p2.at(i) = p1.at(i);
    }
    return r;
}

time_t
Test_impl::fu7(
        const time_t& t1,
        time_t& t2,
        time_t& t3)
throw(Components::CCMException)
{
    t3=t2;
    t2=t1;
    return t3+t1;
}


void
Test_impl::fv1(const long p1)
throw(Components::CCMException)
{
    attr_ = p1;
}

long
Test_impl::fv2()
throw(Components::CCMException)
{
  return attr_;
}

void
Test_impl::set_session_context(Components::SessionContext* context)
    throw(Components::CCMException)
{
    ctx = dynamic_cast<CCM_Test_Context*>(context);
}

void
Test_impl::ccm_activate()
    throw(Components::CCMException)
{
    // OPTIONAL : IMPLEMENT ME HERE !
}

void
Test_impl::ccm_passivate()
    throw(Components::CCMException)
{
    // OPTIONAL : IMPLEMENT ME HERE !
}

void
Test_impl::ccm_remove()
    throw(Components::CCMException)
{
    // OPTIONAL : IMPLEMENT ME HERE !
}

