INCLUDE= -I../

all: idl3

idl3: component home

component: 
	ccmparser ${INCLUDE} ComponentSimple.idl
	
	ccmparser ${INCLUDE} ComponentAttributeBasicTypes.idl
	ccmparser ${INCLUDE} ComponentAttributeUserTypes.idl
	ccmparser ${INCLUDE} ComponentAttributeExceptions.idl

	ccmparser ${INCLUDE} ComponentSupportedInterface.idl
	ccmparser ${INCLUDE} ComponentFacet.idl
	ccmparser ${INCLUDE} ComponentReceptacle.idl
	
	ccmparser ${INCLUDE} ComponentInheritance.idl
	

home:
	ccmparser ${INCLUDE} HomeAttributeBasicTypes.idl
	ccmparser ${INCLUDE} HomeAttributeUserTypes.idl
	ccmparser ${INCLUDE} HomeAttributeExceptions.idl
	
	ccmparser ${INCLUDE} HomeFactoryBasicTypes.idl
	ccmparser ${INCLUDE} HomeFactoryUserTypes.idl
	ccmparser ${INCLUDE} HomeFactoryException.idl
	
	ccmparser ${INCLUDE} HomeFinderMethods.idl

	ccmparser ${INCLUDE} HomeSupportedInterface.idl

	ccmparser ${INCLUDE} HomeInheritance.idl			