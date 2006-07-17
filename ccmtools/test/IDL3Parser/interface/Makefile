INCLUDE= -I../

all: idl3

idl3: 
#	ccmparser InterfaceConstants.idl

	ccmparser InterfaceBasicTypeAttributes.idl
	ccmparser ${INCLUDE} InterfaceUserTypeAttributes.idl
	ccmparser InterfaceVoidTypeOperation.idl
	ccmparser InterfaceBasicTypeOperation.idl	
	ccmparser ${INCLUDE} InterfaceUserTypeOperation.idl
	
#	ccmparser InterfaceOperationExceptions.idl
#	ccmparser InterfaceSubType.idl
