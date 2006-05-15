all: component facet receptacle supports

component:
	make -C component_attributes test
	make -C component_dynamic_connect test 
	make -C component_dynamic_provide test

facet:
	make -C facet_attributes test
	make -C facet_constants test
	make -C facet_exceptions test
	make -C facet_inheritance test
	make -C facet_simple test
	make -C facet_types test

receptacle:
	make -C receptacle_attributes test
	make -C receptacle_constants test
	make -C receptacle_exceptions test
	make -C receptacle_inheritance test
	make -C receptacle_multiple test
	make -C receptacle_simple test
	make -C receptacle_types test

supports:
	make -C supports_attributes test
	make -C supports_constants test
	make -C supports_exceptions test
	make -C supports_inheritance test
	make -C supports_simple test
	make -C supports_types test
