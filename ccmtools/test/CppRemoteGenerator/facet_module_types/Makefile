APP_NAME = Test
DEST_DIR = xxx
IDL_INCLUDE=-I${DEST_DIR}/idl/interface -I${DEST_DIR}/idl/component

all: local remote stubs 

test: remote stubs check clean

idl3: ${APP_NAME}.idl
	ccmtools idl3 -o ${DEST_DIR}/idl ${APP_NAME}.idl

local: idl3
	ccmtools c++local ${IDL_INCLUDE} -o ${DEST_DIR}/src/interface ${DEST_DIR}/idl/interface/world/europe/austria/*.idl
	ccmtools c++local ${IDL_INCLUDE} -a -o ${DEST_DIR}/src/component/Test ${DEST_DIR}/idl/component/world/europe/austria/Test*.idl 
	mkdir ${DEST_DIR}/src/component/Test/test

check_local:
	cp impl/Test_basicType_impl.cc ${DEST_DIR}/src/component/Test/
	cp impl/Test_userType_impl.cc ${DEST_DIR}/src/component/Test/
	cp impl/Test_voidType_impl.h ${DEST_DIR}/src/component/Test/
	cp impl/Test_voidType_impl.cc ${DEST_DIR}/src/component/Test/
	cp impl/_check_world_europe_austria_ccm_local_component_Test.cc ${DEST_DIR}/src/component/Test/test/
	ccmconfix -makefiles -o ${DEST_DIR}/src -pname "remote_facet_module_types" -pversion "1.0.0" 
	confix.py --packageroot=`pwd`/xxx/src --bootstrap --configure --make --targets=check


remote: local
	ccmtools idl2 -o ${DEST_DIR}/src/CCM_CORBA_Stubs ${APP_NAME}.idl
	ccmtools c++remote -o ${DEST_DIR}/src/component/Test ${APP_NAME}.idl

stubs:
	make -C ${DEST_DIR}/src/CCM_CORBA_Stubs server

check: check_local
	cp impl/_check_world_europe_austria_ccm_remote_component_Test.cc ${DEST_DIR}/src/component/Test/test/
	confix.py --packageroot=`pwd`/xxx/src --bootstrap --configure --make --targets=check


check_server: check_local
	cp server/_check_world_europe_austria_ccm_remote_component_Test.cc ${DEST_DIR}/src/component/Test/test/
	confix.py --packageroot=`pwd`/xxx/src --bootstrap --configure --make --targets=check


clean: 
	confix.py --packageroot=`pwd`/xxx/src --make --targets=clean	
	rm -rf xxx 
