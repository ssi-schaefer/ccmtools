// -*- mode: C++; c-basic-offset: 3 -*-
// 
// $Id$
//
#ifndef wx_utils_error_errorout_ostream_h
#define wx_utils_error_errorout_ostream_h

#include <iosfwd>
#include <wamas/platform/utils/smartptr.h>

namespace wamas {
namespace platform {
namespace utils {

class Error;
class ErrorNode;
class ErrorTrace;

void output(const SmartPtr<ErrorNode>&, std::ostream&, int offset=0);
void output(const ErrorTrace&, std::ostream&, int offset=0);
void output(const Error&, std::ostream&, int offset=0);

inline std::ostream& operator<<(std::ostream& s, const ErrorTrace& e) {
   output(e, s);
   return s;
}
inline std::ostream& operator<<(std::ostream& s, const Error& e) {
   output(e, s);
   return s;
}

} // /namespace
} // /namespace
} // /namespace

#endif
