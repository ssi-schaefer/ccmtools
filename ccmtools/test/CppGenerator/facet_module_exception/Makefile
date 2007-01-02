DEST_DIR = xxx
IDL_INCLUDE=-I${DEST_DIR}/idl/interface -I${DEST_DIR}/idl/component

all: mirror

test: mirror check clean

test_empty: local check_empty clean

idl3: Test.idl
	ccmmodel -validator Test.idl
	ccmidl -idl3 -o ${DEST_DIR}/idl Test.idl

local: idl3
	ccmtools c++local ${IDL_INCLUDE} -o ${DEST_DIR}/interface ${DEST_DIR}/idl/interface/world/*.idl
	ccmtools c++local ${IDL_INCLUDE} -o ${DEST_DIR}/interface ${DEST_DIR}/idl/interface/world/europe/*.idl
	ccmtools c++local ${IDL_INCLUDE} -a -o ${DEST_DIR}/component/Test ${DEST_DIR}/idl/component/world/europe/Test.idl ${DEST_DIR}/idl/component/world/europe/TestHome.idl 

mirror: local
	ccmidl -idl3mirror ${IDL_INCLUDE} -o ${DEST_DIR}/idl ${DEST_DIR}/idl/component/world/europe/Test.idl ${DEST_DIR}/idl/component/world/europe/TestHome.idl
	ccmtools c++local ${IDL_INCLUDE} -a -o ${DEST_DIR}/component/TestMirror ${DEST_DIR}/idl/component/world/europe/TestMirror.idl ${DEST_DIR}/idl/component/world/europe/TestHomeMirror.idl
	ccmtools c++local-test ${IDL_INCLUDE} -a -o ${DEST_DIR}/component/TestMirror ${DEST_DIR}/idl/component/world/europe/Test.idl 

check: 
	ccmconfix -confix2 -o ${DEST_DIR} -pname "facet_module_exception" -pversion "1.0.0"
	confix2.py --packageroot=`pwd`/${DEST_DIR} --bootstrap --configure --make --targets=check

check_empty: check

clean: 
	confix2.py --packageroot=`pwd`/${DEST_DIR} --make --targets=clean	
	rm -rf ${DEST_DIR}
