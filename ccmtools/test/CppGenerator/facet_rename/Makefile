
APP_NAME = Test
APP_DIR = xxx
IDL3_DIR = ${APP_DIR}/idl3
IDL_INTERFACE = ${IDL3_DIR}/interface
IDL_COMPONENT = ${IDL3_DIR}/component
IDL_INCLUDE = -I${IDL_INTERFACE} -I${IDL_COMPONENT}

all: local

idl: Test.idl
	ccmtools idl3 -o ${IDL3_DIR} ${APP_NAME}.idl 
	ccmtools idl3mirror -o ${IDL3_DIR} ${APP_NAME}.idl

local: idl
	ccmtools c++local -o ${APP_DIR} ${IDL_INCLUDE} ${IDL_INTERFACE}/*.idl
	ccmtools c++local -a -o ${APP_DIR} ${IDL_INCLUDE} ${IDL_COMPONENT}/*.idl
	ccmtools c++local-test -o ${APP_DIR} ${IDL_INCLUDE} ${IDL_COMPONENT}/Console.idl

check: 
	cp impl/Makefile.py xxx
	confix.py --packageroot=`pwd`/xxx --bootstrap --configure --make --targets=check

clean: 
	rm -rf xxx 
	rm -rf ${HOME}/tmp/xxx
