#! /bin/sh

idldir=${top_srcdir}/../test/idl/supports_exception

${top_srcdir}/test/CppGenerator/test-loader.sh "supports_exception" \
                                               " " \
                                               "${idldir}/Hello.idl"
