all:
	ccmjava -iface -local -app -o gen *.idl
	ccmjava -assembly -o gen *.assembly *.idl
	cp ConsolestderrImpl.java gen/wamas/system
	cp OutputStreamLoggerloggerImpl.java gen/wamas/io
	cp Main.java gen/wamas
	cd gen && javac `find . -name '*.java'`
	cd gen && java wamas.Main

clean:
	rm -rf gen

test: clean all
	rm -rf gen
