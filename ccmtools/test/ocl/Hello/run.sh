#
# $Header$
#
NAME=Hello
#
IDL=$NAME.idl
PKG=testOCL_$NAME
OUT=test_$NAME
VERSION=0.1
#
echo "cp _check_CCM_Local_CCM_Session_Hello.cc $OUT/test" > compile.sh
echo "ccmtools-c++-make -p $PKG -s $OUT" >> compile.sh
chmod 755 compile.sh
#
if ! ccmtools-c++-generate -a -d -c $VERSION -p $PKG -s $OUT $IDL; then exit 1; fi
if ! ccmtools-generate c++dbc -o $OUT $IDL; then exit 1; fi
rm -f mdr.*
if ! cp _check_CCM_Local_CCM_Session_Hello.cc $OUT/test; then exit 1; fi
if ! ccmtools-c++-configure -p $PKG -s $OUT; then exit 1; fi
if ! ccmtools-c++-make -p $PKG -s $OUT; then exit 1; fi
