#! /bin/sh

OUTPUT=test-`echo ${IDL} | sed 's,/,-,g'`.output
ccmtools-generate idl3 ${top_srcdir}/test/idl/${IDL}.idl 
ccmtools-generate idl3mirror ${top_srcdir}/test/IDLGenerator/${IDL}.idl3 
ccmtools-generate idl2 ${top_srcdir}/test/IDLGenerator/${IDL}.idl3mirror
mv ${top_srcdir}/test/IDLGenerator/${IDL}.idl2 ${top_srcdir}/test/IDLGenerator/${IDL}.idl2mirror
ccmtools-generate idl2 ${top_srcdir}/test/IDLGenerator/${IDL}.idl3 


