
all: uml local dbc 

uml: DbCTest.xml.zip
	uml2idl DbCTest.xml.zip DbCTest

local: uml
	ccmtools c++local -a -o component DbCTest.idl
	ccmtools c++local-test -o component DbCTest.idl

dbc: 
	ccmtools c++dbc -o component DbCTest.idl

check: 
	cp impl/Makefile.py component
	cp impl/DbCTest_basic_impl.cc component/impl/
	cp impl/_check_CCM_Local_CCM_Session_DbCTest.cc component/test/
	confix.py --packageroot=`pwd`/component --bootstrap --configure --make --targets=check


clean: 
	rm -rf component
