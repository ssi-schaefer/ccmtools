#! /bin/sh

curdir=`pwd` ; cd ${top_srcdir} ; abssrcdir=`pwd` ; cd ${curdir}
idldir=${abssrcdir}/test/idl/owudb

${top_srcdir}/test/CppGenerator/test-loader.sh owudb \
  "-I${idldir}" \
  "${idldir}/*/*.idl"

