
DEST_DIR = xxx
IDL3REPO = ../idl3/idl3Repo
IDL_INCLUDE= -I${IDL3REPO}/interface -I${IDL3REPO}/component

all: remote build

local: 
	ccmtools c++local ${IDL_INCLUDE} -o ${DEST_DIR}/Test ${IDL3REPO}/interface/world/*.idl
	ccmtools c++local ${IDL_INCLUDE} -a -o ${DEST_DIR}/Test ${IDL3REPO}/component/world/europe/Test*.idl 

remote: local stubs
	ccmtools c++remote ${IDL_INCLUDE} -o ${DEST_DIR}/Test ${IDL3REPO}/interface/world/*.idl
	ccmtools c++remote ${IDL_INCLUDE} -o ${DEST_DIR}/Test ${IDL3REPO}/component/world/europe/Test*.idl

stubs:
	ccmidl -idl2 ${IDL_INCLUDE} -o ${DEST_DIR}/Test/CCM_corba_stubs ${IDL3REPO}/component/world/europe/Test*.idl
	cp impl/Makefile ${DEST_DIR}/Test/CCM_corba_stubs/
	make -C ${DEST_DIR}/Test/CCM_corba_stubs 


build: 
	ccmconfix -makefiles -o ${DEST_DIR} -pname "facet_module_simple_distributed_cpb" -pversion "1.0.0" 
	confix.py --packageroot=`pwd`/${DEST_DIR} --bootstrap --configure --make

clean: 
	confix.py --packageroot=`pwd`/${DEST_DIR} --make --targets="clean uninstall"	
	rm -rf ${DEST_DIR}

