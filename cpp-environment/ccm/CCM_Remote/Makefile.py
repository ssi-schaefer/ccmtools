REQUIRE_H("CORBA.h", REQUIRED) 

REQUIRE_SYMBOL('MICO_ENTRY', REQUIRED)

ACINCLUDE_M4(
    lines=['AC_DEFUN([MY_PRIVATE_MICO_CHECK],',
           '[AC_REQUIRE([CONFIX_MICO])',
           ' test x${CONFIX_HAVE_MICO} = xtrue || AC_MSG_ERROR([Mico not found])])'])

CONFIGURE_IN(
    lines=['MY_PRIVATE_MICO_CHECK'],
    order=AC_LIBRARIES)

try:
     PACKAGE_NAME('ccm-remote-runtime')
     PACKAGE_VERSION('0.6.17')
except Error, e:
     # we'll get here if this is not the package root, and have to
     # ignore this.
     pass
     
EXTRA_DIST('coco2.in')