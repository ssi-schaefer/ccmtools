#ifndef wx_utils_error_error_macros_h
#define wx_utils_error_error_macros_h

#include <sstream>

namespace wamas {
namespace platform {
namespace utils {

/**

   \def COMPOSE_ERROR(ErrorType, var)

   \ingroup utils_error

   \brief Instantiate an error of type \c ErrorType, with name \c var

 */
#define COMPOSE_ERROR(ErrorType, var) \
ErrorType var; \


/**

   \def COMPOSE_ERROR_ERROR(ErrorType, var, e)

   \ingroup utils_error

   \brief Instantiate an error of type \c ErrorType, with name \c var,
   which contains the nested error \c e

 */
#define COMPOSE_ERROR_ERROR(ErrorType, var, e) \
COMPOSE_ERROR(ErrorType, var);\
{ \
    wamas::platform::utils::SmartPtr<wamas::platform::utils::ErrorNode> const& child = e.root(); \
    if ( child.ptr() ) { \
        var.root(child);\
    } \
}


/**

   \def COMPOSE_NODE_NO_MSG

   \ingroup utils_error

   \brief Instantiate an node of type \c ErrorNode, with name \c node,
   contains the message \c msg. \c msg is an expression that can
   contain anything an \c ostream can take.

 */
#define COMPOSE_NODE_NO_MSG \
wamas::platform::utils::SmartPtr<wamas::platform::utils::ErrorNode> node(new wamas::platform::utils::ErrorNode); \
node->file(__FILE__); \
node->line(__LINE__);

/**

   \def COMPOSE_NODE_MSG(msg)

   \ingroup utils_error

   \brief Instantiate an node of type \c ErrorNode, with name \c node,
   contains the message \c msg. \c msg is an expression that can
   contain anything an \c ostream can take.

 */
#define COMPOSE_NODE_MSG(msg) \
wamas::platform::utils::SmartPtr<wamas::platform::utils::ErrorNode> node(new wamas::platform::utils::ErrorNode); \
std::ostringstream os; \
os << msg; \
node->file(__FILE__); \
node->line(__LINE__); \
node->message(os.str());

/**

   \def COMPOSE_ERROR_MSG(ErrorType, var, msg)

   \ingroup utils_error

   \brief Instantiate an error of type \c ErrorType, with name \c var,
   which contains the message \c msg

   Instantiate an error of type \c ErrorType, with name \c var, which
   contains the message \c msg. \c msg is an expression that can
   contain anything an \c ostream can take.

 */
#define COMPOSE_ERROR_MSG_MAKE_MSG(var, msg) \
{ \
    COMPOSE_NODE_MSG(msg) \
    var.root(node); \
}

#define COMPOSE_ERROR_MSG(ErrorType, var, msg) \
COMPOSE_ERROR(ErrorType, var) \
COMPOSE_ERROR_MSG_MAKE_MSG(var, msg) \

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
{ \
    wamas::platform::utils::SmartPtr<wamas::platform::utils::ErrorNode> root(var.root()); \
    root->addChild(tr); \
}

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
    wamas::platform::utils::SmartPtr<wamas::platform::utils::ErrorNode> root = var.root(); \
    wamas::platform::utils::SmartPtr<wamas::platform::utils::ErrorNode> const& child = e.root(); \
    if ( child.ptr() ) { \
        root->addChild(e.root());\
    } \
}

/**

   \def THROW_ERROR(ErrorType)

   \ingroup utils_error

   \brief Throws an error that is composed using \ref COMPOSE_ERROR

 */
#define THROW_ERROR(ErrorType) \
{ \
   COMPOSE_ERROR(ErrorType, var); \
   { \
      COMPOSE_NODE_NO_MSG \
      var.root(node); \
   }\
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

/**

   \def THROW_ERROR_APPEND_MSG(e, msg)

   \ingroup utils_error

   \brief Throws an error that is composed using \ref
   COMPOSE_ERROR_ERROR_MSG

 */
#define THROW_ERROR_APPEND_MSG(e, msg)\
{ \
    COMPOSE_NODE_MSG(msg) \
    node->addChild(e.root()); \
    e.root(node); \
    throw; \
}

/**

   \def THROW_ERRORLIST(e, list, msg)

   \param e error class
   \param list any STL-compatible type which provides begin() and end()
   \param msg error message

   \ingroup utils_error

   \brief Throws a list of errors.

 */
#define THROW_ERRORLIST(ErrorType, list, msg) \
{  \
    COMPOSE_ERROR_MSG( ErrorType, var, msg); \
    var.addChildren(list); \
    throw var; \
}


/**
 * \def CONVERT_UNKNOWN_ERROR(ErrorType,msg)
 *
 * \brief catches and converts unknown exceptions
 *
 * \param ErrorType error class to convert to
 * \param msg error message
 *
 * \ingroup utils_error
 */
#define CONVERT_UNKNOWN_ERROR(ErrorType, msg) \
catch(const wamas::platform::utils::Error& e) \
{ THROW_ERROR_ERROR_MSG(ErrorType, e, msg); } \
catch(const std::exception& e) \
{ THROW_ERROR_MSG(ErrorType, msg << '\n' << e.what()); } \
catch(...) \
{ THROW_ERROR_MSG(ErrorType, msg << "\nUNKNOWN EXCEPTION"); }

#define CONVERT_UNKNOWN_ERROR1(ErrorType1, msg) \
catch(const ErrorType1&) { throw; } \
CONVERT_UNKNOWN_ERROR(ErrorType1, msg)

#define CONVERT_UNKNOWN_ERROR2(ErrorType1, ErrorType2, msg) \
catch(const ErrorType2&) { throw; } \
CONVERT_UNKNOWN_ERROR1(ErrorType1, msg)

#define CONVERT_UNKNOWN_ERROR3(ErrorType1, ErrorType2, ErrorType3, msg) \
catch(const ErrorType3&) { throw; } \
CONVERT_UNKNOWN_ERROR2(ErrorType1, ErrorType2, msg)


} // /namespace
} // /namespace
} // /namespace

#endif
