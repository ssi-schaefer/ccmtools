## process this file with automake to produce Makefile.in


#SUBDIRS = Metamodel IDL3Parser CodeGenerator IDLGenerator CppGenerator
SUBDIRS = CppGenerator CppDbcGenerator

EXTRA_DIST = idl ccd

dist-hook:
	$(RM) -f -r `find $(distdir) -name CVS`
	$(RM) -f `find $(distdir) -name .cvsignore`

