
all:


comp:
	make -C Components

stubs: 
	make -C idl2

build: comp stubs
	javac -classpath ./build:$CLASSPATH -d build src/*.java

# jaco is a shell-script shipping with jacORB
run:
	jaco -classpath ./build:$CLASSPATH Client

clean:
	rm -rf build/*