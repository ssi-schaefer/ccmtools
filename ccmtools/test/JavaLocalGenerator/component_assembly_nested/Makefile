DEST_DIR = xxx
IDL3_DIR = ${DEST_DIR}/idl3
IDL2_DIR = ${DEST_DIR}/idl2
INCLUDE  = -I${IDL3_DIR}/interface -I${IDL3_DIR}/component

all: local 

test: local check clean

idl3: Test.idl
	ccmtools idl3 -o ${IDL3_DIR} Test.idl

local: idl3
	ccmjava -iface -local -o ${DEST_DIR}/src-gen ${INCLUDE} ${IDL3_DIR}/interface/world/*.idl
	ccmjava -iface -local -o ${DEST_DIR}/src-gen ${INCLUDE} ${IDL3_DIR}/component/world/*.idl

	ccmjava -app -o ${DEST_DIR}/src ${INCLUDE} ${IDL3_DIR}/component/world/*.idl

check: 
# First, we check if the generated code compiles at all
	ant -f ./build.xml
	ant -f ./build.xml clean
# Second, we check if a functional test runs through
	cp impl/Assembly.java ${DEST_DIR}/src/world/
	cp impl/AssemblyFactory.java ${DEST_DIR}/src/world/
	cp impl/SuperTestbasicTypeImpl.java ${DEST_DIR}/src/world/ccm/local/
	cp impl/SuperTestuserTypeImpl.java ${DEST_DIR}/src/world/ccm/local/
	cp impl/BasicTestbasicTypeImpl.java ${DEST_DIR}/src/world/ccm/local/
	cp impl/UserTestuserTypeImpl.java ${DEST_DIR}/src/world/ccm/local/
	cp impl/Client.java ${DEST_DIR}/src/
	ant -f ./build.xml
	bin/runClient

clean: 
	rm -rf ${DEST_DIR}
