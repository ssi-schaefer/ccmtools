
DEST_DIR = xxx
IDL3REPO = ${CCM_INSTALL}/include
IDL_INCLUDE= -I${IDL3REPO}

all: remote build

local: 
	ccmtools c++local ${IDL_INCLUDE} -a -o ${DEST_DIR}/Test ${IDL3REPO}/world/europe/Test*.idl 

remote: local stubs
	ccmtools c++remote ${IDL_INCLUDE} -o ${DEST_DIR}/Test ${IDL3REPO}/world/europe/Test*.idl

stubs:
	ccmidl -idl2 ${IDL_INCLUDE} -o ${DEST_DIR}/Test/CCM_corba_stubs ${IDL3REPO}/world/europe/Test*.idl
	cp impl/Makefile ${DEST_DIR}/Test/CCM_corba_stubs/
	make -C ${DEST_DIR}/Test/CCM_corba_stubs 


build: 
	ccmconfix -confix2 -o ${DEST_DIR} -pname "facet_module_simple_distributed_cpb" -pversion "1.0.0" 
	confix2.py --packageroot=`pwd`/${DEST_DIR} --bootstrap --configure --make

clean: 
	confix2.py --packageroot=`pwd`/${DEST_DIR} --make --targets="clean uninstall"	
	rm -rf ${DEST_DIR}

