#ifndef ___CCM__TEST__PYTHON__CONVERT_PRIMITIVES__H___
#define ___CCM__TEST__PYTHON__CONVERT_PRIMITIVES__H___

#include <string>
#include <cstring>
#include <Python.h>

namespace CCM_Test {
namespace Python {

PyObject *convert_int_to_python                ( int arg );
PyObject *convert_const_int_to_python          ( const int arg );
PyObject *convert_long_to_python               ( long arg );
PyObject *convert_const_long_to_python         ( const long arg );
PyObject *convert_unsignedlong_to_python       ( unsigned long arg );
PyObject *convert_const_unsignedlong_to_python ( const unsigned long arg );
PyObject *convert_float_to_python              ( float arg );
PyObject *convert_const_float_to_python        ( const float arg );
PyObject *convert_double_to_python             ( double arg );
PyObject *convert_const_double_to_python       ( const double arg );
PyObject *convert_char_to_python               ( char arg );
PyObject *convert_const_char_to_python         ( const char arg );
PyObject *convert_string_ref_to_python         ( std::string& arg );
PyObject *convert_const_string_to_python       ( const std::string arg );
PyObject *convert_const_string_ref_to_python   ( const std::string& arg );

int           convert_int_from_python          ( PyObject *arg );
long          convert_long_from_python         ( PyObject *arg );
unsigned long convert_unsignedlong_from_python ( PyObject *arg );
float         convert_float_from_python        ( PyObject *arg );
double        convert_double_from_python       ( PyObject *arg );
char          convert_char_from_python         ( PyObject *arg );
std::string   convert_string_from_python       ( PyObject *arg );

} // /namespace Python
} // /namespace CCM_Test

#endif

