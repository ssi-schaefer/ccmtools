IDL_INCLUDE = -I ../

all: idl3

idl3: 
	ccmparser ExceptionEmpty.idl
	ccmparser ExceptionBasicType.idl
	ccmparser ${IDL_INCLUDE} ExceptionUserType.idl
	