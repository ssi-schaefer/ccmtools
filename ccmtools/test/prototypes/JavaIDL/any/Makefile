
all: check 

stubs:
	ccmtools-idl -java -o src-gen anyTest.idl

check: stubs
	ant check

clean: 
	ant clean
	rm -rf src-gen