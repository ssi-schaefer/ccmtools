//
// $Id$
//

#include "StringMap.h"

namespace wamas {
namespace platform {
namespace utils {


std::size_t StringHash::operator()(std::string const& s) const
{
#ifdef HAVE_HASH_MAP
    HASH_MAP_NAMESPACE::hash<const char*> H;
    return H(s.c_str());
#else
    unsigned long h=0;
    for(std::string::const_iterator it=s.begin(); it!=s.end(); ++it)
    {
        h = 5*h + *it;
    }
    return std::size_t(h);
#endif
}


} // /namespace
} // /namespace
} // /namespace
