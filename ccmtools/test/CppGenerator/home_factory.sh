#! /bin/sh

idldir=${top_srcdir}/../test/idl/home_factory

${top_srcdir}/test/CppGenerator/test-loader.sh "home_factory" \
                                               " " \
                                               "${idldir}/Hello.idl"

