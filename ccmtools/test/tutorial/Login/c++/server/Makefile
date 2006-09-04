IDL_SOURCE=../../Login.idl
DEST_DIR = src
IDL3_DIR = ../../idl3repo
IDL2_DIR = ${DEST_DIR}/idl2
INCLUDE  = -I${IDL3_DIR}/interface -I${IDL3_DIR}/component

all: local

local: 
	ccmtools c++local ${INCLUDE} -o ${DEST_DIR}/interface ${IDL3_DIR}/interface/application/*.idl
	ccmtools c++local ${INCLUDE} -a -o ${DEST_DIR}/component/Server ${IDL3_DIR}/component/application/Server*.idl 

check: 
	ccmtools c++local-test ${INCLUDE} -o ${DEST_DIR}/component/Server ${IDL3_DIR}/component/application/Server.idl
	ccmconfix -makefiles -o ${DEST_DIR} -pname "login" -pversion "1.0.0" 
	cp impl/Server_login_impl.cc ${DEST_DIR}/component/Server/
	cp impl/_check_application_ccm_local_component_Server.cc ${DEST_DIR}/component/Server/test/
	confix.py --packageroot=`pwd`/${DEST_DIR} --bootstrap --configure --make --targets=check


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
	ccmconfix -makefiles -o ${DEST_DIR} -pname "login" -pversion "1.0.0" 
	cp impl/Server_login_impl.cc ${DEST_DIR}/component/Server/
	cp impl/_check_application_ccm_remote_component_Server.cc ${DEST_DIR}/component/Server/test/
	confix.py --packageroot=`pwd`/${DEST_DIR} --bootstrap --configure --make --targets=check

clean: 
	confix.py --packageroot=`pwd`/${DEST_DIR} --make --targets=clean	
	rm -rf ${DEST_DIR}

