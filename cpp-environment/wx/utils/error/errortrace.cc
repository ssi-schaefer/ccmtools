// -*- mode: C++; c-basic-offset: 3 -*-
// 
// $Id$
//
#include "errortrace.h"

namespace WX {
namespace Utils {

using namespace std;

static const deque<Error> empty_trace;

// --------------------------------------------------------------------
void ErrorTraceImpl::append(const Error& e) {
   trace_.push_back(e);
}

// --------------------------------------------------------------------
ErrorTrace& ErrorTrace::append(const Error& e) {
   if (!ptr())
      eat(new ErrorTraceImpl);
   ptr()->append(e);
   return *this;
}

const deque<Error>& ErrorTrace::trace() const {
   return cptr()? cptr()->trace(): empty_trace;
}

} // /namespace
} // /namespace
