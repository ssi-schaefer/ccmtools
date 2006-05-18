all: component-test facet-test receptacle-test supports-test

component-test:
	make -C component_attributes test
	make -C component_dynamic_connect test
	make -C component_dynamic_provide test
	make -C component_assembly_nested test
	make -C build_from_install

facet-test:
	make -C facet_module_attributes test
	make -C facet_module_constants test
	make -C facet_module_exception test
	make -C facet_module_inheritance test
	make -C facet_module_simple test
	make -C facet_module_types test

receptacle-test:
	make -C receptacle_module_attributes test
	make -C receptacle_module_constants test
	make -C receptacle_module_exception test
	make -C receptacle_module_inheritance test
	make -C receptacle_module_simple test
	make -C receptacle_module_types test
	make -C receptacle_multiple test

supports-test:
	make -C supports_attributes test
	make -C supports_constants test
	make -C supports_exceptions test
	make -C supports_inheritance test
	make -C supports_simple test
	make -C supports_types test
