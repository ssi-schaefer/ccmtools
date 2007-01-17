
CCMTOOLS_CMD = ccmtools
DEST_DIR = xxx
IDL_INCLUDE=-I${DEST_DIR}/idl/interface -I${DEST_DIR}/idl/component

all: local 

idl: idl3/Hello.idl
	ccmmodel -validator idl3/Hello.idl
	ccmidl -idl3 -o ${DEST_DIR}/idl idl3/Hello.idl

local: idl
	ccmtools c++local ${IDL_INCLUDE} -o ${DEST_DIR}/interface ${DEST_DIR}/idl/interface/world/*.idl
	ccmtools c++local ${IDL_INCLUDE} -a -o ${DEST_DIR}/component/Server ${DEST_DIR}/idl/component/world/Server.idl ${DEST_DIR}/idl/component/world/ServerHome.idl 
	ccmtools c++local-test ${IDL_INCLUDE} -a -o ${DEST_DIR}/component/Server ${DEST_DIR}/idl/component/world/Server.idl 

check: 
	cp impl/Server_hello_impl.cc ${DEST_DIR}/component/Server/
	cp impl/_check_world_Server.cc ${DEST_DIR}/component/Server/test/
	ccmconfix -confix2 -o ${DEST_DIR} -pname "hello_world" -pversion "1.0.0"
	confix2.py --packageroot=`pwd`/${DEST_DIR} --bootstrap --configure --make --targets=check

clean: 
	confix2.py --packageroot=`pwd`/${DEST_DIR} --make --targets=clean	
	rm -rf ${DEST_DIR}
