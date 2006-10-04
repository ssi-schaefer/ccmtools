
/**
 * This file was automatically generated by CCM Tools version 0.5.4
 * <http://ccmtools.sourceforge.net/>
 *
 * ccm::local::CCM_Constants facet class implementation. 
 *
 * // TODO: WRITE YOUR DESCRIPTION HERE! 
 *
 * @author
 * @version 
 **/

#include <cassert>
#include <iostream>
#include <wx/utils/debug.h>

#include "Test_iface_impl.h"

using namespace std;
using namespace wx::utils;

namespace ccm {
namespace local {
namespace component {
namespace Test {

iface_impl::iface_impl(
    ccm::local::component::Test::CCM_Test_impl* component_impl)
  : component(component_impl)
{
  cout << "  BOOLEAN_CONST = " << Constants::BOOLEAN_CONST << endl; 
  cout << "  OCTET_CONST = " << (int)Constants::OCTET_CONST << endl;
  cout << "  SHORT_CONST = " << Constants::SHORT_CONST << endl;

  cout << "  SHORT_CONST = " << Constants::SHORT_CONST << endl;
  cout << "  USHORT_CONST = " << Constants::USHORT_CONST << endl;
  cout << "  LONG_CONST = " << Constants::LONG_CONST << endl;
  cout << "  ULONG_CONST = " << Constants::ULONG_CONST << endl;

  cout << "  CHAR_CONST = " << Constants::CHAR_CONST << endl;
  cout << "  STRING_CONST = " << Constants::STRING_CONST << endl;

  cout << "  FLOAT_CONST = " << Constants::FLOAT_CONST << endl;
  cout << "  DOUBLE_CONST = " << Constants::DOUBLE_CONST << endl;
}

iface_impl::~iface_impl()
{
    // OPTIONAL : IMPLEMENT ME HERE !
}

bool
iface_impl::getBooleanValue()
throw(::Components::ccm::local::CCMException)
{
  return Constants::BOOLEAN_CONST;
}

unsigned char
iface_impl::getOctetValue()
throw(::Components::ccm::local::CCMException)
{
  return Constants::OCTET_CONST;
}

short
iface_impl::getShortValue()
throw(::Components::ccm::local::CCMException)
{
  return Constants::SHORT_CONST;
}

unsigned short
iface_impl::getUnsignedShortValue()
throw(::Components::ccm::local::CCMException)
{
  return Constants::USHORT_CONST;
}

long
iface_impl::getLongValue()
throw(::Components::ccm::local::CCMException)
{
  return Constants::LONG_CONST;
}

unsigned long
iface_impl::getUnsignedLongValue()
throw(::Components::ccm::local::CCMException)
{
  return Constants::ULONG_CONST;
}

char
iface_impl::getCharValue()
throw(::Components::ccm::local::CCMException)
{
  return Constants::CHAR_CONST;
}

std::string
iface_impl::getStringValue()
throw(::Components::ccm::local::CCMException)
{
  return Constants::STRING_CONST;
}

float
iface_impl::getFloatValue()
throw(::Components::ccm::local::CCMException)
{
  return Constants::FLOAT_CONST; 
}

double
iface_impl::getDoubleValue()
throw(::Components::ccm::local::CCMException)
{
  return Constants::DOUBLE_CONST;
}

} // /namespace Test
} // /namespace component
} // /namespace local
} // /namespace ccm
