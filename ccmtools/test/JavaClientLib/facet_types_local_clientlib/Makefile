DEST_DIR = xxx
IDL3_DIR = ${DEST_DIR}/idl3
IDL2_DIR = ${DEST_DIR}/idl2
INCLUDE  = -I${IDL3_DIR}/interface -I${IDL3_DIR}/component

all: clientlib 


idl3: Test.idl
	ccmtools idl3 -o ${IDL3_DIR} Test.idl

idl2: idl3
	ccmtools idl2 -o ${IDL2_DIR} Test.idl
	ccmtools-idl -java -o ${DEST_DIR}/src-gen -I${CCMTOOLS_HOME}/include -I${IDL2_DIR} ${IDL2_DIR}/*.idl

clientlib: idl2
	ccmjava -iface -local -clientlib -o ${DEST_DIR}/src-gen ${INCLUDE} ${IDL3_DIR}/interface/world/europe/austria/*.idl
	ccmjava -iface -local -clientlib -o ${DEST_DIR}/src-gen ${INCLUDE} ${IDL3_DIR}/component/world/europe/austria/Test*.idl
	ccmjava -app -o ${DEST_DIR}/src ${INCLUDE} ${IDL3_DIR}/component/world/europe/austria/Test*.idl

build:
	cp impl/TestvoidTypeImpl.java ${DEST_DIR}/src/world/europe/austria/ccm/local
	cp impl/TestbasicTypeImpl.java ${DEST_DIR}/src/world/europe/austria/ccm/local
	cp impl/TestuserTypeImpl.java ${DEST_DIR}/src/world/europe/austria/ccm/local
	ant -f ./build.xml

check: build 
	bin/runTest
	bin/runClient

clean: 
	rm -rf xxx 
