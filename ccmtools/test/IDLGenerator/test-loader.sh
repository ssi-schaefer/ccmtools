#! /bin/sh

OUTPUT=test-`echo ${IDL} | sed 's,/,-,g'`.output

cwd=`pwd`
cd ${top_srcdir} ; abssrcdir=`pwd` ; cd ${cwd}

${MKDIR} -p ${cwd}/share/${PACKAGE}-${MAJORMINOR}

for f in ${abssrcdir}/*Generator/*Templates ; do
ln -s $f ${cwd}/share/${PACKAGE}-${MAJORMINOR} ; done

export CCMTOOLS_HOME=${cwd}

ret=""

test -z "${ret}" && ccmtools-generate idl3       -o idl3  ${top_srcdir}/test/idl/${IDL}.idl || ret=1
test -z "${ret}" && ccmtools-generate idl3mirror -o idl3  ${top_srcdir}/test/idl/${IDL}.idl || ret=1

test -z "${ret}" && ccmtools-generate idl2       -o idl2  idl3/*.idl3 || ret=1
test -z "${ret}" && ccmtools-generate idl2       -o idl2m idl3/*.idl3mirror || ret=1

test -z "${ret}" && ret=0

${RM} -f -r share idl3 idl2m idl2
exit ${ret}

