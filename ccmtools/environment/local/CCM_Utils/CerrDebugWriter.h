#ifndef __CCM__UTILS__CERRDEBUGWRITER__H__
#define __CCM__UTILS__CERRDEBUGWRITER__H__

#include "DebugWriter.h"

namespace CCM_Utils {

class CerrDebugWriter : public DebugWriter {
public:
  int write ( const char* file, int line,
              const std::string& facility,
              const std::string& msg );
  bool check ( const std::string& facility );
  static CerrDebugWriter& instance (  );   // ensures singleton
private:
  static CerrDebugWriter* inst_;           // the instance of CerrDebugWriter
private:
  CerrDebugWriter (  ) {};
};

} // /namespace CCM_Utils


#endif // end of __CCM__UTILS__CERRDEBUGWRITER__H__


