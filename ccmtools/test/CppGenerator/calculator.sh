#! /bin/sh

curdir=`pwd` ; cd ${top_srcdir} ; abssrcdir=`pwd` ; cd ${curdir}
idldir=${abssrcdir}/test/CppGenerator/Calculator

${top_srcdir}/test/CppGenerator/test-loader.sh \
  "Calculator" "-I${idldir}" "${idldir}/*.idl"

