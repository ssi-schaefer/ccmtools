#! /bin/sh

idldir=${top_srcdir}/../test/idl/facet_exception

${top_srcdir}/test/CppGenerator/test-loader.sh "facet_exception" \
                                               " " \
                                               "${idldir}/Hello.idl"
