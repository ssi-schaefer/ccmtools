#! /bin/sh

curdir=`pwd` ; cd ${top_srcdir} ; abssrcdir=`pwd` ; cd ${curdir}
idldir=${abssrcdir}/test/idl/include

${top_srcdir}/test/CppGenerator/test-loader.sh "include" \
                                               "-I${idldir}" \
                                               "${idldir}/Hello.idl"

