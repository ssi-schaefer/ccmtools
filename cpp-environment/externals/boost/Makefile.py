# $Id$

# The try block is only necessary if wx-toolsbox-package is used
# as a whole
try:
    PACKAGE_NAME('boost')
    PACKAGE_VERSION('1.2.10.p1')
except Error, e:
    # we'll get here if this is not the package root, and have to
    # ignore this.
    pass
