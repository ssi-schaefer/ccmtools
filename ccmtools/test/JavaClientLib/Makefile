
all: component-test facet-test receptacle-test supports-test

component-test:
	make -C attribute_module_simple test-build
	make -C attribute_module_type test-build

facet-test:
	make -C facet_module_constants test-build
	make -C facet_module_exception test-build
	make -C facet_module_inheritance test-build
#	make -C facet_module_simple test-build
	make -C facet_module_types test-build
#	make -C facet_repeated_instantiation test-build

receptacle-test:
	make -C receptacle_module_constants test-build
	make -C receptacle_module_exception test-build
	make -C receptacle_module_inheritance test-build
#	make -C receptacle_module_multiple test-build	
	make -C receptacle_module_simple test-build
	make -C receptacle_module_types test-build
	make -C receptacle_repeated_instantiation test-build

supports-test:
	make -C supports_module_exception test-build
	make -C supports_module_simple test-build
	make -C supports_module_types test-build

