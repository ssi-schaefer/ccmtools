// -*- mode: C++; c-basic-offset: 3 -*-
//
// $Id$
//
#include "errortrace.h"

namespace wamas {
namespace platform {
namespace utils {

using namespace std;

static const deque<SmartPtr<ErrorNode> > empty_trace;

// --------------------------------------------------------------------
void ErrorTraceImpl::append(const SmartPtr<ErrorNode>& n) {
   trace_.push_back(n);
}

// --------------------------------------------------------------------
ErrorTrace& ErrorTrace::append(const SmartPtr<ErrorNode>& n) {
   if (!ptr())
      eat(new ErrorTraceImpl);
   ptr()->append(n);
   return *this;
}

const deque<SmartPtr<ErrorNode> >& ErrorTrace::trace() const {
   return cptr()? cptr()->trace(): empty_trace;
}

} // /namespace
} // /namespace
} // /namespace
