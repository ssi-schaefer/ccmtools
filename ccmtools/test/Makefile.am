## process this file with automake to produce Makefile.in
## $Id$

SUBDIRS = CppGenerator
#Metamodel IDL3Parser CodeGenerator \
#          CppGenerator CCDGenerator

EXTRA_DIST = idl ccd

dist-hook:
	$(RM) -f -r `find $(distdir) -name CVS`
	$(RM) -f `find $(distdir) -name .cvsignore`

