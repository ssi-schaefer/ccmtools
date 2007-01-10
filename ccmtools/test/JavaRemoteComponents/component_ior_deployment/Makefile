DEST_DIR = xxx
IDL3_DIR = ${DEST_DIR}/idl3
IDL2_DIR = ${DEST_DIR}/idl2
INCLUDE  = -I${IDL3_DIR}/interface -I${IDL3_DIR}/component

all: metainf remote clientlib

test: metainf remote clientlib check clean 

idl3: Test.idl
	ccmmodel -validator Test.idl
	ccmidl -idl3 -o ${IDL3_DIR} Test.idl

# Generate component metadata in a component descriptor file.
metainf: idl3
	mkdir ${DEST_DIR}/META-INF
	ccmdescriptor -o ${DEST_DIR}/META-INF -name TestHome ${INCLUDE} ${IDL3_DIR}/component/world/TestHome.idl

local: idl3
	ccmjava -iface -local -o ${DEST_DIR}/src-gen-local ${INCLUDE} ${IDL3_DIR}/interface/world/*.idl
	ccmjava -iface -local -o ${DEST_DIR}/src-gen-local ${INCLUDE} ${IDL3_DIR}/component/world/Test*.idl
	ccmjava -app -o ${DEST_DIR}/src ${INCLUDE} ${IDL3_DIR}/component/world/Test*.idl

stubs: local
	ccmidl -idl2 -o ${IDL2_DIR} Test.idl
	ccmtools-idl -java -o ${DEST_DIR}/src-gen-remote -I${CCMTOOLS_HOME}/idl -I${IDL2_DIR} ${IDL2_DIR}/*.idl

remote: stubs
	ccmjava -remote -o ${DEST_DIR}/src-gen-remote ${INCLUDE} ${IDL3_DIR}/interface/world/*.idl
	ccmjava -remote -o ${DEST_DIR}/src-gen-remote ${INCLUDE} ${IDL3_DIR}/component/world/Test*.idl

clientlib: idl3
	ccmjava -clientlib -o ${DEST_DIR}/src-gen-remote ${INCLUDE} ${IDL3_DIR}/interface/world/*.idl
	ccmjava -clientlib -o ${DEST_DIR}/src-gen-remote ${INCLUDE} ${IDL3_DIR}/component/world/Test*.idl

build: 
	ant -f ./build.xml

check: 
	cp impl/TestImpl.java ${DEST_DIR}/src/world/
	cp impl/TestportImpl.java ${DEST_DIR}/src/world/
	ant -f ./build.xml
	bin/runClientLocal
	bin/runClient

clean: 
	rm -rf ${DEST_DIR}

