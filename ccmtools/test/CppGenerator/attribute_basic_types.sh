#! /bin/sh

idldir=${top_srcdir}/../test/idl/attribute_basic_types

${top_srcdir}/test/CppGenerator/test-loader.sh "attribute_basic_types" \
                                               " " \
                                               "${idldir}/Hello.idl"
