## process this file with automake to produce Makefile.in

jar_DATA = @PACKAGE@.jar

dist_jar_DATA = $(srcdir)/antlr.jar $(srcdir)/jdom.jar

java_classes = $(top_builddir)/@PACKAGE@/Metamodel/BaseIDL/*.class      \
               $(top_builddir)/@PACKAGE@/Metamodel/ComponentIDL/*.class \
               $(top_builddir)/@PACKAGE@/IDL3Parser/*.class             \
               $(top_builddir)/@PACKAGE@/CCDGenerator/*.class

@PACKAGE@.jar: $(java_classes)
	$(JAR) cf @PACKAGE@.jar -C $(top_builddir) @PACKAGE@

clean-local:
	$(RM) -f -r $(jar_DATA)

install-data-local:
	@echo
	@echo "*** IMPORTANT! UPDATE YOUR CLASSPATH! ***"
	@echo "Jar files for @PACKAGE@ (version @VERSION@) are installed in :"
	@echo "$(jardir)"
	@echo
	@echo "Add the following jar files to your CLASSPATH environment :"
	@echo "$(jardir)/@PACKAGE@.jar"
	@echo "$(jardir)/antlr.jar"
	@echo

