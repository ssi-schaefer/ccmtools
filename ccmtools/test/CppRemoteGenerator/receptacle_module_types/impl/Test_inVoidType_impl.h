/*
 * This file was automatically generated by CCM Tools version 0.5.3-pre3
 * <http://ccmtools.sourceforge.net/>
 *
 * world::europe::austria::ccm::local::CCM_VoidTypeInterface facet class definition.
 */

#ifndef __FACET__world_europe_austria_ccm_local_inVoidType__H__
#define __FACET__world_europe_austria_ccm_local_inVoidType__H__

#include <world/europe/austria/ccm/local/VoidTypeInterface.h>
#include "Test_impl.h"

namespace world {
namespace europe {
namespace austria {
namespace ccm {
namespace local {
namespace component {
namespace Test {

class inVoidType_impl
    : virtual public world::europe::austria::ccm::local::CCM_VoidTypeInterface
{
  protected:
    world::europe::austria::ccm::local::component::Test::CCM_Test_impl* component;
    // This attribute is accessed by explicite set and get methods
    // which are part of VoidTypeInterface.
    long attr;
    
  public:
    inVoidType_impl(
        world::europe::austria::ccm::local::component::Test::CCM_Test_impl* component_impl);
    virtual ~inVoidType_impl();

    virtual 
    void 
    f1(const long p1) 
    throw(::Components::ccm::local::CCMException);

    virtual 
    long 
    f2() 
    throw(::Components::ccm::local::CCMException);

};

} // /namespace Test
} // /namespace component
} // /namespace local
} // /namespace ccm
} // /namespace austria
} // /namespace europe
} // /namespace world

#endif // __FACET__world_europe_austria_ccm_local_inVoidType__H__

