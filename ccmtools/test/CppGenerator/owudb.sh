#! /bin/sh

idldir=${top_srcdir}/../test/idl/owudb

${top_srcdir}/test/CppGenerator/test-loader.sh owudb \
  "-I${idldir}/ifaces -I${idldir}/dbsql" \
  "${idldir}/dbsql/*.idl"

