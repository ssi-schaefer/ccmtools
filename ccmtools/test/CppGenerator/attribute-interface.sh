#! /bin/sh

idldir=${top_srcdir}/../test/idl/attribute_interface

${top_srcdir}/test/CppGenerator/test-loader.sh "attribute_interface" \
                                               " " \
                                               "${idldir}/Hello.idl"
