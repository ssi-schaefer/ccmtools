IDL_FILE = ../../CppRemoteGenerator/supports_module_exception/Test.idl
DEST_DIR = xxx
IDL3_DIR = ${DEST_DIR}/idl3
IDL2_DIR = ${DEST_DIR}/idl2
INCLUDE  = -I${IDL3_DIR}/interface -I${IDL3_DIR}/component

all: client_lib 

idl3: ${IDL_FILE}
	ccmtools idl3 -o ${IDL3_DIR} ${IDL_FILE}

idl2: idl3
	ccmtools idl2 -o ${IDL2_DIR} ${IDL_FILE}

client_lib: idl2
	ccmtools-idl -java -o ${DEST_DIR}/src-gen -I${CCM_INSTALL}/include -I${IDL2_DIR} ${IDL2_DIR}/*.idl
	ccmclient -java -o ${DEST_DIR}/src-gen ${INCLUDE} ${IDL3_DIR}/interface/world/europe/austria/*.idl
	ccmclient -java -o ${DEST_DIR}/src-gen ${INCLUDE} ${IDL3_DIR}/component/world/europe/austria/Test*.idl

check: 
	ant -f ./build.xml
	bin/runClient

clean: 
	rm -rf xxx 

