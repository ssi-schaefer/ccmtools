#! /bin/sh

curdir=`pwd` ; cd ${top_srcdir} ; abssrcdir=`pwd` ; cd ${curdir}
idldir=${abssrcdir}/test/CppGenerator/owudb

${top_srcdir}/test/CppGenerator/test-loader.sh \
  "owudb" "-I${idldir}" "${idldir}/*/*.idl"

