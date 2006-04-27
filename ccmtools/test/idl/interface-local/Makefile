DEST_DIR = xxx
IDL3_DIR = ${DEST_DIR}/idl3
IDL2_DIR = ${DEST_DIR}/idl2

all: idl3 idl2


idl3: Test.idl
	ccmtools idl3 -o ${IDL3_DIR} Test.idl

idl2: Test.idl
	ccmtools idl2 -o ${IDL2_DIR} Test.idl

clean:
	rm -rf ${DEST_DIR}