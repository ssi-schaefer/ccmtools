#! /bin/sh

idldir=${top_srcdir}/../test/idl/facet_inheritance

${top_srcdir}/test/CppGenerator/test-loader.sh "facet_inheritance" \
                                               " " \
                                               "${idldir}/Hello.idl"
