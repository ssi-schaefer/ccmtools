## process this file with automake to produce Makefile.in

#SUBDIRS = Metamodel
#SUBDIRS = IDL3Parser
#SUBDIRS = CodeGenerator
#SUBDIRS = IDLGenerator
#SUBDIRS = CppGenerator
#SUBDIRS = CppRemoteGenerator
#SUBDIRS = CppDbcGenerator

SUBDIRS = CppGenerator CppDbcGenerator CppRemoteGenerator

EXTRA_DIST = idl 

dist-hook:
	$(RM) -f -r `find $(distdir) -name CVS`
	$(RM) -f `find $(distdir) -name .cvsignore`

