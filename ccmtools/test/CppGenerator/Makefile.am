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
TESTS = help.sh                     \
	    version.sh                  \
	    attribute.sh 		\
            attribute_basic_types.sh 	\
            attribute_user_types.sh 	\
	    supports.sh 		\
	    supports_basic_types.sh     \
            supports_exception.sh       \
            supports_user_types.sh      \
            facet.sh                    \
            facet_basic_types.sh        \
            facet_exception.sh          \
            facet_user_types.sh         \
	    receptacle.sh               \
            receptacle_basic_types.sh   \
            receptacle_exception.sh     \
            receptacle_multiple.sh      \
            receptacle_user_types.sh    \
	    home_factory.sh             \
            home_exception.sh           \
            home_basic_types.sh         \
            include.sh        		\
            include_dir.sh		\
            include_nested.sh		\
            module.sh   		\
            module-nested.sh		\
            module-reopen.sh            \
            module_mixed.sh

#           calculator.sh
#           hello-facet.sh                \
#           hello-supports.sh             \
#           hello-receptacle.sh           \
#           hello-multiple-receptacles.sh 

dist_check_SCRIPTS = $(TESTS)

# these directories hold application logic and python modules for generated
# code.

EXTRA_DIST = Calculator

clean-local:
	$(RM) -r -f sandbox

dist-hook:
	$(RM) -f -r `find $(distdir) -name CVS`
	$(RM) -f `find $(distdir) -name .cvsignore`

