#! /bin/sh

idldir=${top_srcdir}/../test/idl/supports_interface

${top_srcdir}/test/CppGenerator/test-loader.sh "supports_interface" \
                                               " " \
                                               "${idldir}/Hello.idl"

