APP_NAME = Test
DEST_DIR = xxx
IDL_INCLUDE=-I${DEST_DIR}/idl/interface -I${DEST_DIR}/idl/component

all: mirror

test: mirror check clean

idl3: ${APP_NAME}.idl
	ccmidl -idl3 -o ${DEST_DIR}/idl ${APP_NAME}.idl

local: idl3
	ccmtools c++local ${IDL_INCLUDE} -o ${DEST_DIR}/interface ${DEST_DIR}/idl/interface/*.idl
	ccmtools c++local ${IDL_INCLUDE} -a -o ${DEST_DIR}/component/Test ${DEST_DIR}/idl/component/Test.idl ${DEST_DIR}/idl/component/TestHome.idl 

mirror: local
	ccmtools idl3mirror ${IDL_INCLUDE} -o ${DEST_DIR}/idl ${DEST_DIR}/idl/component/Test.idl ${DEST_DIR}/idl/component/TestHome.idl
	ccmtools c++local ${IDL_INCLUDE} -a -o ${DEST_DIR}/component/Test_mirror ${DEST_DIR}/idl/component/Test_mirror.idl ${DEST_DIR}/idl/component/TestHome_mirror.idl
	ccmtools c++local-test ${IDL_INCLUDE} -a -o ${DEST_DIR}/component/Test_mirror ${DEST_DIR}/idl/component/Test.idl 

check: 
	cp impl/Makefile.py ${DEST_DIR}
	touch ${DEST_DIR}/interface/Makefile.py
	touch ${DEST_DIR}/component/Makefile.py
	touch ${DEST_DIR}/component/Test/Makefile.py
	touch ${DEST_DIR}/component/Test_mirror/Makefile.py
	cp impl/Test_impl.cc ${DEST_DIR}/component/Test/
	confix.py --packageroot=`pwd`/${DEST_DIR} --bootstrap --configure --make --targets=check

clean: 
	confix.py --packageroot=`pwd`/${DEST_DIR} --bootstrap --configure --make --targets=clean	
	rm -rf ${DEST_DIR}
