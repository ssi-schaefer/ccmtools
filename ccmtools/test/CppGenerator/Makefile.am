## process this file with automake to produce Makefile.in

CLASSPATH_ENV = $(top_builddir):$(top_srcdir)/lib/antlr.jar
TESTS_ENVIRONMENT = top_builddir=$(top_builddir) top_srcdir=$(top_srcdir) \
                    JAVA=$(JAVA) CP=$(CP) RM=$(RM) MKDIR=$(MKDIR) \
                    PACKAGE=@PACKAGE@ CLASSPATH=$(CLASSPATH_ENV)

dist_noinst_SCRIPTS = test-loader.sh

# currently under investigation ... uncomment in local copy to test faster.

#TESTS = calculator.sh

# tests are generally in order of decreasing complexity (we want to see whether
# the hard tests fail ; the easy tests will likely pass later).

TESTS = \
  help.sh                     \
  version.sh                  \
  owudb.sh                    \
  calculator.sh               \
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
  home_user_types.sh          \
  receptacle_user_types.sh    \
  facet_user_types.sh         \
  supports_user_types.sh      \
  attribute_user_types.sh     \
  home_basic_types.sh         \
  receptacle_basic_types.sh   \
  facet_basic_types.sh        \
  supports_basic_types.sh     \
  attribute_basic_types.sh    \
  module.sh                   \
  home_factory.sh             \
  receptacle_not_connected.sh \
  receptacle_multiple.sh      \
  receptacle.sh               \
  facet_rename.sh             \
  facet.sh                    \
  supports.sh                 \
  attribute.sh

dist_check_SCRIPTS = $(TESTS)

# these directories hold application logic and python modules for generated
# code.

EXTRA_DIST = \
  attribute                \
  attribute_basic_types    \
  attribute_interface      \
  attribute_user_types     \
  Calculator               \
  constants                \
  facet                    \
  facet_attribute          \
  facet_basic_types        \
  facet_exception          \
  facet_inheritance        \
  facet_interface          \
  facet_rename             \
  facet_user_types         \
  home_basic_types         \
  home_exception           \
  home_factory             \
  home_factory_interface   \
  home_user_types          \
  include                  \
  include_dir              \
  include_nested           \
  module                   \
  module_mixed             \
  module_nested            \
  module-reopen            \
  owudb                    \
  receptacle               \
  receptacle_basic_types   \
  receptacle_exception     \
  receptacle_inheritance   \
  receptacle_interface     \
  receptacle_multiple      \
  receptacle_not_connected \
  receptacle_user_types    \
  supports                 \
  supports_basic_types     \
  supports_exception       \
  supports_inheritance     \
  supports_interface       \
  supports_user_types

clean-local:
	$(RM) -r -f sandbox

dist-hook:
	$(RM) -f -r `find $(distdir) -name CVS`
	$(RM) -f `find $(distdir) -name .cvsignore`

