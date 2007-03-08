ITF="gen/idl/interface"
COMP="gen/idl/component"
SPACE_GEN="space/GEN_ccmtools_local_Space"

all: local assembly

local:
	ccmidl -idl3 -o gen/idl prototype1.idl
	ccmtools c++local -a -o gen/itf -I$(ITF) `find $(ITF) -name '*.idl'`
	ccmtools c++local -a -o gen/world -I$(ITF) -I$(COMP) $(COMP)/World/*.idl
	ccmtools c++local -a -o gen/space -I$(ITF) -I$(COMP) $(COMP)/Space/*.idl

confix:
	ccmconfix -confix2 -o gen -pname "assembly-prototype2" -pversion "0.0.1"
	cd gen && confix2.py --bootstrap --configure --make --targets=check

manual: local confix

assembly:
	ccmtools c++assembly -o gen/space prototype1.idl prototype1.assembly

check: local assembly confix

clean:
	cd gen && confix2.py --make --targets=clean
	rm -rf gen

