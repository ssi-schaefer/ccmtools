#! /bin/sh

idldir=${top_srcdir}/../test/idl/receptacle_user_types

${top_srcdir}/test/CppGenerator/test-loader.sh "receptacle_user_types" \
                                               " " \
                                               "${idldir}/Hello.idl"