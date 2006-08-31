IDL_SOURCE=Login.idl
DEST_DIR = .
IDL3_DIR = ${DEST_DIR}/idl3repo

all: idl3


idl3: ${IDL_SOURCE}
	ccmidl -idl3 -o ${IDL3_DIR} ${IDL_SOURCE}

clean: 
	rm -rf ${IDL3_DIR}

