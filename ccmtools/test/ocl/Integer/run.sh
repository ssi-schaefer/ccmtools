#
# $Header$
#
NAME=MyInteger
#
IDL=$NAME.idl
PKG=testOCL_$NAME
OUT=test_$NAME
VERSION=0.1
#
ccmtools-c++-generate -a -d -c $VERSION -p $PKG -s $OUT $IDL
if ! ccmtools-generate c++dbc -o $OUT $IDL; then exit 1; fi
rm -f mdr.*
ccmtools-c++-configure -p $PKG -s $OUT
ccmtools-c++-make -p $PKG -s $OUT
echo "ccmtools-c++-make -p $PKG -s $OUT" > compile.sh
chmod 755 compile.sh
