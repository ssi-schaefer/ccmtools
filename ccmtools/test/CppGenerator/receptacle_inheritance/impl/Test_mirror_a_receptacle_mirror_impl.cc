
/**
 * This file was automatically generated by CCM Tools version 0.5.3-pre2
 * <http://ccmtools.sourceforge.net/>
 *
 * CCM_InterfaceType facet class implementation. 
 *
 * // TODO: WRITE YOUR DESCRIPTION HERE! 
 *
 * @author
 * @version 
 **/

#include <cassert>
#include <iostream>
#include <WX/Utils/debug.h>

#include "Test_mirror_a_receptacle_mirror_impl.h"


namespace ccm {
namespace local {
namespace component {
namespace Test_mirror {

using namespace std;
using namespace WX::Utils;


a_receptacle_mirror_impl::a_receptacle_mirror_impl(CCM_Test_mirror_impl* component_impl)
  : component(component_impl)
{
    // OPTIONAL : IMPLEMENT ME HERE !
}

a_receptacle_mirror_impl::~a_receptacle_mirror_impl()
{
    // OPTIONAL : IMPLEMENT ME HERE !
}

long
a_receptacle_mirror_impl::op3(const std::string& str)
    throw (Components::CCMException)
{
  cout << str << endl;
  return str.length();
}

long
a_receptacle_mirror_impl::op2(const std::string& str)
    throw (ccm::local::Components::CCMException)
{
  cout << str << endl;
  return str.length();
}

long
a_receptacle_mirror_impl::op1(const std::string& str)
    throw (ccm::local::Components::CCMException)
{
  cout << str << endl;
  return str.length();
}

} // /namespace Test_mirror
} // /namespace component
} // /namespace local
} // /namespace ccm
