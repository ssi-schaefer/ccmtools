IDL_SOURCE=../../Login.idl
DEST_DIR = src
IDL3_DIR = ../../idl3repo
IDL2_DIR = ${DEST_DIR}/idl2
INCLUDE  = -I${IDL3_DIR}/interface -I${IDL3_DIR}/component

all: local

local: 
	ccmtools c++local ${INCLUDE} -o ${DEST_DIR}/interface ${IDL3_DIR}/interface/application/*.idl
	ccmtools c++local ${INCLUDE} -a -o ${DEST_DIR}/component/Server ${IDL3_DIR}/component/application/Server.idl ${IDL3_DIR}/component/application/ServerHome.idl

check: 
	ccmtools c++local-test ${INCLUDE} -o ${DEST_DIR}/component/Server ${IDL3_DIR}/component/application/Server.idl
	cp impl/Server_login_impl.cc ${DEST_DIR}/component/Server/
	cp impl/_check_application_ccm_local_Server.cc ${DEST_DIR}/component/Server/test/
	ccmconfix -confix2 -o ${DEST_DIR} -pname "login" -pversion "1.0.0" 
	confix2.py --packageroot=`pwd`/${DEST_DIR} --bootstrap --configure --make --targets=check


mirror:
	ccmtools c++local ${INCLUDE} -a -o ${DEST_DIR}/component/ServerMirror ${IDL3_DIR}/component/application/ServerMirror.idl ${IDL3_DIR}/component/application/ServerHomeMirror.idl
	ccmtools c++local-test ${INCLUDE} -o ${DEST_DIR}/component/ServerMirror ${IDL3_DIR}/component/application/Server.idl

mirrorcheck:
	cp impl/Server_login_impl.cc ${DEST_DIR}/component/Server/
	cp impl/ServerMirror_impl.cc ${DEST_DIR}/component/ServerMirror/
	ccmconfix -confix2 -o ${DEST_DIR} -pname "login" -pversion "1.0.0" 
	confix2.py --packageroot=`pwd`/${DEST_DIR} --bootstrap --configure --make --targets=check


stubs: #local
	ccmidl -idl2 ${INCLUDE} -o ${DEST_DIR}/component/Server/CCM_corba_stubs ${IDL3_DIR}/interface/application/*.idl
	ccmidl -idl2 ${INCLUDE} -o ${DEST_DIR}/component/Server/CCM_corba_stubs ${IDL3_DIR}/component/application/Server*.idl
	cp impl/Makefile ${DEST_DIR}/component/Server/CCM_corba_stubs
	make -C ${DEST_DIR}/component/Server/CCM_corba_stubs 

remote: stubs
	ccmtools c++remote ${INCLUDE} -o ${DEST_DIR}/component/Server/ ${IDL3_DIR}/interface/application/*.idl 
	ccmtools c++remote ${INCLUDE} -o ${DEST_DIR}/component/Server/ ${IDL3_DIR}/component/application/Server*.idl 

run: 
	ccmtools c++remote-test ${INCLUDE} -o ${DEST_DIR}/component/Server ${IDL3_DIR}/component/application/Server.idl 
	cp impl/Server_login_impl.cc ${DEST_DIR}/component/Server/
	cp impl/_check_application_ccm_remote_Server.cc ${DEST_DIR}/component/Server/test/
	ccmconfix -confix2 -o ${DEST_DIR} -pname "login" -pversion "1.0.0" 
	confix2.py --packageroot=`pwd`/${DEST_DIR} --bootstrap --configure --make --targets=check

clean: 
	confix2.py --packageroot=`pwd`/${DEST_DIR} --make --targets=clean	
	rm -rf ${DEST_DIR}

