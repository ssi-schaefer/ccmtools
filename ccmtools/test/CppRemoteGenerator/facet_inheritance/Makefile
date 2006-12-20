DEST_DIR = xxx
IDL_INCLUDE=-I${DEST_DIR}/idl/interface -I${DEST_DIR}/idl/component

all: remote 

test: remote check clean

test_empty: remote check_empty clean

idl3: Test.idl
	ccmmodel -validator Test.idl
	ccmidl -idl3 -o ${DEST_DIR}/idl Test.idl

local: idl3
	ccmtools c++local ${IDL_INCLUDE} -o ${DEST_DIR}/src/interface ${DEST_DIR}/idl/interface/*.idl
	ccmtools c++local ${IDL_INCLUDE} -a -o ${DEST_DIR}/src/component/Test ${DEST_DIR}/idl/component/Test*.idl 

stubs:
	ccmidl -idl2 -o ${DEST_DIR}/src/component/Test/CCM_corba_stubs Test.idl
	cp impl/Makefile ${DEST_DIR}/src/component/Test/CCM_corba_stubs
	make -C ${DEST_DIR}/src/component/Test/CCM_corba_stubs 

remote: local stubs
	ccmtools c++remote -o ${DEST_DIR}/src/component/Test/ Test.idl
	ccmtools c++remote-test -o ${DEST_DIR}/src/component/Test Test.idl

check: 
	cp impl/Test_iface_impl.cc ${DEST_DIR}/src/component/Test/
	cp impl/_check_ccm_remote_Test.cc ${DEST_DIR}/src/component/Test/test/
	ccmconfix -confix2 -o ${DEST_DIR}/src -pname "remote_facet_inheritance" -pversion "1.0.0"
	confix2.py --packageroot=`pwd`/xxx/src --bootstrap --configure --make --targets=check

check_empty:
	ccmconfix -confix2 -o ${DEST_DIR}/src -pname "remote_facet_inheritance" -pversion "1.0.0"
	confix2.py --packageroot=`pwd`/xxx/src --bootstrap --configure --make --targets=check

clean: 
	confix2.py --packageroot=`pwd`/xxx/src --make --targets=clean	
	rm -rf xxx 

