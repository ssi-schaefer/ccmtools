## process this file with automake to produce Makefile.in

# build this directory before moving on to the template subdirs.
SUBDIRS = . PythonLocalTemplates

CLASSPATH_ENV = CLASSPATH=$(top_builddir)

AM_JAVACFLAGS = -g

dist_noinst_JAVA = PythonGenerator.java          \
                   PythonLocalGeneratorImpl.java
