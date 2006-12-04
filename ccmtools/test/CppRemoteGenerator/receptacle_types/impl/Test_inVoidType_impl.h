/*
 * This file was automatically generated by CCM Tools 
 * <http://ccmtools.sourceforge.net/>
 *
 * ccm::local::CCM_VoidTypeInterface facet class definition.
 */

#ifndef __FACET__ccm_local_inVoidType__H__
#define __FACET__ccm_local_inVoidType__H__

#include <ccm/local/VoidTypeInterface.h>

#include "Test_impl.h"

namespace ccm {
namespace local {

class Test_inVoidType_impl
    : virtual public ccm::local::CCM_VoidTypeInterface
{
  protected:
    ccm::local::Test_impl* component;

    // This attribute is accessed by explicite set and get methods
    // which are part of VoidTypeInterface.
    long attr;

  public:
    Test_inVoidType_impl(
        ccm::local::Test_impl* component_impl);
    virtual ~Test_inVoidType_impl();

    virtual 
    void 
    f1(const long p1) 
    throw(::Components::ccm::local::CCMException);

    virtual 
    long 
    f2() 
    throw(::Components::ccm::local::CCMException);

};

} // /namespace local
} // /namespace ccm

#endif // __FACET__ccm_local_inVoidType__H__

