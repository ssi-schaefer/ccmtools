#! /bin/sh

idldir=${top_srcdir}/../test/idl/facet_basic_types

${top_srcdir}/test/CppGenerator/test-loader.sh "facet_basic_types" \
                                               " " \
                                               "${idldir}/Hello.idl"
