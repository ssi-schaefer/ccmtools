# -*- mode: python -*-

# $Id$

# check if Python headers and libraries are available for embedding
# and/or extending the interpreter. define HAVE_PYTHON_LIB if
# available.

# THIS IS REFERRED TO BY THE CONFIX DOCUMENTATION. DON'T CHANGE IT
# HERE WITHOUT KEEPING THE DOCUMENTATION CONSISTENT.

# every source file that #includes <Python.h> requires a provide
# object which we make available here.

PROVIDE_H('Python.h')



# most of the following autoconf code is stolen from pygtk 2.0.0.

# as a basis for the following checks, we need the python
# interpreter. it will be used to tell us about its installation
# prefix etc.

python_check = "AM_PATH_PYTHON"


# here we find out the include path we need to set in order to get
# #include <Python.h> right.

header_check = "AM_CHECK_PYTHON_HEADERS"

header_func = r"""

dnl a macro to check for ability to create python extensions
dnl  AM_CHECK_PYTHON_HEADERS([ACTION-IF-POSSIBLE], [ACTION-IF-NOT-POSSIBLE])
dnl function also defines PYTHON_INCLUDES
AC_DEFUN([AM_CHECK_PYTHON_HEADERS],
[AC_REQUIRE([AM_PATH_PYTHON])
AC_MSG_CHECKING(for headers required to compile python extensions)
dnl deduce PYTHON_INCLUDES
py_prefix=`$PYTHON -c "import sys; print sys.prefix"`
py_exec_prefix=`$PYTHON -c "import sys; print sys.exec_prefix"`
PYTHON_INCLUDES="-I${py_prefix}/include/python${PYTHON_VERSION}"
if test "$py_prefix" != "$py_exec_prefix"; then
  PYTHON_INCLUDES="$PYTHON_INCLUDES -I${py_exec_prefix}/include/python${PYTHON_VERSION}"
fi
AC_SUBST(PYTHON_INCLUDES)
dnl check if the headers exist:
save_CPPFLAGS="$CPPFLAGS"
CPPFLAGS="$CPPFLAGS $PYTHON_INCLUDES"
AC_TRY_CPP([#include <Python.h>],dnl
[AC_MSG_RESULT(found)
$1],dnl
[AC_MSG_RESULT(not found)
$2])
CPPFLAGS="$save_CPPFLAGS"
])

"""

## header_check = r"""

## AC_MSG_CHECKING(for headers required to compile python extensions)
## dnl deduce PYTHON_INCLUDES
## py_prefix=`$PYTHON -c "import sys; print sys.prefix"`
## py_exec_prefix=`$PYTHON -c "import sys; print sys.exec_prefix"`
## PYTHON_INCLUDES="-I${py_prefix}/include/python${PYTHON_VERSION}"
## if test "$py_prefix" != "$py_exec_prefix"; then
##   PYTHON_INCLUDES="$PYTHON_INCLUDES -I${py_exec_prefix}/include/python${PYTHON_VERSION}"
## fi
## AC_SUBST(PYTHON_INCLUDES)
## dnl check if the headers exist:
## save_CPPFLAGS="$CPPFLAGS"
## CPPFLAGS="$CPPFLAGS $PYTHON_INCLUDES"
## AC_TRY_CPP([#include <Python.h>],dnl
## [AC_MSG_RESULT(found)],dnl
## [AC_MSG_RESULT(not found)
## PYTHON_INCLUDES=
## ])
## CPPFLAGS="$save_CPPFLAGS"

## """

# similarly, we locate the python libraries to get linking right.

lib_check = r"""

AC_MSG_CHECKING(for libpython${PYTHON_VERSION}.a)

py_exec_prefix=`$PYTHON -c "import sys; print sys.exec_prefix"`

py_makefile="${py_exec_prefix}/lib/python${PYTHON_VERSION}/config/Makefile"
if test -f "$py_makefile"; then
dnl extra required libs
  py_localmodlibs=`sed -n -e 's/^LOCALMODLIBS=\(.*\)/\1/p' $py_makefile`
  py_basemodlibs=`sed -n -e 's/^BASEMODLIBS=\(.*\)/\1/p' $py_makefile`
  py_other_libs=`sed -n -e 's/^LIBS=\(.*\)/\1/p' $py_makefile`

dnl now the actual libpython
  if test -e "${py_exec_prefix}/lib/python${PYTHON_VERSION}/config/libpython${PYTHON_VERSION}.a"; then
    PYTHON_LIBS="-L${py_exec_prefix}/lib/python${PYTHON_VERSION}/config -lpython${PYTHON_VERSION} $py_localmodlibs $py_basemodlibs $py_other_libs"
    AC_MSG_RESULT(found)
  else
    AC_MSG_RESULT(not found)
  fi
else
  AC_MSG_RESULT(not found)
fi

AC_SUBST(PYTHON_LIBS)

"""

macro = r"""

if test -n "$PYTHON_LIBS" -a -n "$PYTHON_INCLUDES"; then
   AC_DEFINE(HAVE_PYTHON_LIB, 1, [We can use the Python library])
fi

"""

CONFIGURE_IN(
    lines=[python_check],
    order=AC_PROGRAMS,
    id='my_python_program_here')

CONFIGURE_IN(
    lines=[header_check],
    order=AC_HEADERS,
    id='my_python_headers_here')

ACINCLUDE(
    lines=[header_func],
    id='AM_CHECK_PYTHON_HEADERS')

CONFIGURE_IN(
    lines=[lib_check],
    order=AC_LIBRARIES,
    id='my_python_lib_here')

CONFIGURE_IN(
    lines=[macro],
    order=max(AC_LIBRARIES,AC_HEADERS)+1,
    id='my_python_macro_here')

EXTERNAL_LIBRARY2(
    inc='@PYTHON_INCLUDES@',
    lib='@PYTHON_LIBS@')
