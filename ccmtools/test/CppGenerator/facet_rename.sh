#! /bin/sh

idldir=${top_srcdir}/../test/idl/facet_rename

${top_srcdir}/test/CppGenerator/test-loader.sh "facet_rename" \
                                               " " \
                                               "${idldir}/Hello.idl"

