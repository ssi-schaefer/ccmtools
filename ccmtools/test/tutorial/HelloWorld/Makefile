
APP_NAME = Hello
CCMTOOLS_CMD = ccmtools
DEST_DIR = server

all: local 

uml: uml/Hello.xml.zip
	uml2idl uml/Hello.xml.zip Hello

local: ${APP_NAME}.idl
	${CCMTOOLS_CMD} c++local -a -o ${DEST_DIR} ${APP_NAME}.idl

check: 
	cp impl/Makefile.py gen/
	cp impl/Server_hello_impl.cc ${DEST_DIR}/impl/
	cp test/_check_client.cc ${DEST_DIR}/test/
	touch ${DEST_DIR}/test/Makefile.py
	confix.py --packageroot=`pwd`/${DEST_DIR} --bootstrap --configure --make --targets=check


clean: 
	confix.py --packageroot=`pwd`/${DEST_DIR} --bootstrap --configure --make --targets=clean	
	rm -rf ${DEST_DIR}
