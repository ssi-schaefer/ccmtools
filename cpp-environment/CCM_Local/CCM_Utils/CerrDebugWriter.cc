#include "Debug.h"
#include "CerrDebugWriter.h"

#include <iostream>
#include <cstring>

namespace CCM_Utils {

using namespace std;

CerrDebugWriter* CerrDebugWriter::inst_ = 0;

CerrDebugWriter&
CerrDebugWriter::instance
  (  )
{
  if ( inst_ == 0 ) { inst_ = new CerrDebugWriter (  ); }
  return *inst_;
}

int
CerrDebugWriter::write
  ( const char* file, int line, const string& facility, const string& msg )
{
  // cerr << "[" << file << ":" << line << "] " << msg;
  const char *name = strrchr ( file, '/' );
  cerr << "[" << (name ? name+1 : file) << ":" << line << "] " << msg;
  return 0;
}

bool
CerrDebugWriter::check
  ( const string& facility )
{
  if ( facility.length (  ) == 0 ) {
    return Debug::get_global (  );
  } else {
    return ( Debug::get_global (  ) || Debug::have_level ( facility ) );
  }
}

} // /namespace CCM_Utils


