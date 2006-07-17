INCLUDE= -I../

all: idl3

idl3: 
	ccmparser ComponentBasicTypeAttribute.idl
	ccmparser ${INCLUDE} ComponentUserTypeAttribute.idl
	ccmparser ${INCLUDE} ComponentSupportedInterface.idl

	ccmparser ${INCLUDE} ComponentFacet.idl
	ccmparser ${INCLUDE} ComponentReceptacle.idl		
	
	ccmparser ${INCLUDE} ComponentInheritance.idl
	
	ccmparser ${INCLUDE} HomeSupportedInterface.idl
	ccmparser ${INCLUDE} HomeFactoryOperation.idl
	ccmparser ${INCLUDE} HomeFinderOperation.idl			
	
	ccmparser ${INCLUDE} HomeInheritance.idl			