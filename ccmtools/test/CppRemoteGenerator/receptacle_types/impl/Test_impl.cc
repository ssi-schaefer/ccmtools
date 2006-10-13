
/***
 * This file was automatically generated by CCM Tools version 0.5.3-pre3
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
#include <wx/utils/debug.h>

#include "Test_impl.h"
#include "Test_inBasicType_impl.h"
#include "Test_inUserType_impl.h"
#include "Test_inVoidType_impl.h"

namespace ccm {
namespace local {

using namespace std;
using namespace wx::utils;

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
   // ------------------------------------------------------------------
    // Void Type Check
    // ------------------------------------------------------------------
    {
      cout << "Void Type Check ...";

      SmartPtr<CCM_VoidTypeInterface> out = ctx->get_connection_outVoidType();

      {
	long value = 7;
	long result;
	out->f1(value);
	result = out->f2();
	assert(value == result);
      }

      cout << "OK" << endl;
    }


    // ------------------------------------------------------------------
    // Basic Types Check
    // ------------------------------------------------------------------
    {
      cout << "Basic Types Check ...";
      SmartPtr<CCM_BasicTypeInterface> out = ctx->get_connection_outBasicType();
      {
	short short_2=3, short_3, short_r;
	short_r = out->f1(7,short_2, short_3);
	assert(short_2 == 7);
	assert(short_3 == 3);
	assert(short_r == 3+7);
      }

      {
	long long_2=3, long_3, long_r;
	long_r = out->f2(7,long_2, long_3);
	assert(long_2 == 7);
	assert(long_3 == 3);
	assert(long_r == 3+7);
      }

      {
	unsigned short ushort_2=3, ushort_3, ushort_r;
	ushort_r = out->f3(7,ushort_2, ushort_3);
	assert(ushort_2 == 7);
	assert(ushort_3 == 3);
	assert(ushort_r == 3+7);
      }

      {
	unsigned long ulong_2=3, ulong_3, ulong_r;
	ulong_r = out->f4(7,ulong_2, ulong_3);
	assert(ulong_2 == 7);
	assert(ulong_3 == 3);
	assert(ulong_r == 3+7);
      }

      {
	float float_2=3.0, float_3, float_r;
	float_r = out->f5(7.0,float_2, float_3);
	assert(abs(float_2 - 7.0) < 0.001);
	assert(abs(float_3 - 3.0) < 0.001);
	assert(abs(float_r - (3.0+7.0)) < 0.001);
      }

      {
	double double_2=3.0, double_3, double_r;
	double_r = out->f6(7.0,double_2, double_3);
	assert(abs(double_2 - 7.0) < 0.000001);
	assert(abs(double_3 - 3.0) < 0.000001);
	assert(abs(double_r - (3.0+7.0)) < 0.000001);
      }

      {
	char char_2=3, char_3, char_r;
	char_r = out->f7(7,char_2, char_3);
	assert(char_2 == 7);
	assert(char_3 == 3);
	assert(char_r == 3+7);
      }

      {
	string string_2 = "drei";
	string string_3;
	string string_r;
	string_r = out->f8("sieben",string_2, string_3);
	assert(string_2 == "sieben");
	assert(string_3 == "drei");
	assert(string_r == "dreisieben");
      }

      {
	bool bool_2=false, bool_3, bool_r;
	bool_r = out->f9(true, bool_2, bool_3);
	assert(bool_2 == true);
	assert(bool_3 == false);
	assert(bool_r == false && true);
      }

      {
	unsigned char octet_2=3, octet_3, octet_r;
	octet_r = out->f10(7,octet_2, octet_3);
	assert(octet_2 == 7);
	assert(octet_3 == 3);
	assert(octet_r == 3+7);
      }	

      cout << "OK" << endl;
    }


    // ------------------------------------------------------------------
    // User Types Check
    // ------------------------------------------------------------------
    {
      cout << "User Types Check ...";

      SmartPtr<CCM_UserTypeInterface> out = ctx->get_connection_outUserType();

      // test case: enum Color {red, green, blue, black, orange};
      try { 
	const ccm::local::Color p1 = ccm::local::red;
	ccm::local::Color p2 = ccm::local::green;
	ccm::local::Color p3;
	ccm::local::Color result;
	result = out->f1(p1, p2, p3);
	assert(p2 == ccm::local::red);
	assert(p3 == ccm::local::green);
	assert(result == ccm::local::orange);
      }
      catch(::Components::ccm::local::Exception& e) {
	cerr << e.what() << endl;
      }

      // test case: struct Person { long id; string name; };
      try { 
	Person p1, p2, p3, result;
	p1.name = "Egon"; 
	p1.id = 3;
	p2.name = "Andrea"; 
	p2.id = 23;
	result = out->f2(p1,p2,p3);
	assert(p3.name == "Andrea");
	assert(p3.id == 23); 
	assert(p2.name == "Egon");
	assert(p2.id == 3); 
	assert(result.name  == "EgonAndrea");
	assert(result.id == 26);
      }
      catch(::Components::ccm::local::Exception& e) {
	cerr << e.what() << endl;
      }

      // test case: struct Address{ long id; string name; Person resident };
      try { 
	Address p1, p2, p3, result;
	Person person;
	
	p1.street = "Waltendorf";   
	p1.number = 7;
	person.name = "Egon";   
	person.id   = 3;
	p1.resident = person;
	
	p2.street   = "Petersgasse"; 
	p2.number   =17;
	person.name = "Andrea";   
	person.id   = 23;
	p2.resident = person;
	
	result = out->f3(p1,p2,p3);
      
	assert(p3.street == "Petersgasse");
	assert(p3.number == 17);
	assert(p3.resident.name == "Andrea");
	assert(p3.resident.id == 23);
	
	assert(p2.street == "Waltendorf");
	assert(p2.number == 7);
	assert(p2.resident.name == "Egon");
	assert(p2.resident.id == 3);
	
	assert(result.street == "WaltendorfPetersgasse");
	assert(result.number == 24);
	assert(result.resident.name == "EgonAndrea");
	assert(result.resident.id == 26);
      }
      catch(::Components::ccm::local::Exception& e) {
	cerr << e.what() << endl;
      }
      
      // Test case: typedef sequence<list> LongList;
      try { 
	LongList p1, p2, p3, result;
	const unsigned int size = 11;
	for(int i=0;i<(int)size;i++) {
	  p1.push_back(i);
	  p2.push_back(i+i);
	}
	
	result = out->f4(p1, p2, p3);
	
	assert(result.size() == size);
	for(unsigned int i=0;i<result.size();i++) {
	  long p = result.at(i);
	  assert(p == (long)i);
	}
	
	assert(p2.size() == size);
	for(unsigned int i=0;i<p2.size();i++) {
	  long p = p2.at(i);
	  assert(p == (long)i);
	}
	
	assert(p3.size() == size);
	for(unsigned int i=0;i<p3.size();i++) {
	  long p = p3.at(i);
	  assert(p == (long)(i+i));
	}
      }
      catch(::Components::ccm::local::Exception& e) {
	cerr << e.what() << endl;
      }

      // Test case: typedef sequence<string> StringList;
      try { 
	StringList p1, p2, p3, result;
	const unsigned int size = 7;
	for(int i=0;i<(int)size;i++) {
	  p1.push_back("one");
	  p2.push_back("two");
	}
	
	result = out->f5(p1, p2, p3);

	assert(result.size() == size);
	for(unsigned int i=0;i<result.size();i++) {
	  string p = result.at(i);
	  assert(p == "Test");
	}
	
	assert(p2.size() == size);
	for(unsigned int i=0;i<p2.size();i++) {
	  string p = p2.at(i);
	  assert(p == "one");
	}
	
	assert(p3.size() == size);
	for(unsigned int i=0;i<p3.size();i++) {
	  string p = p3.at(i);
	  assert(p == "two");
	}
      }
      catch(::Components::ccm::local::Exception& e) {
	cerr << e.what() << endl;
      }
      
      // Test case: typedef sequence<Person> PersonList;
      try { 
	PersonList pl1, pl2, pl3, result;
	const unsigned int size = 8;
	for(int i=0;i<(int)size;i++) {
	  Person p1, p2;
	  p1.name = "Egon";
	  p1.id = i;
	  pl1.push_back(p1);
	  p2.name = "Andrea";
	  p2.id = i+i;
	  pl2.push_back(p2);
	}
	result = out->f6(pl1, pl2, pl3);
	assert(result.size() == size);
	for(unsigned int i=0;i<result.size();i++) {
	  Person p = result.at(i);
	  assert(p.id == (long)i);
	  assert(p.name == "Test");
	}
	assert(pl2.size() == size);
	for(unsigned int i=0;i<pl2.size();i++) {
	  Person p = pl2.at(i);
	  assert(p.id == (long)i);
	  assert(p.name == "Egon");
	}
	assert(pl3.size() == size);
	for(unsigned int i=0;i<pl3.size();i++) {
	  Person p = pl3.at(i);
	  assert(p.id == (long)(i+i));
	  assert(p.name == "Andrea");
	}
      }
      catch(::Components::ccm::local::Exception& e) {
	cerr  << e.what() << endl;
      }

      // test case: typedef long time_t;
      try { 
	const ccm::local::time_t p1 = 7;
	ccm::local::time_t p2 = 3, p3, result;
	result = out->f7(p1, p2, p3);
	assert(p2 == 7);
	assert(p3 == 3);
	assert(result == 3+7);
      }
      catch(::Components::ccm::local::Exception& e) {
	cerr << e.what() << endl;
      }

      cout << " OK" << endl;
    }
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
// ccm::local::CCM_BasicTypeInterface facet implementation
//==============================================================================

ccm::local::CCM_BasicTypeInterface*
Test_impl::get_inBasicType()
{
    Test_inBasicType_impl* facet = new Test_inBasicType_impl(this);
    return dynamic_cast< ccm::local::CCM_BasicTypeInterface*>(facet);
}

//==============================================================================
// ccm::local::CCM_UserTypeInterface facet implementation
//==============================================================================

ccm::local::CCM_UserTypeInterface*
Test_impl::get_inUserType()
{
    Test_inUserType_impl* facet = new Test_inUserType_impl(this);
    return dynamic_cast< ccm::local::CCM_UserTypeInterface*>(facet);
}

//==============================================================================
// ccm::local::CCM_VoidTypeInterface facet implementation
//==============================================================================

ccm::local::CCM_VoidTypeInterface*
Test_impl::get_inVoidType()
{	                             
    Test_inVoidType_impl* facet = new Test_inVoidType_impl(this);
    return dynamic_cast< ccm::local::CCM_VoidTypeInterface*>(facet);
}

} // /namespace local
} // /namespace ccm

