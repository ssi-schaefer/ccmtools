/**
@file ms_eh.cc

@brief converts MS-SEH-exceptions into wamas::platform::utils::Error
*/

#include "error.h"

#ifdef  _MSC_VER

#include <excpt.h>
#include <windows.h>

#define CONVERT_SE(SE) case SE: s= #SE ; break;

namespace wamas {
namespace platform {
namespace utils {
namespace MS_SEH {

static void se_translator(unsigned int i, EXCEPTION_POINTERS* p)
{
    std::string s;
    switch(i)
    {
        CONVERT_SE(EXCEPTION_ACCESS_VIOLATION)
        CONVERT_SE(EXCEPTION_BREAKPOINT)
        CONVERT_SE(EXCEPTION_DATATYPE_MISALIGNMENT)
        CONVERT_SE(EXCEPTION_SINGLE_STEP)
        CONVERT_SE(EXCEPTION_ARRAY_BOUNDS_EXCEEDED)
        CONVERT_SE(EXCEPTION_FLT_DENORMAL_OPERAND)
        CONVERT_SE(EXCEPTION_FLT_DIVIDE_BY_ZERO)
        CONVERT_SE(EXCEPTION_FLT_INEXACT_RESULT)
        CONVERT_SE(EXCEPTION_FLT_INVALID_OPERATION)
        CONVERT_SE(EXCEPTION_FLT_OVERFLOW)
        CONVERT_SE(EXCEPTION_FLT_STACK_CHECK)
        CONVERT_SE(EXCEPTION_FLT_UNDERFLOW)
        CONVERT_SE(EXCEPTION_INT_DIVIDE_BY_ZERO)
        CONVERT_SE(EXCEPTION_INT_OVERFLOW)
        CONVERT_SE(EXCEPTION_PRIV_INSTRUCTION)
        CONVERT_SE(EXCEPTION_NONCONTINUABLE_EXCEPTION)
        default:
            THROW_ERROR_MSG(wamas::platform::utils::Error,
                "MS-SE with unknown id " << i);
    }
    THROW_ERROR_MSG(wamas::platform::utils::Error, "MS-SE : " << s);
}

struct SetTranslator
{
    SetTranslator()
    {
        _set_se_translator(se_translator);
    }
};

SetTranslator GLOBAL_SE_TRANSLATOR;

} // /namespace
} // /namespace
} // /namespace
} // /namespace


#endif  // _MSC_VER
