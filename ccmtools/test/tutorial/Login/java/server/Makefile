IDL_SOURCE=../../Login.idl
DEST_DIR = .
IDL3_DIR = ${DEST_DIR}/idl3
IDL2_DIR = ${DEST_DIR}/idl2
INCLUDE  = -I${IDL3_DIR}/interface -I${IDL3_DIR}/component

all: local remote run

test: local check clean

idl3: ${IDL_SOURCE}
	ccmidl -idl3 -o ${IDL3_DIR} ${IDL_SOURCE}

local: idl3
	ccmjava -iface -local -o ${DEST_DIR}/src-gen ${INCLUDE} ${IDL3_DIR}/interface/application/*.idl
	ccmjava -iface -local -o ${DEST_DIR}/src-gen ${INCLUDE} ${IDL3_DIR}/component/application/Server*.idl
	ccmjava -app -o ${DEST_DIR}/src ${INCLUDE} ${IDL3_DIR}/component/application/Server*.idl


check: 
	cp impl/ClientLocal.java ${DEST_DIR}/src
	cp impl/ServerloginImpl.java ${DEST_DIR}/src/application/ccm/local/
	ant -f ./build.xml
	bin/runClientLocal 


stubs: local
	ccmidl -idl2 -o ${IDL2_DIR} ${IDL_SOURCE}
	ccmtools-idl -java -o ${DEST_DIR}/src-gen -I${CCMTOOLS_HOME}/idl -I${IDL2_DIR} ${IDL2_DIR}/*.idl

remote: stubs
	ccmjava -remote -o ${DEST_DIR}/src-gen ${INCLUDE} ${IDL3_DIR}/interface/application/*.idl
	ccmjava -remote -o ${DEST_DIR}/src-gen ${INCLUDE} ${IDL3_DIR}/component/application/Server*.idl

run:
	cp impl/Server.java ${DEST_DIR}/src 
	cp impl/ServerloginImpl.java ${DEST_DIR}/src/application/ccm/local/
	ant -f ./build.xml
	bin/runServer

clean: 
	ant clean
	rm -rf ${DEST_DIR}/idl3
	rm -rf ${DEST_DIR}/idl2
	rm -rf ${DEST_DIR}/src
	rm -rf ${DEST_DIR}/src-gen
