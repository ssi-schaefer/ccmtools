
APP_NAME = Hello
CCMTOOLS_CMD = ccmtools

all: local 

local: ${APP_NAME}.idl
	${CCMTOOLS_CMD} c++local -a -o component ${APP_NAME}.idl
	mkdir client/test

check: 
	cp impl/Makefile.py gen/
	cp impl/Server_hello_impl.cc gen/impl/
	cp test/_check_client.cc gen/test/
	touch gen/test/Makefile.py
	confix.py --packageroot=`pwd`/gen --bootstrap --configure --make --targets=check


clean: 
	confix.py --packageroot=`pwd`/xxx --bootstrap --configure --make --targets=clean	
	rm -rf gen
