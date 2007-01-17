IDL_SOURCE=../../Login.idl
DEST_DIR = src
IDL3_DIR = ../../idl3repo
IDL2_DIR = ${DEST_DIR}/idl2
INCLUDE  = -I${IDL3_DIR}/interface -I${IDL3_DIR}/component

all: stubs

stubs:
	ccmidl -idl2 ${INCLUDE} -o ${DEST_DIR}/component/Server/GEN_ccmtools_corba_stubs ${IDL3_DIR}/interface/application/*.idl
	ccmidl -idl2 ${INCLUDE} -o ${DEST_DIR}/component/Server/GEN_ccmtools_corba_stubs ${IDL3_DIR}/component/application/Server*.idl
	cp impl/Makefile ${DEST_DIR}/component/Server/GEN_ccmtools_corba_stubs
	make -C ${DEST_DIR}/component/Server/GEN_ccmtools_corba_stubs
	mkdir ${DEST_DIR}/test

run: 
	cp impl/_check_ccmtools_remote_client.cc ${DEST_DIR}/test
	ccmconfix -confix2 -o ${DEST_DIR} -pname "login-remote-client" -pversion "1.0.0" 
	confix2.py --packageroot=`pwd`/${DEST_DIR} --bootstrap --configure --make --targets=check

clean: 
	confix2.py --packageroot=`pwd`/${DEST_DIR} --make --targets=clean	
	rm -rf ${DEST_DIR}

