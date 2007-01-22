//
// $Id$
//

#ifndef wx_utils_code_hashmap_h
#define wx_utils_code_hashmap_h


#ifdef HAVE_CONFIG_H
#  include <config.h>
#endif

#ifdef HAVE_HASH_MAP
#  include <ext/hash_map>
#else
#  include <map>
#endif


namespace wamas {
namespace platform {
namespace utils {


/**
@brief Hash-map; a normal map for older C++ compilers.
*/
template< typename KEY, typename VALUE, typename HASHFUNC >
class HashMap
#ifdef HAVE_HASH_MAP
    : public HASH_MAP_NAMESPACE::hash_map<KEY, VALUE, HASHFUNC>
{
public:
    explicit HashMap(std::size_t n)
    : HASH_MAP_NAMESPACE::hash_map<KEY, VALUE, HASHFUNC>(n)
    {}
    
    HashMap() {}
};
#else
    : public std::map<KEY, VALUE>
{
public:
    explicit HashMap(std::size_t) {}
    HashMap() {}
};
#endif


/**
@brief Hash-multi-map; a normal multi-map for older C++ compilers.
*/
template< typename KEY, typename VALUE, typename HASHFUNC >
class HashMultiMap
#ifdef HAVE_HASH_MAP
    : public HASH_MAP_NAMESPACE::hash_multimap<KEY, VALUE, HASHFUNC>
{
public:
    explicit HashMultiMap(std::size_t n)
    : HASH_MAP_NAMESPACE::hash_multimap<KEY, VALUE, HASHFUNC>(n)
    {}
    
    HashMultiMap() {}
};
#else
    : public std::multimap<KEY, VALUE>
{
public:
    explicit HashMultiMap(std::size_t) {}
    HashMultiMap() {}
};
#endif


} // /namespace
} // /namespace
} // /namespace

#endif
