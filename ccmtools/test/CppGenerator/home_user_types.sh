#! /bin/sh

idldir=${top_srcdir}/../test/idl/home_user_types

${top_srcdir}/test/CppGenerator/test-loader.sh "home_user_types" \
                                               " " \
                                               "${idldir}/Hello.idl"

