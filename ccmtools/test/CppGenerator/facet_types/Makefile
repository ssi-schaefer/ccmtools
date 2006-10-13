DEST_DIR = xxx
IDL_INCLUDE=-I${DEST_DIR}/idl/interface -I${DEST_DIR}/idl/component

all: mirror

test: mirror check clean

idl3: Test.idl
	ccmmodel -validator Test.idl
	ccmidl -idl3 -o ${DEST_DIR}/idl Test.idl

local: idl3
	ccmtools c++local ${IDL_INCLUDE} -o ${DEST_DIR}/src/interface ${DEST_DIR}/idl/interface/*.idl
	ccmtools c++local ${IDL_INCLUDE} -a -o ${DEST_DIR}/src/component/Test ${DEST_DIR}/idl/component/Test.idl ${DEST_DIR}/idl/component/TestHome.idl 

mirror: local
	ccmidl -idl3mirror ${IDL_INCLUDE} -o ${DEST_DIR}/idl ${DEST_DIR}/idl/component/Test.idl ${DEST_DIR}/idl/component/TestHome.idl
	ccmtools c++local ${IDL_INCLUDE} -a -o ${DEST_DIR}/src/component/TestMirror ${DEST_DIR}/idl/component/TestMirror.idl ${DEST_DIR}/idl/component/TestHomeMirror.idl
	ccmtools c++local-test ${IDL_INCLUDE} -a -o ${DEST_DIR}/src/component/TestMirror ${DEST_DIR}/idl/component/Test.idl 

check: 
	cp impl/Test_type_test_impl.cc ${DEST_DIR}/src/component/Test/
	cp impl/MyObject.h ${DEST_DIR}/src/component/TestMirror/
	cp impl/MyObject.cc ${DEST_DIR}/src/component/TestMirror/
	cp impl/TestMirror_impl.cc ${DEST_DIR}/src/component/TestMirror/
	cp impl/_check_witout_mirror.cc ${DEST_DIR}/src/component/TestMirror/test
	ccmconfix -confix2 -o ${DEST_DIR}/src -pname "facet_types" -pversion "1.0.0"
	confix2.py --packageroot=`pwd`/${DEST_DIR}/src --bootstrap --configure --make --targets=check

check_empty:
	ccmconfix -confix2 -o ${DEST_DIR}/src -pname "facet_types" -pversion "1.0.0"
	confix2.py --packageroot=`pwd`/${DEST_DIR}/src --bootstrap --configure --make --targets=check

clean: 
	confix2.py --packageroot=`pwd`/${DEST_DIR}/src --make --targets=clean	
	rm -rf ${DEST_DIR}
