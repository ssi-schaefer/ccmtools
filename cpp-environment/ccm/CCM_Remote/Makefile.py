REQUIRE_H("CORBA.h", REQUIRED) 

REQUIRE_SYMBOL('MICO_ENTRY', REQUIRED)

ACINCLUDE_M4(
    lines=['AC_DEFUN([MY_PRIVATE_MICO_CHECK],',
           '[AC_REQUIRE([CONFIX_MICO])',
           ' test x${CONFIX_HAVE_MICO} = xtrue || AC_MSG_ERROR([Mico not found])])'])

CONFIGURE_IN(
    lines=['MY_PRIVATE_MICO_CHECK'],
    order=AC_LIBRARIES)

EXTRA_DIST('coco2.in')