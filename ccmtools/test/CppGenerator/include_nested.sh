#! /bin/sh

idldir=${top_srcdir}/../test/idl/include_nested

${top_srcdir}/test/CppGenerator/test-loader.sh "include_nested" \
                                               " " \
                                               "${idldir}/Hello.idl"

