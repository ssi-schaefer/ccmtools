
//==============================================================================
// Hello_mirror - business logic implementation
//==============================================================================

#include <iostream>
#include <cassert>
#include <CCM_Utils/Debug.h>

#include "Hello_mirror_app.h"

using namespace std;
using namespace CCM_Utils;

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
CCM_Hello_mirror_impl::set_session_context ( localComponents::SessionContext* context )
  throw ( localComponents::CCMException )
{
  DEBUGNL ( " CCM_Hello_mirror_impl->set_session_context (  )" );
  ctx = (CCM_Hello_mirror_Context*) context;
}

void
CCM_Hello_mirror_impl::ccm_activate (  )
  throw ( localComponents::CCMException )
{
  DEBUGNL ( " CCM_Hello_mirror_impl->ccm_activate (  )" );

  cout << "==== Begin of Test Case =========================" << endl;
  CCM_Utils::SmartPtr<CCM_Console> console = ctx->get_connection_console_mirror();

  /* 
   * Test Case for: typedef long time_t;
   */
  CCM_Local::time_t time_t_2 = 3, time_t_3, time_t_r;
  time_t_r = console.ptr()->foo1(7,time_t_2, time_t_3);
  assert(time_t_2 == 7);
  assert(time_t_3 == 3);
  assert(time_t_r == 3+7);

  /*
   * Test Case for: enum Color {red, green, blue, black, orange};
   */
  Color Color_2,Color_3, Color_r;
  Color_2 = Color(blue);
  Color_r = console.ptr()->foo2(Color(red),Color_2, Color_3);
  assert(Color_2 == Color(red));
  assert(Color_3 == Color(blue));
  assert(Color_r == Color(red));
    
  /*
   * Test Case for: struct Value { string s; double dd; };
   */
  Value Value_1, Value_2, Value_3, Value_r;
  Value_1.s = "a"; Value_1.dd = 1.0;
  Value_2.s = "b"; Value_2.dd = 2.0;
  Value_r = console.ptr()->foo3(Value_1,Value_2,Value_3);
  assert(Value_3.s == "b");
  assert(Value_2.s == "a");
  assert(Value_r.s == "ab");

  /* 
   * Test Case for: typedef sequence<Value> map;
   */
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

  /*
   *  Test Case for: typedef double doubleArray[10];
   */
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

 cout << "==== End of Test Case ===========================" << endl;
}

void
CCM_Hello_mirror_impl::ccm_passivate (  )
  throw ( localComponents::CCMException )
{
  DEBUGNL ( " CCM_Hello_mirror_impl->ccm_passivate (  )" );
}

void
CCM_Hello_mirror_impl::ccm_remove (  )
  throw ( localComponents::CCMException )
{
  DEBUGNL ( " CCM_Hello_mirror_impl->ccm_remove (  )" );
}

} // /namespace CCM_Session_Hello_mirror
} // /namespace CCM_Local



