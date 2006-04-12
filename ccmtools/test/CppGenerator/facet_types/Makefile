APP_NAME = Test
DEST_DIR = xxx
CCMTOOLS_CMD = ccmtools
IDL_INCLUDE=-I${DEST_DIR}/idl/interface -I${DEST_DIR}/idl/component

all: mirror

test: mirror check clean

idl3: ${APP_NAME}.idl
	${CCMTOOLS_CMD} idl3 -o ${DEST_DIR}/idl ${APP_NAME}.idl

local: idl3
	${CCMTOOLS_CMD} c++local ${IDL_INCLUDE} -o ${DEST_DIR}/src/interface ${DEST_DIR}/idl/interface/*.idl
	${CCMTOOLS_CMD} c++local ${IDL_INCLUDE} -a -o ${DEST_DIR}/src/component/Test ${DEST_DIR}/idl/component/Test.idl ${DEST_DIR}/idl/component/TestHome.idl 

mirror: local
	${CCMTOOLS_CMD} idl3mirror ${IDL_INCLUDE} -o ${DEST_DIR}/idl ${DEST_DIR}/idl/component/Test.idl ${DEST_DIR}/idl/component/TestHome.idl
	${CCMTOOLS_CMD} c++local ${IDL_INCLUDE} -a -o ${DEST_DIR}/src/component/Test_mirror ${DEST_DIR}/idl/component/Test_mirror.idl ${DEST_DIR}/idl/component/TestHome_mirror.idl
	${CCMTOOLS_CMD} c++local-test ${IDL_INCLUDE} -a -o ${DEST_DIR}/src//component/Test_mirror ${DEST_DIR}/idl/component/Test.idl 

check: 
	ccmconfix -makefiles -o ${DEST_DIR}/src -pname "facet_types" -pversion "1.0.0"
	cp impl/Test_type_test_impl.cc ${DEST_DIR}/src/component/Test/
	cp impl/MyObject.h ${DEST_DIR}/src/component/Test_mirror/
	cp impl/MyObject.cc ${DEST_DIR}/src/component/Test_mirror/
	cp impl/Test_mirror_impl.cc ${DEST_DIR}/src/component/Test_mirror/
	cp test/_check_witout_mirror.cc ${DEST_DIR}/src/component/Test_mirror/test
	confix.py --packageroot=`pwd`/${DEST_DIR}/src --bootstrap --configure --make --targets=check

clean: 
	confix.py --packageroot=`pwd`/${DEST_DIR}/src --bootstrap --configure --make --targets=clean	
	rm -rf ${DEST_DIR}
