#include <cassert>
#include <iostream>

#include <wx/utils/smartptr.h>
#include <wx/utils/Value.h>
#include <wx/utils/ShortValue.h>
#include <wx/utils/StringValue.h>

using namespace std;
using wx::utils::Value;
using wx::utils::ShortValue;
using wx::utils::StringValue;
using wx::utils::SmartPtr;


void printValue(const SmartPtr<wx::utils::Value>& v)
{
  cout <<  "v->typestr = " << v->typestr() << endl;

  if(dynamic_cast<ShortValue*>(v.ptr()))
  {
    SmartPtr<ShortValue> s(dynamic_cast<ShortValue*>(v.ptr()));
    cout << "ShortValue = " << s->value() << endl;   
  }
  else if(dynamic_cast<StringValue*>(v.ptr()))
  {
    SmartPtr<StringValue> s(dynamic_cast<StringValue*>(v.ptr()));
    cout << "StringValue = " << s->value() << endl; 
  }
  //...
}

void testIn(const SmartPtr<wx::utils::Value>& v)
{   
   printValue(v);
}


void testInOut(SmartPtr<wx::utils::Value>& v)
{
  if(dynamic_cast<ShortValue*>(v.ptr()))
  {
    SmartPtr<ShortValue> s(dynamic_cast<ShortValue*>(v.ptr()));
    cout << "ShortValue_IN = " << s->value() << endl;   
    SmartPtr<ShortValue> s_out(new ShortValue(9));
    v = s_out;
  }
}


void testOut(SmartPtr<wx::utils::Value>& v)
{
    SmartPtr<ShortValue> v_out(new ShortValue(11));
    v = v_out;
}


SmartPtr<wx::utils::Value> testResult()
{
  SmartPtr<ShortValue> v(new ShortValue(13));
  return v;
}



int main(int argc, char** argv)
{
  cout << "wx::utils::Value test" << endl;

  {
    ShortValue* s = new ShortValue(7);
    assert(s->value() == 7);
    delete s;
  }

  {
    SmartPtr<ShortValue> v(new ShortValue(7));
    printValue(v);

    SmartPtr<StringValue> s(new StringValue("Hello World"));
    printValue(s);
  }

  {
    SmartPtr<ShortValue> s(new ShortValue(3));
    testIn(s);
  }

  {
    SmartPtr<Value> v(new ShortValue(7));
    testInOut(v);
    SmartPtr<ShortValue> s(dynamic_cast<ShortValue*>(v.ptr()));    
    assert(s->value() == 9);
  }

  {
    SmartPtr<Value> v;
    testOut(v);
    SmartPtr<ShortValue> s(dynamic_cast<ShortValue*>(v.ptr()));    
    assert(s->value() == 11);
  }

  {
    SmartPtr<Value> v = testResult();
    SmartPtr<ShortValue> s(dynamic_cast<ShortValue*>(v.ptr()));    
    assert(s->value() == 13);
  }

  cout << "end of test" << endl;
}



