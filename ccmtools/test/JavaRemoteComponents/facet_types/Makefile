DEST_DIR = xxx
IDL3_DIR = ${DEST_DIR}/idl3
IDL2_DIR = ${DEST_DIR}/idl2
INCLUDE  = -I${IDL3_DIR}/interface -I${IDL3_DIR}/component

all: metainf remote clientlib

test: metainf remote clientlib check_local check clean 

idl3: Test.idl
	ccmmodel -check Test.idl
	ccmidl -idl3 -o ${IDL3_DIR} Test.idl

metainf: idl3
	mkdir ${DEST_DIR}/META-INF
	ccmdescriptor -o ${DEST_DIR}/META-INF -name TestHome ${INCLUDE} ${IDL3_DIR}/component/world/europe/austria/TestHome.idl

local: idl3
	ccmjava -iface -local -o ${DEST_DIR}/src-gen ${INCLUDE} ${IDL3_DIR}/interface/world/*.idl
	ccmjava -iface -local -o ${DEST_DIR}/src-gen ${INCLUDE} ${IDL3_DIR}/interface/world/europe/*.idl
	ccmjava -iface -local -o ${DEST_DIR}/src-gen ${INCLUDE} ${IDL3_DIR}/interface/world/europe/austria/*.idl
	ccmjava -iface -local -o ${DEST_DIR}/src-gen ${INCLUDE} ${IDL3_DIR}/component/world/europe/austria/Test*.idl
	ccmjava -app -o ${DEST_DIR}/src ${INCLUDE} ${IDL3_DIR}/component/world/europe/austria/Test*.idl

stubs: idl3
	ccmtools idl2 -o ${IDL2_DIR} Test.idl
	ccmtools-idl -java -o ${DEST_DIR}/src-gen -I${CCMTOOLS_HOME}/idl -I${IDL2_DIR} ${IDL2_DIR}/*.idl

remote: local stubs
	ccmjava -remote -o ${DEST_DIR}/src-gen ${INCLUDE} ${IDL3_DIR}/interface/world/*.idl
	ccmjava -remote -o ${DEST_DIR}/src-gen ${INCLUDE} ${IDL3_DIR}/interface/world/europe/*.idl
	ccmjava -remote -o ${DEST_DIR}/src-gen ${INCLUDE} ${IDL3_DIR}/interface/world/europe/austria/*.idl
	ccmjava -remote -o ${DEST_DIR}/src-gen ${INCLUDE} ${IDL3_DIR}/component/world/europe/austria/Test*.idl

clientlib: idl3
	ccmjava -clientlib -o ${DEST_DIR}/src-gen ${INCLUDE} ${IDL3_DIR}/interface/world/europe/austria/*.idl
	ccmjava -clientlib -o ${DEST_DIR}/src-gen ${INCLUDE} ${IDL3_DIR}/component/world/europe/austria/Test*.idl

build:
	cp impl/TestbasicTypeImpl.java ${DEST_DIR}/src/world/europe/austria/ccm/local/
	cp impl/TestuserTypeImpl.java  ${DEST_DIR}/src/world/europe/austria/ccm/local/
	cp impl/TestvoidTypeImpl.java ${DEST_DIR}/src/world/europe/austria/ccm/local/ 
	ant -f ./build.xml

check_local: build
	bin/runLocalClient

check: build
	bin/runClient

clean: 
	rm -rf ${DEST_DIR}

