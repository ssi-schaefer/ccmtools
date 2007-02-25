DEST_DIR = xxx
IDL3_DIR = ${DEST_DIR}/idl3
IDL2_DIR = ${DEST_DIR}/idl2
INCLUDE  = -I${IDL3_DIR}/interface -I${IDL3_DIR}/component

all: local 

test: local check clean

idl3: Test.idl
	ccmmodel -validator Test.idl
	ccmidl -idl3 -o ${IDL3_DIR} Test.idl

local: idl3
	ccmjava -iface -local -o ${DEST_DIR}/src-gen ${INCLUDE} ${IDL3_DIR}/interface/wamas/Test/*.idl
	ccmjava -iface -local -o ${DEST_DIR}/src-gen ${INCLUDE} ${IDL3_DIR}/component/wamas/Test/*.idl
	ccmjava -iface -local -o ${DEST_DIR}/src-gen ${INCLUDE} ${IDL3_DIR}/component/wamas/*.idl

	ccmjava -app -o ${DEST_DIR}/src ${INCLUDE} ${IDL3_DIR}/component/wamas/Test/*.idl
	ccmjava -assembly -o ${DEST_DIR}/src ${INCLUDE} Test.assembly ${IDL3_DIR}/component/wamas/*.idl ${IDL3_DIR}/component/wamas/Test/*.idl
	
build:
	ant -f ./build.xml

check: 
	cp impl/Main.java ${DEST_DIR}/src/wamas
	cp impl/C1i1Impl.java ${DEST_DIR}/src/wamas/Test
	ant -f ./build.xml
	bin/runClient

clean: 
#	rm -rf ${DEST_DIR}
