#! /bin/sh

curdir=`pwd` ; cd ${top_srcdir} ; abssrcdir=`pwd` ; cd ${curdir}
idldir=${abssrcdir}/test/idl/module_reopen

${top_srcdir}/test/CppGenerator/test-loader.sh "module-reopen" \
  "-I${idldir}" \
  "${idldir}/*.idl"

