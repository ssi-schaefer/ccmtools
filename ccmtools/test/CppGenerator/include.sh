#! /bin/sh

idldir=${top_srcdir}/../test/idl/include

${top_srcdir}/test/CppGenerator/test-loader.sh "include" \
                                               " " \
                                               "${idldir}/Hello.idl"

