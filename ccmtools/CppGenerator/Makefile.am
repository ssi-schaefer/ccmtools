## process this file with automake to produce Makefile.in
## $Id$

# build this directory before moving on to the template subdirs.
SUBDIRS = . CppLocalTemplates      \
            CppRemoteTemplates     \
            CppRemoteTestTemplates \
            CppMirrorTemplates     \
            CppPythonTemplates

CLASSPATH_ENV = CLASSPATH=$(top_builddir)

AM_JAVACFLAGS = -g

dist_noinst_JAVA = CppGenerator.java               \
                   CppLocalGeneratorImpl.java      \
                   CppRemoteGeneratorImpl.java     \
                   CppRemoteTestGeneratorImpl.java \
                   CppMirrorGeneratorImpl.java     \
                   CppPythonGeneratorImpl.java

