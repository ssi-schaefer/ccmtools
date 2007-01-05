# $Id$

PROVIDE_SYMBOL('MICO_BASIC')
REQUIRE_SYMBOL('MICO_ENTRY', REQUIRED)

PROVIDE_H('CORBA.h')

ACINCLUDE_M4(
    lines=['AC_DEFUN([CONFIX_MICO_BASIC], [AC_REQUIRE([CONFIX_MICO])])'],
    propagate_only=1)

CONFIGURE_IN(
    lines=['CONFIX_MICO_BASIC'],
    order=AC_LIBRARIES,
    propagate_only=1)

EXTERNAL_LIBRARY2(

    # note that these are AC_SUBST'ed by AC_PKG_MICO (which is called
    # by the module in mico/entry/Makefile.py)

    inc='@MICO_INCLUDE@',
    lib='@MICO_LIB@'
    )
