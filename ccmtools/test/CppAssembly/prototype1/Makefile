ITF="gen/idl/interface"
COMP="gen/idl/component"

all: local

local:
	ccmidl -idl3 -o gen/idl prototype1.idl
	ccmtools c++local -a -o gen/world -I$(ITF) -I$(COMP) $(COMP)/World/*.idl
	ccmtools c++local -a -o gen/space -I$(ITF) -I$(COMP) $(COMP)/Space/*.idl
