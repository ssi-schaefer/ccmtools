// -*- mode: C++; c-basic-offset: 3 -*-
// 
// $Id$
//
#ifndef wx_utils_error_error_macros_h
#define wx_utils_error_error_macros_h

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

namespace WX {
namespace Utils {

/**

   \def COMPOSE_ERROR(ErrorType, var)

   \ingroup utils_error

   \brief Instantiate an error of type \c ErrorType, with name \c var

 */
#define COMPOSE_ERROR(ErrorType, var) \
ErrorType var; \
var.file(__FILE__).line(__LINE__);

/**

   \def COMPOSE_ERROR_ERROR(ErrorType, var, e)

   \ingroup utils_error

   \brief Instantiate an error of type \c ErrorType, with name \c var,
   which contains the nested error \c e

 */
#define COMPOSE_ERROR_ERROR(ErrorType, var, e) \
COMPOSE_ERROR(ErrorType, var);\
{\
::WX::Utils::ErrorTrace tr;\
tr.append(e);\
var.trace(tr);\
}

/**

   \def COMPOSE_ERROR_MSG(ErrorType, var, msg)

   \ingroup utils_error

   \brief Instantiate an error of type \c ErrorType, with name \c var,
   which contains the message \c msg

   Instantiate an error of type \c ErrorType, with name \c var, which
   contains the message \c msg. \c msg is an expression that can
   contain anything an \c ostream can take.

 */
#ifdef HAVE_SSTREAM
#  define COMPOSE_ERROR_MSG_MAKE_MSG(var, msg) \
   {\
      std::ostringstream os;\
      os << msg << std::ends;\
      var.message(os.str());\
   }
#else
#  define COMPOSE_ERROR_MSG_MAKE_MSG(var, msg) \
   {\
      ostrstream os;\
      os << msg << ends;\
      var.message(os.str());\
      os.freeze(false);\
   }
#endif

#define COMPOSE_ERROR_MSG(ErrorType, var, msg) \
COMPOSE_ERROR(ErrorType, var) \
COMPOSE_ERROR_MSG_MAKE_MSG(var, msg)

/**

   \def COMPOSE_ERROR_TRACE_MSG(ErrorType, var, tr, msg)

   \ingroup utils_error

   \brief Instantiate an error of type \c ErrorType, with name \c var,
   which contains a nested list of errors, \c tr, and contains the
   message \c msg

   Instantiate an error of type \c ErrorType, with name \c var, which
   contains a nested list of errors, \c tr, and contains the message
   \c msg. \c msg is an expression that can contain anything an \c
   ostream can take.

 */
#define COMPOSE_ERROR_TRACE_MSG(ErrorType, var, tr, msg) \
COMPOSE_ERROR_MSG(ErrorType, var, msg) \
var.trace(tr);

/**

   \def COMPOSE_ERROR_ERROR_MSG(ErrorType, var, e, msg)

   \ingroup utils_error

   \brief Instantiate an error of type \c ErrorType, with name \c var,
   which contains a nested error, \c e, and contains the message \c
   msg

   Instantiate an error of type \c ErrorType, with name \c var, which
   contains a nested error, \c e, and contains the message \c msg. \c
   msg is an expression that can contain anything an \c ostream can
   take.

 */
#define COMPOSE_ERROR_ERROR_MSG(ErrorType, var, e, msg) \
COMPOSE_ERROR_MSG(ErrorType, var, msg) \
{\
::WX::Utils::ErrorTrace tr;\
tr.append(e);\
var.trace(tr);\
}

/**

   \def THROW_ERROR(ErrorType)

   \ingroup utils_error

   \brief Throws an error that is composed using \ref COMPOSE_ERROR

 */
#define THROW_ERROR(ErrorType) \
{ \
   COMPOSE_ERROR(ErrorType, var); \
   throw var; \
}

/**

   \def THROW_ERROR_MSG(ErrorType, msg)

   \ingroup utils_error

   \brief Throws an error that is composed using \ref COMPOSE_ERROR_MSG

 */
#define THROW_ERROR_MSG(ErrorType, msg) \
{ \
   COMPOSE_ERROR_MSG(ErrorType, var, msg); \
   throw var; \
}

/**

   \def THROW_ERROR_TRACE_MSG(ErrorType, tr, msg)

   \ingroup utils_error

   \brief Throws an error that is composed using \ref
   COMPOSE_ERROR_TRACE_MSG

 */
#define THROW_ERROR_TRACE_MSG(ErrorType, tr, msg) \
{ \
   COMPOSE_ERROR_TRACE_MSG(ErrorType, var, tr, msg); \
   throw var; \
}

/**

   \def THROW_ERROR_ERROR_MSG(ErrorType, e, msg)

   \ingroup utils_error

   \brief Throws an error that is composed using \ref
   COMPOSE_ERROR_ERROR_MSG

 */
#define THROW_ERROR_ERROR_MSG(ErrorType, e, msg) \
{ \
   COMPOSE_ERROR_ERROR_MSG(ErrorType, var, e, msg); \
   throw var; \
}

} // /namespace
} // /namespace

#endif
