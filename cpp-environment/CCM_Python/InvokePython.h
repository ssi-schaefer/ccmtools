#ifndef __CCM__TEST__PYTHON__INVOKE_PYTHON__H__
#define __CCM__TEST__PYTHON__INVOKE_PYTHON__H__

#include <cstring>
#include <Python.h>

namespace CCM_Test {
namespace Python {

PyObject *invoke_python ( std::string  module,
                          std::string  function,
                          PyObject    *pyArgs );

} // /namespace Python
} // /namespace CCM_Test

#endif

