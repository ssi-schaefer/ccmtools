## process this file with automake to produce Makefile.in
## $Id$

JAVAROOT = .
CLASSPATH_ENV = CLASSPATH=.:$(top_builddir):$(top_srcdir)/lib/antlr.jar
TESTS_ENVIRONMENT = top_builddir=$(top_builddir) top_srcdir=$(top_srcdir) \
                    JAVA=$(JAVA) $(CLASSPATH_ENV) \
                    PATH=$(top_srcdir)/UI/scripts::${PATH} \
                    CP=$(CP) RM=$(RM) MKDIR=$(MKDIR)

dist_noinst_SCRIPTS = test-loader.sh

TESTS = \
    help.sh                   	  \
    version.sh                	  \
    hello-facet.sh            	  \
    hello-receptacle.sh       	  \
    hello-multiple-receptacles.sh \
    hello-supports.sh             \
    calculator.sh

dist_check_SCRIPTS = $(TESTS)

clean-local:
	$(RM) -r -f sandbox

