#
# $Header$
#
PKG=test_Hello
IDL=Hello.idl
#
ccmtools-c++-generate -a -d -c 0.1 -p $PKG -s out $IDL
ccmtools-generate c++dbc -o out $IDL
rm -f mdr.*
ccmtools-c++-configure -p $PKG -s out
ccmtools-c++-make -p $PKG -s out
echo "ccmtools-c++-make -p $PKG -s out" > compile.sh
chmod 755 compile.sh
