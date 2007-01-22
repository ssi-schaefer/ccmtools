//
// $Id$
//
#ifndef wx_utils_DebugWriterMgr_h
#define wx_utils_DebugWriterMgr_h

#include "CerrDebugWriter.h"

namespace wamas {
namespace platform {
namespace utils {

class DebugWriterMgr {
public:
  DebugWriter& getDebugWriter();
  void setDebugWriter(DebugWriter* debWriter);
  void activate();
  void deactivate();
  static DebugWriterMgr& instance();
private:
  static DebugWriterMgr*  inst_;
  DebugWriter*  defaultWriter_;
  DebugWriter*  explicitWriter_;
  DebugWriter*  debugWriter_;
private:
  DebugWriterMgr();
};

} // /namespace
} // /namespace
} // /namespace

#endif // end of wx_utils_DebugWriterMgr_h
