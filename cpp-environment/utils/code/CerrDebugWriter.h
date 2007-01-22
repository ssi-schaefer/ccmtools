//
// $Id$
//
#ifndef wx_utils_CerrDebugWriter_h
#define wx_utils_CerrDebugWriter_h

#include "DebugWriter.h"

namespace wamas {
namespace platform {
namespace utils {


class CerrDebugWriter : public DebugWriter {
public:
  int write(const char* file, int line,
            const std::string& facility,
            const int level,
            const std::string& msg);
  bool check(const std::string& facility);
  static CerrDebugWriter& instance();   // ensures singleton
private:
  static CerrDebugWriter* inst_;        // the instance of CerrDebugWriter
private:
  CerrDebugWriter() {};
};

} // /namespace
} // /namespace
} // /namespace


#endif // end of wx_utils_CerrDebugWriter_h
