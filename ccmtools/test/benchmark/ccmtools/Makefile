
COMPONENT_DIR = component

all: remote nested 

local: Test.idl
	ccmtools-generate c++local -a -o ${COMPONENT_DIR} Test.idl
#	ccmtools-generate c++local-test -o ${COMPONENT_DIR} Test.idl

nested: SuperTest.idl
	ccmtools-generate c++local -a -o ${COMPONENT_DIR} SuperTest.idl

dbc: local
	ccmtools-generate c++dbc -o ${COMPONENT_DIR} Test.idl

remote: local
	ccmtools-generate idl2 -o ${COMPONENT_DIR}/CORBA_Stubs Test.idl
	ccmtools-generate c++remote -o ${COMPONENT_DIR} Test.idl
#	ccmtools-generate c++remote-test -o ${COMPONENT_DIR} Test.idl

install:
	cp impl/Makefile.py ${COMPONENT_DIR} 
	cp impl/SuperTest_bm_impl.h ${COMPONENT_DIR}/impl
	cp impl/SuperTest_bm_impl.cc ${COMPONENT_DIR}/impl
#	cp test/SuperTestAssembly.h ${COMPONENT_DIR}/test
#	cp test/SuperTestAssembly.cc ${COMPONENT_DIR}/test
#	cp test/SuperTestAssemblyFactory.h ${COMPONENT_DIR}/test
#	cp test/SuperTestAssemblyFactory.cc ${COMPONENT_DIR}/test

	confix.py --profile=benchmark --packageroot=`pwd`/${COMPONENT_DIR} --bootstrap --configure --make --targets=install

check:
#	cp impl/Test_bm_impl.cc ${COMPONENT_DIR}/impl
#	cp test/_check_CCM_Local_Object_Test.cc  ${COMPONENT_DIR}/test
#	cp test/_check_CCM_Local_CCM_Session_Test.cc ${COMPONENT_DIR}/test
#	cp test/_check_CCM_Remote_CCM_Session_Test_remote.cc ${COMPONENT_DIR}/test
#	cp test/_check_CCM_Local_CCM_Session_SuperTest.cc ${COMPONENT_DIR}/test
	confix.py --profile=benchmark --bootstrap --configure --packageroot=`pwd`/${COMPONENT_DIR} --make --targets=check

cppserver: 
	confix.py --profile=benchmark --packageroot=`pwd`/server --bootstrap --configure --make

cppclient:
	confix.py --profile=benchmark --packageroot=`pwd`/client --bootstrap --configure --make

clean:
	confix.py --profile=benchmark --packageroot=`pwd`/${COMPONENT_DIR} --make --targets=uninstall
	rm -rf ${COMPONENT_DIR}
	rm -rf ${HOME}/tmp/benchmark_build/${COMPONENT_DIR}

