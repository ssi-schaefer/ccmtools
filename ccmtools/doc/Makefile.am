## process this file with automake to produce Makefile.in
## $Id$

SUBDIRS = manual tutorial

dist_noinst_DATA = MOF.xml.zip CodeGenerator.xml.zip roadmap.fig

noinst_DATA = apidocs

CLASSPATH_ENV = CLASSPATH=$(top_builddir):$(top_srcdir)/lib/jdom.jar:$(top_srcdir)/lib/antlr.jar

sources = $(top_srcdir)/Metamodel/BaseIDL/*.java                 \
          $(top_srcdir)/Metamodel/ComponentIDL/*.java            \
          $(top_srcdir)/CodeGenerator/*.java                     \
          $(top_builddir)/CodeGenerator/TemplateManagerImpl.java \
          $(top_srcdir)/IDL3Parser/*.java                        \
          $(top_srcdir)/CCDGenerator/*.java

.PHONY: apidocs
apidocs:
	@ $(MKDIR) -p api
	$(CLASSPATH_ENV) $(JAVADOC) -d api $(sources)

clean-local:
	$(RM) -f -r api

