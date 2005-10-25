TARGET_DIR= server
IDL3_INTERFACE_DIR=${TARGET_DIR}/idl3/interface
IDL3_COMPONENT_DIR=${TARGET_DIR}/idl3/component
INTERFACE_DIR= ${TARGET_DIR}/interface
COMPONENT_DIR= ${TARGET_DIR}/component
IDL3_INCLUDE_PATH=-I${IDL3_INTERFACE_DIR} -I${IDL3_COMPONENT_DIR}

all: uml2idl interface local 

uml2idl: md/CarRental.xml.zip
	uml2idl md/CarRental.xml.zip md/CarRental
	ccmtools idl3 -o ${TARGET_DIR}/idl3 md/CarRental.idl

# Generate the CarRental component from IDL3 and build the empty component
idl3:
	ccmtools idl3 -o ${TARGET_DIR}/idl3 idl/*.idl


interface: 
	ccmtools c++local -o ${INTERFACE_DIR} ${IDL3_INCLUDE_PATH} ${IDL3_INTERFACE_DIR}/BigBusiness/*.idl 
	touch ${INTERFACE_DIR}/Makefile.py


local:
	ccmtools c++local -a -o ${COMPONENT_DIR}/CarRental ${IDL3_INCLUDE_PATH} ${IDL3_COMPONENT_DIR}/BigBusiness/CarRental*.idl
	touch ${COMPONENT_DIR}/Makefile.py
	touch ${COMPONENT_DIR}/CarRental/Makefile.py


build:
	cp impl/Makefile.py ${TARGET_DIR}/
	confix.py --packageroot=`pwd`/${TARGET_DIR} --bootstrap --configure --make 

check: 
	cp impl/Makefile.py ${TARGET_DIR}/
	cp impl/CarRental_business_impl.cc ${COMPONENT_DIR}/CarRental/
	cp impl/CarRental_impl.h ${COMPONENT_DIR}/CarRental/
	cp impl/CarRental_maintenance_impl.cc ${COMPONENT_DIR}/CarRental/
	confix.py --packageroot=`pwd`/${TARGET_DIR} --bootstrap --configure --make --targets=check


# Add a mirror component with executable test cases
mirror:
	ccmtools idl3mirror -o ${TARGET_DIR}/idl3 ${IDL3_INCLUDE_PATH} ${IDL3_COMPONENT_DIR}/BigBusiness/CarRental*.idl
	ccmtools c++local -a -o ${COMPONENT_DIR}/CarRental_mirror ${IDL3_INCLUDE_PATH} ${IDL3_COMPONENT_DIR}/BigBusiness/CarRental*_mirror.idl
	touch ${COMPONENT_DIR}/CarRental_mirror/Makefile.py
	ccmtools c++local-test -o ${COMPONENT_DIR}/CarRental_mirror ${IDL3_INCLUDE_PATH} ${IDL3_COMPONENT_DIR}/BigBusiness/CarRental.idl

check_mirror:
	cp impl/CarRental_mirror_impl.cc ${COMPONENT_DIR}/CarRental_mirror/
	confix.py --packageroot=`pwd`/${TARGET_DIR} --bootstrap --configure --make --targets=check


# Add a CarRental client
client:
	cp -r impl/client ${TARGET_DIR}/


# Add a CORBA layer
remote:
	ccmtools idl2 -o ${COMPONENT_DIR}/CarRental/CCM_corba_stubs ${IDL3_INCLUDE_PATH} ${IDL3_INTERFACE_DIR}/BigBusiness/*.idl ${IDL3_COMPONENT_DIR}/BigBusiness/CarRental*.idl
	ccmtools c++remote -o  ${COMPONENT_DIR}/CarRental ${IDL3_INCLUDE_PATH} ${IDL3_INTERFACE_DIR}/BigBusiness/*.idl ${IDL3_COMPONENT_DIR}/BigBusiness/*.idl
	ccmtools c++remote-test -o ${COMPONENT_DIR}/CarRental ${IDL3_INCLUDE_PATH} ${IDL3_COMPONENT_DIR}/BigBusiness/CarRental.idl
	make -C ${COMPONENT_DIR}/CarRental/CCM_corba_stubs

check_remote:
	cp impl/_check_BigBusiness_ccm_remote_component_CarRental.cc ${COMPONENT_DIR}/CarRental/test/
	confix.py --packageroot=`pwd`/${TARGET_DIR} --bootstrap --configure --make --targets=check




clean: 
	confix.py --packageroot=`pwd`/${TARGET_DIR} --make --targets=clean
	rm -rf ${TARGET_DIR}
