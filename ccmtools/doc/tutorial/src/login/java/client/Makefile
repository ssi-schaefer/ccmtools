IDL_SOURCE=../../Login.idl
DEST_DIR = .
IDL3_DIR = ${DEST_DIR}/idl3
IDL2_DIR = ${DEST_DIR}/idl2
INCLUDE  = -I${IDL3_DIR}/interface -I${IDL3_DIR}/component

all: clientlib run


idl3: ${IDL_SOURCE}
	ccmidl -idl3 -o ${IDL3_DIR} ${IDL_SOURCE}

stubs: 
	ccmidl -idl2 -o ${IDL2_DIR} ${IDL_SOURCE}
	ccmtools-idl -java -o ${DEST_DIR}/src-gen -I${CCMTOOLS_HOME}/idl -I${IDL2_DIR} ${IDL2_DIR}/*.idl

clientlib: idl3 stubs
	ccmjava -iface -o ${DEST_DIR}/src-gen ${INCLUDE} ${IDL3_DIR}/interface/application/*.idl
	ccmjava -iface -o ${DEST_DIR}/src-gen ${INCLUDE} ${IDL3_DIR}/component/application/Server*.idl
	ccmjava -clientlib -o ${DEST_DIR}/src-gen ${INCLUDE} ${IDL3_DIR}/interface/application/*.idl
	ccmjava -clientlib -o ${DEST_DIR}/src-gen ${INCLUDE} ${IDL3_DIR}/component/application/Server*.idl

run: 
	ant -f ./build.xml
	bin/runClient

clean: 
	ant clean
	rm -rf ${DEST_DIR}/idl3
	rm -rf ${DEST_DIR}/idl2
	rm -rf ${DEST_DIR}/src-gen
