# $Id$

PROVIDE_SYMBOL('BOOST_MPL')
REQUIRE_SYMBOL('BOOST_ENTRY', REQUIRED)
REQUIRE_SYMBOL('BOOST_COMMON', REQUIRED)

PROVIDE_H('boost/mpl/*')

ACINCLUDE_M4(
    lines=['AC_DEFUN([CONFIX_BOOST_MPL], [AC_REQUIRE([CONFIX_BOOST])])'],
    propagate_only=1)

CONFIGURE_IN(
    lines=['CONFIX_BOOST_MPL'],
    order=AC_LIBRARIES,
    propagate_only=1)

EXTERNAL_LIBRARY2(inc='@BOOST_INC@')
