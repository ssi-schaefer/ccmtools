DEST_DIR = xxx
IDL_INCLUDE=-I${DEST_DIR}/idl/interface -I${DEST_DIR}/idl/component

all: mirror

test: mirror check clean

idl3: Test.idl Console.idl
	ccmmodel -validator Test.idl Console.idl
	ccmidl -idl3 -o ${DEST_DIR}/idl Test.idl Console.idl

local: idl3
	ccmtools c++local ${IDL_INCLUDE} -o ${DEST_DIR}/interface ${DEST_DIR}/idl/interface/world/*.idl
	ccmtools c++local ${IDL_INCLUDE} -a -o ${DEST_DIR}/component/Test ${DEST_DIR}/idl/component/world/Test.idl ${DEST_DIR}/idl/component/world/TestHome.idl 

mirror: local
	ccmidl -idl3mirror ${IDL_INCLUDE} -o ${DEST_DIR}/idl ${DEST_DIR}/idl/component/world/Test.idl ${DEST_DIR}/idl/component/world/TestHome.idl
	ccmtools c++local ${IDL_INCLUDE} -a -o ${DEST_DIR}/component/TestMirror ${DEST_DIR}/idl/component/world/TestMirror.idl ${DEST_DIR}/idl/component/world/TestHomeMirror.idl
	ccmtools c++local-test ${IDL_INCLUDE} -a -o ${DEST_DIR}/component/Test_mirror ${DEST_DIR}/idl/component/world/Test.idl 

check: 
	ccmconfix -makefiles -o ${DEST_DIR} -pname "module_reopen" -pversion "1.0.0"
	confix.py --packageroot=`pwd`/${DEST_DIR} --bootstrap --configure --make --targets=check

clean: 
	confix.py --packageroot=`pwd`/${DEST_DIR} --make --targets=clean	
	rm -rf ${DEST_DIR}
