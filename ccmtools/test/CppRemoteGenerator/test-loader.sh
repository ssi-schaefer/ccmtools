#! /bin/sh

# arguments :
# 1 - package name
# 2 - include path specification
# 3 - idl files to generate code from
# 4 - if zero length, will uninstall package

sandbox_dir=`pwd`/sandbox
build_dir=${sandbox_dir}/build
install_dir=${sandbox_dir}/install
data_dir=${sandbox_dir}/share/${PACKAGE}

${MKDIR} -p ${build_dir} ${install_dir} ${data_dir}

# set up the environment so we can change directories safely. this is nast, but
# necessary if we've got a bunch of relative paths in our environment. also link
# template directories so the generators can find them.

cwd=`pwd`

# get absolute paths for the top source and build dirs.

cd ${top_srcdir} ; abssrcdir=`pwd` ; cd ${cwd}
cd ${top_builddir} ; absbuilddir=`pwd` ; cd ${cwd}

for f in ${abssrcdir}/UI/scripts/ccmtools-* ; do ln -s $f ${sandbox_dir} ; done
for f in ${abssrcdir}/*Generator/*Templates ; do ln -s $f ${data_dir} ; done
for f in ${abssrcdir}/*Generator/*Environment ; do ln -s $f ${data_dir} ; done

PCLASSPATH=${CLASSPATH}
PPATH=${PATH}
export CLASSPATH=${abssrcdir}/lib/antlr.jar:${absbuilddir}
export CCMTOOLS_HOME=${sandbox_dir}
export PATH=${sandbox_dir}:${PATH}

ret=""

cd ${sandbox_dir}

# If there is no C++ environment, cancel test case execution 

test -e ${install_dir}/lib/libCCM_Remote_RemoteComponents.a && exit 1 

# generate component code.

rm -rf ${sandbox_dir}/${1}

test -z "${ret}" && ccmtools-generate c++local -a -o ${1} -i ${2} ${3} || ret=1
test -z "${ret}" && ccmtools-generate idl2 -o ${1}/idl2 -i ${2} ${3} || ret=1
test -z "${ret}" && (cd ${1}/idl2; make; cd ../..) || ret=1
test -z "${ret}" && ccmtools-generate c++remote -o ${1} -i ${2} ${3} || ret=1
test -z "${ret}" && ccmtools-generate c++remote-test -o ${1} -i ${2} ${3} || ret=1



# build and check. copy the contents of the package directory, if it exists, to
# the sandbox (this lets us distribute _app.cc files with the tests).

test -d ${abssrcdir}/test/CppRemoteGenerator/${1}/src && \
  ${CP} -rf ${abssrcdir}/test/CppRemoteGenerator/${1}/src ${1}

test -d ${abssrcdir}/test/CppRemoteGenerator/${1}/test && \
  ${CP} -rf ${abssrcdir}/test/CppRemoteGenerator/${1}/test ${1}

test -z "${ret}" && PYTHONPATH=${install_dir}:${PYTHONPATH} \
  ccmtools-c++-configure -p ${1} || ret=1

test -z "${ret}" && PYTHONPATH=${install_dir}:${PYTHONPATH} \
  ccmtools-c++-make -p ${1} || ret=1

test -z "${ret}" && ret=0

export CLASSPATH=${PCLASSPATH}
export PATH=${PPATH}
cd ${cwd}
exit ${ret}

