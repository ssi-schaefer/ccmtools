DEST_DIR = xxx
IDL3_DIR = ${DEST_DIR}/idl3
IDL2_DIR = ${DEST_DIR}/idl2
INCLUDE  = -I${IDL3_DIR}/interface -I${IDL3_DIR}/component

all: local remote stubs

test-build: remote build clean 

idl3: Test.idl
	ccmtools idl3 -o ${IDL3_DIR} Test.idl

local: idl3
	ccmjava -iface -local -o ${DEST_DIR}/src-gen ${INCLUDE} ${IDL3_DIR}/interface/world/*.idl
	ccmjava -iface -local -o ${DEST_DIR}/src-gen ${INCLUDE} ${IDL3_DIR}/component/world/Test*.idl
	ccmjava -app -o ${DEST_DIR}/src ${INCLUDE} ${IDL3_DIR}/component/world/Test*.idl

remote: idl3
	ccmjava -remote -o ${DEST_DIR}/src-gen ${INCLUDE} ${IDL3_DIR}/interface/world/*.idl
	ccmjava -remote -o ${DEST_DIR}/src-gen ${INCLUDE} ${IDL3_DIR}/component/world/Test*.idl

stubs: idl3
	ccmtools idl2 -o ${IDL2_DIR} Test.idl
	ccmtools-idl -java -o ${DEST_DIR}/src-gen -I${CCM_INSTALL}/include -I${IDL2_DIR} ${IDL2_DIR}/*.idl

build:
	mkdir ${DEST_DIR}/src
	ant -f ./build.xml


check: 
	ant -f ./build.xml
	bin/runClient

clean: 
	rm -rf xxx 

