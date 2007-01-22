//// -*- mode: C++; c-basic-offset: 3 -*-
//
// $Id$
//
#ifndef wx_utils_error_error_impl_h
#define wx_utils_error_error_impl_h

#include "errorout_ostream.h"

#include <wamas/platform/utils/linkassert.h>
#include <wamas/platform/utils/smartptr.h>

#include <string>
#include <sstream>

#include <exception>

namespace wamas {
namespace platform {
namespace utils {

// we cannot have an ErrorTrace automatic member because errortrace.h
// in turn includes this file, which would end up in circular
// includes. so we have to forward declare it and manage it in the .cc
// file instead.
class ErrorTrace;

/**

   \ingroup utils_error

   \brief Conceptually, An Error object is a description of an error
   that happened, either directly or indirectly. Supposed to be thrown
   as exception.

   As objects of type Error are supposed to be thrown as exceptions,
   it makes sense to derive from them (Error itself derives from
   std::exception) to emphasize a specific failure condition at a
   particular point in code, but still provide the common Error
   interface (which is described in the remainder).

   An Error carries a punctual description (message()) of an error
   that has happened. Along with this description it can (should)
   carry the file name and a line number (file(), line()) of the code
   location where it happened.

   An Error can own a nested ErrorTrace object, which is basically a
   list of Error objects. For example, a common use for this is if you
   are calling a function which throws an Error object as
   exception. Then you may want to decribe this error in your own
   (more high level) terms, like "Could not write this record to disk
   because the marshaller failed (detailed description follows)" (what
   follows is the marshaller's Error, contained in the trace).

   Here's a small example of how to use it.

   \code
   Error caught; // e.g. caught by an exception handler
   Error error;
   SmartPtr<ErrorNode> node(
        new ErrorNode(__FILE__,__LINE__,"My own high level description"));
   node->children(caught.root());
   error.root(node);
   throw error;
   \endcode

   So, this example isn't really small as it introduces all of Error
   at once. Fortunately, there are helper macros which make life a lot
   easier. Typical uses are

   \code
   #include <wamas/platform/utils/error_macros.h>

   COMPOSE_ERROR_MSG(DerivedError, err,
                   "My " << 2 << " bucks here");
   \endcode

   which declares a variable err of type DerivedError (which, as the
   name suggests, is derived from class Error). Note the demonstrative
   use of the output operator: you compose a message just as you do
   with ostreams.

   Another typical use is

   \code
   #include <wamas/platform/utils/error_macros.h>

   THROW_ERROR_MSG(DerivedError,
                   "My " << 2 << " bucks here");
   \endcode

   which does the same as above, except that it throws the object
   immediately instead.

 */

class ErrorNode : public RefCounted  {
public:
    ErrorNode();
    ErrorNode(const SmartPtr<ErrorNode>&);
    ErrorNode(const char *, int, const char *);
    ErrorNode(const char *, int, const char *, const SmartPtr<ErrorNode>&);
    ~ErrorNode() throw();

    ErrorNode& file(const std::string& f) { file_ = f; return *this; }
    ErrorNode& line(int l) { line_ = l; return *this; }
    ErrorNode& message(const std::string& m) { message_ = m; return *this; }
    ErrorNode& addChild(const SmartPtr<ErrorNode>&);

    const std::string& file() const { return file_; }
    int line() const { return line_; }
    const std::string& message() const { return message_; }
    const ErrorTrace& children() const { return *children_; }

    ErrorNode& operator=(const ErrorNode&);

private:
    std::string file_;
    int line_;
    std::string message_;
    ErrorTrace* children_;
};


class Error : public std::exception {
public:
    Error() {}
    Error(const SmartPtr<ErrorNode>& n) { root_ = n; }
    Error(const Error& e) { root_ = e.root(); }
    Error(Error* e) { root_ = e->root(); }
    ~Error() throw() {}

    const SmartPtr<ErrorNode>& root() const { return root_; };
    Error& root(const SmartPtr<ErrorNode>& root) {
        root_ = root;
        return *this;
    }


    template<class T>
    void addChildren(const T& children) {
        if ( !this->root_ )
            this->root_.eat(new ErrorNode);
        for ( typename T::const_iterator i=children.begin();
              i != children.end();
              ++i
        ) {
            this->root_->addChild(i->root());
        }
    }

    Error& operator=(const Error&);
    bool good() const { return !root_; }
    operator bool() const { return !good(); }

    virtual const char* what() const throw ();

     LTA_MEMDECL(3);
private:
    SmartPtr<ErrorNode> root_;

    mutable std::string what_;
};

LTA_STATDEF(Error, 3);

} // /namespace
} // /namespace
} // /namespace

#endif

