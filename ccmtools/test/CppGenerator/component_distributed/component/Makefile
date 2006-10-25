
DEST_DIR = xxx
IDL3REPO = ${CCM_INSTALL}/include
IDL_INCLUDE= -I${IDL3REPO}

all: build

local: 
	ccmtools c++local ${IDL_INCLUDE} -a -o ${DEST_DIR}/Test ${IDL3REPO}/world/Test*.idl 

build: local
	ccmconfix -confix2 -o ${DEST_DIR} -pname "component_distributed_component" -pversion "1.0.0" 
	confix2.py --packageroot=`pwd`/${DEST_DIR} --bootstrap --configure --make

clean: 
	confix2.py --packageroot=`pwd`/${DEST_DIR} --make --targets="clean uninstall"	
	rm -rf ${DEST_DIR}

