DEST_DIR = xxx
IDL3_DIR = ${DEST_DIR}/idl3
IDL2_DIR = ${DEST_DIR}/idl2
INCLUDE  = -I${IDL3_DIR}/interface -I${IDL3_DIR}/component

all: local 

test: local check clean


idl3: Test.idl
	ccmtools idl3 -o ${IDL3_DIR} Test.idl

local: idl3
	ccmjava -local -o ${DEST_DIR}/src-gen ${INCLUDE} ${IDL3_DIR}/interface/world/*.idl
	ccmjava -local -o ${DEST_DIR}/src-gen ${INCLUDE} ${IDL3_DIR}/interface/world/europe/*.idl
	ccmjava -local -o ${DEST_DIR}/src-gen ${INCLUDE} ${IDL3_DIR}/component/world/europe/austria/Test*.idl

	ccmjava -app -o ${DEST_DIR}/src ${INCLUDE} ${IDL3_DIR}/component/world/europe/austria/Test*.idl

check: 
	cp impl/Client.java ${DEST_DIR}/src/
	ant -f ./build.xml
	bin/runClient

clean: 
	rm -rf ${DEST_DIR}
