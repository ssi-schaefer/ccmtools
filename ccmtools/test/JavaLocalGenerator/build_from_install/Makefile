DEST_DIR = xxx
IDL3_DIR = ${DEST_DIR}/idl3
IDL2_DIR = ${DEST_DIR}/idl2

# We use the CCM_INSTALL environment variable to point to the PREFIX
# directory defined in the used confix profile.
INCLUDE  = -I${CCM_INSTALL}/include

all: local 

test: local check clean

# Here the IDL3 files will be installed to the confix PREFIX/include directory.
# We generate the local Java component from these installed IDL3 files.
# Note that there is no separation into an interface and a component directory.

idl3: Test.idl
	ccmtools idl3 -o ${IDL3_DIR} Test.idl
# The ccmconfix generator gets the confix package name (-pn) and package 
# version (-pv) settings as command line parameters. These parameters are used
# to generate the toplevel Makefile.py file.
	ccmconfix -makefiles -pn test-idl3 -pv 0.0.1 -o ${IDL3_DIR}
	confix.py --packageroot=`pwd`/${IDL3_DIR} --bootstrap --configure --make --targets=install

local: idl3
	ccmjava -iface -local -o ${DEST_DIR}/src-gen ${INCLUDE} ${CCM_INSTALL}/include/world/europe/austria/*.idl
	ccmjava -app -o ${DEST_DIR}/src ${INCLUDE} ${CCM_INSTALL}/include/world/europe/austria/Test*.idl

check: 
# First, we check if the generated code compiles at all
	ant -f ./build.xml
	ant -f ./build.xml clean
# Second, we check if a functional test runs through
	cp impl/TestvoidTypeImpl.java ${DEST_DIR}/src/world/europe/austria/ccm/local/
	cp impl/TestbasicTypeImpl.java ${DEST_DIR}/src/world/europe/austria/ccm/local/
	cp impl/TestuserTypeImpl.java ${DEST_DIR}/src/world/europe/austria/ccm/local/
	cp impl/Client.java ${DEST_DIR}/src/
	ant -f ./build.xml
	bin/runClient

clean: 
	confix.py --packageroot=`pwd`/${IDL3_DIR} --make --targets=uninstall
	rm -rf ${DEST_DIR}
