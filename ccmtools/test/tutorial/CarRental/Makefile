TARGET_DIR= CarRental
IDL3_INTERFACE_DIR=${TARGET_DIR}/idl3/interface
IDL3_COMPONENT_DIR=${TARGET_DIR}/idl3/component
INTERFACE_DIR= ${TARGET_DIR}/server/interface
COMPONENT_DIR= ${TARGET_DIR}/server/component
IDL3_INCLUDE_PATH=-I${IDL3_INTERFACE_DIR} -I${IDL3_COMPONENT_DIR}

all: uml interface local mirror

uml: md/CarRental.xml.zip
	uml2idl md/CarRental.xml.zip md/CarRental
	ccmtools idl3 -o ${TARGET_DIR}/idl3 md/CarRental.idl

idl3:
	ccmtools idl3 -o ${TARGET_DIR}/idl3 idl/*.idl
 
interface: 
	ccmtools c++local -o ${INTERFACE_DIR} ${IDL3_INCLUDE_PATH} ${IDL3_INTERFACE_DIR}/BigBusiness/*.idl 
	touch ${INTERFACE_DIR}/../Makefile.py
	touch ${INTERFACE_DIR}/Makefile.py

local:
	ccmtools c++local -a -o ${COMPONENT_DIR}/CarRental ${IDL3_INCLUDE_PATH} ${IDL3_COMPONENT_DIR}/BigBusiness/CarRental*.idl
	touch ${COMPONENT_DIR}/Makefile.py
	touch ${COMPONENT_DIR}/CarRental/Makefile.py

mirror:
	ccmtools idl3mirror -o ${TARGET_DIR}/idl3 ${IDL3_INCLUDE_PATH} ${IDL3_COMPONENT_DIR}/BigBusiness/CarRental*.idl
	ccmtools c++local -a -o ${COMPONENT_DIR}/CarRental_mirror ${IDL3_INCLUDE_PATH} ${IDL3_COMPONENT_DIR}/BigBusiness/CarRental*_mirror.idl
	touch ${COMPONENT_DIR}/CarRental_mirror/Makefile.py
	ccmtools c++local-test -o ${COMPONENT_DIR}/CarRental_mirror ${IDL3_INCLUDE_PATH} ${IDL3_COMPONENT_DIR}/BigBusiness/CarRental.idl


check: 
	cp impl/Makefile.py ${TARGET_DIR}/
	cp impl/CarRental_business_impl.cc ${COMPONENT_DIR}/CarRental/impl/
	cp impl/CarRental_impl.h ${COMPONENT_DIR}/CarRental/impl/
	cp impl/CarRental_maintenance_impl.cc ${COMPONENT_DIR}/CarRental/impl/
	cp impl/CarRental_mirror_impl.cc ${COMPONENT_DIR}/CarRental_mirror/impl/
	confix.py --packageroot=`pwd`/CarRental --bootstrap --configure --make --targets=check


clean: 
	rm -rf ${TARGET_DIR}
