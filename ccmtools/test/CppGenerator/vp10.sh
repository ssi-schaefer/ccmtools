#! /bin/sh

curdir=`pwd` ; cd ${top_srcdir} ; abssrcdir=`pwd` ; cd ${curdir}
idldir=${abssrcdir}/test/CppGenerator/vp10

${top_srcdir}/test/CppGenerator/test-loader.sh \
  "vp10" "-I${idldir}" "${idldir}/*.idl"

