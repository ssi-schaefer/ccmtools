## process this file with automake to produce Makefile.in
## $Id$

JAVAROOT = .
CLASSPATH_ENV = CLASSPATH=.:$(top_builddir):$(top_srcdir)/lib/antlr.jar
TESTS_ENVIRONMENT = top_builddir=$(top_builddir) top_srcdir=$(top_srcdir) \
                    JAVA=$(JAVA) $(CLASSPATH_ENV) \
                    PATH=$(top_srcdir)/UI/scripts::${PATH} \
                    CP=$(CP) RM=$(RM) MKDIR=$(MKDIR) \
                    PACKAGE=@PACKAGE@ MAJORMINOR=@CCMTOOLS_MAJORMINOR@

dist_noinst_SCRIPTS = test-loader.sh

# tests are generally in order of increasing complexity.

TESTS = owudb.sh

#TESTS = \
#    help.sh                       \
#    version.sh                    \
#    hello-facet.sh                \
#    hello-supports.sh             \
#    hello-receptacle.sh           \
#    hello-multiple-receptacles.sh \
#    module.sh                     \
#    module-nested.sh              \
#    module-reopen.sh              \
#    calculator.sh                 \
#    owudb.sh

dist_check_SCRIPTS = $(TESTS)

# these directories hold application logic and python modules for generated
# code.

EXTRA_DIST = Calculator

clean-local:
	$(RM) -r -f sandbox

dist-hook:
	$(RM) -f -r `find $(distdir) -name CVS`
	$(RM) -f `find $(distdir) -name .cvsignore`

