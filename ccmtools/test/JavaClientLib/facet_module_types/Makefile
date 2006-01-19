
APP_NAME = Test
CCMTOOLS_CMD = ccmtools
DEST_DIR = xxx
IDL_INCLUDE=-I${DEST_DIR}/idl/interface -I${DEST_DIR}/idl/component

all: stubs client


idl3: ${APP_NAME}.idl
	${CCMTOOLS_CMD} idl3 -o ${DEST_DIR}/idl ${APP_NAME}.idl

idl2: idl3
	${CCMTOOLS_CMD} idl2 -o ${DEST_DIR}/CCM_CORBA_Stubs ${APP_NAME}.idl

stubs: idl2
	make -C ${DEST_DIR}/CCM_CORBA_Stubs client

client: 
	ccmclient -java -o ${DEST_DIR}/JavaClientLib/src-gen Test.idl
	ant

clean: 
	rm -rf xxx 

