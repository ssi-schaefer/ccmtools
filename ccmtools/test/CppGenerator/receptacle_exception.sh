#! /bin/sh

idldir=${top_srcdir}/../test/idl/receptacle_exception

${top_srcdir}/test/CppGenerator/test-loader.sh "receptacle_exception" \
                                               " " \
                                               "${idldir}/Hello.idl"
