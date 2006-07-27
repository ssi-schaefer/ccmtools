
APP_NAME = Test
DEST_DIR = xxx
IDL_INCLUDE=-I${DEST_DIR}/idl/interface -I${DEST_DIR}/idl/component

all: remote stubs 

test: remote stubs check clean

idl3: ${APP_NAME}.idl
	ccmidl -idl3 -o ${DEST_DIR}/idl ${APP_NAME}.idl

local: idl3
	ccmtools c++local ${IDL_INCLUDE} -o ${DEST_DIR}/src/interface ${DEST_DIR}/idl/interface/world/*.idl ${DEST_DIR}/idl/interface/world/america/*.idl ${DEST_DIR}/idl/interface/world/europe/*.idl 
	ccmtools c++local ${IDL_INCLUDE} -a -o ${DEST_DIR}/src/component/Test ${DEST_DIR}/idl/component/world/Test*.idl 

remote: local
	ccmtools idl2 -o ${DEST_DIR}/src/component/Test/CCM_corba_stubs ${APP_NAME}.idl
	ccmtools c++remote -o ${DEST_DIR}/src/component/Test/ ${APP_NAME}.idl
	ccmtools c++remote-test -o ${DEST_DIR}/src/component/Test ${APP_NAME}.idl

stubs:
	make -C ${DEST_DIR}/src/component/Test/CCM_corba_stubs server

check: 
	ccmconfix -makefiles -o ${DEST_DIR}/src -pname "remote_receptacle_module_inheritance" -pversion "1.0.0"
	cp impl/Test_ifaceIn_impl.cc ${DEST_DIR}/src/component/Test/
	cp impl/Test_impl.cc ${DEST_DIR}/src/component/Test/
	cp impl/_check_world_ccm_remote_component_Test.cc ${DEST_DIR}/src/component/Test/test/ 
	confix.py --packageroot=`pwd`/xxx/src --bootstrap --configure --make --targets=check

check_server: 
	ccmconfix -makefiles -o ${DEST_DIR}/src -pname "remote_receptacle_module_inheritance" -pversion "1.0.0"
	cp impl/Test_ifaceIn_impl.cc ${DEST_DIR}/src/component/Test/
	cp impl/Test_impl.cc ${DEST_DIR}/src/component/Test/
	cp server/_check_world_ccm_remote_component_Test.cc ${DEST_DIR}/src/component/Test/test/ 
	confix.py --packageroot=`pwd`/xxx/src --bootstrap --configure --make --targets=check

clean: 
	confix.py --packageroot=`pwd`/xxx/src --make --targets=clean	
	rm -rf xxx 

