#ifndef __CCM__UTILS__DEBUGWRITER__H__
#define __CCM__UTILS__DEBUGWRITER__H__

#include <string>

namespace CCM_Utils {

class DebugWriter {
public:
  virtual int write( const char* file, int line,
                     const std::string& facility,
                     const std::string& msg ) = 0;
  virtual bool check ( const std::string& facility ) = 0;
  virtual ~DebugWriter (  ) {};
};

} // /namespace CCM_Utils

#endif // end of __CCM__UTILS__DEBUGWRITER__H__


