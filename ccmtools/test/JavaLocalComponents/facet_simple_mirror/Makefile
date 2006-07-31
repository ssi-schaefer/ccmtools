DEST_DIR = xxx
IDL3_DIR = ${DEST_DIR}/idl3
IDL2_DIR = ${DEST_DIR}/idl2
INCLUDE  = -I${IDL3_DIR}/interface -I${IDL3_DIR}/component

all: local

test: local check clean

idl3: Test.idl
	ccmidl -idl3 -o ${IDL3_DIR} Test.idl

idl3mirror: idl3
#	ccmtools idl3mirror -o ${IDL3_DIR} ${INCLUDE} ${IDL3_DIR}/component/world/Test*.idl 
	ccmidl -idl3mirror -o ${IDL3_DIR} ${INCLUDE} ${IDL3_DIR}/component/world/Test*.idl 

# Generate component metadata in a component descriptor file.
metainf: idl3
	mkdir ${DEST_DIR}/META-INF
	ccmdescriptor -o ${DEST_DIR}/META-INF -name TestHome ${INCLUDE} ${IDL3_DIR}/component/world/TestHome.idl

local: idl3mirror metainf
	ccmjava -iface -local -o ${DEST_DIR}/src-gen ${INCLUDE} ${IDL3_DIR}/interface/world/*.idl
	ccmjava -iface -local -o ${DEST_DIR}/src-gen ${INCLUDE} ${IDL3_DIR}/component/world/Test*.idl
	ccmjava -app -o ${DEST_DIR}/src ${INCLUDE} ${IDL3_DIR}/component/world/Test*.idl

build: 
	cp impl/Client.java ${DEST_DIR}/src/
#	cp impl/TestportImpl.java ${DEST_DIR}/src/world/ccm/local/
	ant -f ./build.xml

check: build
	bin/runClient

clean: 
	rm -rf ${DEST_DIR}

