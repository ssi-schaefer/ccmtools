#! /bin/sh

idldir=${top_srcdir}/../test/idl/home_factory_interface

${top_srcdir}/test/CppGenerator/test-loader.sh "home_factory_interface" \
                                               " " \
                                               "${idldir}/Hello.idl"
