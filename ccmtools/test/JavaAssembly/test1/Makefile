all:
	ccmjava -app -iface -local test1.idl
	ccmjava -assembly test1.assembly test1.idl
	cp ../prototype/wamas/Main.java wamas/
	javac `find -name '*.java'`
	java wamas.Main
	rm `find -name '*.class'`

doc:
	javadoc -d doc `find wamas -name '*.java'`

clean:
	rm -rf wamas doc

test: clean all
	rm -rf wamas
