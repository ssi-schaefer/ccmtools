
APP_NAME = Test
CCMTOOLS_CMD = ccmtools
DEST_DIR = xxx
IDL_INCLUDE=-I${DEST_DIR}/idl/interface -I${DEST_DIR}/idl/component


all: stubs 



idl3: ${APP_NAME}.idl
	${CCMTOOLS_CMD} idl3 -o ${DEST_DIR}/idl ${APP_NAME}.idl

stubs: idl3
	${CCMTOOLS_CMD} idl2 -o ${DEST_DIR}/CCM_corba_stubs ${APP_NAME}.idl
	make -C ${DEST_DIR}/CCM_corba_stubs client


clean: 
	rm -rf xxx 
