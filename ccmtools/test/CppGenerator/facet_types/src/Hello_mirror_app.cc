
//==============================================================================
// Hello_mirror - business logic implementation
//==============================================================================

#include <iostream>
#include <WX/Utils/debug.h>

#include "Hello_mirror_app.h"

using namespace std;
using namespace WX::Utils;

namespace CCM_Local {
namespace CCM_Session_Hello_mirror {


//==============================================================================
// business logic functionality
//==============================================================================





//==============================================================================
// class implementation
//==============================================================================

CCM_Hello_mirror_impl::CCM_Hello_mirror_impl (  )
{
  DEBUGNL ( "+CCM_Hello_mirror_impl->CCM_Hello_mirror_impl (  )" );
}

CCM_Hello_mirror_impl::~CCM_Hello_mirror_impl (  )
{
  DEBUGNL ( "-CCM_Hello_mirror_impl->~CCM_Hello_mirror_impl (  )" );
}




void
CCM_Hello_mirror_impl::set_session_context ( LocalComponents::SessionContext* context )
  throw ( LocalComponents::CCMException )
{
  DEBUGNL ( " CCM_Hello_mirror_impl->set_session_context (  )" );
  ctx = dynamic_cast<CCM_Hello_mirror_Context*>(context);
}

void
CCM_Hello_mirror_impl::ccm_activate (  )
  throw ( LocalComponents::CCMException )
{
  DEBUGNL ( " CCM_Hello_mirror_impl->ccm_activate (  )" );
  cout << "==== Begin of Test Case =========================" << endl;
  WX::Utils::SmartPtr<CCM_Console> console = ctx->get_connection_console_mirror();

  /* Test Case for: typedef long time_t; */
  CCM_Local::time_t time_t_2 = 3, time_t_3, time_t_r;
  time_t_r = console.ptr()->foo1(7,time_t_2, time_t_3);
  assert(time_t_2 == 7);
  assert(time_t_3 == 3);
  assert(time_t_r == 3+7);

  /* Test Case for: enum Color {red, green, blue, black, orange}; */
  Color Color_2,Color_3, Color_r;
  Color_2 = Color(blue);
  Color_r = console.ptr()->foo2(Color(red),Color_2, Color_3);
  assert(Color_2 == Color(red));
  assert(Color_3 == Color(blue));
  assert(Color_r == Color(red));

  /* Test Case for: struct Value { string s; double dd; }; */
  Value Value_1, Value_2, Value_3, Value_r;
  Value_1.s = "a"; Value_1.dd = 1.0;
  Value_2.s = "b"; Value_2.dd = 2.0;
  Value_r = console.ptr()->foo3(Value_1,Value_2,Value_3);
  assert(Value_3.s == "b");
  assert(Value_2.s == "a");
  assert(Value_r.s == "ab");

  /* Test Case for: typedef sequence<Value> map; */
  map map_1, map_2, map_3, map_r;
  for(int i=0;i<5;i++) {
    Value v1, v2;
    v1.s = "1";
    v1.dd = (double)i;
    map_1.push_back(v1);
    v2.s = "2";
    v2.dd = (double)(i+i);
    map_2.push_back(v2);
  }
  map_r = console.ptr()->foo4(map_1,map_2,map_3);
  for(int i=0;i<map_r.size();i++) {
    Value v = map_r.at(i);
    assert((int)v.dd == i);
  }
  for(int i=0;i<map_2.size();i++) {
    Value v = map_2.at(i);
    assert((int)v.dd == i);
  }
  for(int i=0;i<map_3.size();i++) {
    Value v = map_3.at(i);
    assert((int)v.dd == i+i);
  }

  /* Test Case for: typedef double doubleArray[10]; */
  doubleArray Array_1(10), Array_2(10), Array_3(10), Array_r(10);
  for(int i=0;i<10;i++) {
    Array_1.at(i) = i;
    Array_2.at(i) = i+i;
  }
  Array_r = console.ptr()->foo5(Array_1,Array_2,Array_3);
  for(int i=0;i<10;i++) {
    assert(Array_r.at(i) == i);
    assert(Array_2.at(i) == i);
    assert(Array_3.at(i) == i+i);
  }

  /* basic types test cases */
  short short_2=3, short_3, short_r;
  short_r = console.ptr()->println1(7,short_2, short_3);
  assert(short_2 == 7);
  assert(short_3 == 3);
  assert(short_r == 3+7);

  long long_2=3, long_3, long_r;
  long_r = console.ptr()->println2(7,long_2, long_3);
  assert(long_2 == 7);
  assert(long_3 == 3);
  assert(long_r == 3+7);

  unsigned short ushort_2=3, ushort_3, ushort_r;
  ushort_r = console.ptr()->println3(7,ushort_2, ushort_3);
  assert(ushort_2 == 7);
  assert(ushort_3 == 3);
  assert(ushort_r == 3+7);

  unsigned long ulong_2=3, ulong_3, ulong_r;
  ulong_r = console.ptr()->println4(7,ulong_2, ulong_3);
  assert(ulong_2 == 7);
  assert(ulong_3 == 3);
  assert(ulong_r == 3+7);

  float float_2=3.0, float_3, float_r;
  float_r = console.ptr()->println5(7.0,float_2, float_3);
  assert(float_2 == 7.0);
  assert(float_3 == 3.0);
  assert(float_r == 3.0+7.0);

  double double_2=3.0, double_3, double_r;
  double_r = console.ptr()->println6(7.0,double_2, double_3);
  assert(double_2 == 7.0);
  assert(double_3 == 3.0);
  assert(double_r == 3.0+7.0);

  char char_2=3, char_3, char_r;
  char_r = console.ptr()->println7(7,char_2, char_3);
  assert(char_2 == 7);
  assert(char_3 == 3);
  assert(char_r == 3+7);

  string string_2="drei", string_3, string_r;
  string_r = console.ptr()->println8("sieben",string_2, string_3);
  cout << string_2 << endl;
  cout << string_3 << endl;
  cout << string_r << endl;
  assert(string_2 == "sieben");
  assert(string_3 == "drei");
  assert(string_r == "dreisieben");

  bool bool_2=false, bool_3, bool_r;
  bool_r = console.ptr()->println9(true, bool_2, bool_3);
  assert(bool_2 == true);
  assert(bool_3 == false);
  assert(bool_r == false && true);

  unsigned char uchar_2=3, uchar_3, uchar_r;
  uchar_r = console.ptr()->println10(7,uchar_2, uchar_3);
  assert(uchar_2 == 7);
  assert(uchar_3 == 3);
  assert(uchar_r == 3+7);

  cout << "==== End of Test Case ===========================" << endl;
}

void
CCM_Hello_mirror_impl::ccm_passivate (  )
  throw ( LocalComponents::CCMException )
{
  DEBUGNL ( " CCM_Hello_mirror_impl->ccm_passivate (  )" );
}

void
CCM_Hello_mirror_impl::ccm_remove (  )
  throw ( LocalComponents::CCMException )
{
  DEBUGNL ( " CCM_Hello_mirror_impl->ccm_remove (  )" );
}

} // /namespace CCM_Session_Hello_mirror
} // /namespace CCM_Local



