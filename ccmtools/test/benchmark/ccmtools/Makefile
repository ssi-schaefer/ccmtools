DEST_DIR = xxx
COMPONENT_DIR = xxx/component
IDL3_DIR = idl3
IDL_INCLUDE = -I${IDL3_DIR}

all: remote nested dbc 
server: cppserver
client: cppclient

interface:
	ccmtools c++local ${IDL_INCLUDE} -o ${DEST_DIR}/interface ${IDL3_DIR}/Benchmark.idl

local: interface
	ccmtools c++local ${IDL_INCLUDE} -a -o ${DEST_DIR}/component/Test ${IDL3_DIR}/Test.idl

local_check: 
	cp impl/Makefile.py ${DEST_DIR}
	touch ${DEST_DIR}/interface/Makefile.py 
	touch ${DEST_DIR}/component/Makefile.py
	touch ${DEST_DIR}/component/Test/Makefile.py
	cp impl/Test_bm_impl.cc ${DEST_DIR}/component/Test/
	confix.py --profile=benchmark --packageroot=`pwd`/xxx --bootstrap --configure --make --targets=install
	confix.py --profile=benchmark --packageroot=`pwd`/ccm_local_client --bootstrap --configure --make


# TODO: Refactor the following tasks!!!

remote: local
	ccmtools idl2 ${IDL_INCLUDE} -o ${COMPONENT_DIR}/CORBA_Stubs ${IDL3_DIR}/Benchmark.idl
	ccmtools idl2 ${IDL_INCLUDE} -o ${COMPONENT_DIR}/CORBA_Stubs ${IDL3_DIR}/Test.idl
	ccmtools c++remote ${IDL_INCLUDE} -o ${COMPONENT_DIR} ${IDL3_DIR}/Benchmark.idl
	ccmtools c++remote ${IDL_INCLUDE} -o ${COMPONENT_DIR} ${IDL3_DIR}/Test.idl

nested: 
	ccmtools c++local ${IDL_INCLUDE} -a -o ${COMPONENT_DIR} ${IDL3_DIR}/SuperTest.idl

dbc: 
	ccmtools c++local ${IDL_INCLUDE} -o ${COMPONENT_DIR} ${IDL3_DIR}/DbcBenchmark.idl
	ccmtools c++local ${IDL_INCLUDE} -a -o ${COMPONENT_DIR} ${IDL3_DIR}/DbcTest.idl
	ccmtools c++dbc ${IDL_INCLUDE} -o ${COMPONENT_DIR} ${IDL3_DIR}/DbcTest.idl

install: 
	cp impl/Makefile.py ${COMPONENT_DIR} 
	cp impl/SuperTest_bm_impl.h ${COMPONENT_DIR}/impl
	cp impl/SuperTest_bm_impl.cc ${COMPONENT_DIR}/impl
	cp impl/DbcTest_bmInv1_impl.cc ${COMPONENT_DIR}/impl 
	cp impl/DbcTest_bmInv2_impl.cc ${COMPONENT_DIR}/impl 
	cp impl/DbcTest_bmInv3_impl.cc ${COMPONENT_DIR}/impl 
	cp impl/DbcTest_bmPost_impl.cc ${COMPONENT_DIR}/impl
	confix.py --profile=benchmark --packageroot=`pwd`/${COMPONENT_DIR} --bootstrap --configure --make --targets=install

cppserver: 
	confix.py --profile=benchmark --packageroot=`pwd`/server --bootstrap --configure --make

cppclient:
	confix.py --profile=benchmark --packageroot=`pwd`/client --bootstrap --configure --make

clean:
	confix.py --profile=benchmark --packageroot=`pwd`/${COMPONENT_DIR} --make --targets="uninstall clean"
	rm -rf ${COMPONENT_DIR}
	rm -rf ${HOME}/tmp/benchmark_build/${COMPONENT_DIR}
