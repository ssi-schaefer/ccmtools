## process this file with automake to produce Makefile.in
## $Id$

SUBDIRS = scripts

CLASSPATH_ENV = CLASSPATH=$(top_builddir):$(top_srcdir)/lib/antlr.jar

dist_noinst_DATA = \
    ConsoleCodeGenerator.java   \
    ConsoleDriverImpl.java      \
    ConsoleEnvironmentImpl.java \
    Environment.java

noinst_JAVA = $(dist_noinst_DATA)
