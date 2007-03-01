all: local

local:
	ccmtools c++local -o gen/itf Pusher.idl
	ccmtools c++local -a -o gen/inner Inner.idl
	ccmtools c++local -o gen/outer Outer.idl
	ccmtools c++assembly -o gen/outer Outer.idl Outer.assembly Inner.idl

check: local
	ccmconfix -confix2 -o gen -pname "assembly-multiple-receptacles" -pversion "0.0.1"
	cd gen && confix2.py --bootstrap --configure --make --targets=check

clean:
	cd gen && confix2.py --make --targets=clean
	rm -rf gen
