## process this file with automake to produce Makefile.in

#SUBDIRS = Metamodel
#SUBDIRS = IDL3Parser
#SUBDIRS = CodeGenerator
#SUBDIRS = IDLGenerator
SUBDIRS = CppGenerator
#SUBDIRS = CppDbcGenerator

#SUBDIRS = Metamodel IDL3Parser CodeGenerator IDLGenerator CppGenerator

EXTRA_DIST = idl ccd

dist-hook:
	$(RM) -f -r `find $(distdir) -name CVS`
	$(RM) -f `find $(distdir) -name .cvsignore`

