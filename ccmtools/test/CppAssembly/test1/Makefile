DEST_DIR=gen

IDL_INCLUDE=-I${DEST_DIR}/idl/interface -I${DEST_DIR}/idl/component

all: local

idl: test1.idl
	ccmmodel -validator test1.idl
	ccmidl -idl3 -o ${DEST_DIR}/idl test1.idl

local: idl
	ccmtools c++local ${IDL_INCLUDE} -a -o ${DEST_DIR}/interfaces `find ${DEST_DIR}/idl/interface -name '*.idl'`
	ccmtools c++local ${IDL_INCLUDE} -a -o ${DEST_DIR}/components `find ${DEST_DIR}/idl/component -name '*.idl'`
	ccmtools c++assembly ${IDL_INCLUDE} -o ${DEST_DIR}/components `find ${DEST_DIR}/idl/component -name '*.idl'` test1.assembly

check: local
	ccmconfix -confix2 -o ${DEST_DIR} -pname "assembly-test1" -pversion "0.0.1"
	confix2.py --packageroot=`pwd`/${DEST_DIR} --bootstrap --configure --make

clean:
	confix2.py --packageroot=`pwd`/${DEST_DIR} --make --targets=clean
	rm -rf ${DEST_DIR}
