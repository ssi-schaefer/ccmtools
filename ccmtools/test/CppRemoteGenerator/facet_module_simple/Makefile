DEST_DIR = xxx
IDL_INCLUDE=-I${DEST_DIR}/idl/interface -I${DEST_DIR}/idl/component

all: remote stubs 

test: remote stubs check clean

idl3: Test.idl
	ccmmodel -check Test.idl
	ccmidl -idl3 -o ${DEST_DIR}/idl Test.idl

local: idl3
	ccmtools c++local ${IDL_INCLUDE} -o ${DEST_DIR}/src/interface ${DEST_DIR}/idl/interface/world/*.idl
	ccmtools c++local ${IDL_INCLUDE} -a -o ${DEST_DIR}/src/component/Test ${DEST_DIR}/idl/component/world/Test*.idl 

remote: local 
	ccmtools idl2 -o ${DEST_DIR}/src/CCM_CORBA_Stubs Test.idl
	ccmtools c++remote -o ${DEST_DIR}/src Test.idl
	ccmtools c++remote-test -o ${DEST_DIR}/src/component/Test Test.idl

stubs:
	make -C ${DEST_DIR}/src/CCM_CORBA_Stubs server

check: 
	ccmconfix -makefiles -o ${DEST_DIR}/src -pname "remote_facet_module_simple" -pversion "1.0.0"
	cp impl/Test_my_facet_impl.cc ${DEST_DIR}/src/component/Test/
	cp test/_check_world_ccm_remote_component_Test.cc ${DEST_DIR}/src/component/Test/test/
	confix.py --packageroot=`pwd`/xxx/src --bootstrap --configure --make --targets=check

check_server: 
	ccmconfix -makefiles -o ${DEST_DIR}/src -pname "remote_facet_module_simple" -pversion "1.0.0"
	cp impl/Test_my_facet_impl.cc ${DEST_DIR}/src/component/Test/
	cp server/_check_world_ccm_remote_component_Test.cc ${DEST_DIR}/src/component/Test/test/
	confix.py --packageroot=`pwd`/xxx/src --bootstrap --configure --make --targets=check

clean: 
	confix.py --packageroot=`pwd`/xxx/src --make --targets=clean	
	rm -rf xxx 
