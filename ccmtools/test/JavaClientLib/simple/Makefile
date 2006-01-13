
APP_NAME = Test
CCMTOOLS_CMD = ccmtools
DEST_DIR = xxx
IDL_INCLUDE=-I${DEST_DIR}/idl/interface -I${DEST_DIR}/idl/component

all: remote stubs client 

test: remote stubs check clean

idl3: ${APP_NAME}.idl
	${CCMTOOLS_CMD} idl3 -o ${DEST_DIR}/idl ${APP_NAME}.idl

local: idl3
	${CCMTOOLS_CMD} c++local ${IDL_INCLUDE} -o ${DEST_DIR}/interface ${DEST_DIR}/idl/interface/wamas/*.idl
	${CCMTOOLS_CMD} c++local ${IDL_INCLUDE} -a -o ${DEST_DIR}/component/Test ${DEST_DIR}/idl/component/wamas/Test.idl ${DEST_DIR}/idl/component/wamas/TestHome.idl 

remote: local 
	${CCMTOOLS_CMD} idl2 -o ${DEST_DIR}/CCM_CORBA_Stubs ${APP_NAME}.idl
	${CCMTOOLS_CMD} c++remote -o ${DEST_DIR}/ ${APP_NAME}.idl
	${CCMTOOLS_CMD} c++remote-test -o ${DEST_DIR}/component/Test ${APP_NAME}.idl

stubs:
	make -C ${DEST_DIR}/CCM_CORBA_Stubs

client:
	make -C ${DEST_DIR}/CCM_CORBA_Stubs client
	ccmclient -java -o ${DEST_DIR}/JavaClientLib/src-gen Test.idl
	ant

check: 
	cp impl/Makefile.py ${DEST_DIR}/
	touch ${DEST_DIR}/interface/Makefile.py
	touch ${DEST_DIR}/component/Makefile.py
	touch ${DEST_DIR}/component/Test/Makefile.py
	cp impl/Test_basicTypeIn_impl.cc ${DEST_DIR}/component/Test/
#	cp impl/Test_userType_impl.cc ${DEST_DIR}/component/Test/
#	cp impl/Test_voidType_impl.h ${DEST_DIR}/component/Test/
#	cp impl/Test_voidType_impl.cc ${DEST_DIR}/component/Test/
	cp impl/_check_wamas_ccm_remote_component_Test.cc ${DEST_DIR}/component/Test/test/
	confix.py --packageroot=`pwd`/xxx --bootstrap --configure --make --targets=check

clean: 
	confix.py --packageroot=`pwd`/xxx --make --targets=clean	
	rm -rf xxx 
	rm -rf ${HOME}/tmp/ccmtools_tmp/xxx
