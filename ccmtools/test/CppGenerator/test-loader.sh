#! /bin/sh

sandbox_dir=`pwd`/sandbox
build_dir=${sandbox_dir}/build
install_dir=${sandbox_dir}/install

${MKDIR} -p ${build_dir} ${install_dir}

cwd=`pwd`
cd ${sandbox_dir}

# copy source idl files here.

idl_dir=${top_srcdir}/../test/idl
for file in ${idl_dir}/${2}
do ${CP} ${file} .
done

# generate, make, check, and uninstall the components.

ret=0
if [ "${ret}" = "0" ]
then
  ccmtools-c++-generate -d -i ${install_dir} -b ${build_dir} -p ${1} *.idl && ret=1
  if [ "${ret}" = "0" ]
  then
    ccmtools-c++-make -p ${1} && ret=1
    if [ "${ret}" = "0" ]
    then
      ccmtools-c++-install -p ${1} && ret=1
      if [ "${ret}" = "0" ]
      then
        ccmtools-c++-uninstall -p ${1} && ret=1
      fi
    fi
  fi
fi

${RM} -f *.idl
cd ${cwd}

exit ${ret}

