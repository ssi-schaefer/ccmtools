#! /bin/sh

idldir=${top_srcdir}/../test/idl/facet_interface

${top_srcdir}/test/CppGenerator/test-loader.sh "facet_interface" \
                                               " " \
                                               "${idldir}/Hello.idl"

