## process this file with automake to produce Makefile.in

CLASSPATH_ENV = $(top_builddir):$(top_srcdir)/lib/antlr.jar
TESTS_ENVIRONMENT = top_builddir=$(top_builddir) top_srcdir=$(top_srcdir) \
                    JAVA=$(JAVA) CP=$(CP) RM=$(RM) MKDIR=$(MKDIR) \
                    PACKAGE=@PACKAGE@ CLASSPATH=$(CLASSPATH_ENV)

dist_noinst_SCRIPTS = test-loader.sh

# currently under investigation ... uncomment in local copy to test faster.

#TESTS = supports_types.sh

# tests are generally in order of decreasing complexity (we want to see whether
# the hard tests fail ; the easy tests will likely pass later).

TESTS = \
  help.sh                     \
  version.sh                  \
  owudb.sh                    \
  calculator.sh               \
  vp10.sh                     \
  module_mixed.sh             \
  module-reopen.sh            \
  module-nested.sh            \
  include_nested.sh           \
  include_dir.sh              \
  include.sh                  \
  receptacle-inheritance.sh   \
  facet-inheritance.sh        \
  supports-inheritance.sh     \
  home-factory-interface.sh   \
  receptacle_interface.sh     \
  facet_interface.sh          \
  supports_interface.sh       \
  attribute_interface.sh      \
  home_exception.sh           \
  receptacle_exception.sh     \
  facet_exception.sh          \
  supports_exception.sh       \
  home_types.sh               \
  receptacle_types.sh         \
  facet_types.sh              \
  supports_types.sh           \
  attribute_types.sh          \
  receptacle_not_connected.sh \
  receptacle_multiple.sh

dist_check_SCRIPTS = $(TESTS)

# these directories hold application logic and python modules for generated
# code.

EXTRA_DIST = \
  attribute_interface      \
  attribute_types          \
  Calculator               \
  constants                \
  facet_attribute          \
  facet_exception          \
  facet_inheritance        \
  facet_interface          \
  facet_types              \
  home_exception           \
  home_factory_interface   \
  home_types               \
  include                  \
  include_dir              \
  include_nested           \
  module_mixed             \
  module_nested            \
  module-reopen            \
  owudb                    \
  receptacle_exception     \
  receptacle_inheritance   \
  receptacle_interface     \
  receptacle_multiple      \
  receptacle_not_connected \
  receptacle_types         \
  supports_exception       \
  supports_inheritance     \
  supports_interface       \
  supports_types

clean-local:
	$(RM) -r -f sandbox

dist-hook:
	$(RM) -f -r `find $(distdir) -name CVS`
	$(RM) -f `find $(distdir) -name .cvsignore`

