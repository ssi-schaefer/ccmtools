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
ccmtools-c++-generate -a -d -c $VERSION -p $PKG -s $OUT $IDL
ccmtools-generate c++dbc -o $OUT $IDL
rm -f mdr.*
ccmtools-c++-configure -p $PKG -s $OUT
ccmtools-c++-make -p $PKG -s $OUT
echo "ccmtools-c++-make -p $PKG -s $OUT" > compile.sh
chmod 755 compile.sh
