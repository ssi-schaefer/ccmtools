#! /bin/sh

idldir=${top_srcdir}/../test/idl/supports

${top_srcdir}/test/CppGenerator/test-loader.sh "supports" \
                                               " " \
                                               "${idldir}/Hello.idl"

