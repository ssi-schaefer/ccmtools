## process this file with automake to produce Makefile.in
## $Id$

JAVAROOT = .
CLASSPATH_ENV = CLASSPATH=.:$(top_builddir):$(top_srcdir)/lib/antlr.jar
TESTS_ENVIRONMENT = top_builddir=$(top_builddir) top_srcdir=$(top_srcdir) \
                    JAVA=$(JAVA) $(CLASSPATH_ENV)

dist_noinst_JAVA = PrettyPrinterImpl.java Main.java
dist_noinst_SCRIPTS = test-loader.sh

TESTS = \
    component.sh                 \
    enum.sh                      \
    home.sh                      \
    interface.sh                 \
    operations-primitivetypes.sh \
    provides-operation.sh        \
    provides.sh                  \
    struct.sh                    \
    supports-operation.sh        \
    supports.sh                  \
    uses-multiple-operation.sh   \
    uses-multiple.sh             \
    uses-operation.sh            \
    uses.sh

dist_check_SCRIPTS = $(TESTS)

clean-local:
	$(RM) -f *.output

