## process this file with automake to produce Makefile.in
## $Id$

# build this directory before moving on to the template subdirs.
SUBDIRS = . CppLocalTemplates  \
            CppMirrorTemplates \
            CppPythonTemplates

CLASSPATH_ENV = CLASSPATH=$(top_builddir)

dist_noinst_JAVA = CppGenerator.java           \
                   CppLocalGeneratorImpl.java  \
                   CppMirrorGeneratorImpl.java \
                   CppPythonGeneratorImpl.java

