#! /bin/sh

idldir=${top_srcdir}/../test/idl/receptacle_inheritance

${top_srcdir}/test/CppGenerator/test-loader.sh "receptacle_inheritance" \
                                               " " \
                                               "${idldir}/Hello.idl"
