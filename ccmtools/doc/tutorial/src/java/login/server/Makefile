IDL_SOURCE=../Login.idl
DEST_DIR = .
IDL3_DIR = ${DEST_DIR}/idl3
IDL2_DIR = ${DEST_DIR}/idl2
INCLUDE  = -I${IDL3_DIR}/interface -I${IDL3_DIR}/component

all: remote

test: local check clean

idl3: ${IDL_SOURCE}
	ccmidl -idl3 -o ${IDL3_DIR} ${IDL_SOURCE}

local: idl3
	ccmjava -iface -local -o ${DEST_DIR}/src-gen ${INCLUDE} ${IDL3_DIR}/interface/system/*.idl
	ccmjava -iface -local -o ${DEST_DIR}/src-gen ${INCLUDE} ${IDL3_DIR}/component/system/Server*.idl
	ccmjava -app -o ${DEST_DIR}/src ${INCLUDE} ${IDL3_DIR}/component/system/Server*.idl
	cp impl/ServerloginImpl.java ${DEST_DIR}/src/system/ccm/local/

stubs: local
	ccmtools idl2 -o ${IDL2_DIR} ${IDL_SOURCE}
	ccmtools-idl -java -o ${DEST_DIR}/src-gen-remote -I${CCMTOOLS_HOME}/idl -I${IDL2_DIR} ${IDL2_DIR}/*.idl

remote: stubs
	ccmjava -remote -o ${DEST_DIR}/src-gen-remote ${INCLUDE} ${IDL3_DIR}/interface/system/*.idl
	ccmjava -remote -o ${DEST_DIR}/src-gen-remote ${INCLUDE} ${IDL3_DIR}/component/system/Server*.idl
	cp impl/Server.java ${DEST_DIR}/src
	
build: 
	ant -f ./build.xml

clean: 
	ant clean
	rm -rf ${DEST_DIR}/idl3
	rm -rf ${DEST_DIR}/idl2
	rm -rf ${DEST_DIR}/src
	rm -rf ${DEST_DIR}/src-gen
	rm -rf ${DEST_DIR}/src-gen-remote
