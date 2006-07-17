INCLUDE= -I../

all: idl3

idl3: 
	ccmparser Person.idl
	ccmparser BasicTypeStruct.idl
	ccmparser Address.idl
	ccmparser ${INCLUDE} UserTypeStruct.idl
#	ccmparser RecursiveStruct.idl

