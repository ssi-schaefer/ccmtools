#! /bin/sh

idldir=${top_srcdir}/../test/idl/module_mixed

${top_srcdir}/test/CppGenerator/test-loader.sh "module_mixed" \
                                               " " \
                                               "${idldir}/*.idl"

