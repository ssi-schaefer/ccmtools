#! /bin/sh

idldir=${top_srcdir}/../test/idl/module

${top_srcdir}/test/CppGenerator/test-loader.sh "module" \
  " " \
  "${idldir}/Hello.idl"

