INCLUDE= -I../

all: idl3

idl3: 
	ccmparser EmptyInterface.idl
	
	ccmparser InterfaceConstant.idl

	ccmparser InterfaceBasicTypeAttributes.idl
	ccmparser ${INCLUDE} InterfaceUserTypeAttributes.idl
	ccmparser ${INCLUDE} InterfaceReadonlyAttributes.idl	

	ccmparser InterfaceVoidTypeOperation.idl
	ccmparser InterfaceBasicTypeOperation.idl	
	ccmparser ${INCLUDE} InterfaceUserTypeOperation.idl
	ccmparser InterfaceOnewayOperation.idl
	ccmparser InterfaceOperationContext.idl
	ccmparser ${INCLUDE} InterfaceOperationException.idl 
		
	ccmparser ${INCLUDE} InterfaceException.idl
	ccmparser InterfaceType.idl
#	ccmparser InterfaceForward.idl
	
	ccmparser InterfaceInheritance.idl
