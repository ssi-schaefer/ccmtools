

all: attribute

attribute:
	cd attribute_types && make test

supports:
	cd supports_attribute && make test
	cd supports_exception && make test
	cd supports_inheritance && make test
	cd supports_types && make test

facet:
	cd facet_attribute && make test
	cd facet_exception && make test
	cd facet_inheritance && make test
	cd facet_module_exception && make test
	cd facet_module_types && make test
	cd facet_rename && make test
	cd facet_types && make test

receptacle:
	cd receptacle_attribute && make test
	cd receptacle_exception && make test
	cd receptacle_inheritance && make test
	cd receptacle_multiple && make test
	cd receptacle_not_connected && make test
	cd receptacle_object && make test
	cd receptacle_types && make test

home:
	cd home_exception && make test
	cd home_types && make test

include:
	cd include_nested && make test

module:
	cd module_mixed && make test
	cd module_nested && make test
	cd module_reopen && make test

assembly:
	cd assembly_nested && make test
