#! /bin/sh

sandbox_dir=`pwd`/sandbox
build_dir=${sandbox_dir}/build
install_dir=${sandbox_dir}/install

${MKDIR} -p ${build_dir} ${install_dir}

# copy source idl files.

idl_dir=${top_srcdir}/test/idl
for file in ${idl_dir}/${2}
do ${CP} ${file} ${sandbox_dir}
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

ln -s ${top_srcdir}/../CppGenerator/*Templates .
ln -s ${top_srcdir}/../IDLGenerator/*Templates .
ln -s ${top_srcdir}/../lib/antlr.jar .
ln -s ${top_builddir}/../ccmtools .

export CCMTOOLS_HOME=`pwd`
export PATH=.:../..:${PATH}
export CLASSPATH=./antlr.jar:../../antlr.jar:.:../..:${CLASSPATH}

# generate, make, check, and uninstall the components.

ret=""

if [ ! -e ${install_dir}/lib/libccmtools-cpp-environment_CCM_Utils.a ]
then ccmtools-c++-environment -i ${install_dir} && ret=1
fi

test -z "${ret}" && ret=1 && \
  ccmtools-c++-generate -y -d -c "1.2.3" -p ${1} -i ${install_dir} *.idl && \
  ccmtools-c++-make -p ${1} && \
  ccmtools-c++-install -p ${1} && \
  ccmtools-c++-uninstall -p ${1} && ret=0

${RM} -f *Templates antlr.jar ccmtools* *.idl *.cc *.h
cd ${cwd}
exit ${ret}

