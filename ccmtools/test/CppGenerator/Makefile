

all: attribute

attribute:
	make -C attribute_types test

supports:
	make -C supports_attribute test
	make -C supports_exception test
	make -C supports_inheritance test
	make -C supports_types test

facet:
	make -C facet_attribute test
	make -C facet_exception test
	make -C facet_inheritance test
	make -C facet_module_exception test
	make -C facet_module_types test
	make -C facet_rename test
	make -C facet_types test

receptacle:
	make -C receptacle_attribute test
	make -C receptacle_exception test
	make -C receptacle_inheritance test
	make -C receptacle_multiple test
	make -C receptacle_not_connected test
	make -C receptacle_object test
	make -C receptacle_types test

home:
	make -C home_exception test
	make -C home_types test

include:
	make -C include_nested test

module:
	make -C module_mixed test
	make -C module_nested test
	make -C module_reopen test

assembly:
	make -C assembly_nested test
