#! /bin/sh

sandbox_dir=`pwd`/sandbox
build_dir=${sandbox_dir}/build
install_dir=${sandbox_dir}/install
template_dir=${sandbox_dir}/share/${PACKAGE}-${MAJORMINOR}
idl_srcdir=${top_srcdir}/test/idl
idl_destdir=${sandbox_dir}/idl

${MKDIR} -p ${build_dir} ${install_dir} ${template_dir} ${idl_destdir}

# copy source idl files.

for file in ${idl_srcdir}/${2}
do ${CP} -r ${file} ${idl_destdir}
done

# set up the environment so we can change directories safely. this is nast, but
# necessary if we've got a bunch of relative paths in our environment. also link
# template directories so the generators can find them.

ln -s `which ccmtools-generate`      ${sandbox_dir}
ln -s `which ccmtools-c++-generate`  ${sandbox_dir}
ln -s `which ccmtools-c++-make`      ${sandbox_dir}
ln -s `which ccmtools-c++-install`   ${sandbox_dir}
ln -s `which ccmtools-c++-uninstall` ${sandbox_dir}

cwd=`pwd`
cd ${sandbox_dir}

for t in ${top_srcdir}/../CppGenerator/*Templates \
         ${top_srcdir}/../IDLGenerator/*Templates
do ln -s `echo $t | sed 's,..,../../..,'` ${template_dir}
done
ln -s ${top_srcdir}/../lib/antlr.jar .
ln -s ${top_builddir}/../ccmtools .

export CCMTOOLS_HOME=${sandbox_dir}
export PATH=.:../..:${PATH}
export CLASSPATH=./antlr.jar:../../antlr.jar:.:../..:${CLASSPATH}

ret=""

# deploy environment if it doesn't exist.

test -e ${install_dir}/lib/libccmtools-cpp-environment_CCM_Utils.a || \
  ccmtools-c++-environment -i ${install_dir} || ret=1

# generate component code.

idl_files=`find ${idl_destdir} -name "*.idl"`
test -z "${ret}" && ccmtools-c++-generate -d -c "1.2.3" -p ${1} \
  -i ${install_dir} -I${idl_destdir} ${idl_files} || ret=1

# build and check. copy the contents of the package directory, if it exists, to
# the sandbox (this lets us distribute _app.cc files with the tests).

test -d ${top_srcdir}/../test/CppGenerator/${1} && \
  ${CP} ${top_srcdir}/../test/CppGenerator/${1}/* .

test -z "${ret}" && PYTHONPATH=${install_dir}:${PYTHONPATH} \
  ccmtools-c++-make -p ${1} || ret=1

# install and uninstall.

test -z "${ret}" && ccmtools-c++-install -p ${1} || ret=1
test -z "${ret}" && ccmtools-c++-uninstall -p ${1} || ret=1

test -z "${ret}" && ret=0

${RM} -f -r idl share antlr.jar ccmtools* *.cc *.h *.py
cd ${cwd}
exit ${ret}

