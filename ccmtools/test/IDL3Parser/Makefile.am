## process this file with automake to produce Makefile.in
## $Id$

JAVAROOT = .
CLASSPATH_ENV = CLASSPATH=.:$(top_builddir):$(top_srcdir)/lib/antlr.jar
TESTS_ENVIRONMENT = top_builddir=$(top_builddir) top_srcdir=$(top_srcdir) \
                    JAVA=$(JAVA) $(CLASSPATH_ENV)

dist_noinst_JAVA = Main.java
dist_noinst_SCRIPTS = test-loader.sh

XFAIL_TESTS = \
    fail-component-home.sh       \
    fail-component-support.sh    \
    fail-enum-empty.sh           \
    fail-home-component.sh       \
    fail-interface-syntax.sh     \
    fail-struct-empty.sh

TESTS =
 #$(XFAIL_TESTS) \
#    component.sh                 \
#    enum.sh                      \
#    home.sh                      \
#    interface.sh                 \
#    operations-primitivetypes.sh \
#    provides-operation.sh        \
#    provides.sh                  \
#    struct.sh                    \
#    supports-operation.sh        \
#    supports.sh                  \
#    uses-multiple-operation.sh   \
#    uses-multiple.sh             \
#    uses-operation.sh            \
#    uses.sh

dist_check_SCRIPTS = $(TESTS)

clean-local:
	$(RM) -f *.output

