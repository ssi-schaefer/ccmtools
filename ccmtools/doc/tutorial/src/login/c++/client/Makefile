IDL_SOURCE=../../Login.idl
DEST_DIR = ./src-gen
IDL3_DIR = ${DEST_DIR}/idl3
IDL2_DIR = ${DEST_DIR}/idl2
INCLUDE  = -I${IDL3_DIR}/interface -I${IDL3_DIR}/component

all: stubs

idl3: ${IDL_SOURCE}
	ccmidl -idl3 -o ${IDL3_DIR} ${IDL_SOURCE}

stubs:
	ccmtools idl2 -o ${DEST_DIR}/src/component/Server/CCM_corba_stubs ${IDL_SOURCE}
	make -C ${DEST_DIR}/src/component/Server/CCM_corba_stubs server
	mkdir ${DEST_DIR}/src/test

run: 
	ccmconfix -makefiles -o ${DEST_DIR}/src -pname "login-remote-client" -pversion "1.0.0" 
	cp impl/_check_client.cc ${DEST_DIR}/src/test
	confix.py --packageroot=`pwd`/${DEST_DIR}/src --bootstrap --configure --make --targets=check

clean: 
	confix.py --packageroot=`pwd`/${DEST_DIR}/src --make --targets=clean	
	rm -rf ${DEST_DIR}

