#! /bin/sh

curdir=`pwd` ; cd ${top_srcdir} ; abssrcdir=`pwd` ; cd ${curdir}
idldir=${abssrcdir}/test/idl/module_mixed

${top_srcdir}/test/CppGenerator/test-loader.sh "module_mixed" \
                                               " " \
                                               "${idldir}/*.idl"

