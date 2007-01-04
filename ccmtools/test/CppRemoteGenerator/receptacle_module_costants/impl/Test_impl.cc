
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

#include <cmath>
#include <cassert>
#include <iostream>

#include "Test_impl.h"
#include "Test_ifaceIn_impl.h"

namespace world {
namespace europe {

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
  SmartPtr<CCM_Constants> receptacle =
        ctx->get_connection_ifaceOut();

  {
    bool result = receptacle->getBooleanValue();
    assert(result == Constants::BOOLEAN_CONST);
  }
  
  {
    unsigned char result = receptacle->getOctetValue();
    assert(result == Constants::OCTET_CONST);
  }

  {
    short result = receptacle->getShortValue();
    assert(result == Constants::SHORT_CONST);
  }

  {
    unsigned short result = receptacle->getUnsignedShortValue();
    assert(result == Constants::USHORT_CONST);
  }
  
  {
    long result = receptacle->getLongValue();
    assert(result == Constants::LONG_CONST);
  }
  
  {
    unsigned long result = receptacle->getUnsignedLongValue();
    assert(result == Constants::ULONG_CONST);
  }

  {
    char result = receptacle->getCharValue();
    assert(result == Constants::CHAR_CONST);
  }

  {
    string result = receptacle->getStringValue();
    assert(result == Constants::STRING_CONST);
  }

  {
    float result = receptacle->getFloatValue();
    assert(abs(result - Constants::FLOAT_CONST) < 0.001);
  }
  
  {
    double result = receptacle->getDoubleValue();
    assert(abs(result - Constants::DOUBLE_CONST) < 0.0001);
  }
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

//==============================================================================
// world::ccm::local::CCM_Constants facet implementation
//==============================================================================

CCM_Constants*
Test_impl::get_ifaceIn()
{
    Test_ifaceIn_impl* facet = new Test_ifaceIn_impl(this);
    return dynamic_cast< CCM_Constants*>(facet);
}

} // /namespace europe
} // /namespace world

