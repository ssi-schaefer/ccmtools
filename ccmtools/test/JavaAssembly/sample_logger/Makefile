all:
	ccmjava -iface -local *.idl
	ccmjava -assembly *.assembly *.idl
	javac `find -name '*.java'`
	rm `find -name '*.class'`

setup:
	rm -rf wamas
	ccmjava -app *.idl
