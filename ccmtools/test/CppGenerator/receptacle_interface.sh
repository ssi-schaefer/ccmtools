#! /bin/sh

idldir=${top_srcdir}/../test/idl/receptacle_interface

${top_srcdir}/test/CppGenerator/test-loader.sh "receptacle_interface" \
                                               " " \
                                               "${idldir}/Hello.idl"

