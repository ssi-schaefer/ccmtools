## process this file with automake to produce Makefile.in
## $Id$

JAVAROOT = .
CLASSPATH_ENV = CLASSPATH=.:$(top_builddir):$(top_srcdir)/lib/antlr.jar
TESTS_ENVIRONMENT = top_builddir=$(top_builddir) top_srcdir=$(top_srcdir) \
                    JAVA=$(JAVA) $(CLASSPATH_ENV) MV=$(MV) RM=$(RM) \
                    PATH=$(top_srcdir)/UI/scripts:${PATH} MKDIR=$(MKDIR) \
                    PACKAGE=@PACKAGE@ MAJORMINOR=@CCMTOOLS_MAJORMINOR@

dist_noinst_SCRIPTS = test-loader.sh

TESTS = \
  interface.sh             \
  interface-inheritance.sh \
  interface-short-names.sh

dist_check_SCRIPTS = $(TESTS)

clean-local:
	$(RM) -f *.output
	$(RM) -f *.idl2
	$(RM) -f *.idl3
	$(RM) -f *.idl3mirror
	$(RM) -f *.idl2mirror
	$(RM) -f CCM_*
