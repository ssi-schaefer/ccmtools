
APP_NAME = Test

all:local remote build

local: Test.idl
	ccmtools-generate c++local -a -o xxx Test.idl

remote: local
	ccmtools-generate idl2 -o xxx/CORBA_Stubs Test.idl
	ccmtools-generate c++remote -o xxx Test.idl
	ccmtools-generate c++remote-test -o xxx Test.idl

build: remote
	cp impl/Test_iface_impl.cc xxx/impl
	cp test/_check_CCM_Remote_CCM_Session_Test_remote.cc xxx/test 
	confix.py --packageroot=`pwd`/xxx --bootstrap --configure --make --targets=check
