DEST_DIR = .
IDL3_DIR = ../../idl3repo
IDL2_DIR = ${DEST_DIR}/src-gen/idl2
INCLUDE  = -I${IDL3_DIR}/interface -I${IDL3_DIR}/component

all: local remote run

test: local check clean

local: 
	ccmjava -iface -local -o ${DEST_DIR}/src-gen ${INCLUDE} ${IDL3_DIR}/interface/application/*.idl
	ccmjava -iface -local -o ${DEST_DIR}/src-gen ${INCLUDE} ${IDL3_DIR}/component/application/Server*.idl
	ccmjava -app -o ${DEST_DIR}/src ${INCLUDE} ${IDL3_DIR}/component/application/Server*.idl

check: 
	cp impl/ClientLocal.java ${DEST_DIR}/src
	cp impl/ServerloginImpl.java ${DEST_DIR}/src/application/
	ant -f ./build.xml
	bin/runClientLocal 

stubs: 
	ccmidl -idl2 ${INCLUDE} -o ${IDL2_DIR} ${IDL3_DIR}/interface/application/*.idl
	ccmidl -idl2 ${INCLUDE} -o ${IDL2_DIR} ${IDL3_DIR}/component/application/Server*.idl
	ccmtools-idl -java -o ${DEST_DIR}/src-gen -I${CCMTOOLS_HOME}/idl -I${IDL2_DIR} ${IDL2_DIR}/*.idl

remote: stubs
	ccmjava -remote -o ${DEST_DIR}/src-gen ${INCLUDE} ${IDL3_DIR}/interface/application/*.idl
	ccmjava -remote -o ${DEST_DIR}/src-gen ${INCLUDE} ${IDL3_DIR}/component/application/Server*.idl

run:
	cp impl/Server.java ${DEST_DIR}/src 
	cp impl/ServerloginImpl.java ${DEST_DIR}/src/application/
	ant -f ./build.xml
	bin/runServer

clean: 
	ant clean
	rm -rf ${DEST_DIR}/src
	rm -rf ${DEST_DIR}/src-gen
