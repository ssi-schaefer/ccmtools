//
// $Id$
//
#ifndef wx_utils_DebugWriter_h
#define wx_utils_DebugWriter_h

#include <string>

namespace wamas {
namespace platform {
namespace utils {


class DebugWriter {
public:

  // Debug levels
  enum {
    Emerg,
    Alert,
    Error,
    Notify,
    Debug,
    Trace
  };

  virtual int write(const char* file, int line,
                    const std::string& facility,
                    const int level,
                    const std::string& msg)=0;
  virtual bool check(const std::string& facility)=0;
  virtual ~DebugWriter() {};
};

} // /namespace
} // /namespace
} // /namespace


#endif // end of wx_utils_DebugWriter_h
