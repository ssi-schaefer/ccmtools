## process this file with automake to produce Makefile.in

CLASSPATH_ENV = CLASSPATH=$(top_builddir)

AM_JAVACFLAGS = -g

dist_noinst_DATA = \
  CodeGenerator.java             \
  CCMMOFGraphTraverserImpl.java  \
  Driver.java                    \
  GraphTraverser.java            \
  NodeHandler.java               \
  PythonTemplateImpl.java        \
  PythonTemplateManagerImpl.java \
  TemplateHandler.java           \
  Template.java                  \
  TemplateManager.java

noinst_JAVA = $(dist_noinst_DATA)
