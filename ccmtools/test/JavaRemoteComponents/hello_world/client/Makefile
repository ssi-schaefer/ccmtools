IDL_SOURCE=../HelloWorld.idl
DEST_DIR = .
IDL3_DIR = ${DEST_DIR}/idl3
IDL2_DIR = ${DEST_DIR}/idl2
INCLUDE  = -I${IDL3_DIR}/interface -I${IDL3_DIR}/component

all: clientlib

idl3: ${IDL_SOURCE}
	ccmtools idl3 -o ${IDL3_DIR} ${IDL_SOURCE}

stubs: idl3
	ccmtools idl2 -o ${IDL2_DIR} ${IDL_SOURCE}
	ccmtools-idl -java -o ${DEST_DIR}/src-gen -I${CCMTOOLS_HOME}/idl -I${IDL2_DIR} ${IDL2_DIR}/*.idl

stubs: idl3
	ccmtools idl2 -o ${IDL2_DIR}  ${IDL_SOURCE}
	ccmtools-idl -java -o ${DEST_DIR}/src-gen -I${CCMTOOLS_HOME}/idl -I${IDL2_DIR} ${IDL2_DIR}/*.idl

clientlib: stubs
	ccmjava -iface -clientlib -o ${DEST_DIR}/src-gen ${INCLUDE} ${IDL3_DIR}/interface/world/*.idl
	ccmjava -iface -clientlib -o ${DEST_DIR}/src-gen ${INCLUDE} ${IDL3_DIR}/component/world/HelloWorld*.idl

check: 
	ant -f ./build.xml
	bin/runClient

clean: 
	ant clean
	rm -rf ${DEST_DIR}/idl3
	rm -rf ${DEST_DIR}/idl2
	rm -rf ${DEST_DIR}/src-gen

