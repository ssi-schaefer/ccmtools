## process this file with automake to produce Makefile.in

CLASSPATH_ENV = $(top_builddir):$(top_srcdir)/lib/antlr.jar
TESTS_ENVIRONMENT = top_builddir=$(top_builddir) top_srcdir=$(top_srcdir) \
                    JAVA=$(JAVA) CP=$(CP) RM=$(RM) MKDIR=$(MKDIR) \
                    PACKAGE=@PACKAGE@ CLASSPATH=$(CLASSPATH_ENV)

dist_noinst_SCRIPTS = test-loader.sh

# currently under investigation ... uncomment in local copy to test faster.

TESTS = \
  help.sh version.sh \
  attribute_types.sh \
  supports_types.sh supports_exception.sh supports_inheritance.sh \
  facet_types.sh facet_exception.sh facet_inheritance.sh facet_rename.sh \
  receptacle_types.sh receptacle_exception.sh receptacle_inheritance.sh receptacle_multiple.sh receptacle_not_connected.sh \
  home_types.sh home_exception.sh \
  include_nested.sh \
  module_mixed.sh module_nested.sh  module_reopen.sh


dist_check_SCRIPTS = $(TESTS)

# these directories hold application logic and python modules for generated
# code.

EXTRA_DIST = \
  Calculator               \
  constants                \
  facet_attribute          \
  facet_exception          \
  facet_inheritance        \
  facet_types              \
  home_exception           \
  home_types               \
  include_nested           \
  module_mixed             \
  module_nested            \
  module-reopen            \
  owudb                    \
  receptacle_exception     \
  receptacle_inheritance   \
  receptacle_multiple      \
  receptacle_not_connected \
  receptacle_types         \
  supports_exception       \
  supports_inheritance     \
  supports_types

clean-local:
	$(RM) -r -f sandbox

dist-hook:
	$(RM) -f -r `find $(distdir) -name CVS`
	$(RM) -f `find $(distdir) -name .cvsignore`

