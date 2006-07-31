IDL_SOURCE=../../Login.idl
DEST_DIR = ./src-gen
IDL3_DIR = ${DEST_DIR}/idl3
IDL2_DIR = ${DEST_DIR}/idl2
INCLUDE  = -I${IDL3_DIR}/interface -I${IDL3_DIR}/component

all: local


idl3: ${IDL_SOURCE}
	ccmidl -idl3 -o ${IDL3_DIR} ${IDL_SOURCE}

local: idl3
	ccmtools c++local ${INCLUDE} -o ${DEST_DIR}/src/interface ${IDL3_DIR}/interface/application/*.idl
	ccmtools c++local ${INCLUDE} -a -o ${DEST_DIR}/src/component/Server ${IDL3_DIR}/component/application/Server*.idl 
	ccmtools c++local-test ${INCLUDE} -o ${DEST_DIR}/src/component/Server ${IDL3_DIR}/component/application/Server.idl

check: 
	ccmconfix -makefiles -o ${DEST_DIR}/src -pname "login" -pversion "1.0.0" 
	cp impl/Server_login_impl.cc ${DEST_DIR}/src/component/Server/
	cp impl/_check_application_ccm_local_component_Server.cc ${DEST_DIR}/src/component/Server/test/
	confix.py --packageroot=`pwd`/${DEST_DIR}/src --bootstrap --configure --make --targets=check

stubs: #local
	ccmtools idl2 -o ${DEST_DIR}/src/component/Server/CCM_corba_stubs ${IDL_SOURCE}
	make -C ${DEST_DIR}/src/component/Server/CCM_corba_stubs server

remote: stubs
	ccmtools c++remote -o ${DEST_DIR}/src/component/Server/ ${IDL3_DIR}/interface/application/*.idl 
	ccmtools c++remote -o ${DEST_DIR}/src/component/Server/ ${IDL3_DIR}/component/application/Server*.idl 
	ccmtools c++remote-test -o ${DEST_DIR}/src/component/Server ${IDL3_DIR}/component/application/Server.idl 

run: 
	ccmconfix -makefiles -o ${DEST_DIR}/src -pname "login" -pversion "1.0.0" 
#	cp test/_check_ccm_remote_component_Test.cc ${DEST_DIR}/src/component/Test/test/
	confix.py --packageroot=`pwd`/${DEST_DIR}/src --bootstrap --configure --make --targets=check

clean: 
	confix.py --packageroot=`pwd`/${DEST_DIR}/src --make --targets=clean	
	rm -rf ${DEST_DIR}

