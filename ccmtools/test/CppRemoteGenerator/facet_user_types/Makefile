
APP_NAME = Test

all:local remote build

local: Test.idl
	ccmtools-generate c++local -a -o xxx Test.idl

remote: local
	ccmtools-generate idl2 -o xxx/CORBA_Stubs Test.idl
	ccmtools-generate c++remote -o xxx Test.idl
	ccmtools-generate c++remote-test -o xxx Test.idl
	cp impl/Test_console_impl.cc xxx/impl/
	cp test/_check_CCM_Remote_CCM_Session_Test_remote.cc  xxx/test/

build: remote
	confix.py --packageroot=`pwd`/xxx --bootstrap --configure --make --targets=check

clean: 
	rm -rf xxx ~/tmp/xxx
