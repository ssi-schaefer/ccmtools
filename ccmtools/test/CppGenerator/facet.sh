#! /bin/sh

idldir=${top_srcdir}/../test/idl/facet

${top_srcdir}/test/CppGenerator/test-loader.sh "facet" \
                                               " " \
                                               "${idldir}/Hello.idl"

