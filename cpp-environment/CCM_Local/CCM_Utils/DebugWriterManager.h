#ifndef __CCM__UTILS__DEBUGWRITERMANAGER__H__
#define __CCM__UTILS__DEBUGWRITERMANAGER__H__

#include "CerrDebugWriter.h"

namespace CCM_Utils {

class DebugWriterManager {
public:
  DebugWriter& getDebugWriter (  );
  void setDebugWriter ( DebugWriter* debWriter );
  void activate (  );
  void deactivate (  );
  static DebugWriterManager& instance (  );
private:
  static DebugWriterManager*  inst_;
  DebugWriter*  defaultWriter_;
  DebugWriter*  explicitWriter_;
  DebugWriter*  debugWriter_;
private:
  DebugWriterManager (  );
};

} // /namespace CCM_Utils

#endif // end of __CCM__UTILS__DEBUGWRITERMANAGER__H__


