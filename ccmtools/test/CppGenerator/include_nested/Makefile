DEST_DIR = xxx
IDL_INCLUDE=-I${DEST_DIR}/idl/interface -I${DEST_DIR}/idl/component

all: mirror

test: mirror check clean

test_empty: local check_empty clean

idl3: Console.idl Hello.idl Lcd.idl Message.idl
	ccmmodel -validator Console.idl Hello.idl Lcd.idl Message.idl
	ccmidl -idl3 -o ${DEST_DIR}/idl Console.idl Hello.idl Lcd.idl Message.idl

local: idl3
	ccmtools c++local ${IDL_INCLUDE} -o ${DEST_DIR}/interface ${DEST_DIR}/idl/interface/*.idl
	ccmtools c++local ${IDL_INCLUDE} -a -o ${DEST_DIR}/component/Hello ${DEST_DIR}/idl/component/Hello.idl ${DEST_DIR}/idl/component/HelloHome.idl 

mirror: local
	ccmidl -idl3mirror ${IDL_INCLUDE} -o ${DEST_DIR}/idl ${DEST_DIR}/idl/component/Hello.idl ${DEST_DIR}/idl/component/HelloHome.idl
	ccmtools c++local ${IDL_INCLUDE} -a -o ${DEST_DIR}/component/HelloMirror ${DEST_DIR}/idl/component/HelloMirror.idl ${DEST_DIR}/idl/component/HelloHomeMirror.idl
	ccmtools c++local-test ${IDL_INCLUDE} -a -o ${DEST_DIR}/component/HelloMirror ${DEST_DIR}/idl/component/Hello.idl 

check: 
	ccmconfix -confix2 -o ${DEST_DIR} -pname "include_nested" -pversion "1.0.0"
	confix2.py --packageroot=`pwd`/${DEST_DIR} --bootstrap --configure --make --targets=check

clean: 
	confix2.py --packageroot=`pwd`/${DEST_DIR} --make --targets=clean	
	rm -rf ${DEST_DIR}
