#! /bin/sh

curdir=`pwd` ; cd ${top_srcdir} ; abssrcdir=`pwd` ; cd ${curdir}
idldir=${abssrcdir}/test/idl/include_dir/comp

${top_srcdir}/test/CppGenerator/test-loader.sh "include_dir" \
                                               "-I${idldir}" \
                                               "${idldir}/Hello.idl"

