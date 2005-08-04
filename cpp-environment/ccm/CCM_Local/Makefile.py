# according to the INSTALL file, this directory is built as a
# subdirectory of the cpp-environment package. however, sometimes one
# wants to make this directory and its descendants one separate
# package.

try:
    PACKAGE_NAME('ccm-local')
    PACKAGE_VERSION('0.5.2')
except Error, e:
    # we'll get here if this is not the package root, and have to
    # ignore this.
    pass
