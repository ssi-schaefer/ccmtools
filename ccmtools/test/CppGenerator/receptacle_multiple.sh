#! /bin/sh

idldir=${top_srcdir}/../test/idl/receptacle_multiple

${top_srcdir}/test/CppGenerator/test-loader.sh "receptacle_multiple" \
                                               " " \
                                               "${idldir}/Hello.idl"

