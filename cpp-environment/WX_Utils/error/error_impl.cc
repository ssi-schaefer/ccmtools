// -*- mode: C++; c-basic-offset: 3 -*-
// 
// $Id$
//
#include "error_impl.h"

#include "errortrace.h"
#include "errorout_ostream.h"

#ifdef HAVE_CONFIG_H
# include <config.h>
#endif

// instruct Confix to add the autoconf check AC_CXX_HAVE_SSTREAM (from
// the Autoconf macro archive which is delivered with Confix; see
// http://www.gnu.org/software/ac-archive/htmldoc/ac_cxx_have_sstream.html)
// to the generated configure.in in the toplevel directory.

// we know from the documentation of this M4 macro (from the Autoconf
// macro archive) that it defines HAVE_SSTREAM if <sstream> is
// available.

// CONFIX:CONFIGURE_IN(lines=['AC_CXX_HAVE_SSTREAM'],
// CONFIX:             order=AC_HEADERS,
// CONFIX:             id='AC_CXX_HAVE_SSTREAM')

#ifdef HAVE_SSTREAM
# include <sstream>
#else
# include <strstream.h>
#endif

#include <unistd.h>

namespace WX {
namespace Utils {

using namespace std;

static const ErrorTrace empty_trace;

// --------------------------------------------------------------------
LTA_MEMDEF(Error, 2, "$Id$");

Error::Error(Badness b)
: badness_(b),
  line_(0),
  trace_(0) {}

Error::Error(const Error& e)
: trace_(0) {
   operator=(e);
}

Error::Error(const char* file, int line)
: badness_(Bad),
  file_(file),
  line_(line),
  trace_(0) {}

Error::Error(const char* file, int line, const ErrorTrace& t)
: badness_(Bad),
  file_(file),
  line_(line),
  trace_(new ErrorTrace(t)) {}

Error::~Error() throw() {
   try {
      delete trace_;
   }
   catch (...) {
      // we caught an exception from code we assumed to be
      // trivial. obviously we cannot assume iostream is still ok, so
      // we write this message with brute raw force.
      static const char* msg = "Error::~Error(): caught an exception\n";
      ::write(STDERR_FILENO, msg, strlen(msg));
      abort();
   }
}

Error& Error::operator=(const Error& e) {
   badness_ = e.badness_;
   file_ = e.file_;
   line_ = e.line_;
   message_ = e.message_;
   trace(e.trace());
   //what_ = string(); // we do not copy this one.
   return *this;
}

const ErrorTrace& Error::trace() const {
   return trace_? *trace_: empty_trace;
}

Error& Error::trace(const ErrorTrace& t) {
   if (trace_)
      *trace_ = t;
   else {
      if (&t != &empty_trace)
         trace_ = new ErrorTrace(t);
   }
   return *this;
}

const char*
Error::what()
const
throw()
{
   try {
#ifdef HAVE_SSTREAM
      ostringstream os;
      os << *this;
      what_ = os.str();
#else
      ostrstream os;
      os << *this;
      what_ = os.str();
      os.freeze(false);
#endif
      return what_.c_str();
   }
   catch (...) {
      // we caught an exception from code we assumed to be
      // trivial. obviously we cannot assume iostream is still ok, so
      // we write this message with brute raw force.
      static const char* msg = "Error::what(): caught an exception\n";
      ::write(STDERR_FILENO, msg, strlen(msg));
      abort();
   }
}

Error::Appender::~Appender() {
   if (os_) {
      *os_ << ends;
      e_.message_ += os_->str();
#ifndef HAVE_SSTREAM
      os_->freeze(false);
#endif
      delete os_;
   }
}

#ifdef HAVE_SSTREAM
ostringstream&
#else
ostrstream&
#endif
Error::Appender::os() {
   if (!os_) {
#ifdef HAVE_SSTREAM
      os_ = new ostringstream;
#else
      os_ = new ostrstream;
#endif
   }
   return *os_;
}

} // /namespace
} // /namespace
