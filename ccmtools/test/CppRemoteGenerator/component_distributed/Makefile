
all: comp 

test: comp clean

idl3repo: 
	make -C idl3 

iface: idl3repo
	make -C interface

comp: iface
	make -C component

clean:
	make -C component clean
	make -C interface clean
	make -C idl3 clean


