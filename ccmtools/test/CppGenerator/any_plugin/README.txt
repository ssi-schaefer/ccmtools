HOW TO USE THE ANY PLUGIN MECHANISM
===================================

The CCM Tools Any-PlugIn mechanism is used to parameterize the IDL to C++
mapping. This is useful for reusing existing C structures as part of component
interfaces without explicit C/C++ converters.

1. How it works
---------------
To use existing C structures as IDL interface parameters, we have to
define a new IDL any type:

IDL:
    typedef any PDL_Person;

Now we can use the IDL type Person as a parameter in every IDL interface
operation or attribute.

In addition to the input IDL files, we need an AnyMapping list which is actually
a file that contains a list of IDL to C structure mapping definitions 
(one per line).

Finally, we call ccmtools with the --anytypes=<AnyMappingList> parameter to
map all IDL and 'known any types' to C++:

C++:
namespace ccm
{
    namespace local
    {
        typedef ::Person PDL_Person;
    }	
}

