#include "Debug.h"

#include <set>

namespace CCM_Utils {

typedef std::set<std::string> Levels;

static Levels my_levels_here;
static bool i_do_debug_here;

void
Debug::set_global
  ( bool b )
{
   i_do_debug_here = b;
}

bool
Debug::get_global
  (  )
{
   return i_do_debug_here;
}

void
Debug::add_level
  ( const std::string& l )
{
   my_levels_here.insert ( l );
}

bool
Debug::have_level
  ( const std::string& level )
{
   Levels::const_iterator pos = my_levels_here.find ( level );
   return pos != my_levels_here.end (  );
}

} // /namespace CCM_Utils


