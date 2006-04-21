DEST_DIR = xxx
IDL3_DIR = ${DEST_DIR}/idl3
IDL2_DIR = ${DEST_DIR}/idl2
INCLUDE  = -I${IDL3_DIR}/interface -I${IDL3_DIR}/component

all: remote clientlib

test: remote clientlib check clean 

idl3: Test.idl
	ccmtools idl3 -o ${IDL3_DIR} Test.idl

local: idl3
	ccmjava -iface -local -o ${DEST_DIR}/src-gen ${INCLUDE} ${IDL3_DIR}/interface/world/europe/austria/*.idl
	ccmjava -iface -local -o ${DEST_DIR}/src-gen ${INCLUDE} ${IDL3_DIR}/component/world/europe/austria/Test*.idl
	ccmjava -app -o ${DEST_DIR}/src ${INCLUDE} ${IDL3_DIR}/component/world/europe/austria/Test*.idl

stubs: idl3
	ccmtools idl2 -o ${IDL2_DIR} Test.idl
	ccmtools-idl -java -o ${DEST_DIR}/src-gen -I${CCMTOOLS_HOME}/idl -I${IDL2_DIR} ${IDL2_DIR}/*.idl

remote: local stubs
	ccmjava -remote -o ${DEST_DIR}/src-gen ${INCLUDE} ${IDL3_DIR}/interface/world/europe/austria/*.idl
	ccmjava -remote -o ${DEST_DIR}/src-gen ${INCLUDE} ${IDL3_DIR}/component/world/europe/austria/Test*.idl

clientlib: idl3
	ccmjava -clientlib -o ${DEST_DIR}/src-gen ${INCLUDE} ${IDL3_DIR}/interface/world/europe/austria/*.idl
	ccmjava -clientlib -o ${DEST_DIR}/src-gen ${INCLUDE} ${IDL3_DIR}/component/world/europe/austria/Test*.idl

check: 
	cp impl/TestbasicTypeImpl.java ${DEST_DIR}/src/world/europe/austria/ccm/local/
	cp impl/TestuserTypeImpl.java  ${DEST_DIR}/src/world/europe/austria/ccm/local/
	cp impl/TestvoidTypeImpl.java ${DEST_DIR}/src/world/europe/austria/ccm/local/ 
	ant -f ./build.xml
	bin/runClient

clean: 
	rm -rf xxx 

