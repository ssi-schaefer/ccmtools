## process this file with automake to produce Makefile.in
## $Id$

CLASSPATH_ENV = CLASSPATH=$(top_builddir):$(top_srcdir)/lib/antlr.jar

grammar_file = $(srcdir)/idl3.g
generated_files = IDL3Lexer.java IDL3Parser.java IDL3TokenTypes.java
source_files = IDL3SymbolTable.java ParserManager.java DeclaratorHelper.java
object_files = $(source_files:%.java,%.class)

dist_noinst_JAVA = $(generated_files) $(source_files)

EXTRA_DIST = $(grammar_file)

# all of the $(generated_files) get built at the same time, so we only need one
# of the makefile rules to actually do anything. the other two are blanks.
IDL3Parser.java: $(grammar_file)
IDL3TokenTypes.java: $(grammar_file)
IDL3Lexer.java: $(grammar_file)
	$(CLASSPATH_ENV) $(JAVA) antlr.Tool $(grammar_file)
	$(RM) -f classnoinst.stamp $(object_files)

clean-local:
	$(RM) -f $(generated_files) IDL3TokenTypes.txt

