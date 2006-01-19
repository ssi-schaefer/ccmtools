
APP_NAME = Test
DEST_DIR = xxx
IDL_INCLUDE=-I${DEST_DIR}/idl/interface -I${DEST_DIR}/idl/component

all: client_lib 


idl3: ${APP_NAME}.idl
	ccmtools idl3 -o ${DEST_DIR}/idl ${APP_NAME}.idl

idl2: idl3
	ccmtools idl2 -o ${DEST_DIR}/CCM_corba_stubs ${APP_NAME}.idl

client_lib: idl2
	make -C ${DEST_DIR}/CCM_corba_stubs client
	ccmclient -java -o ${DEST_DIR}/JavaClientLib/src-gen ${IDL_INCLUDE} ${DEST_DIR}/idl/interface/america/*.idl
	ccmclient -java -o ${DEST_DIR}/JavaClientLib/src-gen ${IDL_INCLUDE} ${DEST_DIR}/idl/interface/europe/*.idl
	ccmclient -java -o ${DEST_DIR}/JavaClientLib/src-gen ${IDL_INCLUDE} ${DEST_DIR}/idl/component/world/Test*.idl

check: 
	ant -f ${DEST_DIR}/CCM_corba_stubs/build.xml install -Dprefix=../JavaClientLib -Djarname=ClientStubs
	ant -f ./build.xml
	bin/runClient

clean: 
	rm -rf xxx 

