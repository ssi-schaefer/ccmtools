
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

#include <cassert>
#include <iostream>
#include <wamas/platform/utils/debug.h>

#include "Test_impl.h"
#include "Test_inBasicType_impl.h"
#include "Test_inUserType_impl.h"

namespace world {
namespace europe {
namespace austria {
namespace ccm {
namespace local {

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

const short
Test_impl::short_value() const
    throw(::Components::ccm::local::CCMException)
{
    return short_value_;
}

void
Test_impl::short_value(const short value)
  throw(::Components::ccm::local::CCMException)
{
    short_value_ = value;
}

const long
Test_impl::long_value() const
    throw(::Components::ccm::local::CCMException)
{
    return long_value_;
}

void
Test_impl::long_value(const long value)
  throw(::Components::ccm::local::CCMException)
{
    long_value_ = value;
}

const unsigned short
Test_impl::ushort_value() const
    throw(::Components::ccm::local::CCMException)
{
    return ushort_value_;
}

void
Test_impl::ushort_value(const unsigned short value)
  throw(::Components::ccm::local::CCMException)
{
    ushort_value_ = value;
}

const unsigned long
Test_impl::ulong_value() const
    throw(::Components::ccm::local::CCMException)
{
    return ulong_value_;
}

void
Test_impl::ulong_value(const unsigned long value)
  throw(::Components::ccm::local::CCMException)
{
    ulong_value_ = value;
}

const float
Test_impl::float_value() const
    throw(::Components::ccm::local::CCMException)
{
    return float_value_;
}

void
Test_impl::float_value(const float value)
  throw(::Components::ccm::local::CCMException)
{
    float_value_ = value;
}

const double
Test_impl::double_value() const
    throw(::Components::ccm::local::CCMException)
{
    return double_value_;
}

void
Test_impl::double_value(const double value)
  throw(::Components::ccm::local::CCMException)
{
    double_value_ = value;
}

const char
Test_impl::char_value() const
    throw(::Components::ccm::local::CCMException)
{
    return char_value_;
}

void
Test_impl::char_value(const char value)
  throw(::Components::ccm::local::CCMException)
{
    char_value_ = value;
}

const std::string
Test_impl::string_value() const
    throw(::Components::ccm::local::CCMException)
{
    return string_value_;
}

void
Test_impl::string_value(const std::string value)
  throw(::Components::ccm::local::CCMException)
{
    string_value_ = value;
}

const bool
Test_impl::boolean_value() const
    throw(::Components::ccm::local::CCMException)
{
    return boolean_value_;
}

void
Test_impl::boolean_value(const bool value)
  throw(::Components::ccm::local::CCMException)
{
    boolean_value_ = value;
}

const unsigned char
Test_impl::octet_value() const
    throw(::Components::ccm::local::CCMException)
{
    return octet_value_;
}

void
Test_impl::octet_value(const unsigned char value)
  throw(::Components::ccm::local::CCMException)
{
    octet_value_ = value;
}

const world::europe::austria::ccm::local::Color
Test_impl::color_value() const
    throw(::Components::ccm::local::CCMException)
{
    return color_value_;
}

void
Test_impl::color_value(const world::europe::austria::ccm::local::Color value)
  throw(::Components::ccm::local::CCMException)
{
    color_value_ = value;
}

const world::europe::austria::ccm::local::Person
Test_impl::person_value() const
    throw(::Components::ccm::local::CCMException)
{
    return person_value_;
}

void
Test_impl::person_value(const world::europe::austria::ccm::local::Person value)
  throw(::Components::ccm::local::CCMException)
{
    person_value_ = value;
}

const world::europe::austria::ccm::local::Address
Test_impl::address_value() const
    throw(::Components::ccm::local::CCMException)
{
    return address_value_;
}

void
Test_impl::address_value(const world::europe::austria::ccm::local::Address value)
  throw(::Components::ccm::local::CCMException)
{
    address_value_ = value;
}

const world::europe::austria::ccm::local::LongList
Test_impl::longList_value() const
    throw(::Components::ccm::local::CCMException)
{
    return longList_value_;
}

void
Test_impl::longList_value(const world::europe::austria::ccm::local::LongList value)
  throw(::Components::ccm::local::CCMException)
{
    longList_value_ = value;
}

const world::europe::austria::ccm::local::StringList
Test_impl::stringList_value() const
    throw(::Components::ccm::local::CCMException)
{
    return stringList_value_;
}

void
Test_impl::stringList_value(const world::europe::austria::ccm::local::StringList value)
  throw(::Components::ccm::local::CCMException)
{
    stringList_value_ = value;
}

const world::europe::austria::ccm::local::PersonList
Test_impl::personList_value() const
    throw(::Components::ccm::local::CCMException)
{
    return personList_value_;
}

void
Test_impl::personList_value(const world::europe::austria::ccm::local::PersonList value)
  throw(::Components::ccm::local::CCMException)
{
    personList_value_ = value;
}

const world::europe::austria::ccm::local::time_t
Test_impl::time_t_value() const
    throw(::Components::ccm::local::CCMException)
{
    return time_t_value_;
}

void
Test_impl::time_t_value(const world::europe::austria::ccm::local::time_t value)
  throw(::Components::ccm::local::CCMException)
{
    time_t_value_ = value;
}

void
Test_impl::set_session_context(
    ::Components::ccm::local::SessionContext* context)
    throw(::Components::ccm::local::CCMException)
{
    ctx = dynamic_cast<CCM_Test_Context*>(context);
}

void
Test_impl::ccm_activate()
    throw(::Components::ccm::local::CCMException)
{
   cout << "==== Begin Test Case ====================================" << endl;

    // ---------------------------------------------------------------------
    // Receptacle Attribute Test Cases
    // ---------------------------------------------------------------------

    {
      cout << "Receptacle Attributes (Basic Types) Test...";
      SmartPtr<CCM_BasicTypeInterface> outBasicType =
        ctx->get_connection_outBasicType();

      {
        short value = -7;
        short result;
        outBasicType->short_value(value);
        result = outBasicType->short_value();
        assert(value == result);
      }

      {
        long value = -7777;
        long result;
        outBasicType->long_value(value);
        result = outBasicType->long_value();
        assert(result == value);
      }

      {
        unsigned short value = 7;
        unsigned short result;
        outBasicType->ushort_value(value);
        result = outBasicType->ushort_value();
        assert(result == value);
      }

      {
        unsigned long value = 7777;
        unsigned long result;
        outBasicType->ulong_value(value);
        result = outBasicType->ulong_value();
        assert(result == value);
      }

      {
        float value = -77.77;
        float result;
        outBasicType->float_value(value);
        result = outBasicType->float_value();
        assert(result == value);
      }

      {
        double value = -77.7777;
        double result;
        outBasicType->double_value(value);
        result = outBasicType->double_value();
        assert(result == value);
      }

      {
        char value = 'x';
        char result;
        outBasicType->char_value(value);
        result = outBasicType->char_value();
        assert(result == value);
      }

      {
        string value = "0123456789";
        string result;
        outBasicType->string_value(value);
        result = outBasicType->string_value();
        assert(value == result);
      }

      {
        bool value = true;
        bool result;
        outBasicType->boolean_value(value);
        result = outBasicType->boolean_value();
        assert(result == value);
      }

      {
        unsigned char value = 0xff;
        unsigned char result;
        outBasicType->octet_value(value);
        result = outBasicType->octet_value();
        assert(result == value);
      }
      cout << "OK!" << endl;
    }

    {
      cout << "Receptacle Attributes (User Types) Test...";
      SmartPtr<CCM_UserTypeInterface> outUserType =
        ctx->get_connection_outUserType();
      {
        // enum Color {red, green, blue, black, orange}
        Color value = blue;
        Color result;
        outUserType->color_value(value);        
        result = outUserType->color_value();

        assert(result == value);
      }

      {
        // struct Person { long id; string name; }
        Person value;
        Person result;
        value.name = "Egon";   
        value.id = 3;
        outUserType->person_value(value);
        result = outUserType->person_value();

        assert(result.name == value.name);
        assert(result.id == value.id);
      }

      {
        // struct Address { string street; long number; Person resident; }
        Address value;
        Address result;
        Person person;
        value.street = "Waltendorf";   
        value.number = 7;
        person.name = "Egon";   
        person.id   = 3;
        value.resident = person;
        outUserType->address_value(value);
        result = outUserType->address_value();

        assert(result.street == value.street);
        assert(result.number == value.number);
        assert(result.resident.name == value.resident.name);
        assert(result.resident.id == value.resident.id);
      }

      {
        // typedef sequence<long> LongList
        const int MAX_SIZE = 100; 
        LongList value;
        LongList result;

        value.reserve(MAX_SIZE);
        for(int i=0;i<MAX_SIZE;i++) {
          value.push_back(i);
        }
        outUserType->longList_value(value);
        result = outUserType->longList_value();

        assert((int)result.size() == MAX_SIZE);
        for(int i=0; i < (int)result.size(); i++) {
          assert(result[i] == value[i]);
        }
      }

      {
        // typedef sequence<string> StringList
        const int MAX_SIZE = 100; 
        StringList value;
        StringList result;
        for(int i=0; i < MAX_SIZE; i++) {
          string s = "Egon";
          value.push_back(s);
        }
        outUserType->stringList_value(value);
        result = outUserType->stringList_value();

        assert((int)result.size() == MAX_SIZE);
        for(int i=0; i < (int)result.size(); i++) {
          assert(result[i] == value[i]);
        }
      }

      {
        // typedef sequence<Person> PersonList
        const int MAX_SIZE = 100; 
        PersonList value;
        PersonList result;
        for(int i=0; i < MAX_SIZE; i++) {
          Person p;
          p.name = "Andrea";
          p.id   = i;
          value.push_back(p);
        }

        outUserType->personList_value(value);
        result = outUserType->personList_value();

        assert((int)result.size() == MAX_SIZE);
        for(int i=0; i < (int)result.size(); i++) {
          assert(result[i].name == value[i].name);
          assert(result[i].id == value[i].id);
        }
      }

      {
        // typedef long time_t;
        time_t value = -7777;
        time_t result;
        outUserType->time_t_value(value);
        result = outUserType->time_t_value();
        assert(result == value);
      }

      cout << "OK!" << endl;
    }

   cout << "==== End Test Case =====================================" << endl;
}

void
Test_impl::ccm_passivate()
    throw(::Components::ccm::local::CCMException)
{
    // OPTIONAL : IMPLEMENT ME HERE !
}

void
Test_impl::ccm_remove()
    throw(::Components::ccm::local::CCMException)
{
    // OPTIONAL : IMPLEMENT ME HERE !
}

//==============================================================================
// world::europe::austria::ccm::local::CCM_BasicTypeInterface facet implementation
//==============================================================================

world::europe::austria::ccm::local::CCM_BasicTypeInterface*
Test_impl::get_inBasicType()
{
    Test_inBasicType_impl* facet = new Test_inBasicType_impl(this);
    return dynamic_cast< world::europe::austria::ccm::local::CCM_BasicTypeInterface*>(facet);
}

//==============================================================================
// world::europe::austria::ccm::local::CCM_UserTypeInterface facet implementation
//==============================================================================

world::europe::austria::ccm::local::CCM_UserTypeInterface*
Test_impl::get_inUserType()
{
    Test_inUserType_impl* facet = new Test_inUserType_impl(this);
    return dynamic_cast< world::europe::austria::ccm::local::CCM_UserTypeInterface*>(facet);
}

} // /namespace local
} // /namespace ccm
} // /namespace austria
} // /namespace europe
} // /namespace world

