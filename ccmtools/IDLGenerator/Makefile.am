## process this file with automake to produce Makefile.in
## $Id$

# build this directory before moving on to the template subdirs.
SUBDIRS = . IDL3Templates       \
            IDL3MirrorTemplates \
            IDL2Templates

CLASSPATH_ENV = CLASSPATH=$(top_builddir)

dist_noinst_JAVA = IDLGenerator.java            \
                   IDL3GeneratorImpl.java       \
                   IDL3MirrorGeneratorImpl.java \
                   IDL2GeneratorImpl.java
