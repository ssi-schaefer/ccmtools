
APP_NAME = Hello
CCMTOOLS_CMD = ccmtools
DEST_DIR = server
IDL_INCLUDE=-I${DEST_DIR}/idl/interface -I${DEST_DIR}/idl/component

all: local 

uml2idl: uml/Hello.xml.zip
	uml2idl uml/Hello.xml.zip Hello

idl: uml2idl
	${CCMTOOLS_CMD} idl3 -o ${DEST_DIR}/idl ${APP_NAME}.idl

local: idl
	${CCMTOOLS_CMD} c++local ${IDL_INCLUDE} -o ${DEST_DIR}/interface ${DEST_DIR}/idl/interface/world/*.idl
	${CCMTOOLS_CMD} c++local ${IDL_INCLUDE} -a -o ${DEST_DIR}/component/Server ${DEST_DIR}/idl/component/world/Server.idl ${DEST_DIR}/idl/component/world/ServerHome.idl 
	${CCMTOOLS_CMD} c++local-test ${IDL_INCLUDE} -a -o ${DEST_DIR}/component/Server ${DEST_DIR}/idl/component/world/Server.idl 

check: 
	cp impl/Makefile.py ${DEST_DIR}/
	touch ${DEST_DIR}/interface/Makefile.py
	touch ${DEST_DIR}/component/Makefile.py
	touch ${DEST_DIR}/component/Server/Makefile.py
	cp impl/Server_hello_impl.cc ${DEST_DIR}/component/Server/
	cp impl/_check_world_ccm_local_component_Server.cc ${DEST_DIR}/component/Server/test/
	confix.py --packageroot=`pwd`/${DEST_DIR} --bootstrap --configure --make --targets=check

clean: 
	confix.py --packageroot=`pwd`/${DEST_DIR} --make --targets=clean	
	rm -rf ${DEST_DIR}
