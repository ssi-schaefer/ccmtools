#! /bin/sh

idldir=${top_srcdir}/../test/idl/receptacle_not_connected

${top_srcdir}/test/CppGenerator/test-loader.sh "receptacle_not_connected" \
                                               " " \
                                               "${idldir}/Hello.idl"
