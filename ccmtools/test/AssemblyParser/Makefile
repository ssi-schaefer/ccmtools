all: test1

parser:
	./build-parser.sh

test1: parser
	./parser.sh test1.assembly > test1.1
	./parser.sh test1.1 > test1.2
	diff test1.1 test1.2
	rm test1.1 test1.2
