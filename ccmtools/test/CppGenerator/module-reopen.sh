#! /bin/sh

idldir=${top_srcdir}/../test/idl/module_reopen

${top_srcdir}/test/CppGenerator/test-loader.sh "module-reopen" \
  "-I${idldir}" \
  "${idldir}/*.idl"

