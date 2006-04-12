DEST_DIR = xxx
IDL3_DIR = ${DEST_DIR}/idl3
IDL2_DIR = ${DEST_DIR}/idl2
INCLUDE  = -I${IDL3_DIR}/interface -I${IDL3_DIR}/component

all: client_lib 

test: client_lib check clean

test-build: client_lib build clean 

idl3: Test.idl
	ccmtools idl3 -o ${IDL3_DIR} Test.idl
	ccmconfix -makefiles -pname facet-types -pversion 1.0.0 -o ${IDL3_DIR}

idl2: idl3
	ccmtools idl2 -o ${IDL2_DIR} Test.idl
	ccmtools-idl -java -o ${DEST_DIR}/src-gen -I${CCM_INSTALL}/include -I${IDL2_DIR} ${IDL2_DIR}/*.idl

client_lib: idl2
	ccmjava -iface -clientlib -o ${DEST_DIR}/src-gen ${INCLUDE} ${IDL3_DIR}/interface/world/europe/austria/*.idl
	ccmjava -iface -clientlib -o ${DEST_DIR}/src-gen ${INCLUDE} ${IDL3_DIR}/component/world/europe/austria/Test*.idl

build:
	ant -f ./build.xml

check: build 
	bin/runClient

doxy: Test.idl
	doxygen Doxyfile

clean: 
	rm -rf xxx 
	rm -rf doc
