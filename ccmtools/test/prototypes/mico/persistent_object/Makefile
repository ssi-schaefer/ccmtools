


all: check

idl:
	cd src && idl HelloWorld.idl

check: idl
	confix.py --bootstrap --configure --make --targets=check


clean:
	confix.py --bootstrap --configure --make --targets=clean