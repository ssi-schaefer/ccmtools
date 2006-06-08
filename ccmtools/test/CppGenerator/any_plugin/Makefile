APP_NAME = Test
DEST_DIR = xxx
CCMTOOLS_CMD = ccmtools
IDL_INCLUDE=-I${DEST_DIR}/idl/interface -I${DEST_DIR}/idl/component

all: local

test: local check clean

idl3: ${APP_NAME}.idl
	${CCMTOOLS_CMD} idl3 -o ${DEST_DIR}/idl ${APP_NAME}.idl

local: idl3
	${CCMTOOLS_CMD} c++local --anytypes=impl/PdlAnyMappings.ccmany ${IDL_INCLUDE} -o ${DEST_DIR}/interface ${DEST_DIR}/idl/interface/*.idl
	${CCMTOOLS_CMD} c++local ${IDL_INCLUDE} -a -o ${DEST_DIR}/component/Test ${DEST_DIR}/idl/component/Test.idl ${DEST_DIR}/idl/component/TestHome.idl 

	${CCMTOOLS_CMD} c++local-test ${IDL_INCLUDE} -a -o ${DEST_DIR}/component/Test ${DEST_DIR}/idl/component/Test.idl 

check: 
	ccmconfix -makefiles -o ${DEST_DIR} -pname "any-plugin" -pversion "1.0.0"
	cp impl/Person.h ${DEST_DIR}/interface/
	cp impl/DTO.h ${DEST_DIR}/interface/
	cp impl/Test_facet_impl.cc ${DEST_DIR}/component/Test/
	cp impl/_check_ccm_local_component_Test.cc ${DEST_DIR}/component/Test/test/
	confix.py --packageroot=`pwd`/${DEST_DIR} --bootstrap --configure --make --targets=check

clean: 
	confix.py --packageroot=`pwd`/${DEST_DIR} --make --targets=clean	
	rm -rf ${DEST_DIR}
