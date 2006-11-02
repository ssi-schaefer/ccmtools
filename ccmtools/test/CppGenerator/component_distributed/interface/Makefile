
DEST_DIR = xxx
IDL3REPO = ${CCM_INSTALL}/include
APP_NAME = Test
IDL_INCLUDE= -I${IDL3REPO}

all: install

local: 
	ccmtools c++local ${IDL_INCLUDE} -o ${DEST_DIR} ${IDL3REPO}/world/IFace.idl

install: local 
	ccmconfix -confix2 -o ${DEST_DIR} -pname "component_distributed_interface" -pversion "1.0.0" 
	confix2.py --packageroot=`pwd`/${DEST_DIR} --bootstrap --configure --make --targets=install

clean: 
	confix2.py --packageroot=`pwd`/${DEST_DIR} --make --targets="uninstall clean"	
	rm -rf ${DEST_DIR}

