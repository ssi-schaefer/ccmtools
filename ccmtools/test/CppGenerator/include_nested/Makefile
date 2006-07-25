APP_NAME = 
DEST_DIR = xxx
IDL_INCLUDE=-I${DEST_DIR}/idl/interface -I${DEST_DIR}/idl/component

all: mirror

test: mirror check clean

idl3: Console.idl Hello.idl Lcd.idl Message.idl
	ccmidl -idl3 -o ${DEST_DIR}/idl Console.idl Hello.idl Lcd.idl Message.idl

local: idl3
	ccmtools c++local ${IDL_INCLUDE} -o ${DEST_DIR}/interface ${DEST_DIR}/idl/interface/*.idl
	ccmtools c++local ${IDL_INCLUDE} -a -o ${DEST_DIR}/component/Hello ${DEST_DIR}/idl/component/Hello.idl ${DEST_DIR}/idl/component/HelloHome.idl 

mirror: local
	ccmtools idl3mirror ${IDL_INCLUDE} -o ${DEST_DIR}/idl ${DEST_DIR}/idl/component/Hello.idl ${DEST_DIR}/idl/component/HelloHome.idl
	ccmtools c++local ${IDL_INCLUDE} -a -o ${DEST_DIR}/component/Hello_mirror ${DEST_DIR}/idl/component/Hello_mirror.idl ${DEST_DIR}/idl/component/HelloHome_mirror.idl
	ccmtools c++local-test ${IDL_INCLUDE} -a -o ${DEST_DIR}/component/Hello_mirror ${DEST_DIR}/idl/component/Hello.idl 

check: 
	cp impl/Makefile.py ${DEST_DIR}
	touch ${DEST_DIR}/interface/Makefile.py
	touch ${DEST_DIR}/component/Makefile.py
	touch ${DEST_DIR}/component/Hello/Makefile.py
	touch ${DEST_DIR}/component/Hello_mirror/Makefile.py
	confix.py --packageroot=`pwd`/${DEST_DIR} --bootstrap --configure --make --targets=check

clean: 
	confix.py --packageroot=`pwd`/${DEST_DIR} --bootstrap --configure --make --targets=clean	
	rm -rf ${DEST_DIR}
