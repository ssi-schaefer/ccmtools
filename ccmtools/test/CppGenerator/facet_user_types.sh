#! /bin/sh

idldir=${top_srcdir}/../test/idl/facet_user_types

${top_srcdir}/test/CppGenerator/test-loader.sh "facet_user_types" \
                                               " " \
                                               "${idldir}/Hello.idl"