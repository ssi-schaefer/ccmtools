SOURCE_DIR = ../CarRental
DEST_DIR = xxx
IDL3_DIR = ${DEST_DIR}/idl3
IDL2_DIR = ${DEST_DIR}/idl2
INCLUDE  = -I${IDL3_DIR}/interface -I${IDL3_DIR}/component

all: clientlib 

idl3: 
	ccmtools idl3 -o ${IDL3_DIR} ${SOURCE_DIR}/idl/*.idl

idl2: idl3
	ccmtools idl2 -o ${IDL2_DIR} ${INCLUDE} ${IDL3_DIR}/interface/BigBusiness/*.idl
	ccmtools idl2 -o ${IDL2_DIR} ${INCLUDE} ${IDL3_DIR}/component/BigBusiness/*.idl

stubs: idl2
	ccmtools-idl -java -o ${DEST_DIR}/src-gen -I${CCM_INSTALL}/include -I${IDL2_DIR} ${IDL2_DIR}/*.idl

clientlib: stubs
	ccmjava -clientlib -o ${DEST_DIR}/src-gen ${INCLUDE} ${IDL3_DIR}/interface/BigBusiness/*.idl
	ccmjava -clientlib -o ${DEST_DIR}/src-gen ${INCLUDE} ${IDL3_DIR}/component/BigBusiness/*.idl

check: 
	ant -f ./build.xml
	bin/runClient

clean: 
	rm -rf xxx 
