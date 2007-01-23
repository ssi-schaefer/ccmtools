#include <cmath>
#include <cassert>
#include <iostream>

#include <wamas/platform/utils/smartptr.h>
#include <wamas/platform/utils/Value.h>
#include <wamas/platform/utils/StringValue.h>

#include <world/BasicTypesStructure.h>
#include <world/UserTypesStructure.h>

using namespace std;
using wamas::platform::utils::SmartPtr;
using wamas::platform::utils::Value;
using wamas::platform::utils::StringValue;
using namespace world;


int main(int argc, char** argv)
{
  {
    cout << "check BasicTypesStructure default constructor...";
    BasicTypesStructure s;
    assert(s.shortMember == 0);
    assert(s.longMember == 0L);
    assert(s.ushortMember == 0U);
    assert(s.uLongMember == 0U);
    assert(abs(s.floatMember) < 0.001F);
    assert(abs(s.doubleMember) < 0.0001);
    assert(s.charMember == ' ');
    assert(s.stringMember == "");
    assert(s.booleanMember == false);
    assert(s.octetMember == 0x0);
    assert(s.wcharMember == L' ');
    assert(s.wstringMember ==L"");
    cout << "OK!" << endl;
  }


  {
    cout << "check BasicTypesStructure init constructor...";
	BasicTypesStructure s(-1, -2L, 3U, 4UL, 3.14F, 3.1415, 'e', "CCMTools", true, 0x7f, L'X', L"sf.net");
    assert(s.shortMember == -1);
    assert(s.longMember == -2L);
    assert(s.ushortMember == 3U);
    assert(s.uLongMember == 4UL);
    assert(abs(s.floatMember - 3.14F) < 0.001F);
    assert(abs(s.doubleMember - 3.1415) < 0.0001);
    assert(s.charMember == 'e');
    assert(s.stringMember == "CCMTools");
    assert(s.booleanMember == true);
    assert(s.octetMember == 0x7f);
    assert(s.wcharMember == L'X');
    assert(s.wstringMember == L"sf.net");
    cout << "OK!" << endl;
  }


  {
    cout << "check UserTypesStructure default constructor...";
    UserTypesStructure s;

    assert(s.anyMember.ptr() == NULL);    
    assert(s.typedefMember == 0L);
    assert(s.enumMember == red); // set enum to the first value
    assert(s.structMember.charMember == ' ');
    assert(s.structMember.stringMember == "");
    assert(s.sequenceMember.empty() == true);
    assert(s.interfaceMember == EmptyInterface::SmartPtr());
    cout << "OK!" << endl;
  }


  {
    cout << "check UserTypesStructure init constructor...";
	Value* vp = new StringValue("Jimmy");
	SmartPtr<Value> any(vp);
	TimeStamp ts = 1234L;
	Color c = green;
	BasicTypesStructure b;
	b.stringMember = "HALLO";
	StringSeq seq;
	seq.push_back("This");
	seq.push_back("is");
	seq.push_back("a");
	seq.push_back("test!");
	EmptyInterface* ip = new EmptyInterface();
	SmartPtr<EmptyInterface> iface(ip);
	
	UserTypesStructure s(any, ts, c, b, seq, iface);
	
	assert(s.anyMember.ptr() == vp);    
    assert(s.typedefMember == 1234L);
    assert(s.enumMember == green);
    assert(s.structMember.stringMember == "HALLO");
    assert(s.sequenceMember.at(0) == "This");
    assert(s.sequenceMember.at(1) == "is");
    assert(s.sequenceMember.at(2) == "a");
    assert(s.sequenceMember.at(3) == "test!");
    assert(s.interfaceMember.ptr() == ip);       
    cout << "OK!" << endl;
  }
}
