#! /bin/sh

idldir=${top_srcdir}/../test/idl/supports_user_types

${top_srcdir}/test/CppGenerator/test-loader.sh "supports_user_types" \
                                               " " \
                                               "${idldir}/Hello.idl"