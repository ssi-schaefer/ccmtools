# $Id$

# The try block is only necessary if wx-toolsbox-package is used
# as a whole
try:
    PACKAGE_NAME('mico')
    PACKAGE_VERSION('0.1.0')
except Error, e:
    # we'll get here if this is not the package root, and have to
    # ignore this.
    pass
