#! /bin/sh

idldir=${top_srcdir}/../test/idl/home_exception

${top_srcdir}/test/CppGenerator/test-loader.sh "home_exception" \
                                               " " \
                                               "${idldir}/Hello.idl"
