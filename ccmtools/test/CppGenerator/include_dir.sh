#! /bin/sh

idldir=${top_srcdir}/../test/idl/include_dir

${top_srcdir}/test/CppGenerator/test-loader.sh "include_dir" \
                                               " " \
                                               "${idldir}/comp/Hello.idl"

