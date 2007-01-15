# according to the INSTALL file, this directory is built as a
# subdirectory of the cpp-environment package. however, sometimes one
# wants to make this directory and its descendants one separate
# package.

try:
    PACKAGE_NAME('ccm-runtime-local')
    PACKAGE_VERSION('0.6.18')
except Error, e:
    # we'll get here if this is not the package root, and have to
    # ignore this.
    pass

EXTRA_DIST('coco2.in')
