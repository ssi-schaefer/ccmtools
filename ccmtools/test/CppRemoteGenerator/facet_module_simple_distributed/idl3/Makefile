DEST_DIR = idl3Repo

all: install

idl3: 
	ccmmodel -check *.idl
	ccmtools idl3 -o ${DEST_DIR} *.idl
#	ccmconfix -makefiles -o ${DEST_DIR} -pname "idl3Repo" -pversion "1.0.0"

install: idl3
#	confix.py --packageroot=`pwd`/${DEST_DIR} --bootstrap --configure --make --targets=install

clean: 
#	confix.py --packageroot=`pwd`/${DEST_DIR} --make --targets=clean
	rm -rf ${DEST_DIR}

