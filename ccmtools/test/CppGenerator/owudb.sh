#! /bin/sh

idldir=${top_srcdir}/../test/idl/owudb

files=""
for f in ${top_srcdir}/test/idl/owudb/ifaces/*.idl \
         ${top_srcdir}/test/idl/owudb/dbsql/*.idl
do files="${files} `echo ${f} | sed 's,..,../..,'`"
done

${top_srcdir}/test/CppGenerator/test-loader.sh owudb \
  "-I${idldir}" "${files}"

