#! /bin/sh

curdir=`pwd` ; cd ${top_srcdir} ; abssrcdir=`pwd` ; cd ${curdir}
idldir=${abssrcdir}/test/idl/include_nested

${top_srcdir}/test/CppGenerator/test-loader.sh "include_nested" \
                                               "-I${idldir}" \
                                               "${idldir}/Hello.idl"

