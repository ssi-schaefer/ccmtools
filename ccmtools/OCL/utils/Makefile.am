## process this file with automake to produce Makefile.in
## $Id$

# build this directory before moving on to the template subdirs.

CLASSPATH_ENV = CLASSPATH=$(CLASSPATH):$(top_builddir)

AM_JAVACFLAGS = -g

dist_noinst_JAVA = OclConstants.java         \
                   OclChecker.java           \
                   OclMdrAdapter.java        \
                   OclModelCreator.java      \
                   OclXmlWriter.java         \
                   OclParserException.java   \
                   OclParsetreeCreator.java  \
                   OclNormalization.java     \
                   OclTextWriter.java        \
                   OclLexer.java OclParser.java OCLTokenTypes.java \
                   Main.java OclCreatorImp.java Factory.java


