#! /bin/sh

idldir=${top_srcdir}/../test/idl/attribute

${top_srcdir}/test/CppGenerator/test-loader.sh "attribute" \
                                               " " \
                                               "${idldir}/Hello.idl"
