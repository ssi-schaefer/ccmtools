#! /bin/sh

idldir=${top_srcdir}/../test/idl/module_nested

${top_srcdir}/test/CppGenerator/test-loader.sh "module-nested" \
  " " \
  "${idldir}/Hello.idl"

