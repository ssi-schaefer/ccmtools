
APP_NAME = Test

all: remote

local: Test.idl
	ccmtools-generate c++local -a -o xxx Test.idl

remote: local
	ccmtools-generate idl2 -o xxx/CORBA_Stubs Test.idl
	ccmtools-generate c++remote -o xxx Test.idl
	ccmtools-generate c++remote-test -o xxx Test.idl

check: 
	cp impl/Makefile.py xxx/
	cp impl/Test_iface_impl.cc xxx/impl
	cp test/_check_CCM_Remote_CCM_Session_Test_remote.cc xxx/test 
	confix.py --packageroot=`pwd`/xxx --bootstrap --configure --make --targets=check

clean:
	confix.py --packageroot=`pwd`/xxx --bootstrap --configure --make --targets=clean
	rm -rf xxx
	rm -rf ${HOME}/tmp/xxx
