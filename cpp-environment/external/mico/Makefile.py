EXTERNAL_LIBRARY(
    enabled='no',
    defroot='/usr/local',
    incdir='include',
    libdir='lib',
    lib_bases=['mico2.3.11',
               'micocoss2.3.11',
	       #'micoccm2.3.11',
               # mico needs dlopen() and friends
               'dl'],
    provide=['CORBA.h'],
    featuremacros=['HAVE_MICO']
    )
