
//==============================================================================
// Hello_mirror - business logic implementation
//==============================================================================

#include <iostream>
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
  ctx = dynamic_cast<CCM_Hello_mirror_Context*>(context);
}

void
CCM_Hello_mirror_impl::ccm_activate (  )
  throw ( localComponents::CCMException )
{
  DEBUGNL ( " CCM_Hello_mirror_impl->ccm_activate (  )" );
  cout << "==== Begin of Test Case =========================" << endl;
  CCM_Utils::SmartPtr<CCM_Console> console = ctx->get_connection_console_mirror();
  
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



