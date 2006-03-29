DEST_DIR = xxx
IDL3_DIR = ${DEST_DIR}/idl3
IDL2_DIR = ${DEST_DIR}/idl2
INCLUDE  = -I${IDL3_DIR}/interface -I${IDL3_DIR}/component

all: local 

test: local check clean


idl3: Test.idl
	ccmtools idl3 -o ${IDL3_DIR} Test.idl

idl3mirror: idl3
	ccmtools idl3mirror ${INCLUDE} -o ${IDL3_DIR} ${IDL3_DIR}/component/world/europe/austria/Test*.idl

local: idl3mirror
	ccmjava -iface -local -o ${DEST_DIR}/src-gen ${INCLUDE} ${IDL3_DIR}/interface/world/europe/austria/*.idl
	ccmjava -iface -local -o ${DEST_DIR}/src-gen ${INCLUDE} ${IDL3_DIR}/component/world/europe/austria/Test*.idl

	ccmjava -app -o ${DEST_DIR}/src ${INCLUDE} ${IDL3_DIR}/component/world/europe/austria/Test*.idl

check: 
	cp impl/Client.java ${DEST_DIR}/src/
	cp impl/TestImpl.java ${DEST_DIR}/src/world/europe/austria/ccm/local/
	cp impl/Test_mirrorvoidType_mirrorImpl.java ${DEST_DIR}/src/world/europe/austria/ccm/local/
	cp impl/Test_mirrorbasicType_mirrorImpl.java ${DEST_DIR}/src/world/europe/austria/ccm/local/
	cp impl/Test_mirroruserType_mirrorImpl.java ${DEST_DIR}/src/world/europe/austria/ccm/local/
	ant -f ./build.xml
	bin/runClient

clean: 
	rm -rf ${DEST_DIR}
