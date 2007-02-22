IDL_INCLUDE=-Igen/idl/interface -Igen/idl/component
WAMAS_IDL=gen/idl/component/wamas

all: local

idl:
	ccmidl -idl3 -o gen/idl *.idl

local: idl
	ccmtools c++local ${IDL_INCLUDE} -a -o gen/interfaces `find gen/idl/interface -name '*.idl'`
	ccmtools c++local ${IDL_INCLUDE} -a -o gen/io ${WAMAS_IDL}/io/*.idl
	ccmtools c++local ${IDL_INCLUDE} -a -o gen/system ${WAMAS_IDL}/system/*.idl
	ccmtools c++local ${IDL_INCLUDE} -a -o gen/helpers ${WAMAS_IDL}/helpers/*.idl
	ccmtools c++assembly -o gen/helpers stderr_logger.idl stderr_logger.assembly

check: local
	ccmconfix -confix2 -o gen -pname "assembly-sample_logger" -pversion "0.0.1"
	cd gen && confix2.py --bootstrap --configure --make

clean:
	cd gen && confix2.py --make --targets=clean
	rm -rf gen
