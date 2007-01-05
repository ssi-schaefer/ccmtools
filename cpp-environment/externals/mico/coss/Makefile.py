# $Id$

PROVIDE_SYMBOL('MICO_COSS')
REQUIRE_SYMBOL('MICO_BASIC', REQUIRED)
REQUIRE_SYMBOL('MICO_ENTRY', REQUIRED)

PROVIDE_H('coss/CosNaming.h')

acm4 = """

AC_DEFUN([CONFIX_MICO_COSS],
[
AC_REQUIRE([CONFIX_MICO_BASIC])

# CONFIX_MICO_BASIC sets MICO_INC_CONFIX if we have mico. take this as
# a criterion for our setting of MICO_COSS_LIB. MICODIR and
# MICOVERSION are set by the AC_PKG_MICO macro which is called by
# CONFIX_MICO_BASIC in turn.

if test x${CONFIX_HAVE_MICO} = xtrue; then
    MICO_COSS_LIB="-L${MICODIR}/lib -lmicocoss${MICOVERSION}"
else
    MICO_COSS_LIB=
fi
AC_SUBST(MICO_COSS_LIB)

])

"""

ACINCLUDE_M4(
    lines=[acm4],
    propagate_only=1)

CONFIGURE_IN(
    lines=['CONFIX_MICO_COSS'],
    order=AC_LIBRARIES,
    propagate_only=1)

EXTERNAL_LIBRARY2(

    # note that MICO_INCLUDE is AC_SUBST'ed by AC_PKG_MICO (which is
    # called by the module in mico/entry/Makefile.py)

    inc='@MICO_INCLUDE@',
    lib='@MICO_COSS_LIB@'
    )
