## process this file with automake to produce Makefile.in

if BUILD_DOCS
SUBDIRS_DOCS = doc
else
SUBDIRS_DOCS =
endif

if BUILD_TESTS
SUBDIRS_TESTS = test
else
SUBDIRS_TESTS =
endif

SUBDIRS = Metamodel IDL3Parser CCDGenerator .     \
          CodeGenerator IDLGenerator CppGenerator \
          UI lib $(SUBDIRS_DOCS) $(SUBDIRS_TESTS)

DIST_SUBDIRS = Metamodel IDL3Parser CCDGenerator .     \
               CodeGenerator IDLGenerator CppGenerator \
               UI lib doc test

dist_noinst_SCRIPTS = template-info

CLASSPATH_ENV = CLASSPATH=${top_builddir}

extra_java_src = Constants.java.in
noinst_JAVA = $(top_builddir)/Constants.java

EXTRA_DIST = binary-dist.in $(extra_java_src) README.binary RELEASE-0.3.2 \
             confix.example debian autogen.py ccmtools-autogen-top-check

clean-local:
	$(RM) -f -r $(top_builddir)/ccmtools

dist-hook:
	$(RM) -f -r `find $(distdir) -name CVS`
	$(RM) -f `find $(distdir) -name .cvsignore`

install-data-local:
	@echo
	@echo "*** IMPORTANT! UPDATE YOUR CLASSPATH! ***"
	@echo "Jar files for @PACKAGE@ are now installed in :"
	@echo "$(jardir)"
	@echo
	@echo "Add the following Jar files to your CLASSPATH environment :"
	@echo "$(jardir)/@PACKAGE@.jar"
	@echo "$(jardir)/antlr.jar"
	@echo

