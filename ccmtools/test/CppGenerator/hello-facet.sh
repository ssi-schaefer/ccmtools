#! /bin/sh

idldir=${top_srcdir}/../test/idl/helloworld

${top_srcdir}/test/CppGenerator/test-loader.sh "helloworld-facet" \
  " " \
  "${idldir}/hello_facets.idl"

