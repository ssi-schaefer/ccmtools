#include "ConvertPrimitives.h"

using namespace std;

namespace CCM_Test {
namespace Python {

PyObject *
convert_int_to_python ( int arg )
{ return PyInt_FromLong ( (long) arg ); }

PyObject *
convert_const_int_to_python ( const int arg )
{ return PyInt_FromLong ( (long) arg ); }

PyObject *
convert_long_to_python ( long arg )
{ return PyLong_FromLong ( arg ); }

PyObject *
convert_const_long_to_python ( const long arg )
{ return PyLong_FromLong ( arg ); }

PyObject *
convert_unsignedlong_to_python ( unsigned long arg )
{ return PyLong_FromUnsignedLong ( arg ); }

PyObject *
convert_const_unsignedlong_to_python ( const unsigned long arg )
{ return PyLong_FromUnsignedLong ( arg ); }

PyObject *
convert_float_to_python ( float arg )
{ return PyFloat_FromDouble ( (double) arg ); }

PyObject *
convert_const_float_to_python ( const float arg )
{ return PyFloat_FromDouble ( (double) arg ); }

PyObject *
convert_double_to_python ( double arg )
{ return PyFloat_FromDouble ( arg ); }

PyObject *
convert_const_double_to_python ( const double arg )
{ return PyFloat_FromDouble ( arg ); }

PyObject *
convert_char_to_python ( char arg )
{
  char str[2];
  str[0] = arg;
  str[1] = '\0';
  return PyString_FromString ( str );
}

PyObject *
convert_const_char_to_python ( const char arg )
{
  char str[2];
  str[0] = arg;
  str[1] = '\0';
  return PyString_FromString ( str );
}

PyObject *
convert_string_ref_to_python ( string& arg )
{ return PyString_FromString ( arg.c_str (  ) ); }

PyObject *
convert_const_string_to_python ( const string arg )
{ return PyString_FromString ( arg.c_str (  ) ); }

PyObject *
convert_const_string_ref_to_python ( const string& arg )
{ return PyString_FromString ( arg.c_str (  ) ); }

int
convert_int_from_python ( PyObject *arg )
{ return (int) PyInt_AsLong ( arg ); }

long
convert_long_from_python ( PyObject *arg )
{ return PyLong_AsLong ( arg ); }

unsigned long
convert_unsignedlong_from_python ( PyObject *arg )
{ return PyLong_AsUnsignedLong ( arg ); }

float
convert_float_from_python ( PyObject *arg )
{ return (float) PyFloat_AsDouble ( arg ); }

double
convert_double_from_python ( PyObject *arg )
{ return PyFloat_AsDouble ( arg ); }

char
convert_char_from_python ( PyObject *arg )
{
  char *result = PyString_AsString ( arg );
  return result[0];
}

string
convert_string_from_python ( PyObject *arg )
{ return string ( PyString_AsString ( arg ) ); }

} // /namespace Python
} // /namespace CCM_Test



