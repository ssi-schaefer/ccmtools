
/**
 * This file was automatically generated by 
 * <http://ccmtools.sourceforge.net/>
 * DO NOT EDIT !
 *
 * CCM_BasicTypeInterface facet class implementation. 
 *
 * // TODO: WRITE YOUR DESCRIPTION HERE! 
 *
 * @author
 * @version 
 **/

#include <cassert>
#include <iostream>

#include "BasicTest_basicType_impl.h"

using namespace std;
using namespace wamas::platform::utils;

BasicTest_basicType_impl::BasicTest_basicType_impl(BasicTest_impl* component_impl)
  : component(component_impl)
{
    // OPTIONAL : IMPLEMENT ME HERE !
}

BasicTest_basicType_impl::~BasicTest_basicType_impl()
{
    // OPTIONAL : IMPLEMENT ME HERE !
}

short
BasicTest_basicType_impl::f1(const short p1, short& p2, short& p3)
    throw (::Components::CCMException)
{
    p3=p2;
    p2=p1;
    return p3+p1;
}

long
BasicTest_basicType_impl::f2(const long p1, long& p2, long& p3)
    throw (::Components::CCMException)
{
    p3=p2;
    p2=p1;
    return p3+p1;
}

unsigned short
BasicTest_basicType_impl::f3(const unsigned short p1, unsigned short& p2, unsigned short& p3)
    throw (::Components::CCMException)
{
    p3=p2;
    p2=p1;
    return p3+p1;
}

unsigned long
BasicTest_basicType_impl::f4(const unsigned long p1, unsigned long& p2, unsigned long& p3)
    throw (::Components::CCMException)
{
    p3=p2;
    p2=p1;
    return p3+p1;
}

float
BasicTest_basicType_impl::f5(const float p1, float& p2, float& p3)
    throw (::Components::CCMException)
{
    p3=p2;
    p2=p1;
    return p3+p1;
}

double
BasicTest_basicType_impl::f6(const double p1, double& p2, double& p3)
    throw (::Components::CCMException)
{
    p3=p2;
    p2=p1;
    return p3+p1;
}

char
BasicTest_basicType_impl::f7(const char p1, char& p2, char& p3)
    throw (::Components::CCMException)
{
    p3=p2;
    p2=p1;
    return p3+p1;
}

std::string
BasicTest_basicType_impl::f8(const std::string& p1, std::string& p2, std::string& p3)
    throw (::Components::CCMException)
{
    p3=p2;
    p2=p1;
    return p3+p1;
}

bool
BasicTest_basicType_impl::f9(const bool p1, bool& p2, bool& p3)
    throw (::Components::CCMException)
{
    p3=p2;
    p2=p1;
    return p3 && p1;
}

unsigned char
BasicTest_basicType_impl::f10(const unsigned char p1, unsigned char& p2, unsigned char& p3)
    throw (::Components::CCMException)
{
    p3=p2;
    p2=p1;
    return p3+p1;
}
