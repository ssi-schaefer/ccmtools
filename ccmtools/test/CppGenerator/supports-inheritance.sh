#! /bin/sh

idldir=${top_srcdir}/../test/idl/supports_inheritance

${top_srcdir}/test/CppGenerator/test-loader.sh "supports_inheritance" \
                                               " " \
                                               "${idldir}/Hello.idl"
