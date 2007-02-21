DEST_DIR=gen
ITF_DIR=${DEST_DIR}/interfaces
C1_DIR=${DEST_DIR}/C1
C2_DIR=${DEST_DIR}/C2
C3_DIR=${DEST_DIR}/C3

IDL_INCLUDE=-I${DEST_DIR}/idl/interface -I${DEST_DIR}/idl/component

all: local

idl: test1.idl
	ccmmodel -validator test1.idl
	ccmidl -idl3 -o ${DEST_DIR}/idl test1.idl

local: idl
	ccmtools c++local ${IDL_INCLUDE} -a -o ${ITF_DIR} `find ${DEST_DIR}/idl/interface -name '*.idl'`
	ccmtools c++local ${IDL_INCLUDE} -a -o ${C1_DIR} `find ${DEST_DIR}/idl/component -name '?1.idl'`
	ccmtools c++local ${IDL_INCLUDE} -a -o ${C2_DIR} `find ${DEST_DIR}/idl/component -name '?2.idl'`
	ccmtools c++local ${IDL_INCLUDE} -a -o ${C3_DIR} `find ${DEST_DIR}/idl/component -name '?3.idl'`
	ccmtools c++assembly ${IDL_INCLUDE} -o ${C3_DIR} `find ${DEST_DIR}/idl/component -name '*.idl'` test1.assembly

check: local
	ccmconfix -confix2 -o ${DEST_DIR} -pname "assembly-test1" -pversion "0.0.1"
	cd ${DEST_DIR} && confix2.py --bootstrap --configure --make

clean:
	confix2.py --packageroot=`pwd`/${DEST_DIR} --make --targets=clean
	rm -rf ${DEST_DIR}
