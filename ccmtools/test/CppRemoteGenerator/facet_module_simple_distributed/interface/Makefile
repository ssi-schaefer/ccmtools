
DEST_DIR = xxx
IDL3REPO = ../idl3/idl3Repo
APP_NAME = Test
IDL_INCLUDE= -I${IDL3REPO}/interface -I${IDL3REPO}/component

all: remote stubs install

local: 
	ccmtools c++local ${IDL_INCLUDE} -o ${DEST_DIR} ${IDL3REPO}/interface/world/*.idl

remote: local 
	ccmtools idl2 -o ${DEST_DIR}/CCM_corba_stubs ${IDL3REPO}/interface/world/*.idl
	ccmtools c++remote -o ${DEST_DIR} ${IDL3REPO}/interface/world/*.idl

stubs:
	make -C ${DEST_DIR}/CCM_corba_stubs server


install: 
	ccmconfix -makefiles -o ${DEST_DIR} -pname "facet_module_simple_distributed_interface" -pversion "1.0.0" 
	confix.py --packageroot=`pwd`/${DEST_DIR} --bootstrap --configure --make --targets=install

clean: 
	confix.py --packageroot=`pwd`/${DEST_DIR} --make --targets="uninstall clean"	
	rm -rf ${DEST_DIR}

