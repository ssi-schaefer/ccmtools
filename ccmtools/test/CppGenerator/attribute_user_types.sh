#! /bin/sh

idldir=${top_srcdir}/../test/idl/attribute_user_types

${top_srcdir}/test/CppGenerator/test-loader.sh "attribute_user_types" \
                                               " " \
                                               "${idldir}/Hello.idl"

