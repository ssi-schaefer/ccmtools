## process this file with automake to produce Makefile.in

SUBDIRS = scripts

CLASSPATH_ENV = CLASSPATH=$(top_builddir):$(top_srcdir)/lib/antlr.jar

AM_JAVACFLAGS = -g

dist_noinst_DATA = \
    ConsoleCodeGenerator.java   \
    ConsoleDriverImpl.java      \
    ConsoleEnvironmentImpl.java \
    Environment.java

noinst_JAVA = $(dist_noinst_DATA)
