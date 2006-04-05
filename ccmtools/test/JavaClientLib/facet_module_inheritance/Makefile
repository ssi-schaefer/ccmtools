DEST_DIR = xxx
IDL3_DIR = ${DEST_DIR}/idl3
IDL2_DIR = ${DEST_DIR}/idl2
INCLUDE  = -I${IDL3_DIR}/interface -I${IDL3_DIR}/component

all: client_lib 

test-build: client_lib build clean 

idl3: Test.idl
	ccmtools idl3 -o ${IDL3_DIR} Test.idl

idl2: idl3
	ccmtools idl2 -o ${IDL2_DIR} Test.idl

client_lib: idl2
	ccmtools-idl -java -o ${DEST_DIR}/src-gen -I${CCM_INSTALL}/include -I${IDL2_DIR} ${IDL2_DIR}/*.idl

	ccmjava -iface -clientlib -o ${DEST_DIR}/src-gen ${INCLUDE} ${IDL3_DIR}/interface/europe/*.idl
	ccmjava -iface -clientlib -o ${DEST_DIR}/src-gen ${INCLUDE} ${IDL3_DIR}/interface/america/*.idl
	ccmjava -iface -clientlib -o ${DEST_DIR}/src-gen ${INCLUDE} ${IDL3_DIR}/component/world/Test*.idl

build:
	ant -f ./build.xml

check: build 
	bin/runClient

clean: 
	rm -rf xxx 

