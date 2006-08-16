
DEST_DIR = xxx
IDL3REPO = ../idl3/idl3Repo
APP_NAME = Test
IDL_INCLUDE= -I${IDL3REPO}/interface -I${IDL3REPO}/component

all: remote install

local: 
	ccmtools c++local ${IDL_INCLUDE} -o ${DEST_DIR} ${IDL3REPO}/interface/america/*.idl

remote: local stubs
	ccmtools c++remote ${IDL_INCLUDE} -o ${DEST_DIR} ${IDL3REPO}/interface/america/*.idl

stubs:
	ccmidl -idl2 ${IDL_INCLUDE} -o ${DEST_DIR}/CCM_corba_stubs ${IDL3REPO}/interface/america/*.idl
	ccmidl -idl2 ${IDL_INCLUDE} -o ${DEST_DIR}/CCM_corba_stubs ${IDL3REPO}/interface/world/*.idl
	cp impl/Makefile ${DEST_DIR}/CCM_corba_stubs
#	make -C ${DEST_DIR}/CCM_corba_stubs 


install: 
	ccmconfix -makefiles -o ${DEST_DIR} -pname "facet_module_simple_distributed_cpa" -pversion "1.0.0" 
	confix.py --packageroot=`pwd`/${DEST_DIR} --bootstrap --configure --make --targets=install

clean: 
	confix.py --packageroot=`pwd`/${DEST_DIR} --make --targets="uninstall clean"	
	rm -rf ${DEST_DIR}

