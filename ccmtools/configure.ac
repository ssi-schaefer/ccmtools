dnl Process this file with autoconf to produce a configure script.
dnl $Id$

AC_INIT(ccmtools, 0.3.2)
AC_CONFIG_SRCDIR(Metamodel/BaseIDL/BaseIdl.java)
AM_INIT_AUTOMAKE

dnl Checks for programs.

AC_PROG_CC
AC_PROG_INSTALL
AC_PROG_MAKE_SET
AC_PATH_PROG([CP],    [[cp copy]])
AC_PATH_PROG([RM],    [[rm delete]])
AC_PATH_PROG([MKDIR], [[mkdir md]])
AC_PATH_PROG(LATEX,   [[latex]])
AC_PATH_PROG(BIBTEX,  [[bibtex]])
AC_PATH_PROG(DVIPS,   [[dvips]])
AC_PATH_PROG(DVIPDF,  [[dvipdf]])
AC_PATH_PROG(FIG2DEV, [[fig2dev]])
AC_PATH_PROG(JAVADOC, [[javadoc]])
AM_PATH_JAVA_SDK(1.4)

dnl Checks for libraries.

dnl Checks for header files.

dnl Checks for library functions.

dnl Export versions.

CCMTOOLS_MAJORMINOR=`echo ${VERSION} | sed -e 's/\.[[0-9]]\+$//'`
AC_SUBST(CCMTOOLS_MAJORMINOR)

dnl Check for building tests.

AC_ARG_ENABLE(tests,
AC_HELP_STRING([--disable-tests],[disable tests]),
[case "${enableval}" in
  yes) BUILD_TESTS=yes ;;
  no)  BUILD_TESTS=no ;;
  *) AC_MSG_ERROR(bad value ${enableval} for --disable-tests) ;;
esac],
[BUILD_TESTS=yes])
AM_CONDITIONAL(BUILD_TESTS, test "x$BUILD_TESTS" = "xyes")

dnl Check for building docs.

AC_ARG_ENABLE(docs,
AC_HELP_STRING([--enable-docs],[make documentation]),
[case "${enableval}" in
  yes) BUILD_DOCS=yes ;;
  no)  BUILD_DOCS=no ;;
  *) AC_MSG_ERROR(bad value ${enableval} for --enable-docs) ;;
esac],
[BUILD_DOCS=no])
AM_CONDITIONAL(BUILD_DOCS, test "x$BUILD_DOCS" = "xyes")

dnl Set location of code generation templates.

FILTERED_PREFIX=`echo ${prefix} | sed 's,/$,,'`
TEMPLATE_ROOT=${FILTERED_PREFIX}/share/${PACKAGE}-${CCMTOOLS_MAJORMINOR}
AC_DEFINE_UNQUOTED(TEMPLATE_ROOT, "$TEMPLATE_ROOT", [Define the base location for templates])
AC_SUBST(TEMPLATE_ROOT)

dnl Generate output.

AC_CONFIG_FILES(
Makefile
binary-dist
Constants.java
lib/Makefile
doc/Makefile
doc/guide/Makefile
doc/manual/Makefile
doc/tutorial/Makefile
test/Makefile
test/Metamodel/Makefile
test/IDL3Parser/Makefile
test/CodeGenerator/Makefile
test/CppGenerator/Makefile
test/CCDGenerator/Makefile
Metamodel/Makefile
Metamodel/BaseIDL/Makefile
Metamodel/ComponentIDL/Makefile
IDL3Parser/Makefile
CCDGenerator/Makefile
CodeGenerator/Makefile
IDLGenerator/Makefile
IDLGenerator/IDL3Templates/Makefile
IDLGenerator/IDL3MirrorTemplates/Makefile
IDLGenerator/IDL2Templates/Makefile
CppGenerator/Makefile
CppGenerator/CppLocalTemplates/Makefile
CppGenerator/CppRemoteTemplates/Makefile
CppGenerator/CppMirrorTemplates/Makefile
CppGenerator/CppPythonTemplates/Makefile
UI/Makefile
UI/scripts/Makefile
)

AC_OUTPUT
