## preferred provider.
EXTERNAL_LIBRARY(defroot='/usr',
                 lib_bases=['python2.3'],
                 provide=['Python.h'],
                 incdir='include/python2.3')

## backup provider.
EXTERNAL_LIBRARY(defroot='/usr',
                 lib_bases=['python2.2'],
                 provide=['Python.h'],
                 incdir='include/python2.2')

