
APP_NAME = Test

all: remote 

local: ${APP_NAME}.idl
	ccmtools-generate c++local -a -o xxx ${APP_NAME}.idl

remote: local
	ccmtools-generate idl2 -o xxx/CORBA_Stubs ${APP_NAME}.idl
	ccmtools-generate c++remote -o xxx ${APP_NAME}.idl
	ccmtools-generate c++remote-test -o xxx ${APP_NAME}.idl

check: 
	cp impl/Makefile.py xxx/
	cp impl/Test_impl.cc xxx/impl/
	cp impl/Test_inPort_impl.cc xxx/impl/
	cp test/_check_CCM_Remote_CCM_Session_Test_remote.cc xxx/test
	confix.py --packageroot=`pwd`/xxx --bootstrap --configure --make --targets=check

clean: 
	confix.py --packageroot=`pwd`/xxx --bootstrap --configure --make --targets=clean	
	rm -rf xxx 
	rm -rf ${HOME}/tmp/ccmtools_tmp/xxx
