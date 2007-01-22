
#include <cassert>
#include <iostream>

#include <wamas/platform/utils/smartptr.h>
#include <wamas/platform/utils/Value.h>

#include <BasicTypesStructure.h>
#include <UserTypesStructure.h>

using namespace std;
using wamas::platform::utils::SmartPtr;
using wamas::platform::utils::Value;


int main(int argc, char** argv)
{
  {
    cout << "check BasicTypesStructure default constructor...";
    BasicTypesStructure s;
    assert(s.shortMember == 0);
    assert(s.longMember == 0L);
    assert(s.ushortMember == 0U);
    assert(s.uLongMember == 0U);
    assert(s.floatMember == 0.0F);
    assert(s.doubleMember == 0.0);
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
	// TODO
    cout << "OK!" << endl;
  }


  {
    cout << "check UserTypesStructure default constructor...";
    UserTypesStructure s;

    assert(s.anyMember.ptr() == NULL);
    
    assert(s.typedefMember == 0L);

    // TODO: Enum ???

    assert(s.structMember.charMember == ' ');
    assert(s.structMember.stringMember == "");

    assert(s.sequenceMember.empty() == true);
    
    assert(s.interfaceMember == EmptyInterface::SmartPtr());

    cout << "OK!" << endl;
  }


  {
    cout << "check UserTypesStructure init constructor...";
	// TODO
    cout << "OK!" << endl;
  }
}
