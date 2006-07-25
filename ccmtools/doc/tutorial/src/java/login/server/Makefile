IDL_SOURCE=../Login.idl
DEST_DIR = .
IDL3_DIR = ${DEST_DIR}/idl3
IDL2_DIR = ${DEST_DIR}/idl2
INCLUDE  = -I${IDL3_DIR}/interface -I${IDL3_DIR}/component

all: local 

test: local check clean

idl3: ${IDL_SOURCE}
	ccmtools idl3 -o ${IDL3_DIR} ${IDL_SOURCE}

local: idl3
	ccmjava -iface -local -o ${DEST_DIR}/src-gen ${INCLUDE} ${IDL3_DIR}/interface/system/*.idl
	ccmjava -iface -local -o ${DEST_DIR}/src-gen ${INCLUDE} ${IDL3_DIR}/component/system/Server*.idl
	ccmjava -app -o ${DEST_DIR}/src ${INCLUDE} ${IDL3_DIR}/component/system/Server*.idl


check: 
#	cp impl/HelloWorldhelloImpl.java ${DEST_DIR}/src/world/ccm/local/
	ant -f ./build.xml

clean: 
	ant clean
	rm -rf ${DEST_DIR}/idl3
	rm -rf ${DEST_DIR}/src-gen
	rm -rf ${DEST_DIR}/src
