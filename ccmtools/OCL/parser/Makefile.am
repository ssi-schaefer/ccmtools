## process this file with automake to produce Makefile.in
## $Id$

CLASSPATH_ENV = CLASSPATH=$(top_builddir):$(top_srcdir)/lib/antlr.jar:$(top_srcdir)/lib/oclmetamodel.jar:$(top_srcdir)/lib/mdr01.jar
AM_JAVACFLAGS = -g

grammar_file = $(srcdir)/OCL.g
generated_files = OclLexer.java OclParser.java OCLTokenTypes.java
source_files = OclParserException.java OclParsetreeCreator.java OclConstants.java
object_files = $(source_files:%.java,%.class)

dist_noinst_JAVA = $(generated_files) $(source_files)

EXTRA_DIST = $(grammar_file)

# all of the $(generated_files) get built at the same time, so we only need one
# of the makefile rules to actually do anything. the other two are blanks.
OclParser.java: $(grammar_file)
OCLTokenTypes.java: $(grammar_file)
OclLexer.java: $(grammar_file)
	$(CLASSPATH_ENV) $(JAVA) antlr.Tool $(grammar_file)
	$(RM) -f classnoinst.stamp $(object_files)

clean-local:
	$(RM) -f $(generated_files) OCLTokenTypes.txt

