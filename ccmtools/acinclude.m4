# Checks for javac and jar.
#
# Leif Johnson <leif@ambient.2y.net>
# copyright (c) 2002-2003 Salomon Automation.
#
# This program is free software; you can redistribute it and/or modify it under
# the terms of the GNU General Public License as published by the Free Software
# Foundation; either version 2, or (at your option) any later version.
#
# This program is distributed in the hope that it will be useful, but WITHOUT
# ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
# FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
#
# You should have received a copy of the GNU General Public License along with
# this program; if not, write to the Free Software Foundation, Inc., 59 Temple
# Place - Suite 330, Boston, MA 02111-1307, USA.

# First the java sdk checks. If they work, these define $JAVA, $JAVAC, $JAR,
# $JAVA_VERSION, $jardir, $pkgjardir.

AC_DEFUN([AM_PATH_JAVA_SDK],[
  m4_if([$1],[],[
    # The user supplied no version number. Use the default Java interpreter,
    # compiler, and jar archiver.
    AC_PATH_PROG([JAVA], java)
    AC_PATH_PROG([JAVAC], javac)
    AC_PATH_PROG([JAR], jar)
    am_display_JAVA=java
    am_display_JAVAC=javac
    am_display_JAR=jar
  ], [
    # we need to do a version check. just check the interpreter, since the
    # compiler and jar archiver don't provide version numbers (for whatever
    # bizarre reason).
    if test -n "$JAVA" ; then
      AC_MSG_CHECKING([whether $JAVA has version >= $1])
      AM_JAVA_SDK_CHECK_VERSION([$JAVA], [$1],
                                [AC_MSG_RESULT(yes)], [AC_MSG_ERROR(no)])
      # If the user set $JAVA, use it and don't search something else. If $JAVA
      # is set, then $JAVAC and $JAR need to be as well ...
      test -z "$JAVAC" -o -z "$JAR" && \
        AC_MSG_ERROR([if JAVA is set, then JAVAC and JAR must be set as well])
    else
      AC_MSG_CHECKING([whether java has version >= $1])
      AM_JAVA_SDK_CHECK_VERSION(java, [$1],
                                [AC_MSG_RESULT(yes)], [AC_MSG_ERROR(no)])
      AC_PATH_PROG([JAVA], java)
      AC_PATH_PROG([JAVAC], javac)
      AC_PATH_PROG([JAR], jar)
      am_display_JAVA=java
      am_display_JAVAC=javac
      am_display_JAR=jar
    fi
  ])

  AC_CACHE_CHECK([for $am_display_JAVA version], [am_cv_java_version],
    [am_cv_java_version=`$JAVA -version 2>&1 | grep '^java version ' \
                         | sed 's/^.ava version "\(.*\)"$/\1/'`])
  AC_SUBST([JAVA_VERSION], [$am_cv_java_version])

  # check on the javac program flags.
  if test "x${JAVACFLAGS-unset}" = xunset; then JAVACFLAGS=" "; fi
  AC_SUBST(JAVACFLAGS)
  _AM_IF_OPTION([no-dependencies],, [_AM_DEPENDENCIES(JAVAC)])

  # check on the jar program flags.
  if test "x${JARFLAGS-unset}" = xunset; then JARFLAGS=" "; fi
  AC_SUBST(JARFLAGS)
  _AM_IF_OPTION([no-dependencies],, [_AM_DEPENDENCIES(JAR)])

  # make a prefix for Java, though it should be exceedingly rare to need to
  # override it. then define a jardir and pkgjardir where we want to put jar
  # files.
  AC_SUBST([JAVA_PREFIX], ['${prefix}'])
  AC_SUBST([jardir], [${JAVA_PREFIX}/share/java])
  AC_SUBST([pkgjardir], [\${jardir}/$PACKAGE])
])

# AM_JAVA_SDK_CHECK_VERSION(PROG, VERSION, [ACTION-IF-TRUE], [ACTION-IF-FALSE])
# ---------------------------------------------------------------------------
# Run ACTION-IF-TRUE if the java interpreter PROG has version >= VERSION.
# Run ACTION-IF-FALSE otherwise.
AC_DEFUN([AM_JAVA_SDK_CHECK_VERSION],
 [AS_IF([AM_RUN_LOG([
    JVER=`$1 -version 2>&1 | grep '^java version ' | sed 's/^.ava version "\(.*\)"$/\1/'`
    JMAJ=`echo "$JVER" | cut -d . -f 1`
    JMIN=`echo "$JVER" | cut -d . -f 2`
    JREV=`echo "$JVER" | cut -d . -f 3`
    BMAJ=`echo "$1" | cut -d . -f 1`
    BMIN=`echo "$1" | cut -d . -f 2`
    BREV=`echo "$1" | cut -d . -f 3`
    test $JMAJ -gt $BMAJ && exit 0
    test $JMAJ -lt $BMAJ && exit 1
    test $JMIN -gt $BMIN && exit 0
    test $JMIN -lt $BMIN && exit 1
    test $JREV -gt $BREV && exit 0
    test $JREV -lt $BREV && exit 1
    exit 0
  ])], [$3], [$4])])
