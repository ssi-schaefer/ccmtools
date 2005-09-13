APP_NAME = Test
DEST_DIR = xxx
CCMTOOLS_CMD = ccmtools
IDL_INCLUDE=-I${DEST_DIR}/idl/interface -I${DEST_DIR}/idl/component

all: mirror

test: mirror check clean

idl3: ${APP_NAME}.idl 
	${CCMTOOLS_CMD} idl3 -o ${DEST_DIR}/idl ${APP_NAME}.idl

local: idl3
	${CCMTOOLS_CMD} c++local ${IDL_INCLUDE} -a -o ${DEST_DIR}/component/Test ${DEST_DIR}/idl/component/world/europe/austria/Test.idl ${DEST_DIR}/idl/component/world/europe/austria/TestHome.idl 

mirror: local
	${CCMTOOLS_CMD} idl3mirror ${IDL_INCLUDE} -o ${DEST_DIR}/idl ${DEST_DIR}/idl/component/world/europe/austria/Test.idl ${DEST_DIR}/idl/component/world/europe/austria/TestHome.idl
	${CCMTOOLS_CMD} c++local ${IDL_INCLUDE} -a -o ${DEST_DIR}/component/Test_mirror ${DEST_DIR}/idl/component/world/europe/austria/Test_mirror.idl ${DEST_DIR}/idl/component/world/europe/austria/TestHome_mirror.idl
	${CCMTOOLS_CMD} c++local-test ${IDL_INCLUDE} -a -o ${DEST_DIR}/component/Test_mirror ${DEST_DIR}/idl/component/world/europe/austria/Test.idl 

check: 
	cp impl/Makefile.py ${DEST_DIR}
	touch ${DEST_DIR}/component/Makefile.py
	touch ${DEST_DIR}/component/Test/Makefile.py
	touch ${DEST_DIR}/component/Test_mirror/Makefile.py
	confix.py --packageroot=`pwd`/${DEST_DIR} --bootstrap --configure --make --targets=check

clean: 
	confix.py --packageroot=`pwd`/${DEST_DIR} --bootstrap --configure --make --targets=clean	
	rm -rf ${DEST_DIR}
