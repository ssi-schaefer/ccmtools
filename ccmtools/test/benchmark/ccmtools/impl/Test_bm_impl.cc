
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

using namespace std;
using namespace WX::Utils;

namespace CCM_Local {
namespace CCM_Session_Test {

bm_impl::bm_impl(CCM_Local::CCM_Session_Test::CCM_Test_impl* component_impl)
  : component(component_impl)
{
    DEBUGNL("+bm_impl->bm_impl()");

    attr_l_ = 7;          // inv i1: attr_l > 0 and attr_l < 10
    attr_s_ = "012345";   // inv i2: attr_s.size > 0 and attr_s.size < 100

    // inv i3: attr_ll->size() > 0 and attr_ll->size() < 100
    for(long i=0; i<100; i++)
      attr_ll_.push_back(i);
}

bm_impl::~bm_impl()
{
    DEBUGNL ( "-bm_impl->~bm_impl (  )" );

    // OPTIONAL : IMPLEMENT ME HERE !
}

const long
bm_impl::attr_l() const
    throw(LocalComponents::CCMException)
{
    DEBUGNL(" bm_impl->attr_l()");
    return attr_l_;
}

void
bm_impl::attr_l(const long value)
    throw(LocalComponents::CCMException)
{
    DEBUGNL(" bm_impl->attr_l(value)");
    attr_l_ = value;
}

const std::string
bm_impl::attr_s() const
    throw(LocalComponents::CCMException)
{
    DEBUGNL(" bm_impl->attr_s()");
    return attr_s_;
}

void
bm_impl::attr_s(const std::string value)
    throw(LocalComponents::CCMException)
{
    DEBUGNL(" bm_impl->attr_s(value)");
    attr_s_ = value;
}

const LongList
bm_impl::attr_ll() const
    throw(LocalComponents::CCMException)
{
    DEBUGNL(" bm_impl->attr_ll()");
    return attr_ll_;
}

void
bm_impl::attr_ll(const LongList value)
    throw(LocalComponents::CCMException)
{
    DEBUGNL(" bm_impl->attr_ll(value)");
    attr_ll_ = value;
}

void
bm_impl::f0()
    throw (LocalComponents::CCMException)
{
    DEBUGNL("bm_impl->f0()");

    // TODO : IMPLEMENT ME HERE !
}

void
bm_impl::f_in1(const long l1)
    throw (LocalComponents::CCMException)
{
    DEBUGNL("bm_impl->f_in1(l1)");

    // TODO : IMPLEMENT ME HERE !
}

void
bm_impl::f_in2(const std::string& s1)
    throw (LocalComponents::CCMException)
{
    DEBUGNL("bm_impl->f_in2(s1)");

    // TODO : IMPLEMENT ME HERE !
}

void
bm_impl::f_in3(const LongList& ll1)
    throw (LocalComponents::CCMException)
{
    DEBUGNL("bm_impl->f_in3(ll1)");

    // TODO : IMPLEMENT ME HERE !
}

void
bm_impl::f_inout1(long& l1)
    throw (LocalComponents::CCMException)
{
    DEBUGNL("bm_impl->f_inout1(l1)");

    // TODO : IMPLEMENT ME HERE !
}

void
bm_impl::f_inout2(std::string& s1)
    throw (LocalComponents::CCMException)
{
    DEBUGNL("bm_impl->f_inout2(s1)");

    // TODO : IMPLEMENT ME HERE !
}

void
bm_impl::f_inout3(LongList& ll1)
    throw (LocalComponents::CCMException)
{
    DEBUGNL("bm_impl->f_inout3(ll1)");

    // TODO : IMPLEMENT ME HERE !
}

void
bm_impl::f_out1(long& l1)
    throw (LocalComponents::CCMException)
{
    DEBUGNL("bm_impl->f_out1(l1)");

    // TODO : IMPLEMENT ME HERE !
}

void
bm_impl::f_out2(std::string& s1)
    throw (LocalComponents::CCMException)
{
    DEBUGNL("bm_impl->f_out2(s1)");

    // TODO : IMPLEMENT ME HERE !
}

void
bm_impl::f_out3(LongList& ll1)
    throw (LocalComponents::CCMException)
{
    DEBUGNL("bm_impl->f_out3(ll1)");

    // TODO : IMPLEMENT ME HERE !
}

long
bm_impl::f_ret1()
    throw (LocalComponents::CCMException)
{
    DEBUGNL("bm_impl->f_ret1()");

    // TODO : IMPLEMENT ME HERE !
}

std::string
bm_impl::f_ret2()
    throw (LocalComponents::CCMException)
{
    DEBUGNL("bm_impl->f_ret2()");

    // TODO : IMPLEMENT ME HERE !
}

LongList
bm_impl::f_ret3()
    throw (LocalComponents::CCMException)
{
    DEBUGNL("bm_impl->f_ret3()");

    // TODO : IMPLEMENT ME HERE !
}

} // /namespace CCM_Session_Test
} // /namespace CCM_Local
