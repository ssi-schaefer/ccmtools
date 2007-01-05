PROVIDE_SYMBOL('MICO_ENTRY')

acm4 = """

AC_DEFUN([CONFIX_MICO],
[
  AC_PKG_MICO
  
  # see if we have found anything and announce it to user code
  if test -n "$MICOSHAREDDIR"; then
      CONFIX_HAVE_MICO=true
      AC_DEFINE([HAVE_MICO], [], [Can we use Mico?])
  else
      CONFIX_HAVE_MICO=
  fi
])

"""

ACINCLUDE_M4(
    lines=[acm4],
    propagate_only=1)
