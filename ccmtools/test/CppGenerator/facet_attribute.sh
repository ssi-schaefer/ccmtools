#! /bin/sh

idldir=${top_srcdir}/../test/idl/facet_attribute

${top_srcdir}/test/CppGenerator/test-loader.sh "facet_attribute" \
                                               " " \
                                               "${idldir}/Hello.idl"

