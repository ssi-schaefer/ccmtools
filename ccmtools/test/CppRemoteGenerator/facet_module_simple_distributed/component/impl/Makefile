
all: server client

server: 
	ccmtools-idl -mico -I${CCM_INSTALL}/include -I${CCMTOOLS_HOME}/idl *.idl

client:
	ccmtools-idl -java -I${CCMTOOLS_HOME}/idl *.idl
