#! /bin/sh

idldir=${top_srcdir}/../test/idl/receptacle_basic_types

${top_srcdir}/test/CppGenerator/test-loader.sh "receptacle_basic_types" \
                                               " " \
                                               "${idldir}/Hello.idl"
