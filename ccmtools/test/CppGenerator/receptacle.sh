#! /bin/sh

idldir=${top_srcdir}/../test/idl/receptacle

${top_srcdir}/test/CppGenerator/test-loader.sh "receptacle" \
                                               " " \
                                               "${idldir}/Hello.idl"

