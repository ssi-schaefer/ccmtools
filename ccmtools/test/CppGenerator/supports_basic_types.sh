#! /bin/sh

idldir=${top_srcdir}/../test/idl/supports_basic_types

${top_srcdir}/test/CppGenerator/test-loader.sh "supports_basic_types" \
                                               " " \
                                               "${idldir}/Hello.idl"
