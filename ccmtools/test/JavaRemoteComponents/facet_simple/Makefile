DEST_DIR = xxx
IDL3_DIR = ${DEST_DIR}/idl3
IDL2_DIR = ${DEST_DIR}/idl2
INCLUDE  = -I${IDL3_DIR}/interface -I${IDL3_DIR}/component

all: remote clientlib

test: remote clientlib check_local check clean 

idl3: Test.idl
	ccmtools idl3 -o ${IDL3_DIR} Test.idl

local: idl3
	ccmjava -iface -local -o ${DEST_DIR}/src-gen-local ${INCLUDE} ${IDL3_DIR}/interface/world/*.idl
	ccmjava -iface -local -o ${DEST_DIR}/src-gen-local ${INCLUDE} ${IDL3_DIR}/component/world/Test*.idl
	ccmjava -app -o ${DEST_DIR}/src ${INCLUDE} ${IDL3_DIR}/component/world/Test*.idl

stubs: local
	ccmtools idl2 -o ${IDL2_DIR} Test.idl
	ccmtools-idl -java -o ${DEST_DIR}/src-gen-remote -I${CCMTOOLS_HOME}/idl -I${IDL2_DIR} ${IDL2_DIR}/*.idl

remote: stubs
	ccmjava -remote -o ${DEST_DIR}/src-gen-remote ${INCLUDE} ${IDL3_DIR}/interface/world/*.idl
	ccmjava -remote -o ${DEST_DIR}/src-gen-remote ${INCLUDE} ${IDL3_DIR}/component/world/Test*.idl

clientlib: idl3
	ccmjava -clientlib -o ${DEST_DIR}/src-gen-remote ${INCLUDE} ${IDL3_DIR}/interface/world/*.idl
	ccmjava -clientlib -o ${DEST_DIR}/src-gen-remote ${INCLUDE} ${IDL3_DIR}/component/world/Test*.idl

build: 
	cp impl/TestinPortImpl.java ${DEST_DIR}/src/world/ccm/local/
	ant -f ./build.xml

check_local: build
	bin/runClientLocal

check: build
	ant -f ./build.xml
	bin/runClient

clean: 
	rm -rf ${DEST_DIR}

