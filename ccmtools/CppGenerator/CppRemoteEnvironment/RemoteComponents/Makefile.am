## process this file with automake to produce Makefile.in

environmentdir = $(TEMPLATE_ROOT)/CppRemoteEnvironment/RemoteComponents
dist_environment_DATA = \
  CCM.idl \
  Makefile.py

EXTRA_DIST = Makefile.idl

install-data-local:
	$(CP) Makefile.idl $(environmentdir)/Makefile

