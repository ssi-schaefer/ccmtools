
/**
 * CCM_Benchmark facet class implementation. 
 *
 * // TODO: WRITE YOUR DESCRIPTION HERE! 
 *
 * @author
 * @version 
 *
 * This class implements a facet's methods and attributes.
 **/

#include <cassert>
#include <iostream>
#include <WX/Utils/debug.h>

#include "Test_bm_impl.h"


namespace ccm {
namespace local {
namespace component {
namespace Test {

using namespace std;
using namespace WX::Utils;
using namespace ccm::local;

bm_impl::bm_impl(component::CCM_Test_impl* component_impl)
  : component(component_impl)
{
}

bm_impl::~bm_impl()
{
    // OPTIONAL : IMPLEMENT ME HERE !
}



const long
bm_impl::long_attr() const
    throw(Components::CCMException)
{
    return long_attr_;
}

void
bm_impl::long_attr(const long value)
    throw(Components::CCMException)
{
    long_attr_ = value;
}

const std::string
bm_impl::string_attr() const
    throw(Components::CCMException)
{
    return string_attr_;
}

void
bm_impl::string_attr(const std::string value)
    throw(Components::CCMException)
{
    string_attr_ = value;
}

const LongList
bm_impl::LongList_attr() const
    throw(Components::CCMException)
{
    return LongList_attr_;
}

void
bm_impl::LongList_attr(const LongList value)
    throw(Components::CCMException)
{
    LongList_attr_ = value;
}



void
bm_impl::f0()
    throw (Components::CCMException)
{
    // TODO : IMPLEMENT ME HERE !
}

void
bm_impl::f_in1(const long l1)
    throw (Components::CCMException)
{
    // TODO : IMPLEMENT ME HERE !
}

void
bm_impl::f_in2(const std::string& s1)
    throw (Components::CCMException)
{
    // TODO : IMPLEMENT ME HERE !
}

void
bm_impl::f_in3(const LongList& ll1)
    throw (Components::CCMException)
{
    // TODO : IMPLEMENT ME HERE !
}

void
bm_impl::f_inout1(long& l1)
    throw (Components::CCMException)
{
    l1 = long_attr_;
}

void
bm_impl::f_inout2(std::string& s1)
    throw (Components::CCMException)
{
    s1 = string_attr_;
}

void
bm_impl::f_inout3(LongList& ll1)
    throw (Components::CCMException)
{
    ll1 = LongList_attr_;
}

void
bm_impl::f_out1(long& l1)
    throw (Components::CCMException)
{
    l1 = long_attr_;
}

void
bm_impl::f_out2(std::string& s1)
    throw (Components::CCMException)
{
    s1 = string_attr_;
}

void
bm_impl::f_out3(LongList& ll1)
    throw (Components::CCMException)
{
    ll1 = LongList_attr_;
}

long
bm_impl::f_ret1()
    throw (Components::CCMException)
{
    return long_attr_;
}

std::string
bm_impl::f_ret2()
    throw (Components::CCMException)
{
    return string_attr_;
}

LongList
bm_impl::f_ret3()
    throw (Components::CCMException)
{
    return LongList_attr_;
}

} // /namespace CCM_Session_Test
} // /namespace CCM_Local
