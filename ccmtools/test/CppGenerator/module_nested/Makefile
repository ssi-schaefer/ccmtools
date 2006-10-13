DEST_DIR = xxx
IDL_INCLUDE=-I${DEST_DIR}/idl/interface -I${DEST_DIR}/idl/component

all: mirror

test: mirror check clean

idl3: Test.idl
	ccmmodel -validator Test.idl 
	ccmidl -idl3 -o ${DEST_DIR}/idl Test.idl

local: idl3
	ccmtools c++local ${IDL_INCLUDE} -a -o ${DEST_DIR}/component/Test ${DEST_DIR}/idl/component/world/europe/austria/Test.idl ${DEST_DIR}/idl/component/world/europe/austria/TestHome.idl 

mirror: local
	ccmidl -idl3mirror ${IDL_INCLUDE} -o ${DEST_DIR}/idl ${DEST_DIR}/idl/component/world/europe/austria/Test.idl ${DEST_DIR}/idl/component/world/europe/austria/TestHome.idl
	ccmtools c++local ${IDL_INCLUDE} -a -o ${DEST_DIR}/component/TestMirror ${DEST_DIR}/idl/component/world/europe/austria/TestMirror.idl ${DEST_DIR}/idl/component/world/europe/austria/TestHomeMirror.idl
	ccmtools c++local-test ${IDL_INCLUDE} -a -o ${DEST_DIR}/component/TestMirror ${DEST_DIR}/idl/component/world/europe/austria/Test.idl 

check: 
	ccmconfix -confix2 -o ${DEST_DIR} -pname "module_nested" -pversion "1.0.0"
	confix2.py --packageroot=`pwd`/${DEST_DIR} --bootstrap --configure --make --targets=check

clean: 
	confix2.py --packageroot=`pwd`/${DEST_DIR} --make --targets=clean	
	rm -rf ${DEST_DIR}
