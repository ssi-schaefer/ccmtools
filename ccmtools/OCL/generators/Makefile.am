## process this file with automake to produce Makefile.in
## $Id$

# build this directory before moving on to the template subdirs.

CLASSPATH_ENV = CLASSPATH=$(top_builddir):$(top_srcdir)/lib/antlr.jar:$(top_srcdir)/lib/oclmetamodel.jar:$(top_srcdir)/lib/mdr01.jar

AM_JAVACFLAGS = -g

dist_noinst_JAVA = ClassIterator.java         \
                   ComponentIterator.java     \
                   ConstraintCode.java        \
                   ConstraintCppCode.java     \
                   HomeIterator.java          \
                   InterfaceIterator.java     \
                   OclCodeGenerator.java      \
                   OclCppGenerator.java       \
                   OclStandardGenerator.java  \
                   OclTypeChecker.java        \
                   ValueIterator.java

