#! /bin/sh

idldir=${top_srcdir}/../test/idl/owudb

${top_srcdir}/test/CppGenerator/test-loader.sh owudb \
  "-I${idldir}" \
  "${idldir}/owudb.idl"

