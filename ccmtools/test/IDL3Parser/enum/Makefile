
all: idl3

idl3: 
	ccmparser DayOfWeek.idl
	ccmparser Color.idl
	ccmparser Empty.idl

check:
	ccmidl -idl3 -I../ -o xxx Color.idl
	idl Color.idl
	cd  xxx; idl Color.idl; cd ..
	cmp Color.h xxx/Color.h
	rm -rf *.cc *.h xxx/
