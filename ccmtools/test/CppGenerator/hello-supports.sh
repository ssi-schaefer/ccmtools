#! /bin/sh

idldir=${top_srcdir}/../test/idl/helloworld

${top_srcdir}/test/CppGenerator/test-loader.sh "helloworld-supports" \
  " " \
  "${idldir}/hello_supports.idl"

