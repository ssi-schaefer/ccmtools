DEST_DIR = xxx
IDL_INCLUDE=-I${DEST_DIR}/idl/interface -I${DEST_DIR}/idl/component

all: local remote

test: remote check clean

idl3: Test.idl
	ccmmodel -validator Test.idl
	ccmidl -idl3 -o ${DEST_DIR}/idl Test.idl

local: idl3
	ccmtools c++local ${IDL_INCLUDE} -o ${DEST_DIR}/src/interface ${DEST_DIR}/idl/interface/world/europe/austria/*.idl
	ccmtools c++local ${IDL_INCLUDE} -a -o ${DEST_DIR}/src/component/Test ${DEST_DIR}/idl/component/world/europe/austria/Test*.idl 
	mkdir ${DEST_DIR}/src/component/Test/test

check_local:
	cp impl/Test_basicType_impl.cc ${DEST_DIR}/src/component/Test/
	cp impl/Test_userType_impl.cc ${DEST_DIR}/src/component/Test/
	cp impl/Test_voidType_impl.h ${DEST_DIR}/src/component/Test/
	cp impl/Test_voidType_impl.cc ${DEST_DIR}/src/component/Test/
	cp impl/_check_world_europe_austria_ccm_local_Test.cc ${DEST_DIR}/src/component/Test/test/
	ccmconfix -makefiles -o ${DEST_DIR}/src -pname "remote_facet_module_types" -pversion "1.0.0" 
	confix.py --packageroot=`pwd`/xxx/src --bootstrap --configure --make --targets=check

stubs:
	ccmidl -idl2 -o ${DEST_DIR}/src/CCM_corba_stubs Test.idl
	cp impl/Makefile ${DEST_DIR}/src/CCM_corba_stubs
	make -C ${DEST_DIR}/src/CCM_corba_stubs 

remote: local stubs
	ccmtools c++remote -o ${DEST_DIR}/src/component/Test Test.idl

check: check_local
	cp impl/_check_world_europe_austria_ccm_remote_Test.cc ${DEST_DIR}/src/component/Test/test/
	confix.py --packageroot=`pwd`/xxx/src --bootstrap --configure --make --targets=check


check_server: check_local
	cp server/_check_world_europe_austria_ccm_remote_Test.cc ${DEST_DIR}/src/component/Test/test/
	confix.py --packageroot=`pwd`/xxx/src --bootstrap --configure --make --targets=check

check_empty:
	ccmconfix -makefiles -o ${DEST_DIR}/src -pname "remote_facet_module_types" -pversion "1.0.0" 
	confix.py --packageroot=`pwd`/xxx/src --bootstrap --configure --make --targets=check

clean: 
	confix.py --packageroot=`pwd`/xxx/src --make --targets=clean	
	rm -rf xxx 
