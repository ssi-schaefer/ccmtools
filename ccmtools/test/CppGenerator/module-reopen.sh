#! /bin/sh

curdir=`pwd` ; cd ${top_srcdir} ; abssrcdir=`pwd` ; cd ${curdir}
idldir=${abssrcdir}/test/CppGenerator/module-reopen

${top_srcdir}/test/CppGenerator/test-loader.sh \
  "module_reopen" "-I${idldir}" "${idldir}/*.idl"

