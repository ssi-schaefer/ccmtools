DEST_DIR = xxx
IDL_INCLUDE=-I${DEST_DIR}/idl/interface -I${DEST_DIR}/idl/component

all: remote stubs 

test: remote stubs check clean

idl3: Test.idl
	ccmmodel -check Test.idl
	ccmidl -idl3 -o ${DEST_DIR}/idl Test.idl

local: idl3
	ccmtools c++local ${IDL_INCLUDE} -o ${DEST_DIR}/src/interface ${DEST_DIR}/idl/interface/*.idl
	ccmtools c++local ${IDL_INCLUDE} -a -o ${DEST_DIR}/src/component/Test ${DEST_DIR}/idl/component/Test.idl ${DEST_DIR}/idl/component/TestHome.idl 

remote: local
	ccmtools idl2 -o ${DEST_DIR}/src/CCM_corba_stubs Test.idl
	ccmtools c++remote -o ${DEST_DIR}/src Test.idl
	ccmtools c++remote-test -o ${DEST_DIR}/src/component/Test Test.idl

stubs:
	make -C ${DEST_DIR}/src/CCM_corba_stubs

check: 
	cp impl/Test_impl.cc ${DEST_DIR}/src/component/Test/
	cp impl/Test_impl.h ${DEST_DIR}/src/component/Test/
	cp impl/_check_ccm_remote_component_Test.cc ${DEST_DIR}/src/component/Test/test/
	ccmconfix -makefiles -o ${DEST_DIR}/src -pname "remote_" -pversion "1.0.0"
	confix.py --packageroot=`pwd`/xxx/src --bootstrap --configure --make --targets=check

clean: 
	confix.py --packageroot=`pwd`/xxx/src --make --targets=clean	
	rm -rf xxx 

