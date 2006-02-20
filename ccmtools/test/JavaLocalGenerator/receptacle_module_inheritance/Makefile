DEST_DIR = xxx
IDL3_DIR = ${DEST_DIR}/idl3
IDL2_DIR = ${DEST_DIR}/idl2
INCLUDE  = -I${IDL3_DIR}/interface -I${IDL3_DIR}/component

all: local stubs

test: local stubs check clean


idl3: Test.idl
	ccmtools idl3 -o ${IDL3_DIR} Test.idl

idl3mirror: idl3
	ccmtools idl3mirror ${INCLUDE} -o ${IDL3_DIR} ${IDL3_DIR}/component/world/Test*.idl

local: idl3mirror
	ccmjava -local -o ${DEST_DIR}/src-gen ${INCLUDE} ${IDL3_DIR}/interface/world/*.idl
	ccmjava -local -o ${DEST_DIR}/src-gen ${INCLUDE} ${IDL3_DIR}/interface/world/europe/*.idl
	ccmjava -local -o ${DEST_DIR}/src-gen ${INCLUDE} ${IDL3_DIR}/interface/world/america/*.idl
	ccmjava -local -o ${DEST_DIR}/src-gen ${INCLUDE} ${IDL3_DIR}/component/world/Test*.idl

	ccmjava -app -o ${DEST_DIR}/src ${INCLUDE} ${IDL3_DIR}/interface/world/*.idl
	ccmjava -app -o ${DEST_DIR}/src ${INCLUDE} ${IDL3_DIR}/interface/world/europe/*.idl
	ccmjava -app -o ${DEST_DIR}/src ${INCLUDE} ${IDL3_DIR}/interface/world/america/*.idl
	ccmjava -app -o ${DEST_DIR}/src ${INCLUDE} ${IDL3_DIR}/component/world/Test*.idl

stubs: idl3
	ccmtools idl2 -o ${DEST_DIR}/idl2 ${INCLUDE} ${IDL3_DIR}/interface/world/*.idl
	ccmtools idl2 -o ${DEST_DIR}/idl2 ${INCLUDE} ${IDL3_DIR}/interface/world/europe/*.idl
	ccmtools idl2 -o ${DEST_DIR}/idl2 ${INCLUDE} ${IDL3_DIR}/interface/world/america/*.idl
	ccmtools idl2 -o ${DEST_DIR}/idl2 ${INCLUDE} ${IDL3_DIR}/component/world/Test*.idl
	ccmtools-idl -java -o ${DEST_DIR}/src-gen -I${CCM_INSTALL}/include -I${IDL2_DIR} ${IDL2_DIR}/*.idl

check: 
	cp impl/Client.java ${DEST_DIR}/src/
	cp impl/TestImpl.java ${DEST_DIR}/src/world/ccm/local/
	cp impl/Test_mirroriface_mirrorImpl.java ${DEST_DIR}/src/world/ccm/local/
	ant -f ./build.xml
	bin/runClient

clean: 
	rm -rf ${DEST_DIR}
