## process this file with automake to produce Makefile.in

JAVAROOT = .
CLASSPATH_ENV = CLASSPATH=.:$(top_builddir):$(top_srcdir)/lib/jdom.jar:$(top_srcdir)/lib/antlr.jar
TESTS_ENVIRONMENT = top_builddir=$(top_builddir) top_srcdir=$(top_srcdir) \
                    JAVA=$(JAVA) $(CLASSPATH_ENV)

dist_check_JAVA = Main.java

TESTS =

XFAIL_TESTS =

clean-local:
	$(RM) -f *.output

