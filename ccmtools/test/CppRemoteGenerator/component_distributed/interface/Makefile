
DEST_DIR = xxx
IDL3REPO = ${CCM_INSTALL}/include
APP_NAME = Test
IDL_INCLUDE= -I${IDL3REPO}

all: remote install

local: 
	ccmtools c++local ${IDL_INCLUDE} -o ${DEST_DIR} ${IDL3REPO}/america/*.idl
	ccmtools c++local ${IDL_INCLUDE} -o ${DEST_DIR} ${IDL3REPO}/world/*.idl

remote: local stubs
	ccmtools c++remote ${IDL_INCLUDE} -o ${DEST_DIR} ${IDL3REPO}/america/*.idl
	ccmtools c++remote ${IDL_INCLUDE} -o ${DEST_DIR} ${IDL3REPO}/world/*.idl

stubs:
	ccmidl -idl2 ${IDL_INCLUDE} -o ${DEST_DIR}/CCM_corba_stubs ${IDL3REPO}/america/*.idl
	ccmidl -idl2 ${IDL_INCLUDE} -o ${DEST_DIR}/CCM_corba_stubs ${IDL3REPO}/world/*.idl
	cp impl/Makefile ${DEST_DIR}/CCM_corba_stubs
	make -C ${DEST_DIR}/CCM_corba_stubs 


install: 
	ccmconfix -confix2 -o ${DEST_DIR} -pname "facet_module_simple_distributed_cpa" -pversion "1.0.0" 
	confix2.py --packageroot=`pwd`/${DEST_DIR} --bootstrap --configure --make --targets=install

clean: 
	confix2.py --packageroot=`pwd`/${DEST_DIR} --make --targets="uninstall clean"	
	rm -rf ${DEST_DIR}

