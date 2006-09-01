IDL_SOURCE=../../Login.idl
DEST_DIR = src
IDL3_DIR = ../../idl3repo
IDL2_DIR = ${DEST_DIR}/idl2
INCLUDE  = -I${IDL3_DIR}/interface -I${IDL3_DIR}/component

all: stubs

stubs:
	ccmidl -idl2 -o ${DEST_DIR}/component/Server/CCM_corba_stubs ${IDL_SOURCE}
	cp impl/Makefile ${DEST_DIR}/component/Server/CCM_corba_stubs
	make -C ${DEST_DIR}/component/Server/CCM_corba_stubs
	mkdir ${DEST_DIR}/test

run: 
	ccmconfix -makefiles -o ${DEST_DIR} -pname "login-remote-client" -pversion "1.0.0" 
	cp impl/_check_client.cc ${DEST_DIR}/test
	confix.py --packageroot=`pwd`/${DEST_DIR} --bootstrap --configure --make --targets=check

clean: 
	confix.py --packageroot=`pwd`/${DEST_DIR} --make --targets=clean	
	rm -rf ${DEST_DIR}

