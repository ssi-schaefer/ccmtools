
APP_NAME = Test

all: remote 
server: cppserver
client: cppclient

local: Test.idl
	ccmtools-generate c++local -a -o xxx Test.idl
	ccmtools-generate c++local-test -o xxx Test.idl

remote: local
	ccmtools-generate idl2 -o xxx/CORBA_Stubs Test.idl
	ccmtools-generate c++remote -o xxx Test.idl
	ccmtools-generate c++remote-test -o xxx Test.idl

install: 
	confix.py --packageroot=`pwd`/xxx --bootstrap --configure --make --targets=install

check:
	cp test/Measurement.h xxx/test
	cp test/Measurement.cc xxx/test/
	cp impl/Test_bm_impl.cc xxx/impl
	cp test/_check_CCM_Local_Object_Test.cc xxx/test
	cp test/_check_CCM_Local_CCM_Session_Test.cc xxx/test
	cp test/_check_CCM_Remote_CCM_Session_Test_remote.cc xxx/test
	confix.py --packageroot=`pwd`/xxx  --make --targets=check

cppserver: 
	confix.py --packageroot=`pwd`/server --bootstrap --configure --make

cppclient:
	confix.py --packageroot=`pwd`/client --bootstrap --configure --make

clean:
	rm -rf xxx
	rm -rf ${HOME}/tmp/ccmtools_tmp/xxx
	confix.py --packageroot=`pwd`/xxx  --make --targets=uninstall
