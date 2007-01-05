# $Id$

PROVIDE_SYMBOL('BOOST_PROGRAM_OPTIONS')

REQUIRE_SYMBOL('BOOST_ENTRY', REQUIRED)

PROVIDE_H('boost/program_options.hpp')


acm4 = """

AC_DEFUN([CONFIX_BOOST_PROGRAM_OPTIONS],
[
AC_REQUIRE([AC_CANONICAL_TARGET])
AC_REQUIRE([CONFIX_BOOST])
orig_cxxflags=${CXXFLAGS}

# HACK ALERT: boost's libraries seem to be compiled threadingly, by
# default. add -pthread to the compiler commandline of the test
# program, assuming that gcc (or any compiler that supports -pthread)
# is used.

# NOTE that the -pthread flag is only used for the boost program-options
# test program to compile and link. it is not propagated to any module
# that uses program-options.

case "$target" in 
    *-*-solaris*)
        THE_FUCKING_AIX_PTHREAD_FLAG=-pthreads
        ;;
    *)
        THE_FUCKING_AIX_PTHREAD_FLAG=-pthread
        ;;
esac

CXXFLAGS="${BOOST_INC} ${CXXFLAGS} ${THE_FUCKING_AIX_PTHREAD_FLAG}"
# Save current programming language (e.g. CC i.e. C)
AC_LANG_SAVE
# Set language to CXX (i.e. C++)
AC_LANG_CPLUSPLUS
# run autoconf check (beware: it must be in the archive)
AX_BOOST_PROGRAM_OPTIONS
# restore previous language again
AC_LANG_RESTORE

# restore previous CXX-flages
CXXFLAGS=${orig_cxxflags}

if test x${BOOST_PROGRAM_OPTIONS_LIB} != x; then
    CONFIX_BOOST_PROGRAM_OPTIONS_LIB="-l${BOOST_PROGRAM_OPTIONS_LIB}"
fi
# wichtig fuer lib @CONFIX_BOOST_PROGRAM_OPTIONS_LIB@'
AC_SUBST(CONFIX_BOOST_PROGRAM_OPTIONS_LIB)
])

"""

ACINCLUDE_M4(
    lines=[acm4],
    propagate_only=1)

CONFIGURE_IN(
    lines=['CONFIX_BOOST_PROGRAM_OPTIONS'],
    order=AC_LIBRARIES,
    propagate_only=1)

EXTERNAL_LIBRARY2(
    inc='@BOOST_INC@',
    libpath=['@BOOST_LIB@'],
    lib='@CONFIX_BOOST_PROGRAM_OPTIONS_LIB@')
