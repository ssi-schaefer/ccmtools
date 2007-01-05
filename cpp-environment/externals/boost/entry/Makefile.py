PROVIDE_SYMBOL('BOOST_ENTRY')

acm4 = """

AC_DEFUN([CONFIX_BOOST],
[
  CONFIX_HAVE_BOOST=
  BOOST_INC=
  BOOST_LIB=

  AC_MSG_CHECKING([for boost])
  
  AC_ARG_WITH([boost],
              AC_HELP_STRING([--with-boost], [The prefix of Boost (default /usr)]),
              [
                case "${withval}" in
                  yes)
                    CONFIX_HAVE_BOOST=true
                    BOOST_INC=
                    BOOST_LIB=
                    AC_MSG_RESULT([default])
                    ;;
                  no)
                    CONFIX_HAVE_BOOST=false
                    BOOST_INC=
                    BOOST_LIB=
                    AC_MSG_RESULT([no])
                    ;;
                  *)
                    CONFIX_HAVE_BOOST=true
                    BOOST_INC="-I${withval}/include"
                    BOOST_LIB="-L${withval}/lib"
                    test -d "${withval}" || AC_MSG_WARN([Directory ${withval} does not exist])
                    AC_MSG_RESULT([${withval}])
                    ;;
                esac
              ],
              [
                AC_MSG_RESULT([not given])
              ])

if test x${CONFIX_HAVE_BOOST} = xtrue; then
  AC_DEFINE([HAVE_BOOST], [], [Can we use the boost library?])
fi

AC_SUBST(BOOST_INC)
AC_SUBST(BOOST_LIB)

])

"""

ACINCLUDE_M4(
    lines=[acm4],
    propagate_only=1)
