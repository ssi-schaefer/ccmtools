## process this file with automake to produce Makefile.in

# build this directory before moving on to the template subdirs.
SUBDIRS = . \
  CppLocalTemplates      \
  CppLocalTestTemplates  \
  CppLocalDbcTemplates   \
  CppRemoteTemplates     \
  CppRemoteTestTemplates \
  CppPythonTemplates

#CLASSPATH_ENV = CLASSPATH=$(CLASSPATH):$(top_builddir)
CLASSPATH_ENV = CLASSPATH=$(CLASSPATH):$(top_builddir):$(top_srcdir)/lib/antlr.jar:$(top_srcdir)/lib/oclmetamodel.jar:$(top_srcdir)/lib/mdr01.jar

AM_JAVACFLAGS = -g

dist_noinst_JAVA = \
  CppGenerator.java               \
  CppLocalGeneratorImpl.java      \
  CppLocalTestGeneratorImpl.java  \
  CppLocalDbcGeneratorImpl.java   \
  CppRemoteGeneratorImpl.java     \
  CppRemoteTestGeneratorImpl.java \
  CppPythonGeneratorImpl.java

