DEST_DIR = xxx
IDL_INCLUDE=-I${DEST_DIR}/idl/interface -I${DEST_DIR}/idl/component

all: local

test: local check clean

test_empty: local check_empty clean

idl3: Test.idl
	ccmmodel -validator Test.idl
	ccmidl -idl3 -o ${DEST_DIR}/idl Test.idl

local: idl3
	ccmtools c++local ${IDL_INCLUDE} -o ${DEST_DIR}/interface ${DEST_DIR}/idl/interface/*.idl
	ccmtools c++local ${IDL_INCLUDE} -a -o ${DEST_DIR}/component/Test ${DEST_DIR}/idl/component/Test.idl ${DEST_DIR}/idl/component/TestHome.idl 
	mkdir ${DEST_DIR}/component/Test/test

check: 
	cp impl/_check_Test.cc ${DEST_DIR}/component/Test/test
	ccmconfix -confix2 -o ${DEST_DIR} -pname "attribute_types" -pversion "1.0.0"
	confix2.py --packageroot=`pwd`/${DEST_DIR} --bootstrap --configure --make --targets=check

check_empty:
	ccmconfix -confix2 -o ${DEST_DIR} -pname "attribute_types" -pversion "1.0.0"
	confix2.py --packageroot=`pwd`/${DEST_DIR} --bootstrap --configure --make 
	
clean: 
	confix2.py --packageroot=`pwd`/${DEST_DIR} --make --targets=clean	
	rm -rf ${DEST_DIR}
