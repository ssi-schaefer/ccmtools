all: clean-stamps stamp.attribute

clean-stamps:
	rm -f stamp.*

clean-xxx:
	find . -name xxx | xargs rm -rf

full: clean-xxx\
	stamp.attribute\
	stamp.supports\
	stamp.facet\
	stamp.receptacle\
	stamp.home\
	stamp.include\
	stamp.module\
	stamp.assembly

stamp.attribute:
	make -C attribute_types test
	touch stamp.attribute

stamp.supports:
	make -C supports_attribute test
	make -C supports_exception test
	make -C supports_inheritance test
	make -C supports_types test
	touch stamp.supports

stamp.facet:
	make -C facet_attribute test
	make -C facet_exception test
	make -C facet_inheritance test
	make -C facet_module_exception test
#	make -C facet_module_types test
	make -C facet_rename test
	make -C facet_types test
	make -C facet_constants test
	touch stamp.facet

stamp.receptacle:
	make -C receptacle_attribute test
	make -C receptacle_exception test
	make -C receptacle_inheritance test
	make -C receptacle_multiple test
	make -C receptacle_not_connected test
	make -C receptacle_object test
	make -C receptacle_types test
	touch stamp.receptacle

stamp.home:
	make -C home_exception test
	make -C home_types test
	touch stamp.home

stamp.include:
	make -C include_nested test
	touch stamp.include

stamp.module:
#	make -C module_mixed test
	make -C module_nested test
#	make -C module_reopen test
	touch stamp.module

stamp.assembly:
	make -C assembly_nested test
	touch stamp.assembly

