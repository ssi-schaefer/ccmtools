//
// $Id$
//

#ifndef wx_utils_code_hashset_h
#define wx_utils_code_hashset_h


#ifdef HAVE_CONFIG_H
#  include <config.h>
#endif

#ifdef HAVE_HASH_MAP
#  include <ext/hash_set>
#else
#  include <set>
#endif


namespace wamas {
namespace platform {
namespace utils {


/**
@brief Hash-set; a normal set for older C++ compilers.
*/
template< typename VALUE, typename HASHFUNC >
class HashSet
#ifdef HAVE_HASH_MAP
    : public HASH_MAP_NAMESPACE::hash_set<VALUE, HASHFUNC>
{
public:
    explicit HashSet(std::size_t n)
    : HASH_MAP_NAMESPACE::hash_set<VALUE, HASHFUNC>(n)
    {}

    HashSet() {}
};
#else
    : public std::set<VALUE>
{
public:
    explicit HashSet(std::size_t) {}
    HashSet() {}
};
#endif


} // /namespace
} // /namespace
} // /namespace

#endif
