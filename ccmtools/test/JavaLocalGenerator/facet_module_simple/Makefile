DEST_DIR = xxx
IDL3_DIR = ${DEST_DIR}/idl3
IDL2_DIR = ${DEST_DIR}/idl2
INCLUDE  = -I${IDL3_DIR}/interface -I${IDL3_DIR}/component

all: client_lib 

idl3: Test.idl
	ccmtools idl3 -o ${IDL3_DIR} Test.idl

client_lib: idl3
#	ccmjava -clientlib -o ${DEST_DIR}/src-gen ${INCLUDE} ${IDL3_DIR}/interface/world/*.idl
#	ccmjava -clientlib -o ${DEST_DIR}/src-gen ${INCLUDE} ${IDL3_DIR}/component/world/Test*.idl
	ccmjava -local -o ${DEST_DIR}/src-gen ${INCLUDE} ${IDL3_DIR}/interface/world/*.idl
	ccmjava -local -o ${DEST_DIR}/src-gen ${INCLUDE} ${IDL3_DIR}/component/world/Test*.idl

	ccmjava -impl -o ${DEST_DIR}/src ${INCLUDE} ${IDL3_DIR}/interface/world/*.idl
	ccmjava -impl -o ${DEST_DIR}/src ${INCLUDE} ${IDL3_DIR}/component/world/Test*.idl

check: 
	cp impl/inPortImpl.java ${DEST_DIR}/src/world/ccm/local/
	cp impl/Client.java ${DEST_DIR}/src/
	ant -f ./build.xml
	bin/runClient

clean: 
	rm -rf ${DEST_DIR}
