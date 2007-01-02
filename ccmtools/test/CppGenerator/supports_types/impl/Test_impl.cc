
/***
 * Test component business logic implementation.
 * 
 * // TODO: WRITE YOUR DESCRIPTION HERE! 
 *
 * @author
 * @version 
 *
 * This file structure was automatically generated by CCM Tools
 * <http://ccmtools.sourceforge.net/> and contains a component's
 * implementation classes. 
 ***/

#include <cassert>
#include <iostream>

#include "Test_impl.h"
#include "MyObject.h"

using namespace std;
using namespace wamas::platform::utils;

//==============================================================================
// CCM_Test - component implementation
//==============================================================================

Test_impl::Test_impl (  )
{
}

Test_impl::~Test_impl (  )
{
}

short
Test_impl::op_b1(const short p1, short& p2, short& p3)
  throw (::Components::CCMException)
{
  p3=p2;
  p2=p1;
  return p3+p1;
}

long
Test_impl::op_b2(const long p1, long& p2, long& p3)
  throw (::Components::CCMException)
{
  p3=p2;
  p2=p1;
  return p3+p1;
}

unsigned short
Test_impl::op_b3(const unsigned short p1, unsigned short& p2, unsigned short& p3)
  throw (::Components::CCMException)
{
  p3=p2;
  p2=p1;
  return p3+p1;
}

unsigned long
Test_impl::op_b4(const unsigned long p1, unsigned long& p2, unsigned long& p3)
  throw (::Components::CCMException)
{
  p3=p2;
  p2=p1;
  return p3+p1;
}

float
Test_impl::op_b5(const float p1, float& p2, float& p3)
  throw (::Components::CCMException)
{
  p3=p2;
  p2=p1;
  return p3+p1;
}

double
Test_impl::op_b6(const double p1, double& p2, double& p3)
  throw (::Components::CCMException)
{
  p3=p2;
  p2=p1;
  return p3+p1;
}

char
Test_impl::op_b7(const char p1, char& p2, char& p3)
  throw (::Components::CCMException)
{
  p3=p2;
  p2=p1;
  return p3+p1;
}

std::string
Test_impl::op_b8(const std::string& p1, std::string& p2, std::string& p3)
  throw (::Components::CCMException)
{
  p3=p2;
  p2=p1;
  return p3+p1;
}

bool
Test_impl::op_b9(const bool p1, bool& p2, bool& p3)
  throw (::Components::CCMException)
{
  p3=p2;
  p2=p1;
  return p3 && p1;
}

unsigned char
Test_impl::op_b10(const unsigned char p1, unsigned char& p2, unsigned char& p3)
  throw (::Components::CCMException)
{
  p3=p2;
  p2=p1;
  return p3+p1;
}

time_t
Test_impl::op_u1(const time_t& p1, time_t& p2, time_t& p3)
  throw (::Components::CCMException)
{
  p3=p2;
  p2=p1;
  return p3+p1;
}

Color
Test_impl::op_u2(const Color& p1, Color& p2, Color& p3)
  throw (::Components::CCMException)
{
  p3=p2;
  p2=p1;
  return p1;
}

Pair
Test_impl::op_u3(const Pair& p1, Pair& p2, Pair& p3)
  throw (::Components::CCMException)
{
  Pair r;
  r.key = p1.key + p2.key;
  r.value = p1.value + p2.value;
  p3=p2;
  p2=p1;
  return r;
}

Map
Test_impl::op_u4(const Map& p1, Map& p2, Map& p3)
  throw (::Components::CCMException)
{
  Map r;
  for(unsigned int i=0;i<p1.size();i++) {
    Pair p;
    p.key = "test";
    p.value = (double)i;
    r.push_back(p);
    p3.push_back(p2.at(i));
    p2.at(i) = p1.at(i);
  }
  return r;
}

SmartPtr<Console>
Test_impl::op_i1(const SmartPtr<Console>& p1, SmartPtr<Console>& p2, 
		     SmartPtr<Console>& p3)
  throw (::Components::CCMException)
{
  MyObject* my_object3 = new MyObject;
  p3 = SmartPtr<Console>(my_object3);
  p3->prompt(p2->prompt());

  const string p1_prompt = "prompt1> ";
  p2->prompt(p1_prompt);

  MyObject* my_object4 = new MyObject;
  SmartPtr<Console> result(my_object4);
  result->prompt(p3->prompt() + p1_prompt);

  return result;
}

void
Test_impl::set_session_context ( ::Components::SessionContext* context )
  throw ( ::Components::CCMException )
{
  ctx = dynamic_cast<ccm::local::CCM_Test_Context*> ( context );
}

void
Test_impl::ccm_activate (  )
  throw ( ::Components::CCMException )
{
}

void
Test_impl::ccm_passivate (  )
  throw ( ::Components::CCMException )
{
}

void
Test_impl::ccm_remove (  )
  throw ( ::Components::CCMException )
{
}

