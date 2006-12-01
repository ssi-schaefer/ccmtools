
/***
 * This file was automatically generated by CCM Tools
 * <http://ccmtools.sourceforge.net/>
 *
 * Test component business logic definition.
 * 
 * // TODO: WRITE YOUR DESCRIPTION HERE! 
 *
 * @author
 * @version 
 ***/

#ifndef __COMPONENT_ccm_local_component_Test_Test_IMPL__H__
#define __COMPONENT_ccm_local_component_Test_Test_IMPL__H__

#include <ccm/local/Test_share.h>

namespace ccm {
namespace local {

/**
 * This class implements a component's equivalent and supported interfaces
 * as well as component attributes. Additionally, session component callback 
 * methods must be implemented.  
 *
 * Test component class 
 *
 * // TODO: WRITE YOUR DESCRIPTION HERE! 
 *
 * @author
 * @version 
 **/
class Test_impl
    : virtual public CCM_Test
{
  private:
  
  // This attribute is accessed by explicite set and get methods
  // which are part of VoidTypeInterface.
  long attr_;
		
  public:
    CCM_Test_Context* ctx;

    Test_impl();
    virtual ~Test_impl();

    short 
    fb1(
        const short p1,
        short& p2,
        short& p3) 
    throw(::Components::ccm::local::CCMException);

    long 
    fb2(
        const long p1,
        long& p2,
        long& p3) 
    throw(::Components::ccm::local::CCMException);

    unsigned short 
    fb3(
        const unsigned short p1,
        unsigned short& p2,
        unsigned short& p3) 
    throw(::Components::ccm::local::CCMException);

    unsigned long 
    fb4(
        const unsigned long p1,
        unsigned long& p2,
        unsigned long& p3) 
    throw(::Components::ccm::local::CCMException);

    float 
    fb5(
        const float p1,
        float& p2,
        float& p3) 
    throw(::Components::ccm::local::CCMException);

    double 
    fb6(
        const double p1,
        double& p2,
        double& p3) 
    throw(::Components::ccm::local::CCMException);

    char 
    fb7(
        const char p1,
        char& p2,
        char& p3) 
    throw(::Components::ccm::local::CCMException);

    std::string 
    fb8(
        const std::string& p1,
        std::string& p2,
        std::string& p3) 
    throw(::Components::ccm::local::CCMException);

    bool 
    fb9(
        const bool p1,
        bool& p2,
        bool& p3) 
    throw(::Components::ccm::local::CCMException);

    unsigned char 
    fb10(
        const unsigned char p1,
        unsigned char& p2,
        unsigned char& p3) 
    throw(::Components::ccm::local::CCMException);

    ccm::local::Color 
    fu1(
        const ccm::local::Color& p1,
        ccm::local::Color& p2,
        ccm::local::Color& p3) 
    throw(::Components::ccm::local::CCMException);

    ccm::local::Person 
    fu2(
        const ccm::local::Person& p1,
        ccm::local::Person& p2,
        ccm::local::Person& p3) 
    throw(::Components::ccm::local::CCMException);

    ccm::local::Address 
    fu3(
        const ccm::local::Address& p1,
        ccm::local::Address& p2,
        ccm::local::Address& p3) 
    throw(::Components::ccm::local::CCMException);

    ccm::local::LongList 
    fu4(
        const ccm::local::LongList& p1,
        ccm::local::LongList& p2,
        ccm::local::LongList& p3) 
    throw(::Components::ccm::local::CCMException);

    ccm::local::StringList 
    fu5(
        const ccm::local::StringList& p1,
        ccm::local::StringList& p2,
        ccm::local::StringList& p3) 
    throw(::Components::ccm::local::CCMException);

    ccm::local::PersonList 
    fu6(
        const ccm::local::PersonList& p1,
        ccm::local::PersonList& p2,
        ccm::local::PersonList& p3) 
    throw(::Components::ccm::local::CCMException);

    ccm::local::time_t 
    fu7(
        const ccm::local::time_t& t1,
        ccm::local::time_t& t2,
        ccm::local::time_t& t3) 
    throw(::Components::ccm::local::CCMException);

    void 
    fv1(const long p1) 
    throw(::Components::ccm::local::CCMException);

    long 
    fv2() 
    throw(::Components::ccm::local::CCMException);

    // CCM callback methods
    virtual void set_session_context(::Components::ccm::local::SessionContext* ctx)
        throw(::Components::ccm::local::CCMException);
    virtual void ccm_activate()
        throw(::Components::ccm::local::CCMException);
    virtual void ccm_passivate()
        throw(::Components::ccm::local::CCMException);
    virtual void ccm_remove()
        throw(::Components::ccm::local::CCMException);
};

} // /namespace local
} // /namespace ccm

#endif

