all: idl

idl:
	jidl -I${CCM_INSTALL}/include:. -d ./src *.idl
	javac -classpath ../build:$CLASSPATH -d ../build src/*.java

clean:
	rm -rf src/*

# Note that jidl is a shell-script (originally idl) shipping with JacORB