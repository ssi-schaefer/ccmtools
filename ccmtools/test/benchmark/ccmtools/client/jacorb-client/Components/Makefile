
all: idl

idl:
	jidl -I. -d ./src CCM.idl
	cp ConfigValueImpl.java src/Components/
	cp CookieImpl.java src/Components/
	javac -d ../build src/Components/*.java

clean:
	rm -rf src/Components

# Note that jidl is a shell-script (originally idl) shipping with JacORB