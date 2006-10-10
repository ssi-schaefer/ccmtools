APP_DIR = xxx
IDL3_DIR = ${APP_DIR}/idl3
IDL_INTERFACE = ${IDL3_DIR}/interface
IDL_COMPONENT = ${IDL3_DIR}/component
IDL_INCLUDE = -I${IDL_INTERFACE} -I${IDL_COMPONENT}
COMPONENT_DIR= ${APP_DIR}/src/component
INTERFACE_DIR= ${APP_DIR}/src/interface

all: local

test: local check clean

local: interface basic user super super_mirror

idl: Test.idl
	ccmmodel -validator Test.idl
	ccmidl -idl3 -o ${IDL3_DIR} Test.idl
	ccmidl -idl3mirror -o ${IDL3_DIR} Test.idl

interface: idl
	ccmtools c++local -o ${INTERFACE_DIR} ${IDL_INCLUDE} ${IDL_INTERFACE}/*.idl

basic: idl
	ccmtools c++local -a -o ${COMPONENT_DIR}/BasicTest ${IDL_INCLUDE} ${IDL_COMPONENT}/BasicTest.idl ${IDL_COMPONENT}/BasicTestHome.idl

user: idl
	ccmtools c++local -a -o ${COMPONENT_DIR}/UserTest ${IDL_INCLUDE} ${IDL_COMPONENT}/UserTest.idl ${IDL_COMPONENT}/UserTestHome.idl

super: idl
	ccmtools c++local -a -o ${COMPONENT_DIR}/SuperTest ${IDL_INCLUDE} ${IDL_COMPONENT}/SuperTest.idl ${IDL_COMPONENT}/SuperTestHome.idl

super_mirror:
	ccmtools c++local -a -o ${COMPONENT_DIR}/SuperTestMirror ${IDL_INCLUDE} ${IDL_COMPONENT}/SuperTestMirror.idl ${IDL_COMPONENT}/SuperTestHomeMirror.idl
	ccmtools c++local-test -o ${COMPONENT_DIR}/SuperTestMirror ${IDL_INCLUDE} ${IDL_COMPONENT}/SuperTest.idl


check: 
	cp -r impl/assembly ${COMPONENT_DIR}/SuperTest
	cp impl/test/_check* ${COMPONENT_DIR}/SuperTestMirror/test/
	cp impl/BasicTest_basicType_impl.cc ${COMPONENT_DIR}/BasicTest/
	cp impl/UserTest_userType_impl.cc ${COMPONENT_DIR}/UserTest/
	cp impl/SuperTest_basicType_impl.cc ${COMPONENT_DIR}/SuperTest/
	cp impl/SuperTest_userType_impl.cc ${COMPONENT_DIR}/SuperTest/	
	cp impl/SuperTestMirror_impl.cc ${COMPONENT_DIR}/SuperTestMirror/
	ccmconfix -makefiles -o ${APP_DIR}/src -pname "assembly_nested" -pversion "1.0.0"
	confix.py --packageroot=`pwd`/${APP_DIR}/src --bootstrap --configure --make --targets=check

check_empty: 
	ccmconfix -makefiles -o ${APP_DIR}/src -pname "assembly_nested" -pversion "1.0.0"
	confix.py --packageroot=`pwd`/${APP_DIR}/src --bootstrap --configure --make 
	
	
clean: 
	confix.py --packageroot=`pwd`/${APP_DIR}/src --make --targets=clean
	rm -rf ${APP_DIR}
