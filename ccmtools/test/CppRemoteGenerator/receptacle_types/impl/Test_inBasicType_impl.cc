
/**
 * This file was automatically generated by CCM Tools 
 * <http://ccmtools.sourceforge.net/>
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

#include "Test_inBasicType_impl.h"

using namespace std;

Test_inBasicType_impl::Test_inBasicType_impl(Test_impl* component_impl)
  : component(component_impl)
{
    // OPTIONAL : IMPLEMENT ME HERE !
}

Test_inBasicType_impl::~Test_inBasicType_impl()
{
    // OPTIONAL : IMPLEMENT ME HERE !
}

short
Test_inBasicType_impl::f1(
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
Test_inBasicType_impl::f2(
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
Test_inBasicType_impl::f3(
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
Test_inBasicType_impl::f4(
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
Test_inBasicType_impl::f5(
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
Test_inBasicType_impl::f6(
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
Test_inBasicType_impl::f7(
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
Test_inBasicType_impl::f8(
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
Test_inBasicType_impl::f9(
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
Test_inBasicType_impl::f10(
        const unsigned char p1,
        unsigned char& p2,
        unsigned char& p3)
throw(Components::CCMException)
{
    p3=p2;
    p2=p1;
    return p3+p1;
}
