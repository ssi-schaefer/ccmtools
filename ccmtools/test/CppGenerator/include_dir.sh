#! /bin/sh

cwd=`pwd`
sandbox_dir=`pwd`/sandbox

cd ${top_srcdir} ; abssrcdir=`pwd` ; cd ${cwd}
cd ${top_builddir} ; absbuilddir=`pwd` ; cd ${cwd}

idldir=${abssrcdir}/test/CppGenerator/include_dir

PCLASSPATH=${CLASSPATH}
PPATH=${PATH}
export CLASSPATH=${abssrcdir}/lib/antlr.jar:${absbuilddir}
export CCMTOOLS_HOME=${sandbox_dir}
export PATH=${sandbox_dir}:${PATH}

ret=""

test -z "${ret}" && ${top_srcdir}/test/CppGenerator/test-loader.sh \
  "_include_dir_base" "-I${idldir}/ifaces" \
  "${idldir}/ifaces/Lcd.idl ${idldir}/ifaces/Console.idl" \
  "keep-install" || ret=1

test -z "${ret}" && ${top_srcdir}/test/CppGenerator/test-loader.sh \
  "include_dir" "-I${idldir}/comp -I${idldir}/ifaces" \
  "${idldir}/comp/Hello.idl" "keep-install" || ret=1

test -z "${ret}" && ret=0

cd ${sandbox_dir}
ccmtools-c++-uninstall -p _include_dir_base
ccmtools-c++-uninstall -p include_dir
${RM} -f -r share ccmtools-* *.cc *.h *.py
cd ${cwd}

export CLASSPATH=${PCLASSPATH}
export PATH=${PPATH}
exit ${ret}

