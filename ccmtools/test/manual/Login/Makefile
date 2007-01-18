IDL_SOURCE=Login.idl
DEST_DIR = .
IDL3_DIR = ${DEST_DIR}/idl3repo
INCLUDE  = -I${IDL3_DIR}/interface -I${IDL3_DIR}/component

all: idl3


idl3: ${IDL_SOURCE}
	ccmidl -idl3 -o ${IDL3_DIR} ${IDL_SOURCE}

idl3mirror:
	ccmidl -idl3mirror ${INCLUDE} -o ${IDL3_DIR} ${IDL3_DIR}/component/application/Server*.idl

clean: 
	rm -rf ${IDL3_DIR}

