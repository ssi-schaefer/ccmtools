#! /bin/sh

idldir=${top_srcdir}/../test/idl/home_basic_types

${top_srcdir}/test/CppGenerator/test-loader.sh "home_basic_types" \
                                               " " \
                                               "${idldir}/Hello.idl"

