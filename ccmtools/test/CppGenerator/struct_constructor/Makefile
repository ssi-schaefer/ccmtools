DEST_DIR = xxx
IDL_INCLUDE=-I${DEST_DIR}/idl/interface 

all: local

test: local check clean

idl3: Test.idl
	ccmmodel -validator Test.idl
	ccmidl -idl3 -o ${DEST_DIR}/idl Test.idl

local: idl3
	ccmtools c++local ${IDL_INCLUDE} -o ${DEST_DIR}/src/interface ${DEST_DIR}/idl/interface/*.idl
	mkdir ${DEST_DIR}/src/test

check: 
	cp impl/_check_struct_init.cc ${DEST_DIR}/src/test
	ccmconfix -confix2 -o ${DEST_DIR}/src -pname "struct_constructor" -pversion "1.0.0"
	confix2.py --packageroot=`pwd`/${DEST_DIR}/src --bootstrap --configure --make --targets=check

clean: 
	confix2.py --packageroot=`pwd`/${DEST_DIR}/src --make --targets=clean	
	rm -rf ${DEST_DIR}
