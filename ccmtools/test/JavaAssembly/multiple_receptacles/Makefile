all:
	ccmjava -app -iface -local *.idl
	ccmjava -assembly Outer.assembly *.idl
	javac `find -name '*.java'`
	rm `find -name '*.class'`

doc:
	javadoc -d doc `find wamas -name '*.java'`

clean:
	rm -rf wamas doc
