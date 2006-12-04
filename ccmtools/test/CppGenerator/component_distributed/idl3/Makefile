DEST_DIR = CCM_idl3Repo

all: install

idl3: 
	ccmmodel -validator *.idl
	ccmidl -idl3 -o ${DEST_DIR} *.idl
	ccmconfix -confix2 -o ${DEST_DIR} -pname "component_distributed_idl3" -pversion "1.0.0"

install: idl3
	confix2.py --packageroot=`pwd`/${DEST_DIR} --bootstrap --configure --make --targets=install

clean: 
	confix2.py --packageroot=`pwd`/${DEST_DIR} --make --targets="clean uninstall"
	rm -rf ${DEST_DIR}

