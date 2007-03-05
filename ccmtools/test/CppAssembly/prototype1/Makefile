ITF="gen/idl/interface"
COMP="gen/idl/component"
SPACE_GEN="space/GEN_ccmtools_local_Space"

all: local

local:
	ccmidl -idl3 -o gen/idl prototype1.idl
	ccmtools c++local -a -o gen/itf -I$(ITF) `find $(ITF) -name '*.idl'`
	ccmtools c++local -a -o gen/world -I$(ITF) -I$(COMP) $(COMP)/World/*.idl
	ccmtools c++local -a -o gen/space -I$(ITF) -I$(COMP) $(COMP)/Space/*.idl

copy:
	cp -p impl/space/*.h impl/space/*.cc gen/space/
	cp -p impl/$(SPACE_GEN)/*.h impl/$(SPACE_GEN)/*.cc gen/$(SPACE_GEN)/

check: local copy
	ccmconfix -confix2 -o gen -pname "assembly-prototype2" -pversion "0.0.1"
	cd gen && confix2.py --bootstrap --configure --make --targets=check

clean:
	cd gen && confix2.py --make --targets=clean
	rm -rf gen

