#! /bin/sh

idldir=${top_srcdir}/../test/idl/helloworld

${top_srcdir}/test/CppGenerator/test-loader.sh \
  "helloworld-multiple-receptacles" \
  " " \
  "${idldir}/hello_multiple_receptacles.idl"

