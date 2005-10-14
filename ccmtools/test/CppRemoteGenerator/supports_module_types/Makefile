APP_NAME = Test
CCMTOOLS_CMD = ccmtools
DEST_DIR = xxx
IDL_INCLUDE=-I${DEST_DIR}/idl/interface -I${DEST_DIR}/idl/component

all: remote stubs 

test: remote stubs check clean

idl3: ${APP_NAME}.idl
	${CCMTOOLS_CMD} idl3 -o ${DEST_DIR}/idl ${APP_NAME}.idl

local: idl3
	${CCMTOOLS_CMD} c++local ${IDL_INCLUDE} -o ${DEST_DIR}/interface ${DEST_DIR}/idl/interface/world/europe/austria/*.idl
	${CCMTOOLS_CMD} c++local ${IDL_INCLUDE} -a -o ${DEST_DIR}/component/Test ${DEST_DIR}/idl/component/world/europe/austria/Test.idl ${DEST_DIR}/idl/component/world/europe/austria/TestHome.idl 

remote: local
	${CCMTOOLS_CMD} idl2 -o ${DEST_DIR}/CCM_corba_stubs ${APP_NAME}.idl
	${CCMTOOLS_CMD} c++remote -o ${DEST_DIR}/ ${APP_NAME}.idl
	${CCMTOOLS_CMD} c++remote-test -o ${DEST_DIR}/component/Test ${APP_NAME}.idl

stubs:
	make -C ${DEST_DIR}/CCM_corba_stubs
check: 
	cp impl/Makefile.py ${DEST_DIR}/
	touch ${DEST_DIR}/interface/Makefile.py
	touch ${DEST_DIR}/component/Makefile.py
	touch ${DEST_DIR}/component/Test/Makefile.py
	cp impl/Test_impl.cc ${DEST_DIR}/component/Test/
	cp impl/Test_impl.h ${DEST_DIR}/component/Test/
	cp impl/_check_world_europe_austria_ccm_remote_component_Test.cc ${DEST_DIR}/component/Test/test/
	confix.py --packageroot=`pwd`/xxx --bootstrap --configure --make --targets=check

clean: 
	confix.py --packageroot=`pwd`/xxx --make --targets=clean	
	rm -rf xxx 

