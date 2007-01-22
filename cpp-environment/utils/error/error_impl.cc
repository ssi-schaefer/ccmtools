// -*- mode: C++; c-basic-offset: 3 -*-
//
// $Id$
//
#include "error_impl.h"


#include "errortrace.h"
#include "errorout_ostream.h"

#include <sstream>

#if !defined(_WIN32)
#  include <unistd.h>
#endif

namespace wamas {
namespace platform {
namespace utils {

using namespace std;

static const ErrorTrace empty_trace;

// --------------------------------------------------------------------
LTA_MEMDEF(Error, 3, "$Id$");

// --------------------------------------------------------------------
// class ErrorNode

ErrorNode::ErrorNode()
:   line_(0),
    children_(new ErrorTrace) {}

ErrorNode::ErrorNode(const SmartPtr<ErrorNode>& n)
:   line_(0),
    children_(new ErrorTrace)
{
    children_->append(n);
}

ErrorNode::ErrorNode(const char*f, const int l, const char* m)
:   file_(f),
    line_(l),
    message_(m),
    children_(new ErrorTrace) {}

ErrorNode::ErrorNode(const char* f, int l, const char* m, const SmartPtr<ErrorNode>& n)
:   file_(f),
    line_(l),
    message_(m),
    children_(new ErrorTrace)
{
    children_->append(n);
}

ErrorNode::~ ErrorNode() throw()
{
    try {
        delete children_;
    }
    catch (...) {
      // we caught an exception from code we assumed to be
      // trivial. obviously we cannot assume iostream is still ok, so
      // we write this message with brute raw force.
        static const char* msg = "ErrorNode::~ErrorNode(): caught an exception\n";
#if defined(_WIN32)
      fprintf(stderr,"%s",msg);
#else
            ::write(STDERR_FILENO, msg, strlen(msg));
#endif
      abort();
    }
}

ErrorNode& ErrorNode::addChild(const SmartPtr<ErrorNode>& n)
{
    children_->append(n);
    return *this;
}

ErrorNode& ErrorNode::operator =(const ErrorNode& e)
{
    file_ = e.file_;
    line_ = e.line_;
    message_ = e.message_;
    *children_ = e.children();
    return *this;
}

// --------------------------------------------------------------------
// class Error
Error& Error::operator =(const Error& e)
{
    root(e.root());
    return *this;
}

const char* Error::what() const throw()
{
   try {
      ostringstream os;
      output(*this, os);
      what_ = os.str();
      return what_.c_str();
   }
   catch (...) {
      // we caught an exception from code we assumed to be
      // trivial. obviously we cannot assume iostream is still ok, so
      // we write this message with brute raw force.
      static const char* msg = "Error::what(): caught an exception\n";
#if defined(_WIN32)
      fprintf(stderr,"%s",msg);
#else
      ::write(STDERR_FILENO, msg, strlen(msg));
#endif
      abort();
   }
}

} // /namespace
} // /namespace
} // /namespace

