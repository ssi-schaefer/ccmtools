// -*- mode: C++; c-basic-offset: 3 -*-
//
// $Id$
//
#ifndef wx_utils_error_errortrace_h
#define wx_utils_error_errortrace_h

#include "error.h"

#include <wamas/platform/utils/smartptr.h>
#include <deque>

namespace wamas {
namespace platform {
namespace utils {

class ErrorTraceImpl : public RefCounted {
public:
   void append(const SmartPtr<ErrorNode>&);
   const std::deque<SmartPtr<ErrorNode> >& trace() const { return trace_; }
private:
   std::deque<SmartPtr<ErrorNode> > trace_;
};


/**
   \ingroup utils_error

   \brief A list (possibly nested - a tree) of Error objects, each
   carrying descriptions of particular errors that have happened along
   execution of a code path. Supposed to be thrown as exception. For a
   more detailed description and examples see the documentation of
   class Error.

 */
class ErrorTrace : private SmartPtr<ErrorTraceImpl> {
public:
   ErrorTrace() {}
   ErrorTrace(const ErrorTrace&);
   ErrorTrace& operator=(const ErrorTrace&);
   ErrorTrace& append(const SmartPtr<ErrorNode>&);
   int n() const { return trace().size(); }
   const std::deque<SmartPtr<ErrorNode> >& trace() const;
};

inline ErrorTrace::ErrorTrace(const ErrorTrace& t)
: SmartPtr<ErrorTraceImpl>(t) {}

inline ErrorTrace& ErrorTrace::operator=(const ErrorTrace& t) {
   SmartPtr<ErrorTraceImpl>::operator=(t);
   return *this;
}

} // /namespace
} // /namespace
} // /namespace

#endif
