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

ret=0
if [ "${ret}" = "0" ]
then
  ccmtools-c++-generate -d -i ${install_dir} -b ${build_dir} -c "1.2.3" -p ${1} *.idl || ret=1
  if [ "${ret}" = "0" ]
  then
    ccmtools-c++-make -p ${1} || ret=1
    if [ "${ret}" = "0" ]
    then
      ccmtools-c++-install -p ${1} || ret=1
      if [ "${ret}" = "0" ]
      then
        ccmtools-c++-uninstall -p ${1} || ret=1
      fi
    fi
  fi
fi

${RM} -f *Templates
${RM} -f antlr.jar
${RM} -f ccmtools*
${RM} -f *.idl
cd ${cwd}

exit ${ret}

