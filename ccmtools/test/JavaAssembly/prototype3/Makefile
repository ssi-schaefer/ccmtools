all:
	ccmjava -o xxx -app -iface -local *.idl
	ccmjava -o xxx -assembly *.assembly *.idl
	cd xxx && javac `find . -name '*.java'`
	rm `find xxx -name '*.class'`

doc:
	cd xxx && javadoc -d doc `find . -name '*.java'`

clean:
	rm -rf xxx

test: clean all
	rm -rf xxx
