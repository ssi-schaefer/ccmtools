//
// $Id$
//
#ifndef wx_utils_DebugWriter_h
#define wx_utils_DebugWriter_h

#include <string>

namespace WX {
namespace Utils {


class DebugWriter {
public:
  virtual int write(const char* file, int line,
                    const std::string& facility,
                    const std::string& msg)=0;
  virtual bool check(const std::string& facility)=0;
  virtual ~DebugWriter() {};
};

} // /namespace
} // /namespace


#endif // end of wx_utils_DebugWriter_h
